package controllers;

import com.github.mati1979.play.soyplugin.ajax.auth.AuthManager;
import com.github.mati1979.play.soyplugin.ajax.auth.PermissableAuthManager;
import com.github.mati1979.play.soyplugin.ajax.process.OutputProcessor;
import com.github.mati1979.play.soyplugin.ajax.utils.I18nUtils;
import com.github.mati1979.play.soyplugin.ajax.utils.PathUtils;
import com.github.mati1979.play.soyplugin.bundle.EmptySoyMsgBundleResolver;
import com.github.mati1979.play.soyplugin.bundle.SoyMsgBundleResolver;
import com.github.mati1979.play.soyplugin.compile.EmptyTofuCompiler;
import com.github.mati1979.play.soyplugin.compile.TofuCompiler;
import com.github.mati1979.play.soyplugin.locale.EmptyLocaleProvider;
import com.github.mati1979.play.soyplugin.locale.LocaleProvider;
import com.github.mati1979.play.soyplugin.template.EmptyTemplateFilesResolver;
import com.github.mati1979.play.soyplugin.template.TemplateFilesResolver;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.template.soy.msgs.SoyMsgBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.annotation.concurrent.ThreadSafe;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static com.github.mati1979.play.soyplugin.config.PlayConfAccessor.*;

@ThreadSafe
public class SoyAjaxController extends Controller {

    private final static int DEF_CACHE_MAX_SIZE = 10000;

    private final static String DEF_TIME_UNIT = "DAYS";

    private final static int DEF_EXPIRE_AFTER_WRITE = 1;

    private static final Logger logger = LoggerFactory.getLogger(SoyAjaxController.class);

    private String cacheControl = AJAX_CACHE_CONTROL;

    private String expireHeaders = AJAX_EXPIRE_HEADERS;

    /** maximum number of entries this cache will hold */
    private int cacheMaxSize = DEF_CACHE_MAX_SIZE;

    /** number of time units after which once written entries will expire */
    private int expireAfterWrite = DEF_EXPIRE_AFTER_WRITE;

    /** String used to denote a TimeUnit */
    private String expireAfterWriteUnit = DEF_TIME_UNIT;

    /**
     * This is a compiled to javascript cache (compiled soy templates), which consists of key: hash
     * and as a value we have a Map<String,String>
     * In this map, a key is an array to path, example: server-time,client-words and value:
     * is a String with compiled template.
     * To prevent DDOS attack we model the first cache as a limited cache with maximum number of entries
     * and also an expire after write to cache
     */
    private Cache<String, Map<String,String>> cachedJsTemplates = CacheBuilder.newBuilder()
            .expireAfterWrite(expireAfterWrite, TimeUnit.valueOf(expireAfterWriteUnit))
            .maximumSize(cacheMaxSize)
            .concurrencyLevel(1) //look up a constant class, 1 is not very clear
            .build();

    private TemplateFilesResolver templateFilesResolver = new EmptyTemplateFilesResolver();

    private TofuCompiler tofuCompiler = new EmptyTofuCompiler();

    private SoyMsgBundleResolver soyMsgBundleResolver = new EmptySoyMsgBundleResolver();

    private LocaleProvider localeProvider = new EmptyLocaleProvider();

    /**
     * whether debug is on or off, if it is on then caching of entries will not work
     * to support hot reloading while developing, if it is on, then we assume it is
     * like production mode and caching of compiled soy to JavaScript templates
     * will be working. In addition in production mode (debug off)
     * CacheControl (Http 1.1) and Expires (Http 1.0) http headers
     * will be set to user configured values.
     */
    private boolean hotReloadMode = GLOBAL_HOT_RELOAD_MODE;

    /**
     * character encoding, by default utf-8
     */
    private String encoding = GLOBAL_ENCODING;

    /**
     * List of output processors, output processors typically perform obfuscation
     * of generated JavaScript code
     */
    private List<OutputProcessor> outputProcessors = new ArrayList<OutputProcessor>();

    /**
     * By default there is no AuthManager and an external user can compile all templates to JavaScript
     * This can pose security risk and therefore it is possible to change this and inject
     * an AuthManager implementation that will only allow to compile those templates that a developer agreed to.
     */

