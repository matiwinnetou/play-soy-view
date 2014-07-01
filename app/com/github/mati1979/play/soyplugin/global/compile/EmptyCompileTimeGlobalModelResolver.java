package com.github.mati1979.play.soyplugin.global.compile;

import java.util.Optional;
import com.google.template.soy.data.SoyMapData;

/**
 * Created with IntelliJ IDEA.
 * User: mati
 * Date: 21/06/2013
 * Time: 22:59
 *
 * Null Object Pattern implementation
 */
public class EmptyCompileTimeGlobalModelResolver implements CompileTimeGlobalModelResolver {

    public Optional<SoyMapData> resolveData() {
        return Optional.empty();
    }

}
