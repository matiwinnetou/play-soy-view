package com.github.mati1979.play.soyplugin.data;

import play.api.Configuration;
import play.api.Environment;
import play.api.inject.Binding;
import play.api.inject.Module;
import scala.collection.Seq;

public class ConvertModule extends Module {

    @Override
    public Seq<Binding<?>> bindings(final Environment environment, final Configuration configuration) {
        return seq(
                bind(ToSoyDataConverter.class).to(ReflectionToSoyDataConverter.class)
        );
    }

}
