package pl.matisoft.soy.global.runtime;

import com.google.common.base.Optional;
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

    public Optional<SoyMapData> resolveData(final Http.Request request, final Http.Response response, final Map<String, ? extends Object> model) {
        return Optional.absent();
    }

}
