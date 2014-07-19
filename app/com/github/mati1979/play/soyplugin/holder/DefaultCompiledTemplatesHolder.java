package com.github.mati1979.play.soyplugin.holder;

import com.github.mati1979.play.soyplugin.compile.EmptyTofuCompiler;
import com.github.mati1979.play.soyplugin.compile.TofuCompiler;
import com.github.mati1979.play.soyplugin.config.SoyViewConf;
import com.github.mati1979.play.soyplugin.template.EmptyTemplateFilesResolver;
import com.github.mati1979.play.soyplugin.template.TemplateFilesResolver;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.template.soy.tofu.SoyTofu;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: mati
 * Date: 02/11/2013
 * Time: 14:04
 */
public class DefaultCompiledTemplatesHolder implements CompiledTemplatesHolder {

    private static final play.Logger.ALogger logger = play.Logger.of(DefaultCompiledTemplatesHolder.class);

    private TofuCompiler tofuCompiler = new EmptyTofuCompiler();

    private TemplateFilesResolver templatesFileResolver = new EmptyTemplateFilesResolver();

    private Cache<String, Optional<SoyTofu>> cache = CacheBuilder.newBuilder().build();

    private SoyViewConf soyViewConf = null;

    private final static String TEMPLATES_KEY = "templates";

    public DefaultCompiledTemplatesHolder(final TofuCompiler tofuCompiler,
                                          final TemplateFilesResolver templatesFileResolver,
                                          final SoyViewConf soyViewConf) throws IOException {
        this.tofuCompiler = tofuCompiler;
        this.templatesFileResolver = templatesFileResolver;
        this.soyViewConf = soyViewConf;
        this.cache = CacheBuilder.newBuilder().maximumSize(1).expireAfterWrite(soyViewConf.globalHotReloadCompileTimeInSecs(), TimeUnit.SECONDS).build();
        this.cache.put(TEMPLATES_KEY, Optional.empty());
        init();
    }

    public Optional<SoyTofu> compiledTemplates() throws IOException {
        if (shouldCompileTemplates()) {
            if (cache.getIfPresent(TEMPLATES_KEY) != null && cache.getIfPresent(TEMPLATES_KEY).isPresent()) {
                return cache.getIfPresent(TEMPLATES_KEY);
            }
            final Stopwatch stopwatch = Stopwatch.createStarted();
            logger.debug("Compiling templates...");
            cache.put(TEMPLATES_KEY, Optional.ofNullable(compileTemplates()));
            stopwatch.stop();
            logger.info(String.format("Compiling templates, took: %d ms", stopwatch.elapsed(TimeUnit.MILLISECONDS)));
        }

        return cache.getIfPresent(TEMPLATES_KEY);
    }

    private boolean shouldCompileTemplates() {
        return soyViewConf.globalHotReloadMode() || !cache.getIfPresent(TEMPLATES_KEY).isPresent();
    }

    public void init() throws IOException {
        logger.debug("TemplatesHolder init...");
        if (soyViewConf.compilePrecompileTemplates()) {
            logger.info("Precompilation of soy templates...");
            cache.put(TEMPLATES_KEY, Optional.ofNullable(compileTemplates()));
        }
    }

    private SoyTofu compileTemplates() throws IOException {
        Preconditions.checkNotNull(templatesFileResolver, "templatesRenderer cannot be null!");
        Preconditions.checkNotNull(tofuCompiler, "tofuCompiler cannot be null!");

        final Collection<URL> templateFiles = templatesFileResolver.resolve();
        if (templateFiles.size() > 0) {
            logger.debug("Compiling templates, no:{}", templateFiles.size());

            return tofuCompiler.compile(templateFiles);
        }

        throw new IOException("0 template files have been found, check your templateFilesResolver!");
    }

}