    private AuthManager authManager = new PermissableAuthManager();

    public SoyAjaxController() {
    }

    public SoyAjaxController(AuthManager authManager,
                             LocaleProvider localeProvider,
                             SoyMsgBundleResolver soyMsgBundleResolver,
                             TofuCompiler tofuCompiler,
                             TemplateFilesResolver templateFilesResolver,
                             List<OutputProcessor> outputProcessors) {
        this.authManager = authManager;
        this.localeProvider = localeProvider;
        this.soyMsgBundleResolver = soyMsgBundleResolver;
        this.tofuCompiler = tofuCompiler;
        this.templateFilesResolver = templateFilesResolver;
        this.outputProcessors = outputProcessors;
    }

    public void init() {
        this.cachedJsTemplates = CacheBuilder.newBuilder()
                .expireAfterWrite(expireAfterWrite, TimeUnit.valueOf(expireAfterWriteUnit))
                .maximumSize(cacheMaxSize)
                .concurrencyLevel(1) //look up a constant class, 1 is not very clear
                .build();
    }

    public Result compile(final String hash,
                          final List<String> file,
                          final String locale,
                          final boolean disableProcessors) throws IOException {
        return compileJs(file.toArray(new String[0]), hash, new Boolean(disableProcessors).booleanValue(), locale);
    }

    private Result compileJs(final String[] templateFileNames,
                             final String hash,
                             final boolean disableProcessors,
                             final String locale
    ) {
        Preconditions.checkNotNull(templateFilesResolver, "templateFilesResolver cannot be null");

        try {
            if (isHotReloadModeOff()) {
                final Optional<String> template = extractAndCombineAll(hash, templateFileNames);
                if (template.isPresent()) {
                    return prepareResponseFor(template.get(), disableProcessors);
                }
            }

            final Map<URL,String> compiledTemplates = compileTemplates(templateFileNames, request(), locale);
            final Optional<String> allCompiledTemplates = concatCompiledTemplates(compiledTemplates);
            if (!allCompiledTemplates.isPresent()) {
                return notFound("Template file(s) could not be resolved.");
            }
            if (isHotReloadModeOff()) {
                synchronized (cachedJsTemplates) {
                    Map<String, String> map = cachedJsTemplates.getIfPresent(hash);
                    if (map == null) {
                        map = new ConcurrentHashMap<String, String>();
                    } else {
                        map.put(PathUtils.arrayToPath(templateFileNames), allCompiledTemplates.get());
                    }
                    this.cachedJsTemplates.put(hash, map);
                }
            }

            return prepareResponseFor(allCompiledTemplates.get(), disableProcessors);
        } catch (final Exception ex) {
            return internalServerError(ex.getMessage());
        }
    }

    private Optional<String> extractAndCombineAll(final String hash, final String[] templateFileNames) throws IOException {
        synchronized (cachedJsTemplates) {
            final Map<String, String> map = cachedJsTemplates.getIfPresent(hash);
            if (map != null) {
                final String template = map.get(PathUtils.arrayToPath(templateFileNames));

                return Optional.fromNullable(template);
            }
        }

        return Optional.absent();
    }

    private Map<URL,String> compileTemplates(final String[] templateFileNames, final Http.Request request, final String locale) throws Exception {
        final HashMap<URL,String> map = new HashMap<URL,String>();
        for (final String templateFileName : templateFileNames) {
            try {
                final Optional<URL> templateUrl = templateFilesResolver.resolve(templateFileName);
                if (!templateUrl.isPresent()) {
                    throw new Exception("File not found:" + templateFileName);
                }
                if (!authManager.isAllowed(templateFileName)) {
                    throw new Exception("no permission to compile:" + templateFileName);
                }
                logger.debug("Compiling JavaScript template:" + templateUrl.orNull());
                final Optional<String> templateContent = compileTemplateAndAssertSuccess(request, templateUrl, locale);
                if (!templateContent.isPresent()) {
                    throw new Exception("file cannot be compiled:" + templateUrl);
                }

                map.put(templateUrl.get(), templateContent.get());
            } catch (final IOException e) {
                throw new Exception("Unable to find file:" + templateFileName + ".soy");
            }
        }

        return map;
    }

