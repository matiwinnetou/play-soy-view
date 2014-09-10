package com.github.mati1979.play.soyplugin.i18n;

import play.Play;
import play.mvc.Http;

import java.util.Locale;
import java.util.Optional;

/**
 * Created by mati on 03/02/2014.
 */
public class PlayLocaleProvider implements LocaleProvider {

    private static final play.Logger.ALogger logger = play.Logger.of(PlayLocaleProvider.class);

    @Override
    public Optional<Locale> resolveLocale(final Http.Request request) {
        final String lang = Play.application().configuration().getString("application.langs", "en");
        final String[] langs = lang.split(",");

        logger.debug("play config langs:" + langs);

        if (langs.length > 0) {
            final Locale locale = Locale.forLanguageTag(langs[0]);

            logger.debug("using lang:" + locale);

            return Optional.of(locale);
        }

        return Optional.empty();
    }

}
