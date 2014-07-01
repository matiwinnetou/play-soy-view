package com.github.mati1979.play.soyplugin.spring;

import com.github.mati1979.play.soyplugin.bundle.DefaultSoyMsgBundleResolver;
import com.github.mati1979.play.soyplugin.bundle.SoyMsgBundleResolver;
import com.github.mati1979.play.soyplugin.compile.DefaultTofuCompiler;
import com.github.mati1979.play.soyplugin.compile.TofuCompiler;
import com.github.mati1979.play.soyplugin.config.PlayConfAccessor;
import com.github.mati1979.play.soyplugin.config.PlaySoyViewConf;
import com.github.mati1979.play.soyplugin.config.SoyViewConf;
import com.github.mati1979.play.soyplugin.data.ReflectionToSoyDataConverter;
import com.github.mati1979.play.soyplugin.data.ToSoyDataConverter;
import com.github.mati1979.play.soyplugin.global.compile.CompileTimeGlobalModelResolver;
import com.github.mati1979.play.soyplugin.global.compile.DefaultCompileTimeGlobalModelResolver;
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
import com.github.mati1979.play.soyplugin.template.FileSystemTemplateFilesResolver;
import com.github.mati1979.play.soyplugin.template.TemplateFilesResolver;
import java.util.Optional;
import com.google.template.soy.jssrc.SoyJsSrcOptions;
import com.google.template.soy.tofu.SoyTofuOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    public FileSystemTemplateFilesResolver soyTemplateFilesResolver() {
        return new FileSystemTemplateFilesResolver(soyViewConf());
    }

    @Bean
    public GlobalRuntimeModelResolver soyGlobalRuntimeModelResolver() {
        return new EmptyGlobalRuntimeModelResolver();
    }

    @Bean
    public CompileTimeGlobalModelResolver soyCompileTimeGlobalModelResolver() {
        final Map<String, Object> merged = new HashMap<>();

        final Optional<play.Configuration> pluginConfig = PlayConfAccessor.getPluginConfig();
        if (pluginConfig.isPresent()) {
            merged.putAll(pluginConfig.get().asMap());
        }

        final Optional<play.Configuration> userConfig = PlayConfAccessor.getUserConfig();
        if (userConfig.isPresent()) {
            merged.putAll(userConfig.get().asMap());
        }

        return new DefaultCompileTimeGlobalModelResolver(merged);
    }

    @Bean
    public ToSoyDataConverter soyToSoyDataConverter() {
        return new ReflectionToSoyDataConverter();
    }

    @Bean
    public SoyJsSrcOptions soyJsSourceOptions() {
        return new SoyJsSrcOptions();
    }

    @Bean
    public SoyTofuOptions soyTofuOptions(final SoyViewConf soyViewConf) {
        final SoyTofuOptions soyTofuOptions = new SoyTofuOptions();
        soyTofuOptions.setUseCaching(!soyViewConf.globalHotReloadMode());

        return soyTofuOptions;
    }

    @Bean
    public TofuCompiler soyTofuCompiler(final CompileTimeGlobalModelResolver compileTimeGlobalModelResolver,
                                        final SoyJsSrcOptions soyJsSrcOptions,
                                        final SoyTofuOptions soyTofuOptions) {
        return new DefaultTofuCompiler(compileTimeGlobalModelResolver, soyViewConf(), soyJsSrcOptions, soyTofuOptions);
    }

    @Bean
    public SoyMsgBundleResolver soyMsgBundleResolver() {
        return new DefaultSoyMsgBundleResolver(soyViewConf());
    }

    @Bean
    public CompiledTemplatesHolder soyTemplatesHolder(final TemplateFilesResolver templateFilesResolver, final TofuCompiler tofuCompiler) {
        try {
            return new DefaultCompiledTemplatesHolder(tofuCompiler, templateFilesResolver, soyViewConf());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public SoyViewConf soyViewConf() {
        return new PlaySoyViewConf();
    }

    @Bean
    public TemplateRenderer soyTemplateRenderer() {
        return new DefaultTemplateRenderer(soyViewConf());
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