    private Optional<String> concatCompiledTemplates(final Map<URL,String> compiledTemplates) throws IOException, SecurityException {
        if (compiledTemplates.isEmpty()) {
            return Optional.absent();
        }

        final StringBuilder allJsTemplates = new StringBuilder();

        for (final String compiledTemplate : compiledTemplates.values()) {
            allJsTemplates.append(compiledTemplate);
        }

        return Optional.of(allJsTemplates.toString());
    }

    private Result prepareResponseFor(final String templateContent, final boolean disableProcessors) throws IOException {
        response().getHeaders().put("Content-Type", "text/javascript; charset=" + encoding);
        response().getHeaders().put("Cache-Control", hotReloadMode ? "no-cache" : cacheControl);

        if (StringUtils.hasText(expireHeaders) && !hotReloadMode) {
            response().getHeaders().put("Expires", expireHeaders);
        }

        if (disableProcessors) {
            return ok(templateContent);
        }

        String processTemplate = templateContent;
        try {
            for (final OutputProcessor outputProcessor : outputProcessors) {
                final StringWriter writer = new StringWriter();
                outputProcessor.process(new StringReader(templateContent), writer);
                processTemplate = writer.getBuffer().toString();
            }

            return ok(processTemplate);
        } catch (final Exception ex) {
            logger.warn("Unable to process template", ex);
            return internalServerError("Unable to process template!");
        }
    }

    private Optional<String> compileTemplateAndAssertSuccess(final Http.Request request, final Optional<URL> templateFile, final String locale) throws IOException {
        Preconditions.checkNotNull(localeProvider, "localeProvider cannot be null");
        Preconditions.checkNotNull(soyMsgBundleResolver, "soyMsgBundleResolver cannot be null");
        Preconditions.checkNotNull(tofuCompiler, "tofuCompiler cannot be null");

        if (!templateFile.isPresent()) {
            return Optional.absent();
        }

        Optional<Locale> localeOptional = Optional.fromNullable(I18nUtils.getLocaleFromString(locale));
        if (!localeOptional.isPresent()) {
            localeOptional = localeProvider.resolveLocale(request);
        }

        final Optional<SoyMsgBundle> soyMsgBundle = soyMsgBundleResolver.resolve(localeOptional);
        final Optional<String> compiledTemplate = tofuCompiler.compileToJsSrc(templateFile.orNull(), soyMsgBundle.orNull());

        return compiledTemplate;
    }

    private boolean isHotReloadMode() {
        return hotReloadMode;
    }

    private boolean isHotReloadModeOff() {
        return !hotReloadMode;
    }

    public void setCacheControl(final String cacheControl) {
        this.cacheControl = cacheControl;
    }

    public void setTemplateFilesResolver(final TemplateFilesResolver templateFilesResolver) {
        this.templateFilesResolver = templateFilesResolver;
    }

    public void setTofuCompiler(final TofuCompiler tofuCompiler) {
        this.tofuCompiler = tofuCompiler;
    }

    public void setSoyMsgBundleResolver(final SoyMsgBundleResolver soyMsgBundleResolver) {
        this.soyMsgBundleResolver = soyMsgBundleResolver;
    }

    public void setLocaleProvider(final LocaleProvider localeProvider) {
        this.localeProvider = localeProvider;
    }

    public void setHotReloadMode(final boolean hotReloadMode) {
        this.hotReloadMode = hotReloadMode;
    }

    public void setEncoding(final String encoding) {
        this.encoding = encoding;
    }

    public void setExpireHeaders(final String expiresHeaders) {
        this.expireHeaders = expiresHeaders;
    }

    public void setOutputProcessors(List<OutputProcessor> outputProcessors) {
        this.outputProcessors = outputProcessors;
    }

    public void setAuthManager(AuthManager authManager) {
        this.authManager = authManager;
    }

    public void setCacheMaxSize(int cacheMaxSize) {
        this.cacheMaxSize = cacheMaxSize;
    }

    public void setExpireAfterWrite(int expireAfterWrite) {
        this.expireAfterWrite = expireAfterWrite;
    }

    public void setExpireAfterWriteUnit(String expireAfterWriteUnit) {
        this.expireAfterWriteUnit = expireAfterWriteUnit;
    }

}
