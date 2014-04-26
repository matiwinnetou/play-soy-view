package com.github.mati1979.play.soyplugin.config;

/**
 * Created by mszczap on 26.04.14.
 */
public class DefaultSoyViewConf implements SoyViewConf {

    private boolean hotReloadMode = ConfigDefaults.GLOBAL_HOT_RELOAD_MODE;

    private String globalEncoding = ConfigDefaults.GLOBAL_CHARSET_ENCODING;

    private boolean compilePrecompileTemplates = ConfigDefaults.COMPILE_PRECOMPILE_TEMPLATES;

    private String resolveTemplatesLocation = ConfigDefaults.RESOLVE_TEMPLATES_LOCATION;

    private boolean resolveRecursive = ConfigDefaults.RESOLVE_RECURSIVE;

    private String resolveFilesExtension = ConfigDefaults.RESOLVE_FILES_EXTENSION;

    private String i18mMessagesPath = ConfigDefaults.I18N_MESSAGES_PATH;

    private boolean fallbackToEnglish = ConfigDefaults.I18N_FALLBACK_TO_ENGLISH;

    private boolean ajaxSecurityEnabled = ConfigDefaults.AJAX_SECURITY_ENABLED;

    private String ajaxAllowedUrls = ConfigDefaults.AJAX_ALLOWED_URLS;

    private String ajaxExpireHeaders = ConfigDefaults.AJAX_CACHE_CONTROL;

    private String ajaxCacheControl = ConfigDefaults.AJAX_EXPIRE_HEADERS;

    @Override
    public boolean globalHotReloadMode() {
        return hotReloadMode;
    }

    @Override
    public String globalEncoding() {
        return globalEncoding;
    }

    @Override
    public boolean compilePrecompileTemplates() {
        return compilePrecompileTemplates;
    }

    @Override
    public String resolveTemplatesLocation() {
        return resolveTemplatesLocation;
    }

    @Override
    public boolean resolveRecursive() {
        return resolveRecursive;
    }

    @Override
    public String resolveFilesExtension() {
        return resolveFilesExtension;
    }

    @Override
    public String i18nMessagesPath() {
        return i18mMessagesPath;
    }

    @Override
    public boolean i18nFallbackToEnglish() {
        return fallbackToEnglish;
    }

    @Override
    public boolean ajaxSecurityEnabled() {
        return ajaxSecurityEnabled;
    }

    @Override
    public String ajaxAllowedUrls() {
        return ajaxAllowedUrls;
    }

    @Override
    public String ajaxExpireHeaders() {
        return ajaxExpireHeaders;
    }

    @Override
    public String ajaxCacheControl() {
        return ajaxCacheControl;
    }


    public static class Builder {

        private DefaultSoyViewConf defaultSoyViewConf;

        private Builder() {
            defaultSoyViewConf = new DefaultSoyViewConf();
        }

        public Builder withHotReloadMode(boolean hotReloadMode) {
            defaultSoyViewConf.hotReloadMode = hotReloadMode;
            return this;
        }

        public Builder withGlobalEncoding(String globalEncoding) {
            defaultSoyViewConf.globalEncoding = globalEncoding;
            return this;
        }

        public Builder withCompilePrecompileTemplates(boolean compilePrecompileTemplates) {
            defaultSoyViewConf.compilePrecompileTemplates = compilePrecompileTemplates;
            return this;
        }

        public Builder withResolveTemplatesLocation(String resolveTemplatesLocation) {
            defaultSoyViewConf.resolveTemplatesLocation = resolveTemplatesLocation;
            return this;
        }

        public Builder withResolveRecursive(boolean resolveRecursive) {
            defaultSoyViewConf.resolveRecursive = resolveRecursive;
            return this;
        }

        public Builder withResolveFilesExtension(String resolveFilesExtension) {
            defaultSoyViewConf.resolveFilesExtension = resolveFilesExtension;
            return this;
        }

        public Builder withI18mMessagesPath(String i18mMessagesPath) {
            defaultSoyViewConf.i18mMessagesPath = i18mMessagesPath;
            return this;
        }

        public Builder withFallbackToEnglish(boolean fallbackToEnglish) {
            defaultSoyViewConf.fallbackToEnglish = fallbackToEnglish;
            return this;
        }

        public Builder withAjaxSecurityEnabled(boolean ajaxSecurityEnabled) {
            defaultSoyViewConf.ajaxSecurityEnabled = ajaxSecurityEnabled;
            return this;
        }

        public Builder withAjaxAllowedUrls(String ajaxAllowedUrls) {
            defaultSoyViewConf.ajaxAllowedUrls = ajaxAllowedUrls;
            return this;
        }

        public Builder withAjaxExpireHeaders(String ajaxExpireHeaders) {
            defaultSoyViewConf.ajaxExpireHeaders = ajaxExpireHeaders;
            return this;
        }

        public Builder withAjaxCacheControl(String ajaxCacheControl) {
            defaultSoyViewConf.ajaxCacheControl = ajaxCacheControl;
            return this;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public DefaultSoyViewConf build(){
            return defaultSoyViewConf;
        }

    }

}
