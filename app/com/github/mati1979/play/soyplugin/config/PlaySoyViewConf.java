package com.github.mati1979.play.soyplugin.config;

import com.google.inject.Inject;
import play.Configuration;

/**
 * Created by mszczap on 26.04.14.
 */
public class PlaySoyViewConf implements SoyViewConf {

    private ConfigDefaults configDefaults;
    private Configuration configuration;

    @Inject
    public PlaySoyViewConf(final ConfigDefaults configDefaults,
                           final Configuration configuration) {
        this.configDefaults = configDefaults;
        this.configuration = configuration;
    }

    @Override
    public boolean globalHotReloadMode() {
        return getConfig().getBoolean(ConfigKeys.GLOBAL_HOT_RELOAD_MODE, configDefaults.globalHotReloadMode());
    }

    @Override
    public int globalHotReloadCompileTimeInSecs() {
        return getConfig().getInt(ConfigKeys.GLOBAL_HOT_RELOAD_COMPILE_TIME_IN_SECS, configDefaults.globalHotReloadCompileTimeInSecs());
    }

    @Override
    public String globalEncoding() {
        return getConfig().getString(ConfigKeys.GLOBAL_CHARSET_ENCODING, configDefaults.globalCharsetEncoding());
    }

    @Override
    public boolean compilePrecompileTemplates() {
        return getConfig().getBoolean(ConfigKeys.COMPILE_PRECOMPILE_TEMPLATES, configDefaults.compilePrecompileTemplates());
    }

    @Override
    public String resolveTemplatesLocation() {
        return getConfig().getString(ConfigKeys.RESOLVE_TEMPLATES_LOCATION, configDefaults.resolveTemplatesLocation());
    }

    @Override
    public boolean resolveRecursive() {
        return getConfig().getBoolean(ConfigKeys.RESOLVE_RECURSIVE, configDefaults.resolveRecursive());
    }

    @Override
    public String resolveFilesExtension() {
        return getConfig().getString(ConfigKeys.RESOLVE_FILE_EXTENSION, configDefaults.resolveFilesExtension());
    }

    @Override
    public String i18nMessagesPath() {
        return getConfig().getString(ConfigKeys.RESOLVE_FILE_EXTENSION, configDefaults.resolveFilesExtension());
    }

    @Override
    public boolean i18nFallbackToEnglish() {
        return getConfig().getBoolean(ConfigKeys.I18N_FALLBACK_TO_ENGLISH, configDefaults.i18nFallbackToEnglish());
    }

    @Override
    public boolean ajaxSecurityEnabled() {
        return getConfig().getBoolean(ConfigKeys.AJAX_SECURITY_ENABLED, configDefaults.ajaxSecurityEnabled());
    }

    @Override
    public String ajaxAllowedUrls() {
        return getConfig().getString(ConfigKeys.AJAX_ALLOWED_URLS, configDefaults.ajaxAllowedUrls());
    }

    @Override
    public String ajaxExpireHeaders() {
        return getConfig().getString(ConfigKeys.AJAX_EXPIRE_HEADERS, configDefaults.ajaxExpireHeaders());
    }

    @Override
    public String ajaxCacheControl() {
        return getConfig().getString(ConfigKeys.AJAX_CACHE_CONTROL, configDefaults.ajaxCacheControl());
    }

    private Configuration getConfig() {
        return configuration.getConfig("play.soy.view.config");
    }

}
