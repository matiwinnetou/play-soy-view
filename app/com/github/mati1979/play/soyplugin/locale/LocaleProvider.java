package com.github.mati1979.play.soyplugin.locale;

import java.util.Optional;
import play.mvc.Http;

import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: mati
 * Date: 20/06/2013
 * Time: 00:07
 *
 * An interface that resolves locale needed to obtain SoyMsgBundle object.
 */
public interface LocaleProvider {

    Optional<Locale> resolveLocale(Http.Request request);

}
