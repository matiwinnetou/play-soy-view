package pl.matisoft.soy.global.runtime;

import java.util.Map;

import com.google.template.soy.data.SoyMapData;
import play.mvc.Http;

/**
 * Created with IntelliJ IDEA.
 * User: mati
 * Date: 01/11/2013
 * Time: 16:29
 */
public interface RuntimeDataResolver {

    void resolveData(Http.Request request, Http.Response response, Map<String, ? extends Object> model, SoyMapData root);

}
