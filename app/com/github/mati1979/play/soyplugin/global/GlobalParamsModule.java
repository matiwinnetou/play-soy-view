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

public class GlobalParamsModule extends Module {

    @Override
    public Seq<Binding<?>> bindings(Environment environment, Configuration configuration) {
        return seq(
                bind(CompileTimeGlobalModelResolver.class).to(EmptyCompileTimeGlobalModelResolver.class),
                bind(GlobalRuntimeModelResolver.class).to(EmptyGlobalRuntimeModelResolver.class)
        );
    }

}
