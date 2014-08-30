package com.github.mati1979.play.soyplugin.config;

import javax.inject.Inject;

/**
 * Created by mszczap on 26.04.14.
 */
public class DefaultSoyViewConf implements SoyViewConf {

    private ConfigDefaults configDefaults;

    @Inject
    public DefaultSoyViewConf(final ConfigDefaults configDefaults) {
        this.configDefaults = configDefaults;
    }

    @Override
    public boolean globalHotReloadMode() {
        return configDefaults.globalHotReloadMode();
    }

    @Override
    public int globalHotReloadCompileTimeInSecs() {
        return configDefaults.globalHotReloadCompileTimeInSecs();
    }

    @Override
    public String globalEncoding() {
        return configDefaults.globalCharsetEncoding();
    }

    @Override
    public boolean compilePrecompileTemplates() {
        return configDefaults.compilePrecompileTemplates();
    }

    @Override
    public String resolveTemplatesLocation() {
        return configDefaults.resolveTemplatesLocation();
    }

    @Override
    public boolean resolveRecursive() {
        return configDefaults.resolveRecursive();
    }

    @Override
    public String resolveFilesExtension() {
        return configDefaults.resolveFilesExtension();
    }

    @Override
    public String i18nMessagesPath() {
        return configDefaults.i18nMessagesPath();
    }

    @Override
    public boolean i18nFallbackToEnglish() {
        return configDefaults.i18nFallbackToEnglish();
    }

    @Override
    public boolean ajaxSecurityEnabled() {
        return configDefaults.ajaxSecurityEnabled();
    }

    @Override
    public String ajaxAllowedUrls() {
        return configDefaults.ajaxAllowedUrls();
    }

    @Override
    public String ajaxExpireHeaders() {
        return configDefaults.ajaxExpireHeaders();
    }

    @Override
    public String ajaxCacheControl() {
        return configDefaults.ajaxCacheControl();
    }

}
