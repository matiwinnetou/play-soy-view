package com.github.mati1979.play.soyplugin.compile;

import com.google.template.soy.jssrc.SoyJsSrcOptions;
import com.google.template.soy.tofu.SoyTofuOptions;
import play.api.Configuration;
import play.api.Environment;
import play.api.inject.Binding;
import play.api.inject.Module;
import scala.collection.Seq;

/**
 * Created by mati on 10/09/2014.
 */
public class CompileModule extends Module {

    @Override
    public Seq<Binding<?>> bindings(final Environment environment, final Configuration configuration) {
        if (configuration.getBoolean("play.soy.view.module.compile").apply(true).get()) {
            return seq(
                    bind(CompiledTemplatesHolder.class).to(DefaultCompiledTemplatesHolder.class),
                    bind(TofuCompiler.class).to(DefaultTofuCompiler.class),
                    bind(SoyJsSrcOptions.class).toInstance(new SoyJsSrcOptions()),
                    bind(SoyTofuOptions.class).toInstance(new SoyTofuOptions())
            );
        }

        return seq();
    }

}
