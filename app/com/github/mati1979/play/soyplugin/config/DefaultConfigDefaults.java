package com.github.mati1979.play.soyplugin.config;

import play.Environment;

import javax.inject.Inject;

/**
 * Created by mati on 30/08/2014.
 */
public class DefaultConfigDefaults implements ConfigDefaults {

    private final static boolean GLOBAL_HOT_RELOAD_MODE = false;

    private final static int GLOBAL_HOT_RELOAD_COMPILE_TIME_IN_SECS = 5;

    private final static String GLOBAL_CHARSET_ENCODING = "utf-8";

    /**
     * Whether templates should be precompiled on the startup of application
     */
    private final static boolean COMPILE_PRECOMPILE_TEMPLATES = true;

    private final static boolean RESOLVE_RECURSIVE = true;

    private final static String RESOLVE_FILES_EXTENSION = "soy";

    private final static String RESOLVE_TEMPLATES_LOCATION = "conf/soy";

    private static final String I18N_MESSAGES_PATH = "conf/xliffs/messages";

    private final static boolean I18N_FALLBACK_TO_ENGLISH = true;

    private final static boolean AJAX_SECURITY_ENABLED = true;

    private final static String AJAX_ALLOWED_URLS = "";

    private final static String AJAX_CACHE_CONTROL = "no-cache";

    private final static String AJAX_EXPIRE_HEADERS = "";

    private Environment environment;

    @Inject
    public DefaultConfigDefaults(final Environment environment) {
        this.environment = environment;
    }

    @Override
    public boolean globalHotReloadMode() {
        return environment.isDev();
    }

    @Override
    public int globalHotReloadCompileTimeInSecs() {
        return GLOBAL_HOT_RELOAD_COMPILE_TIME_IN_SECS;
    }

    @Override
    public String globalCharsetEncoding() {
        return GLOBAL_CHARSET_ENCODING;
    }

    @Override
    public boolean compilePrecompileTemplates() {
        return COMPILE_PRECOMPILE_TEMPLATES;
    }

    @Override
    public boolean resolveRecursive() {
        return RESOLVE_RECURSIVE;
    }

    @Override
    public String resolveFilesExtension() {
        return RESOLVE_FILES_EXTENSION;
    }

    @Override
    public String resolveTemplatesLocation() {
        return RESOLVE_TEMPLATES_LOCATION;
    }

    @Override
    public String i18nMessagesPath() {
        return I18N_MESSAGES_PATH;
    }

    @Override
    public boolean i18nFallbackToEnglish() {
        return I18N_FALLBACK_TO_ENGLISH;
    }

    @Override
    public boolean ajaxSecurityEnabled() {
        return AJAX_SECURITY_ENABLED;
    }

    @Override
    public String ajaxAllowedUrls() {
        return AJAX_ALLOWED_URLS;
    }

    @Override
    public String ajaxCacheControl() {
        return AJAX_CACHE_CONTROL;
    }

    @Override
    public String ajaxExpireHeaders() {
        return AJAX_EXPIRE_HEADERS;
    }

}
