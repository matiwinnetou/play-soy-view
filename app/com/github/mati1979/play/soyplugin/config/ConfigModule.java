package com.github.mati1979.play.soyplugin.config;

import play.api.Configuration;
import play.api.Environment;
import play.api.inject.Binding;
import play.api.inject.Module;
import scala.collection.Seq;

/**
 * Created by mati on 10/09/2014.
 */
public class ConfigModule extends Module {

    @Override
    public Seq<Binding<?>> bindings(Environment environment, Configuration configuration) {
        if (configuration.getBoolean("play.soy.view.module.config").apply(true).get()) {
            return seq(
                    bind(SoyViewConf.class).to(PlaySoyViewConf.class),
                    bind(ConfigDefaults.class).to(DefaultConfigDefaults.class)
            );
        }

        return seq();
    }

}
