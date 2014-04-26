package com.github.mati1979.play.soyplugin.ajax.utils;

import com.google.common.base.Joiner;

/**
 * Created with IntelliJ IDEA.
 * User: mati
 * Date: 26/10/2013
 * Time: 11:41
 */
public class PathUtils {

    private PathUtils() {
        // protects from instantiation
    }

    /**
     * Converts a String array to a String with coma separator
     * @param array - array of soy files
     * @return soy files with commas
     */
   public static String arrayToPath(final String[] array) {
        if (array == null) {
            return "";
        }
        final Joiner joiner = Joiner.on(",").skipNulls();

        return joiner.join(array);
    }

}
