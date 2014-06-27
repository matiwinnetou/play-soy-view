package com.github.mati1979.play.soyplugin.ajax.runtime;

import com.github.mati1979.play.soyplugin.ajax.hash.HashFileGenerator;
import com.github.mati1979.play.soyplugin.global.runtime.RuntimeDataResolver;
import com.google.template.soy.data.SoyMapData;
import play.mvc.Http;

import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mszczap
 * Date: 14.11.13
 * Time: 11:02
 */
public class SoyHashesRuntimeDataResolver implements RuntimeDataResolver {

    private HashFileGenerator hashFileGenerator;

    public SoyHashesRuntimeDataResolver(final HashFileGenerator hashFileGenerator) {
        this.hashFileGenerator = hashFileGenerator;
    }

    public void resolveData(final Http.Request request,
                            final Http.Response response,
                            final Map<String, ?> model,
                            final SoyMapData root) {
        try {
            root.put("soyplugin.ajax.soy.hash", hashFileGenerator.hash().orElse(null));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

}
