package com.github.mati1979.play.soyplugin.config;

import play.api.Configuration;
import play.api.Environment;
import play.api.inject.Binding;
import play.api.inject.Module;
import scala.collection.Seq;

public class ConfigModule extends Module {

    @Override
    public Seq<Binding<?>> bindings(Environment environment, Configuration configuration) {
        return seq(
                bind(SoyViewConf.class).to(PlaySoyViewConf.class),
                bind(ConfigDefaults.class).to(DefaultConfigDefaults.class)
        );
    }

}
