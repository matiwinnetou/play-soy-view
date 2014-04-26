package com.github.mati1979.play.soyplugin.ajax.auth;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mati
 * Date: 12/10/2013
 * Time: 23:44
 *
 * A configurable implementation of AuthManager that simply takes a list of allowed
 * templates to be compiled from an internal unmodifiable list
 */
public class ConfigurableAuthManager implements AuthManager {

    /**friendly*/ List<String> allowedTemplates = Lists.newArrayList();

    public ConfigurableAuthManager(final List<String> allowedTemplates) {
        this.allowedTemplates = allowedTemplates;
    }

    @Override
    public boolean isAllowed(final String url) {
        return allowedTemplates.contains(url);
    }

}
