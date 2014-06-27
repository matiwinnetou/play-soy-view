package com.github.mati1979.play.soyplugin.locale;

import java.util.Optional;
import play.mvc.Http;

import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: mati
 * Date: 20/06/2013
 * Time: 22:59
 *
 * A default implementation that returns a locale passed as a configuration.
 */
public class DefaultLocaleProvider implements LocaleProvider {

    private Locale locale = Locale.US;

    public DefaultLocaleProvider(Locale locale) {
        this.locale = locale;
    }

    public DefaultLocaleProvider() {
    }

    @Override
    public Optional<Locale> resolveLocale(final Http.Request request) {
        return Optional.ofNullable(locale);
    }

}
