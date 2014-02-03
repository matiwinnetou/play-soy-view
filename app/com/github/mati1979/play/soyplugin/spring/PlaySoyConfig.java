package com.github.mati1979.play.soyplugin.spring;

import com.google.template.soy.jssrc.SoyJsSrcOptions;
import com.google.template.soy.tofu.SoyTofuOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.mati1979.play.soyplugin.bundle.DefaultSoyMsgBundleResolver;
import com.github.mati1979.play.soyplugin.bundle.SoyMsgBundleResolver;
import com.github.mati1979.play.soyplugin.compile.DefaultTofuCompiler;
import com.github.mati1979.play.soyplugin.compile.TofuCompiler;
import com.github.mati1979.play.soyplugin.config.ConfigKeys;
import com.github.mati1979.play.soyplugin.config.SoyViewConfigDefaults;
import com.github.mati1979.play.soyplugin.data.DefaultToSoyDataConverter;
import com.github.mati1979.play.soyplugin.data.ToSoyDataConverter;
import com.github.mati1979.play.soyplugin.global.compile.CompileTimeGlobalModelResolver;
import com.github.mati1979.play.soyplugin.global.compile.EmptyCompileTimeGlobalModelResolver;
import com.github.mati1979.play.soyplugin.global.runtime.EmptyGlobalRuntimeModelResolver;
import com.github.mati1979.play.soyplugin.global.runtime.GlobalRuntimeModelResolver;
import com.github.mati1979.play.soyplugin.holder.CompiledTemplatesHolder;
import com.github.mati1979.play.soyplugin.holder.DefaultCompiledTemplatesHolder;
import com.github.mati1979.play.soyplugin.locale.AcceptHeaderLocaleProvider;
import com.github.mati1979.play.soyplugin.locale.LocaleProvider;
import com.github.mati1979.play.soyplugin.plugin.DefaultSoy;
import com.github.mati1979.play.soyplugin.plugin.Soy;
import com.github.mati1979.play.soyplugin.render.DefaultTemplateRenderer;
import com.github.mati1979.play.soyplugin.render.TemplateRenderer;
import com.github.mati1979.play.soyplugin.template.DefaultTemplateFilesResolver;
import com.github.mati1979.play.soyplugin.template.TemplateFilesResolver;
import play.Play;

/**
 * Created by mati on 02/02/2014.
 */
@Configuration
public class PlaySoyConfig {

    @Bean
    public LocaleProvider soyLocaleProvider() {
        return new AcceptHeaderLocaleProvider();
    }

    @Bean
    public DefaultTemplateFilesResolver soyTemplateFilesResolver() throws Exception {
        return new DefaultTemplateFilesResolver();
    }

    @Bean
    public GlobalRuntimeModelResolver soyGlobalRuntimeModelResolver() {
        return new EmptyGlobalRuntimeModelResolver();
    }

    @Bean
    public CompileTimeGlobalModelResolver soyCompileTimeGlobalModelResolver() throws Exception {
        return new EmptyCompileTimeGlobalModelResolver();
    }

    @Bean
    public ToSoyDataConverter soyToSoyDataConverter() {
        return new DefaultToSoyDataConverter();
    }

    @Bean
    public SoyJsSrcOptions soyJsSourceOptions() {
        return new SoyJsSrcOptions();
    }

    @Bean
    public SoyTofuOptions soyTofuOptions() {
        final SoyTofuOptions soyTofuOptions = new SoyTofuOptions();
        soyTofuOptions.setUseCaching(Play.application().configuration().getBoolean(ConfigKeys.GLOBAL_HOT_RELOAD_MODE, SoyViewConfigDefaults.GLOBAL_HOT_RELOAD_MODE));

        return soyTofuOptions;
    }

    @Bean
    public TofuCompiler soyTofuCompiler(final CompileTimeGlobalModelResolver compileTimeGlobalModelResolver, final SoyJsSrcOptions soyJsSrcOptions, final SoyTofuOptions soyTofuOptions) {
        final DefaultTofuCompiler defaultTofuCompiler = new DefaultTofuCompiler();
        defaultTofuCompiler.setCompileTimeGlobalModelResolver(compileTimeGlobalModelResolver);
        defaultTofuCompiler.setSoyJsSrcOptions(soyJsSrcOptions);
        defaultTofuCompiler.setSoyTofuOptions(soyTofuOptions);

        return defaultTofuCompiler;
    }

    @Bean
    public SoyMsgBundleResolver soyMsgBundleResolver() {
        return new DefaultSoyMsgBundleResolver();
    }

    @Bean
    public CompiledTemplatesHolder soyTemplatesHolder(final TemplateFilesResolver templateFilesResolver, final TofuCompiler tofuCompiler) throws Exception {
        final DefaultCompiledTemplatesHolder defaultCompiledTemplatesHolder = new DefaultCompiledTemplatesHolder();
        defaultCompiledTemplatesHolder.setTemplatesFileResolver(templateFilesResolver);
        defaultCompiledTemplatesHolder.setTofuCompiler(tofuCompiler);

        return defaultCompiledTemplatesHolder;
    }

    @Bean
    public TemplateRenderer soyTemplateRenderer() {
        return new DefaultTemplateRenderer();
    }

    @Bean
    public Soy soy(final CompiledTemplatesHolder compiledTemplatesHolder,
                   final GlobalRuntimeModelResolver globalRuntimeModelResolver,
                   final LocaleProvider localeProvider,
                   final SoyMsgBundleResolver msgBundleResolver,
                   final TemplateRenderer templateRenderer,
                   final ToSoyDataConverter toSoyDataConverter) {

        final DefaultSoy defaultSoy = new DefaultSoy();
        defaultSoy.setCompiledTemplatesHolder(compiledTemplatesHolder);
        defaultSoy.setGlobalRuntimeModelResolver(globalRuntimeModelResolver);
        defaultSoy.setLocaleProvider(localeProvider);
        defaultSoy.setSoyMsgBundleResolver(msgBundleResolver);
        defaultSoy.setTemplateRenderer(templateRenderer);
        defaultSoy.setToSoyDataConverter(toSoyDataConverter);

        return defaultSoy;
    }

}
