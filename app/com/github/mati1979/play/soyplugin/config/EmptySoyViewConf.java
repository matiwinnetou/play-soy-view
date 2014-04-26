package com.github.mati1979.play.soyplugin.config;

/**
 * Created by mszczap on 26.04.14.
 */
public class EmptySoyViewConf implements SoyViewConf {

    @Override
    public boolean globalHotReloadMode() {
        return false;
    }

    @Override
    public String globalEncoding() {
        return "utf-8";
    }

    @Override
    public boolean compilePrecompileTemplates() {
        return false;
    }

    @Override
    public String resolveTemplatesLocation() {
        return "";
    }

    @Override
    public boolean resolveRecursive() {
        return false;
    }

    @Override
    public String resolveFilesExtension() {
        return "soy";
    }

    @Override
    public String i18nMessagesPath() {
        return "";
    }

    @Override
    public boolean i18nFallbackToEnglish() {
        return true;
    }

    @Override
    public boolean ajaxSecurityEnabled() {
        return false;
    }

    @Override
    public String ajaxAllowedUrls() {
        return "";
    }

    @Override
    public String ajaxExpireHeaders() {
        return "";
    }

    @Override
    public String ajaxCacheControl() {
        return "";
    }

}
