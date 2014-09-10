package com.github.mati1979.play.soyplugin.spring;

import play.api.Configuration;
import play.api.Environment;
import play.api.inject.Binding;
import play.api.inject.Module;
import scala.collection.Seq;

/**
 * Created by mati on 27/08/2014.
 */
@Deprecated
public class PlaySoyViewModule extends Module {

    @Override
    public Seq<Binding<?>> bindings(final Environment environment, final Configuration configuration) {
        return seq();
//        return seq(
//                //global params module
//                bind(CompileTimeGlobalModelResolver.class).to(EmptyCompileTimeGlobalModelResolver.class),
//                bind(GlobalRuntimeModelResolver.class).to(EmptyGlobalRuntimeModelResolver.class),
//
//                //resolve
//                bind(TemplateFilesResolver.class).to(FileSystemTemplateFilesResolver.class),
//
//                //conf
//                bind(ConfigDefaults.class).to(DefaultConfigDefaults.class),
//                bind(SoyViewConf.class).to(PlaySoyViewConf.class),
//
//                //convert
//                bind(ToSoyDataConverter.class).to(ReflectionToSoyDataConverter.class),
//
//                bind(SoyJsSrcOptions.class).toInstance(new SoyJsSrcOptions()),
//                bind(SoyTofuOptions.class).toInstance(new SoyTofuOptions()),
//
//                //compile
//                bind(CompiledTemplatesHolder.class).to(DefaultCompiledTemplatesHolder.class),
//                bind(TofuCompiler.class).to(DefaultTofuCompiler.class),
//
//                //18in
//                bind(LocaleProvider.class).to(AcceptHeaderLocaleProvider.class),
//                bind(SoyMsgBundleResolver.class).to(DefaultSoyMsgBundleResolver.class),
//
//                //render
//                bind(TemplateRenderer.class).to(DefaultTemplateRenderer.class),
//
//                //soy
//                bind(Soy.class).to(DefaultSoy.class),
//
//                //ajax
//                bind(SoyAllowedUrlsResolver.class).to(DefaultSoyAllowedUrlsResolver.class),
//                bind(HashFileGenerator.class).to(MD5HashFileGenerator.class),
//                bind(AuthManager.class).to(PermissableAuthManager.class),
//
//                bind(OutputProcessor.class).qualifiedWith(Names.named("google")).to(GoogleClosureOutputProcessor.class),
//                bind(OutputProcessors.class).to(DefaultOutputProcessors.class),
//
//                bind(SoyAjaxController.class).toSelf()
//        );
    }

}
