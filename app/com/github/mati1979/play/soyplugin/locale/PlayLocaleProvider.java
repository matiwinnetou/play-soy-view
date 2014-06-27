package com.github.mati1979.play.soyplugin.locale;

import java.util.Optional;
import play.Play;
import play.mvc.Http;

import java.util.Locale;

/**
 * Created by mati on 03/02/2014.
 */
public class PlayLocaleProvider implements LocaleProvider {

    @Override
    public Optional<Locale> resolveLocale(Http.Request request) {
        final String lang = Play.application().configuration().getString("application.langs", "en");
        final String[] langs = lang.split(",");

        if (langs.length > 0) {
            return Optional.of(Locale.forLanguageTag(langs[0]));
        }

        return Optional.empty();
    }

}
