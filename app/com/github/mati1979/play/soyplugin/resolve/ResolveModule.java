package com.github.mati1979.play.soyplugin.resolve;

import play.api.Configuration;
import play.api.Environment;
import play.api.inject.Binding;
import play.api.inject.Module;
import scala.collection.Seq;

public class ResolveModule extends Module {

    @Override
    public Seq<Binding<?>> bindings(final Environment environment, final Configuration configuration) {
        return seq(
                bind(TemplateFilesResolver.class).to(FileSystemTemplateFilesResolver.class)
        );
    }

}
