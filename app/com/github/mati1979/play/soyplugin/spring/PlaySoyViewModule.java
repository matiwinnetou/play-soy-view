package com.github.mati1979.play.soyplugin.spring;

import com.github.mati1979.play.soyplugin.ajax.SoyAjaxController;
import com.github.mati1979.play.soyplugin.ajax.allowedurls.DefaultSoyAllowedUrlsResolver;
import com.github.mati1979.play.soyplugin.ajax.allowedurls.SoyAllowedUrlsResolver;
import com.github.mati1979.play.soyplugin.ajax.auth.AuthManager;
import com.github.mati1979.play.soyplugin.ajax.auth.PermissableAuthManager;
import com.github.mati1979.play.soyplugin.ajax.hash.HashFileGenerator;
import com.github.mati1979.play.soyplugin.ajax.hash.MD5HashFileGenerator;
import com.github.mati1979.play.soyplugin.ajax.process.DefaultOutputProcessors;
import com.github.mati1979.play.soyplugin.ajax.process.OutputProcessors;
import com.github.mati1979.play.soyplugin.bundle.DefaultSoyMsgBundleResolver;
import com.github.mati1979.play.soyplugin.bundle.SoyMsgBundleResolver;
import com.github.mati1979.play.soyplugin.compile.DefaultTofuCompiler;
import com.github.mati1979.play.soyplugin.compile.TofuCompiler;
import com.github.mati1979.play.soyplugin.config.PlaySoyViewConf;
import com.github.mati1979.play.soyplugin.config.SoyViewConf;
import com.github.mati1979.play.soyplugin.data.ReflectionToSoyDataConverter;
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
import com.github.mati1979.play.soyplugin.template.FileSystemTemplateFilesResolver;
import com.github.mati1979.play.soyplugin.template.TemplateFilesResolver;
import com.google.template.soy.jssrc.SoyJsSrcOptions;
import com.google.template.soy.tofu.SoyTofuOptions;
import play.api.Configuration;
import play.api.Environment;
import play.api.inject.Binding;
import play.api.inject.Module;
import scala.collection.Seq;

/**
 * Created by mati on 27/08/2014.
 */
public class PlaySoyViewModule extends Module {

    @Override
    public Seq<Binding<?>> bindings(final Environment environment, final Configuration configuration) {
        return seq(
                bind(GlobalRuntimeModelResolver.class).to(EmptyGlobalRuntimeModelResolver.class),
                bind(LocaleProvider.class).to(AcceptHeaderLocaleProvider.class),
                bind(TemplateFilesResolver.class).to(FileSystemTemplateFilesResolver.class),
                bind(SoyViewConf.class).to(PlaySoyViewConf.class),
                bind(CompileTimeGlobalModelResolver.class).to(EmptyCompileTimeGlobalModelResolver.class),
                bind(ToSoyDataConverter.class).to(ReflectionToSoyDataConverter.class),
                bind(SoyJsSrcOptions.class).toInstance(new SoyJsSrcOptions()),
                bind(SoyTofuOptions.class).toInstance(new SoyTofuOptions()),
                bind(TofuCompiler.class).to(DefaultTofuCompiler.class),
                bind(SoyMsgBundleResolver.class).to(DefaultSoyMsgBundleResolver.class),
                bind(CompiledTemplatesHolder.class).to(DefaultCompiledTemplatesHolder.class),
                bind(TemplateRenderer.class).to(DefaultTemplateRenderer.class),
                bind(Soy.class).to(DefaultSoy.class),

                bind(SoyAllowedUrlsResolver.class).to(DefaultSoyAllowedUrlsResolver.class),
                bind(HashFileGenerator.class).to(MD5HashFileGenerator.class),
                bind(AuthManager.class).to(PermissableAuthManager.class),
                bind(OutputProcessors.class).to(DefaultOutputProcessors.class),
                //bind(List.class).qualifiedWith("processors").toInstance(Lists.newArrayList(new GoogleClosureOutputProcessor())),
                bind(SoyAjaxController.class).toSelf()
        );
    }

}
