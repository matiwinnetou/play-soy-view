package com.github.mati1979.play.soyplugin.ajax.config;

import com.github.mati1979.play.soyplugin.ajax.url.DefaultTemplateUrlComposer;
import com.github.mati1979.play.soyplugin.ajax.url.TemplateUrlComposer;
import com.github.mati1979.play.soyplugin.bundle.SoyMsgBundleResolver;
import com.github.mati1979.play.soyplugin.compile.TofuCompiler;
import com.github.mati1979.play.soyplugin.config.ConfigDefaults;
import com.github.mati1979.play.soyplugin.config.ConfigKeys;
import com.github.mati1979.play.soyplugin.locale.LocaleProvider;
import com.github.mati1979.play.soyplugin.spring.PlaySoyConfig;
import com.github.mati1979.play.soyplugin.template.TemplateFilesResolver;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import controllers.SoyAjaxController;
import com.github.mati1979.play.soyplugin.ajax.auth.AuthManager;
import com.github.mati1979.play.soyplugin.ajax.auth.ConfigurableAuthManager;
import com.github.mati1979.play.soyplugin.ajax.auth.PermissableAuthManager;
import com.github.mati1979.play.soyplugin.ajax.hash.HashFileGenerator;
import com.github.mati1979.play.soyplugin.ajax.hash.MD5HashFileGenerator;
import com.github.mati1979.play.soyplugin.ajax.process.OutputProcessor;
import com.github.mati1979.play.soyplugin.ajax.process.google.GoogleClosureOutputProcessor;
import play.Play;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: mati
 * Date: 12/11/2013
 * Time: 22:15
 */
@Configuration
@Import(PlaySoyConfig.class)
public class PlaySoyViewAjaxConfig {

    private boolean hotReloadMode = Play.application().configuration().getBoolean(ConfigKeys.GLOBAL_HOT_RELOAD_MODE, ConfigDefaults.GLOBAL_HOT_RELOAD_MODE);

    private String encoding = Play.application().configuration().getString(ConfigKeys.GLOBAL_CHARSET_ENCODING, ConfigDefaults.GLOBAL_CHARSET_ENCODING);

    private boolean ajaxSecurityEnabled = Play.application().configuration().getBoolean(ConfigKeys.AJAX_SECURITY_ENABLED, ConfigDefaults.AJAX_SECURITY_ENABLED);

    private String ajaxAllowedUrls = Play.application().configuration().getString(ConfigKeys.AJAX_ALLOWED_URLS, ConfigDefaults.AJAX_ALLOWED_URLS);

    private String cacheControl = Play.application().configuration().getString(ConfigKeys.AJAX_CACHE_CONTROL, ConfigDefaults.AJAX_CACHE_CONTROL);

    private String expireHeaders = Play.application().configuration().getString(ConfigKeys.AJAX_CACHE_CONTROL, ConfigDefaults.AJAX_EXPIRE_HEADERS);

    @Bean
    public HashFileGenerator soyMd5HashFileGenerator() {
        final MD5HashFileGenerator md5HashFileGenerator = new MD5HashFileGenerator();
        md5HashFileGenerator.setHotReloadMode(hotReloadMode);

        return md5HashFileGenerator;
    }

    @Bean
    public TemplateUrlComposer soyTemplateUrlComposer(final TemplateFilesResolver templateFilesResolver, HashFileGenerator hashFileGenerator) {
        return new DefaultTemplateUrlComposer(templateFilesResolver, hashFileGenerator);
    }

    @Bean
    public SoyAjaxController soyAjaxController(final AuthManager authManager,
                                               final LocaleProvider localeProvider,
                                               final TemplateFilesResolver templateFilesResolver,
                                               final TofuCompiler tofuCompiler,
                                               final SoyMsgBundleResolver soyMsgBundleResolver) {
        final GoogleClosureOutputProcessor googleClosureOutputProcessor = new GoogleClosureOutputProcessor();
        googleClosureOutputProcessor.setEncoding(encoding);

        final SoyAjaxController soyAjaxController = new SoyAjaxController();
        soyAjaxController.setAuthManager(authManager);
        soyAjaxController.setEncoding(encoding);
        soyAjaxController.setHotReloadMode(hotReloadMode);
        soyAjaxController.setLocaleProvider(localeProvider);
        soyAjaxController.setOutputProcessors(Lists.<OutputProcessor>newArrayList(googleClosureOutputProcessor));
        if (cacheControl != null && !cacheControl.isEmpty()) {
            soyAjaxController.setCacheControl(cacheControl);
        }
        soyAjaxController.setTemplateFilesResolver(templateFilesResolver);
        soyAjaxController.setTofuCompiler(tofuCompiler);
        soyAjaxController.setSoyMsgBundleResolver(soyMsgBundleResolver);
        if (expireHeaders != null && !expireHeaders.isEmpty()) {
            soyAjaxController.setExpireHeaders(expireHeaders);
        }
        soyAjaxController.init();

        return soyAjaxController;
    }

    @Bean
    public AuthManager soyAuthManager() {
        if (ajaxSecurityEnabled && ajaxAllowedUrls != null && !ajaxAllowedUrls.isEmpty()) {
            final String[] split = ajaxAllowedUrls.split(",");
            return new ConfigurableAuthManager(Arrays.asList(split));
        }

        return new PermissableAuthManager();
    }

}
