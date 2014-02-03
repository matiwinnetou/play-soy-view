package pl.matisoft.soy.spring;

import com.google.template.soy.jssrc.SoyJsSrcOptions;
import com.google.template.soy.tofu.SoyTofuOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.matisoft.soy.bundle.DefaultSoyMsgBundleResolver;
import pl.matisoft.soy.bundle.SoyMsgBundleResolver;
import pl.matisoft.soy.compile.DefaultTofuCompiler;
import pl.matisoft.soy.compile.TofuCompiler;
import pl.matisoft.soy.config.ConfigKeys;
import pl.matisoft.soy.config.SoyViewConfigDefaults;
import pl.matisoft.soy.data.DefaultToSoyDataConverter;
import pl.matisoft.soy.data.ToSoyDataConverter;
import pl.matisoft.soy.global.compile.CompileTimeGlobalModelResolver;
import pl.matisoft.soy.global.compile.EmptyCompileTimeGlobalModelResolver;
import pl.matisoft.soy.global.runtime.EmptyGlobalRuntimeModelResolver;
import pl.matisoft.soy.global.runtime.GlobalRuntimeModelResolver;
import pl.matisoft.soy.holder.CompiledTemplatesHolder;
import pl.matisoft.soy.holder.DefaultCompiledTemplatesHolder;
import pl.matisoft.soy.locale.AcceptHeaderLocaleProvider;
import pl.matisoft.soy.locale.LocaleProvider;
import pl.matisoft.soy.plugin.DefaultSoy;
import pl.matisoft.soy.plugin.Soy;
import pl.matisoft.soy.render.DefaultTemplateRenderer;
import pl.matisoft.soy.render.TemplateRenderer;
import pl.matisoft.soy.template.DefaultTemplateFilesResolver;
import pl.matisoft.soy.template.TemplateFilesResolver;
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
