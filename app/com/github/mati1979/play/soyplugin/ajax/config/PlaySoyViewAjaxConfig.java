package com.github.mati1979.play.soyplugin.ajax.config;

import com.github.mati1979.play.soyplugin.ajax.auth.AuthManager;
import com.github.mati1979.play.soyplugin.ajax.auth.ConfigurableAuthManager;
import com.github.mati1979.play.soyplugin.ajax.auth.PermissableAuthManager;
import com.github.mati1979.play.soyplugin.ajax.hash.HashFileGenerator;
import com.github.mati1979.play.soyplugin.ajax.hash.MD5HashFileGenerator;
import com.github.mati1979.play.soyplugin.ajax.process.OutputProcessor;
import com.github.mati1979.play.soyplugin.ajax.process.google.GoogleClosureOutputProcessor;
import com.github.mati1979.play.soyplugin.bundle.SoyMsgBundleResolver;
import com.github.mati1979.play.soyplugin.compile.TofuCompiler;
import com.github.mati1979.play.soyplugin.config.PlayConfAccessor;
import com.github.mati1979.play.soyplugin.locale.LocaleProvider;
import com.github.mati1979.play.soyplugin.spring.PlaySoyConfig;
import com.github.mati1979.play.soyplugin.template.TemplateFilesResolver;
import com.google.common.collect.Lists;
import com.github.mati1979.play.soyplugin.ajax.SoyAjaxController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Arrays;

import static com.github.mati1979.play.soyplugin.config.PlayConfAccessor.*;

/**
 * Created with IntelliJ IDEA.
 * User: mati
 * Date: 12/11/2013
 * Time: 22:15
 */
@Configuration
@Import(PlaySoyConfig.class)
public class PlaySoyViewAjaxConfig {

    @Bean
    public HashFileGenerator soyMd5HashFileGenerator() {
        final MD5HashFileGenerator md5HashFileGenerator = new MD5HashFileGenerator();
        md5HashFileGenerator.setHotReloadMode(GLOBAL_HOT_RELOAD_MODE);

        return md5HashFileGenerator;
    }

    @Bean
    public SoyAjaxController soyAjaxController(final AuthManager authManager,
                                               final LocaleProvider localeProvider,
                                               final TemplateFilesResolver templateFilesResolver,
                                               final TofuCompiler tofuCompiler,
                                               final SoyMsgBundleResolver soyMsgBundleResolver) {
        final GoogleClosureOutputProcessor googleClosureOutputProcessor = new GoogleClosureOutputProcessor();
        googleClosureOutputProcessor.setEncoding(GLOBAL_ENCODING);

        final SoyAjaxController soyAjaxController = new SoyAjaxController();
        soyAjaxController.setAuthManager(authManager);
        soyAjaxController.setEncoding(GLOBAL_ENCODING);
        soyAjaxController.setHotReloadMode(GLOBAL_HOT_RELOAD_MODE);
        soyAjaxController.setLocaleProvider(localeProvider);
        soyAjaxController.setOutputProcessors(Lists.<OutputProcessor>newArrayList(googleClosureOutputProcessor));
        if (AJAX_CACHE_CONTROL != null && !PlayConfAccessor.AJAX_CACHE_CONTROL.isEmpty()) {
            soyAjaxController.setCacheControl(AJAX_CACHE_CONTROL);
        }
        soyAjaxController.setTemplateFilesResolver(templateFilesResolver);
        soyAjaxController.setTofuCompiler(tofuCompiler);
        soyAjaxController.setSoyMsgBundleResolver(soyMsgBundleResolver);
        if (AJAX_EXPIRE_HEADERS != null && !AJAX_EXPIRE_HEADERS.isEmpty()) {
            soyAjaxController.setExpireHeaders(AJAX_EXPIRE_HEADERS);
        }
        soyAjaxController.init();

        return soyAjaxController;
    }

    @Bean
    public AuthManager soyAuthManager() {
        if (AJAX_SECURITY_ENABLED && AJAX_ALLOWED_URLS != null && !AJAX_ALLOWED_URLS.isEmpty()) {
            final String[] split = AJAX_ALLOWED_URLS.split(",");
            return new ConfigurableAuthManager(Arrays.asList(split));
        }

        return new PermissableAuthManager();
    }

}
