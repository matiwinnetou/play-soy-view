package com.github.mati1979.play.soyplugin.ajax.allowedurls;

import com.github.mati1979.play.soyplugin.config.SoyViewConf;
import com.github.mati1979.play.soyplugin.template.TemplateFilesResolver;
import com.google.common.base.*;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mszczap on 26.04.14.
 */
public class DefaultSoyAllowedUrlsResolver implements SoyAllowedUrlsResolver {

    private final TemplateFilesResolver templateFilesResolver;
    private final SoyViewConf soyViewConf;

    public DefaultSoyAllowedUrlsResolver(final TemplateFilesResolver templateFilesResolver,
                                         final SoyViewConf soyViewConf) {
        this.templateFilesResolver = templateFilesResolver;
        this.soyViewConf = soyViewConf;
    }

    @Override
    public SoyAllowedUrls allowedUrls() {
        final List<String> templateNames = Arrays.asList(soyViewConf.ajaxAllowedUrls().split(","));
        final Map<String,Supplier<Optional<URL>>> cache = urlsCache(templateFilesResolver, templateNames);

        final ImmutableList<URL> urls = FluentIterable.from(templateNames)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean apply(final String templateName) {
                        return cache.get(templateName).get().isPresent();
                    }
                })
                .transform(new Function<String, URL>() {
                    @Override
                    public URL apply(final String templateName) {
                        return cache.get(templateName).get().get();
                    }
                })
                .toList();

        return new SoyAllowedUrls(urls, templateNames, soyViewConf.ajaxSecurityEnabled());
    }

    private Map<String,Supplier<Optional<URL>>> urlsCache(final TemplateFilesResolver templateFilesResolver,
                                                          final List<String> templateNames) {
        final HashMap cache = new HashMap<String,Supplier<Optional<URL>>>();
        for (final String templateName : templateNames) {
            final Supplier<Optional<URL>> memoize = Suppliers.memoize(new Supplier<Optional<URL>>() {

                @Override
                public Optional<URL> get() {
                    try {
                        return templateFilesResolver.resolve(templateName);
                    } catch (final IOException e) {
                        return Optional.absent();
                    }
                }

            });

            cache.put(templateName, memoize);
        }

        return cache;
    }

}
