package com.github.mati1979.play.soyplugin.ajax;

import com.github.mati1979.play.soyplugin.ajax.allowedurls.DefaultSoyAllowedUrlsResolver;
import com.github.mati1979.play.soyplugin.ajax.allowedurls.SoyAllowedUrlsResolver;
import com.github.mati1979.play.soyplugin.ajax.auth.AuthManager;
import com.github.mati1979.play.soyplugin.ajax.auth.PermissableAuthManager;
import com.github.mati1979.play.soyplugin.ajax.hash.HashFileGenerator;
import com.github.mati1979.play.soyplugin.ajax.hash.MD5HashFileGenerator;
import com.github.mati1979.play.soyplugin.ajax.process.EmptyOutputProcessors;
import com.github.mati1979.play.soyplugin.ajax.process.OutputProcessor;
import com.github.mati1979.play.soyplugin.ajax.process.OutputProcessors;
import com.github.mati1979.play.soyplugin.ajax.process.google.GoogleClosureOutputProcessor;
import com.google.inject.name.Names;
import play.api.Configuration;
import play.api.Environment;
import play.api.inject.Binding;
import play.api.inject.Module;
import scala.collection.Seq;

/**
 * Created by mati on 10/09/2014.
 */
public class AjaxModule extends Module {

    @Override
    public Seq<Binding<?>> bindings(final Environment environment, final Configuration configuration) {
        return seq(
                bind(SoyAllowedUrlsResolver.class).to(DefaultSoyAllowedUrlsResolver.class),
                bind(HashFileGenerator.class).to(MD5HashFileGenerator.class),
                bind(AuthManager.class).to(PermissableAuthManager.class),

                bind(OutputProcessor.class).qualifiedWith(Names.named("google")).to(GoogleClosureOutputProcessor.class),
                bind(OutputProcessors.class).to(EmptyOutputProcessors.class),

                bind(SoyAjaxController.class).toSelf()
        );
    }

}

