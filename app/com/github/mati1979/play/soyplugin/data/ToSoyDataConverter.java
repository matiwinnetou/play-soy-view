package com.github.mati1979.play.soyplugin.data;

import java.util.Optional;
import com.google.template.soy.data.SoyMapData;

import javax.annotation.Nullable;

/**
 * Created with IntelliJ IDEA.
 * User: mati
 * Date: 20/06/2013
 * Time: 00:27
 *
 * An interface, which converts a view model (e.g) POJO object to a Soy's
 * compatible data structure
 */
public interface ToSoyDataConverter {

    /**
     * Convert a view model object to SoyMapData structure
     *
     * @param model
     * @return
     */
    Optional<SoyMapData> toSoyMap(@Nullable final Object model);

}
