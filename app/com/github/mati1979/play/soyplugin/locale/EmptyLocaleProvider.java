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
 * An empty implementation, very useful for application localised only in English
 */
public class EmptyLocaleProvider implements LocaleProvider {

    @Override
    public Optional<Locale> resolveLocale(final Http.Request request) {
        return Optional.empty();
    }

}
