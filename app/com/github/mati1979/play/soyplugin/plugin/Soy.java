package com.github.mati1979.play.soyplugin.plugin;

import com.google.template.soy.data.SoyMapData;
import play.mvc.Http;

/**
 * Created by mati on 02/02/2014.
 */
public interface Soy {

    String html(String view, Object model) throws Exception;

    String html(String view, SoyMapData soyMapData) throws Exception;

    String html(String view) throws Exception;

    String html(Http.Request request, Http.Response response, String view, SoyMapData soyMapData) throws Exception;

    String html(Http.Request request, Http.Response response, String view, Object model) throws Exception;

    String html(Http.Request request, Http.Response response, String view) throws Exception;

}
