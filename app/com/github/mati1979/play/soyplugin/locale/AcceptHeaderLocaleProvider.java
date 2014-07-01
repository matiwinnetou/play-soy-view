package com.github.mati1979.play.soyplugin.locale;

import play.i18n.Lang;
import play.mvc.Http;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * User: mati
 * Date: 20/06/2013
 * Time: 00:08
 *
 * An implementation of LocaleProvider that uses user's browser Accept-Header
 * setting resolving a preferred locale based on browser configuration.
 */
public class AcceptHeaderLocaleProvider implements LocaleProvider {

    private static final play.Logger.ALogger logger = play.Logger.of(AcceptHeaderLocaleProvider.class);

    public Optional<Locale> resolveLocale(final Http.Request request) {
        final List<Lang> langs = request.acceptLanguages();
        logger.debug("http request langs:" + langs);
        if (langs.size() > 0) {
            final Locale locale = langs.iterator().next().toLocale();
            logger.debug("using first lang:" + locale);

            return Optional.of(locale);
        }

        return Optional.empty();
    }

}
