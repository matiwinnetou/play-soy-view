package com.github.mati1979.play.soyplugin.global.runtime;

import java.util.Optional;
import com.google.template.soy.data.SoyMapData;
import play.mvc.Http;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mati
 * Date: 21/06/2013
 * Time: 22:59
 *]
 * An implementation of Null Object Pattern
 */
public class EmptyGlobalRuntimeModelResolver implements GlobalRuntimeModelResolver {

    public EmptyGlobalRuntimeModelResolver() {
    }

    public Optional<SoyMapData> resolveData(final Http.Request request, final Http.Response response, final Map<String, ? extends Object> model) {
        return Optional.empty();
    }

}
