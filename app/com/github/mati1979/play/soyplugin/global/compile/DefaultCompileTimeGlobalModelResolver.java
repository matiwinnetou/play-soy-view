package com.github.mati1979.play.soyplugin.global.compile;

import java.util.Optional;
import com.google.template.soy.data.SoyMapData;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: mati
 * Date: 21/06/2013
 * Time: 23:06
 *
 * A default implementation, which delegates to a map
 */
public class DefaultCompileTimeGlobalModelResolver implements CompileTimeGlobalModelResolver {

    private Map data;

    public DefaultCompileTimeGlobalModelResolver(Map data) {
        this.data = data;
    }

    public DefaultCompileTimeGlobalModelResolver(final Properties properties) {
        data = new HashMap();
        for (final String propertyName : properties.stringPropertyNames()) {
            data.put(propertyName, properties.getProperty(propertyName));
        }
    }

    @Override
    public Optional<SoyMapData> resolveData() {
        if (data == null || data.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new SoyMapData(data));
    }

}
