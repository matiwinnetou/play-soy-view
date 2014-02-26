package com.github.mati1979.play.soyplugin.spring;

import com.github.mati1979.play.soyplugin.bundle.DefaultSoyMsgBundleResolver;
import com.github.mati1979.play.soyplugin.bundle.SoyMsgBundleResolver;
import com.github.mati1979.play.soyplugin.compile.DefaultTofuCompiler;
import com.github.mati1979.play.soyplugin.compile.TofuCompiler;
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
import com.google.template.soy.jssrc.SoyJsSrcOptions;
import com.google.template.soy.tofu.SoyTofuOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.github.mati1979.play.soyplugin.config.PlayConfAccessor.GLOBAL_HOT_RELOAD_MODE;

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
        soyTofuOptions.setUseCaching(!GLOBAL_HOT_RELOAD_MODE);

        return soyTofuOptions;
    }

    @Bean
    public TofuCompiler soyTofuCompiler(final CompileTimeGlobalModelResolver compileTimeGlobalModelResolver, final SoyJsSrcOptions soyJsSrcOptions, final SoyTofuOptions soyTofuOptions) {
        return new DefaultTofuCompiler(compileTimeGlobalModelResolver, soyJsSrcOptions, soyTofuOptions);
    }

    @Bean
    public SoyMsgBundleResolver soyMsgBundleResolver() {
        return new DefaultSoyMsgBundleResolver();
    }

    @Bean
    public CompiledTemplatesHolder soyTemplatesHolder(final TemplateFilesResolver templateFilesResolver, final TofuCompiler tofuCompiler) throws Exception {
        return new DefaultCompiledTemplatesHolder(tofuCompiler, templateFilesResolver);
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
        return new DefaultSoy(compiledTemplatesHolder, globalRuntimeModelResolver, msgBundleResolver, templateRenderer, toSoyDataConverter, localeProvider);
    }

}
