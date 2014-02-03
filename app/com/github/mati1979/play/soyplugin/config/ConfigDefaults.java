package com.github.mati1979.play.soyplugin.config;

/**
 * Created with IntelliJ IDEA.
 * User: mati
 * Date: 23/06/2013
 * Time: 18:02
 */
public class ConfigDefaults {

    public final static boolean GLOBAL_HOT_RELOAD_MODE = false;

    public final static String GLOBAL_CHARSET_ENCODING = "utf-8";

    /**
     * Whether templates should be precompiled on the startup of application
     */
    public final static boolean COMPILE_PRECOMPILE_TEMPLATES = false;


    public final static boolean RESOLVE_RECURSIVE = true;

    public final static String RESOLVE_FILES_EXTENSION = "soy";

    public final static String RESOLVE_TEMPLATES_LOCATION = "app/views/closure";


    public static final String I18N_MESSAGES_PATH = "conf/xliffs/messages";

    public final static boolean I18N_FALLBACK_TO_ENGLISH = true;

    public final static boolean AJAX_SECURITY_ENABLED = false;

    public final static String AJAX_ALLOWED_URLS = "";

    public final static String AJAX_CACHE_CONTROL = "no-cache";

    public final static String AJAX_EXPIRE_HEADERS = "";

}
