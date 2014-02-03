package com.github.mati1979.play.soyplugin.ajax.runtime;

import com.github.mati1979.play.soyplugin.ajax.hash.HashFileGenerator;
import com.github.mati1979.play.soyplugin.global.runtime.RuntimeDataResolver;
import com.github.mati1979.play.soyplugin.template.TemplateFilesResolver;
import com.google.common.base.*;
import com.google.common.collect.FluentIterable;
import com.google.template.soy.data.SoyMapData;
import play.mvc.Http;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.mati1979.play.soyplugin.config.PlayConfAccessor.AJAX_ALLOWED_URLS;

/**
 * Created with IntelliJ IDEA.
 * User: mszczap
 * Date: 14.11.13
 * Time: 11:02
 */
public class SoyHashesRuntimeDataResolver implements RuntimeDataResolver {

    private HashFileGenerator hashFileGenerator;

    private TemplateFilesResolver templateFilesResolver;

    private String ajaxAllowedUrls = AJAX_ALLOWED_URLS;

    public SoyHashesRuntimeDataResolver(HashFileGenerator hashFileGenerator, TemplateFilesResolver templateFilesResolver) {
        this.hashFileGenerator = hashFileGenerator;
        this.templateFilesResolver = templateFilesResolver;
    }

    public SoyHashesRuntimeDataResolver() {
    }

    public void resolveData(final Http.Request request, final Http.Response response, final Map<String, ?> model, final SoyMapData root) {
        try {
            root.put("soyplugin.ajax.soy.hash", hashFileGenerator.hashMulti(urls()).or(""));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<URL> urls() {
        final List<String> templateNames = Arrays.asList(ajaxAllowedUrls.split(","));
        final Map<String,Supplier<Optional<URL>>> cache = urlsCache(templateNames);

        return FluentIterable.from(templateNames)
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
    }

    private Map<String,Supplier<Optional<URL>>> urlsCache(final List<String> templateNames) {
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

    public void setHashFileGenerator(HashFileGenerator hashFileGenerator) {
        this.hashFileGenerator = hashFileGenerator;
    }

    public void setTemplateFilesResolver(TemplateFilesResolver templateFilesResolver) {
        this.templateFilesResolver = templateFilesResolver;
    }

    public void setAjaxAllowedUrls(String ajaxAllowedUrls) {
        this.ajaxAllowedUrls = ajaxAllowedUrls;
    }

}
