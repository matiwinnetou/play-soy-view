package com.github.mati1979.play.soyplugin.i18n;

import play.api.Configuration;
import play.api.Environment;
import play.api.inject.Binding;
import play.api.inject.Module;
import scala.collection.Seq;

public class I18nModule extends Module {

    @Override
    public Seq<Binding<?>> bindings(final Environment environment, final Configuration configuration) {
        return seq(
                bind(LocaleProvider.class).to(AcceptHeaderLocaleProvider.class),
                bind(SoyMsgBundleResolver.class).to(DefaultSoyMsgBundleResolver.class)
        );
    }

}
