//package com.github.mati1979.play.soyplugin.ajax.config;
//
//import com.github.mati1979.play.soyplugin.ajax.SoyAjaxController;
//import com.github.mati1979.play.soyplugin.ajax.allowedurls.DefaultSoyAllowedUrlsResolver;
//import com.github.mati1979.play.soyplugin.ajax.allowedurls.SoyAllowedUrls;
//import com.github.mati1979.play.soyplugin.ajax.allowedurls.SoyAllowedUrlsResolver;
//import com.github.mati1979.play.soyplugin.ajax.auth.AuthManager;
//import com.github.mati1979.play.soyplugin.ajax.auth.ConfigurableAuthManager;
//import com.github.mati1979.play.soyplugin.ajax.auth.PermissableAuthManager;
//import com.github.mati1979.play.soyplugin.ajax.hash.HashFileGenerator;
//import com.github.mati1979.play.soyplugin.ajax.hash.MD5HashFileGenerator;
//import com.github.mati1979.play.soyplugin.ajax.process.OutputProcessor;
//import com.github.mati1979.play.soyplugin.ajax.process.google.GoogleClosureOutputProcessor;
//import com.github.mati1979.play.soyplugin.bundle.SoyMsgBundleResolver;
//import com.github.mati1979.play.soyplugin.compile.TofuCompiler;
//import com.github.mati1979.play.soyplugin.config.SoyViewConf;
//import com.github.mati1979.play.soyplugin.i18n.LocaleProvider;
//import com.github.mati1979.play.soyplugin.spring.PlaySoyConfig;
//import com.github.mati1979.play.soyplugin.template.TemplateFilesResolver;
//import com.google.common.collect.Lists;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//
///**
// * Created with IntelliJ IDEA.
// * User: mati
// * Date: 12/11/2013
// * Time: 22:15
// */
//@Configuration
//@Import(PlaySoyConfig.class)
//public class PlaySoyViewAjaxConfig {
//
//    @Bean(initMethod = "init")
//    public HashFileGenerator soyMd5HashFileGenerator(final SoyAllowedUrls soyAllowedUrls,
//                                                     final SoyViewConf soyViewConf) {
//        return new MD5HashFileGenerator(soyAllowedUrls, soyViewConf);
//    }
//
//    @Bean(initMethod = "init")
//    public SoyAjaxController soyAjaxController(final AuthManager authManager,
//                                               final LocaleProvider localeProvider,
//                                               final TemplateFilesResolver templateFilesResolver,
//                                               final TofuCompiler tofuCompiler,
//                                               final SoyMsgBundleResolver soyMsgBundleResolver,
//                                               final SoyViewConf soyViewConf) {
//        return new SoyAjaxController(authManager,
//                localeProvider,
//                soyMsgBundleResolver,
//                tofuCompiler,
//                templateFilesResolver,
//                Lists.<OutputProcessor>newArrayList(new GoogleClosureOutputProcessor(soyViewConf)),
//                soyViewConf
//        );
//    }
//
//    @Bean
//    public SoyAllowedUrlsResolver soyAllowedUrlsResolver(final TemplateFilesResolver templateFilesResolver,
//                                      final SoyViewConf soyViewConf) {
//        return new DefaultSoyAllowedUrlsResolver(templateFilesResolver, soyViewConf);
//    }
//
//    @Bean
//    public SoyAllowedUrls soyAllowedUrls(final SoyAllowedUrlsResolver soyAllowedUrlsResolver) {
//        return soyAllowedUrlsResolver.allowedUrls();
//    }
//
//    @Bean
//    public AuthManager soyAuthManager(final SoyAllowedUrls soyAllowedUrls) {
//        if (soyAllowedUrls.isEnabledSecurity()) {
//            return new ConfigurableAuthManager(soyAllowedUrls.urls());
//        }
//
//        return new PermissableAuthManager();
//    }
//
//}
