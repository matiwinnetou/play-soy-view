package com.github.mati1979.play.soyplugin.i18n;

import java.util.Optional;
import com.google.template.soy.msgs.SoyMsgBundle;

import java.io.IOException;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: mati
 * Date: 20/06/2013
 * Time: 22:37
 *
 * An empty implementation of SoyMsgBundleResolver that always returns empty SoyMsgBundle,
 * i.e. absent value
 */
public class EmptySoyMsgBundleResolver implements SoyMsgBundleResolver {

    @Override
    public Optional<SoyMsgBundle> resolve(final Optional<Locale> locale) throws IOException {
        return Optional.empty();
    }

}
