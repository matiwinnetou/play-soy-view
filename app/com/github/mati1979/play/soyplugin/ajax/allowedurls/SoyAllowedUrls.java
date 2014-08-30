package com.github.mati1979.play.soyplugin.ajax.allowedurls;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.net.URL;
import java.util.List;

/**
 * Created by mszczap on 25.04.14.
 */
public class SoyAllowedUrls {

    private List<String> urls = Lists.newArrayList();

    private List<URL> resolvedUrls = ImmutableList.of();

    private boolean enabledSecurity = false;

    public SoyAllowedUrls(final List<URL> resolvedUrls,
                          final List<String> urls,
                          boolean enabledSecurity) {
        this.urls = urls;
        this.resolvedUrls = resolvedUrls;
        this.enabledSecurity = enabledSecurity;
    }

    private SoyAllowedUrls() {
    }

    public boolean isEnabledSecurity() {
        return enabledSecurity;
    }

    public List<String> urls() {
        return urls;
    }

    public List<URL> resolvedUrls() {
        return resolvedUrls;
    }

    public static SoyAllowedUrls empty() {
        return new SoyAllowedUrls();
    }

    @Override
    public String toString() {
        return "SoyAllowedUrls{" +
                "urls=" + urls +
                ", resolvedUrls=" + resolvedUrls +
                ", enabledSecurity=" + enabledSecurity +
                '}';
    }

}
