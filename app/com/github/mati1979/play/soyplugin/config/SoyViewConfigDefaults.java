package com.github.mati1979.play.soyplugin.config;

/**
 * Created with IntelliJ IDEA.
 * User: mati
 * Date: 23/06/2013
 * Time: 18:02
 */
public class SoyViewConfigDefaults {

    public final static boolean GLOBAL_HOT_RELOAD_MODE = false;

    public final static String GLOBAL_CHARSET_ENCODING = "utf-8";

    /**
     * Whether templates should be precompiled on the startup of application
     */
    public final static boolean COMPILE_PRECOMPILE_TEMPLATES = false;


    public final static boolean RESOLVE_RECURSIVE = true;

    public final static String RESOLVE_FILES_EXTENSION = "soy";

    public final static String RESOLVE_TEMPLATES_LOCATION = "app/views/closure";


    public static final String I18N_MESSAGES_PATH = "app/views/xliffs/messages";

    public final static boolean I18N_FALLBACK_TO_ENGLISH = true;

}
