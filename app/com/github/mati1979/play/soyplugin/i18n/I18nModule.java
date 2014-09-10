package com.github.mati1979.play.soyplugin.i18n;

import play.api.Configuration;
import play.api.Environment;
import play.api.inject.Binding;
import play.api.inject.Module;
import scala.collection.Seq;

/**
 * Created by mati on 10/09/2014.
 */
public class I18nModule extends Module {

    @Override
    public Seq<Binding<?>> bindings(final Environment environment, final Configuration configuration) {
        if (configuration.getBoolean("play.soy.view.module.i18n").apply(true).get()) {
            return seq(
                    bind(LocaleProvider.class).to(AcceptHeaderLocaleProvider.class),
                    bind(SoyMsgBundleResolver.class).to(DefaultSoyMsgBundleResolver.class)
            );
        }

        return seq();
    }

}
