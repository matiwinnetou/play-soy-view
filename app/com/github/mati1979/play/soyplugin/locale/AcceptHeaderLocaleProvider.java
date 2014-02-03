package com.github.mati1979.play.soyplugin.locale;

import com.google.common.base.Optional;
import play.i18n.Lang;
import play.mvc.Http;

import java.util.List;
import java.util.Locale;

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

    public Optional<Locale> resolveLocale(final Http.Request request) {
        final List<Lang> langs = Http.Context.current().request().acceptLanguages();
        if (langs.size() > 0) {
            return Optional.of(langs.iterator().next().toLocale());
        }

        return Optional.absent();
    }

}
