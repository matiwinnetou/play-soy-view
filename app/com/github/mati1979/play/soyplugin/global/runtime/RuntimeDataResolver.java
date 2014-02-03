package com.github.mati1979.play.soyplugin.global.runtime;

import com.google.template.soy.data.SoyMapData;
import play.mvc.Http;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mati
 * Date: 01/11/2013
 * Time: 16:29
 */
public interface RuntimeDataResolver {

    void resolveData(Http.Request request, Http.Response response, Map<String, ? extends Object> model, SoyMapData root);

}
