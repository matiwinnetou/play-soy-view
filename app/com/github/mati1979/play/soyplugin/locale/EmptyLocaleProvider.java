package com.github.mati1979.play.soyplugin.locale;

import java.util.Locale;

import com.google.common.base.Optional;
import play.mvc.Http;

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
        return Optional.absent();
    }

}
