package com.github.mati1979.play.soyplugin.global;

import com.github.mati1979.play.soyplugin.global.compile.CompileTimeGlobalModelResolver;
import com.github.mati1979.play.soyplugin.global.compile.EmptyCompileTimeGlobalModelResolver;
import com.github.mati1979.play.soyplugin.global.runtime.EmptyGlobalRuntimeModelResolver;
import com.github.mati1979.play.soyplugin.global.runtime.GlobalRuntimeModelResolver;
import play.api.Configuration;
import play.api.Environment;
import play.api.inject.Binding;
import play.api.inject.Module;
import scala.collection.Seq;

/**
 * Created by mati on 10/09/2014.
 */
public class GlobalParamsModule extends Module {

    @Override
    public Seq<Binding<?>> bindings(Environment environment, Configuration configuration) {
        if (configuration.getBoolean("play.soy.view.module.global.params").apply(true).get()) {
            return seq(
                    bind(CompileTimeGlobalModelResolver.class).to(EmptyCompileTimeGlobalModelResolver.class),
                    bind(GlobalRuntimeModelResolver.class).to(EmptyGlobalRuntimeModelResolver.class)
            );
        }

        return seq();
    }

}
