package com.github.mati1979.play.soyplugin.ajax.allowedurls;

import com.github.mati1979.play.soyplugin.config.SoyViewConf;
import com.github.mati1979.play.soyplugin.resolve.TemplateFilesResolver;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by mszczap on 26.04.14.
 */
public class DefaultSoyAllowedUrlsResolver implements SoyAllowedUrlsResolver {

    private static final play.Logger.ALogger logger = play.Logger.of(DefaultSoyAllowedUrlsResolver.class);

    private final TemplateFilesResolver templateFilesResolver;
    private final SoyViewConf soyViewConf;

    @Inject
    public DefaultSoyAllowedUrlsResolver(final TemplateFilesResolver templateFilesResolver,
                                         final SoyViewConf soyViewConf) {
        this.templateFilesResolver = templateFilesResolver;
        this.soyViewConf = soyViewConf;
    }

    @Override
    public SoyAllowedUrls allowedUrls() {
        final List<String> templateNames = Arrays.asList(soyViewConf.ajaxAllowedUrls().split(","));
        final Map<String, Supplier<Optional<URL>>> cache = urlsCache(templateFilesResolver, templateNames);

        final ImmutableList<URL> urls = FluentIterable.from(templateNames)
                .filter(templateName -> cache.get(templateName).get().isPresent())
                .transform(templateName -> cache.get(templateName).get().get())
                .toList();

        logger.debug("AllowedUrls:" + urls);

        return new SoyAllowedUrls(urls, templateNames, soyViewConf.ajaxSecurityEnabled());
    }

    private Map<String,Supplier<Optional<URL>>> urlsCache(final TemplateFilesResolver templateFilesResolver,
                                                          final List<String> templateNames) {
        final HashMap cache = new HashMap<String,Supplier<Optional<URL>>>();
        for (final String templateName : templateNames) {
            final Supplier<Optional<URL>> memoize = Suppliers.memoize(() -> {
                try {
                    return templateFilesResolver.resolve(templateName);
                } catch (final IOException e) {
                    return Optional.empty();
                }
            });

            cache.put(templateName, memoize);
        }

        return cache;
    }

}
