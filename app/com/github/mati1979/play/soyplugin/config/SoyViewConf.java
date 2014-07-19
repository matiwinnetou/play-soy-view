package com.github.mati1979.play.soyplugin.config;

/**
 * Created by mszczap on 26.04.14.
 */
public interface SoyViewConf {

    boolean globalHotReloadMode();

    int globalHotReloadCompileTimeInSecs();

    String globalEncoding();

    boolean compilePrecompileTemplates();


    String resolveTemplatesLocation();

    boolean resolveRecursive();

    String resolveFilesExtension();

    /**
     * will cache msgBundles if a hotReloadMode is off, if debug is on,
     *  will compile msg bundles each time it is invoked */
    String i18nMessagesPath();

    /**
     * in case translation is missing for a passed in locale,
     * whether the implementation should fallback to English returning
     * an english translation if available
     */
    boolean i18nFallbackToEnglish();


    boolean ajaxSecurityEnabled();

    String ajaxAllowedUrls(); //comma separated

    String ajaxExpireHeaders();

    String ajaxCacheControl();

}
