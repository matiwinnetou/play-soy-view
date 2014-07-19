package com.github.mati1979.play.soyplugin.config;

/**
 * Created by mszczap on 26.04.14.
 */
public class PlaySoyViewConf implements SoyViewConf {

    @Override
    public boolean globalHotReloadMode() {
        return PlayConfAccessor.GLOBAL_HOT_RELOAD_MODE;
    }

    @Override
    public int globalHotReloadCompileTimeInSecs() {
        return PlayConfAccessor.GLOBAL_HOT_RELOAD_COMPILE_TIME_IN_SECS;
    }

    @Override
    public String globalEncoding() {
        return PlayConfAccessor.GLOBAL_ENCODING;
    }

    @Override
    public boolean compilePrecompileTemplates() {
        return PlayConfAccessor.COMPILE_PRECOMPILE_TEMPLATES;
    }

    @Override
    public String resolveTemplatesLocation() {
        return PlayConfAccessor.COMPILE_TEMPLATES_LOCATION;
    }

    @Override
    public boolean resolveRecursive() {
        return PlayConfAccessor.COMPILE_RECURSIVE;
    }

    @Override
    public String resolveFilesExtension() {
        return PlayConfAccessor.COMPILE_FILES_EXTENSION;
    }

    @Override
    public String i18nMessagesPath() {
        return PlayConfAccessor.I18N_MESSAGES_PATH;
    }

    @Override
    public boolean i18nFallbackToEnglish() {
        return PlayConfAccessor.I18N_FALLBACK_TO_ENGLISH;
    }

    @Override
    public boolean ajaxSecurityEnabled() {
        return PlayConfAccessor.AJAX_SECURITY_ENABLED;
    }

    @Override
    public String ajaxAllowedUrls() {
        return PlayConfAccessor.AJAX_ALLOWED_URLS;
    }

    @Override
    public String ajaxExpireHeaders() {
        return PlayConfAccessor.AJAX_EXPIRE_HEADERS;
    }

    @Override
    public String ajaxCacheControl() {
        return PlayConfAccessor.AJAX_CACHE_CONTROL;
    }

}
