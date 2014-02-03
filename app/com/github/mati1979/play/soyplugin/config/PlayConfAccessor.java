package com.github.mati1979.play.soyplugin.config;

import play.Play;

/**
 * Created by mati on 03/02/2014.
 */
public class PlayConfAccessor {

    //GLOBAL
    public static boolean GLOBAL_HOT_RELOAD_MODE = Play.application().configuration().getBoolean(ConfigKeys.GLOBAL_HOT_RELOAD_MODE, ConfigDefaults.GLOBAL_HOT_RELOAD_MODE);
    public static String GLOBAL_ENCODING = Play.application().configuration().getString(ConfigKeys.GLOBAL_CHARSET_ENCODING, ConfigDefaults.GLOBAL_CHARSET_ENCODING);

    //COMPILE
    public static boolean COMPILE_PRECOMPILE_TEMPLATES = Play.application().configuration().getBoolean(ConfigKeys.COMPILE_PRECOMPILE_TEMPLATES, ConfigDefaults.COMPILE_PRECOMPILE_TEMPLATES);
    public static String COMPILE_TEMPLATES_LOCATION = Play.application().configuration().getString(ConfigKeys.RESOLVE_TEMPLATES_LOCATION, ConfigDefaults.RESOLVE_TEMPLATES_LOCATION);
    public static boolean COMPILE_RECURSIVE = Play.application().configuration().getBoolean(ConfigKeys.RESOLVE_RECURSIVE, ConfigDefaults.RESOLVE_RECURSIVE);
    public static String COMPILE_FILES_EXTENSION = Play.application().configuration().getString(ConfigKeys.RESOLVE_FILE_EXTENSION, ConfigDefaults.RESOLVE_FILES_EXTENSION);

    //I18N
    public static String I18N_MESSAGES_PATH = Play.application().configuration().getString(ConfigKeys.I18N_MESSAGES_PATH, ConfigDefaults.I18N_MESSAGES_PATH);
    public static boolean I18N_FALLBACK_TO_ENGLISH = Play.application().configuration().getBoolean(ConfigKeys.I18N_FALLBACK_TO_ENGLISH, ConfigDefaults.I18N_FALLBACK_TO_ENGLISH);

    //AJAX
    public static boolean AJAX_SECURITY_ENABLED = Play.application().configuration().getBoolean(ConfigKeys.AJAX_SECURITY_ENABLED, ConfigDefaults.AJAX_SECURITY_ENABLED);
    public static String AJAX_ALLOWED_URLS = Play.application().configuration().getString(ConfigKeys.AJAX_ALLOWED_URLS, ConfigDefaults.AJAX_ALLOWED_URLS);
    public static String AJAX_EXPIRE_HEADERS = Play.application().configuration().getString(ConfigKeys.AJAX_CACHE_CONTROL, ConfigDefaults.AJAX_EXPIRE_HEADERS);
    public static String AJAX_CACHE_CONTROL = Play.application().configuration().getString(ConfigKeys.AJAX_CACHE_CONTROL, ConfigDefaults.AJAX_CACHE_CONTROL);

}
