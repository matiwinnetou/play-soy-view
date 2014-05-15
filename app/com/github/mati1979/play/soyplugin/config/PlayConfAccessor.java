package com.github.mati1979.play.soyplugin.config;

import com.google.common.base.Optional;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;
import play.Configuration;
import play.Play;

/**
 * Created by mati on 03/02/2014.
 */
public class PlayConfAccessor {

    public static Optional<play.Configuration> getPluginConfig() {
        final Config defaultConfig = defaultConfig();
        final Configuration config = Play.application().configuration().getConfig("ClosurePlugin.config.plugin");

        if (config == null) {
            return Optional.of(new Configuration(defaultConfig));
        }

        final Config merged = config.underlying().withFallback(defaultConfig);

        return Optional.of(new Configuration(merged));
    }

    public static Optional<Configuration> getUserConfig() {
        return Optional.fromNullable(Play.application().configuration().getConfig("ClosurePlugin.config.user"));
    }

    public static Config defaultConfig() {
        return ConfigFactory.empty()
                .withValue(ConfigKeys.GLOBAL_HOT_RELOAD_MODE, ConfigValueFactory.fromAnyRef(ConfigDefaults.GLOBAL_HOT_RELOAD_MODE))
                .withValue(ConfigKeys.GLOBAL_CHARSET_ENCODING, ConfigValueFactory.fromAnyRef(ConfigDefaults.GLOBAL_CHARSET_ENCODING))

                .withValue(ConfigKeys.COMPILE_PRECOMPILE_TEMPLATES, ConfigValueFactory.fromAnyRef(ConfigDefaults.COMPILE_PRECOMPILE_TEMPLATES))
                .withValue(ConfigKeys.RESOLVE_TEMPLATES_LOCATION, ConfigValueFactory.fromAnyRef(ConfigDefaults.RESOLVE_TEMPLATES_LOCATION))
                .withValue(ConfigKeys.RESOLVE_FILE_EXTENSION, ConfigValueFactory.fromAnyRef(ConfigDefaults.RESOLVE_FILES_EXTENSION))
                .withValue(ConfigKeys.RESOLVE_RECURSIVE, ConfigValueFactory.fromAnyRef(ConfigDefaults.RESOLVE_RECURSIVE))

                .withValue(ConfigKeys.I18N_MESSAGES_PATH, ConfigValueFactory.fromAnyRef(ConfigDefaults.I18N_MESSAGES_PATH))
                .withValue(ConfigKeys.I18N_FALLBACK_TO_ENGLISH, ConfigValueFactory.fromAnyRef(ConfigDefaults.I18N_FALLBACK_TO_ENGLISH))

                .withValue(ConfigKeys.AJAX_SECURITY_ENABLED, ConfigValueFactory.fromAnyRef(ConfigDefaults.AJAX_SECURITY_ENABLED))
                .withValue(ConfigKeys.AJAX_ALLOWED_URLS, ConfigValueFactory.fromAnyRef(ConfigDefaults.AJAX_ALLOWED_URLS))
                .withValue(ConfigKeys.AJAX_CACHE_CONTROL, ConfigValueFactory.fromAnyRef(ConfigDefaults.AJAX_EXPIRE_HEADERS))
                .withValue(ConfigKeys.AJAX_CACHE_CONTROL, ConfigValueFactory.fromAnyRef(ConfigDefaults.AJAX_CACHE_CONTROL))
                ;
    }

    //GLOBAL
    public static boolean GLOBAL_HOT_RELOAD_MODE = getPluginConfig().or(Configuration.root()).getBoolean(ConfigKeys.GLOBAL_HOT_RELOAD_MODE, ConfigDefaults.GLOBAL_HOT_RELOAD_MODE);

    public static String GLOBAL_ENCODING = getPluginConfig().or(Configuration.root()).getString(ConfigKeys.GLOBAL_CHARSET_ENCODING, ConfigDefaults.GLOBAL_CHARSET_ENCODING);

    //COMPILE
    public static boolean COMPILE_PRECOMPILE_TEMPLATES = getPluginConfig().or(Configuration.root()).getBoolean(ConfigKeys.COMPILE_PRECOMPILE_TEMPLATES, ConfigDefaults.COMPILE_PRECOMPILE_TEMPLATES);

    public static String COMPILE_TEMPLATES_LOCATION = getPluginConfig().or(Configuration.root()).getString(ConfigKeys.RESOLVE_TEMPLATES_LOCATION, ConfigDefaults.RESOLVE_TEMPLATES_LOCATION);

    public static boolean COMPILE_RECURSIVE = getPluginConfig().or(Configuration.root()).getBoolean(ConfigKeys.RESOLVE_RECURSIVE, ConfigDefaults.RESOLVE_RECURSIVE);

    public static String COMPILE_FILES_EXTENSION = getPluginConfig().or(Configuration.root()).getString(ConfigKeys.RESOLVE_FILE_EXTENSION, ConfigDefaults.RESOLVE_FILES_EXTENSION);

    //I18N
    public static String I18N_MESSAGES_PATH = getPluginConfig().or(Configuration.root()).getString(ConfigKeys.I18N_MESSAGES_PATH, ConfigDefaults.I18N_MESSAGES_PATH);

    public static boolean I18N_FALLBACK_TO_ENGLISH = getPluginConfig().or(Configuration.root()).getBoolean(ConfigKeys.I18N_FALLBACK_TO_ENGLISH, ConfigDefaults.I18N_FALLBACK_TO_ENGLISH);

    //AJAX
    public static boolean AJAX_SECURITY_ENABLED = getPluginConfig().or(Configuration.root()).getBoolean(ConfigKeys.AJAX_SECURITY_ENABLED, ConfigDefaults.AJAX_SECURITY_ENABLED);

    public static String AJAX_ALLOWED_URLS = getPluginConfig().or(Configuration.root()).getString(ConfigKeys.AJAX_ALLOWED_URLS, ConfigDefaults.AJAX_ALLOWED_URLS);

    public static String AJAX_EXPIRE_HEADERS = getPluginConfig().or(Configuration.root()).getString(ConfigKeys.AJAX_EXPIRE_HEADERS, ConfigDefaults.AJAX_EXPIRE_HEADERS);

    public static String AJAX_CACHE_CONTROL = getPluginConfig().or(Configuration.root()).getString(ConfigKeys.AJAX_CACHE_CONTROL, ConfigDefaults.AJAX_CACHE_CONTROL);

}
