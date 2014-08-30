package com.github.mati1979.play.soyplugin.locale;

import play.mvc.Http;

import java.util.Locale;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * User: mati
 * Date: 20/06/2013
 * Time: 22:59
 *
 * A default implementation that returns a locale passed as a configuration.
 */
public class DefaultLocaleProvider implements LocaleProvider {

    private static final play.Logger.ALogger logger = play.Logger.of(AcceptHeaderLocaleProvider.class);

    private Locale locale = Locale.US;

    public DefaultLocaleProvider(final Locale locale) {
        this.locale = locale;
    }

    public DefaultLocaleProvider() {
    }

    @Override
    public Optional<Locale> resolveLocale(final Http.Request request) {
        logger.debug("using locale:" + locale);
        return Optional.ofNullable(locale);
    }

}
