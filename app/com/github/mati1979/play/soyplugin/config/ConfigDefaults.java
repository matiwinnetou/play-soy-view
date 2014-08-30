package com.github.mati1979.play.soyplugin.config;

/**
 * Created with IntelliJ IDEA.
 * User: mati
 * Date: 23/06/2013
 * Time: 18:02
 */
public interface ConfigDefaults {

    //GLOBAL
    boolean globalHotReloadMode();

    int globalHotReloadCompileTimeInSecs();

    String globalCharsetEncoding();

    //COMPILE
    boolean compilePrecompileTemplates();

    //RESOLVE
    boolean resolveRecursive();

    String resolveFilesExtension();

    String resolveTemplatesLocation();

    //I18N
    String i18nMessagesPath();

    boolean i18nFallbackToEnglish();

    //AJAX
    boolean ajaxSecurityEnabled();

    String ajaxAllowedUrls();

    String ajaxCacheControl();

    String ajaxExpireHeaders();

}
