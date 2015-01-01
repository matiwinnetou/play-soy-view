package com.github.mati1979.play.soyplugin.soy;

import com.google.template.soy.data.SoyMapData;
import play.mvc.Http;

public interface Soy {

    String html(String view, Object model);

    String html(String view, SoyMapData soyMapData);

    String html(String view);

    String html(Http.Request request, Http.Response response, String view, SoyMapData soyMapData);

    String html(Http.Request request, Http.Response response, String view, Object model);

    String html(Http.Request request, Http.Response response, String view);

}
