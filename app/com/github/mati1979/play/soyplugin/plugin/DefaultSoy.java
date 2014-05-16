package com.github.mati1979.play.soyplugin.plugin;

import com.github.mati1979.play.soyplugin.bundle.EmptySoyMsgBundleResolver;
import com.github.mati1979.play.soyplugin.bundle.SoyMsgBundleResolver;
import com.github.mati1979.play.soyplugin.config.EmptySoyViewConf;
import com.github.mati1979.play.soyplugin.data.EmptyToSoyDataConverter;
import com.github.mati1979.play.soyplugin.data.ToSoyDataConverter;
import com.github.mati1979.play.soyplugin.exception.ExceptionInTemplate;
import com.github.mati1979.play.soyplugin.global.runtime.EmptyGlobalRuntimeModelResolver;
import com.github.mati1979.play.soyplugin.global.runtime.GlobalRuntimeModelResolver;
import com.github.mati1979.play.soyplugin.holder.CompiledTemplatesHolder;
import com.github.mati1979.play.soyplugin.holder.EmptyCompiledTemplatesHolder;
import com.github.mati1979.play.soyplugin.locale.EmptyLocaleProvider;
import com.github.mati1979.play.soyplugin.locale.LocaleProvider;
import com.github.mati1979.play.soyplugin.render.DefaultTemplateRenderer;
import com.github.mati1979.play.soyplugin.render.RenderRequest;
import com.github.mati1979.play.soyplugin.render.TemplateRenderer;
import com.google.common.base.Optional;
import com.google.template.soy.base.SoySyntaxException;
import com.google.template.soy.data.SoyMapData;
import play.mvc.Http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by mati on 02/02/2014.
 */
public class DefaultSoy implements Soy {

    private CompiledTemplatesHolder compiledTemplatesHolder = new EmptyCompiledTemplatesHolder();

    private GlobalRuntimeModelResolver globalRuntimeModelResolver = new EmptyGlobalRuntimeModelResolver();

    private SoyMsgBundleResolver soyMsgBundleResolver = new EmptySoyMsgBundleResolver();

    private TemplateRenderer templateRenderer = new DefaultTemplateRenderer(new EmptySoyViewConf());

    private ToSoyDataConverter toSoyDataConverter = new EmptyToSoyDataConverter();

    private LocaleProvider localeProvider = new EmptyLocaleProvider();

    public DefaultSoy(final CompiledTemplatesHolder compiledTemplatesHolder,
                      final GlobalRuntimeModelResolver globalRuntimeModelResolver,
                      final SoyMsgBundleResolver soyMsgBundleResolver,
                      final TemplateRenderer templateRenderer,
                      final ToSoyDataConverter toSoyDataConverter,
                      final LocaleProvider localeProvider) {
        this.compiledTemplatesHolder = compiledTemplatesHolder;
        this.globalRuntimeModelResolver = globalRuntimeModelResolver;
        this.soyMsgBundleResolver = soyMsgBundleResolver;
        this.templateRenderer = templateRenderer;
        this.toSoyDataConverter = toSoyDataConverter;
        this.localeProvider = localeProvider;
    }

    @Override
    public String html(final String view, final Object model) {
        final Http.Request request = Http.Context.current().request();
        final Http.Response response = Http.Context.current().response();

        return htmlPriv(request, response, view, toSoyDataConverter.toSoyMap(model));
    }

    @Override
    public String html(final String view, final SoyMapData model) {
        final Http.Request request = Http.Context.current().request();
        final Http.Response response = Http.Context.current().response();

        return htmlPriv(request, response, view, Optional.of(model));
    }

    @Override
    public String html(final String view) {
        final Http.Request request = Http.Context.current().request();
        final Http.Response response = Http.Context.current().response();

        return htmlPriv(request, response, view, Optional.<SoyMapData>absent());
    }

    @Override
    public String html(final Http.Request request, final Http.Response response, final String view, final SoyMapData soyMapData) {
        return htmlPriv(request, response, view, Optional.fromNullable(soyMapData));
    }

    @Override
    public String html(final Http.Request request, final Http.Response response, final String view, final Object model) {
        return htmlPriv(request, response, view, toSoyDataConverter.toSoyMap(model));
    }

    @Override
    public String html(final Http.Request request, final Http.Response response, final String view) {
        return htmlPriv(request, response, view, Optional.<SoyMapData>absent());
    }

    private String htmlPriv(final Http.Request request, final Http.Response response, final String viewName, final Optional<SoyMapData> soyMapData) {
        final Optional<Locale> localeOptional = localeProvider.resolveLocale(request);

        try {
            final RenderRequest renderRequest = new RenderRequest.Builder()
                    .templateName(viewName)
                    .compiledTemplates(compiledTemplatesHolder.compiledTemplates())
                    .globalRuntimeModel(globalRuntimeModelResolver.resolveData(request, response, runtimeData(soyMapData)))
                    .soyMsgBundle(soyMsgBundleResolver.resolve(localeOptional))
                    .soyModel(soyMapData)
                    .build();
            return templateRenderer.render(renderRequest);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        } catch (final SoySyntaxException e) {
            throw ExceptionInTemplate.createExceptionInTemplate(e);
        }
    }

    private Map runtimeData(final Optional<SoyMapData> soyMapData) {
        Map runtimeData = new HashMap();
        if (soyMapData.isPresent()) {
            runtimeData = soyMapData.get().asMap();
        }

        return runtimeData;
    }

}
