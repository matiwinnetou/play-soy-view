package com.github.mati1979.play.soyplugin.plugin;

import com.github.mati1979.play.soyplugin.bundle.EmptySoyMsgBundleResolver;
import com.github.mati1979.play.soyplugin.bundle.SoyMsgBundleResolver;
import com.github.mati1979.play.soyplugin.data.EmptyToSoyDataConverter;
import com.github.mati1979.play.soyplugin.data.ToSoyDataConverter;
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
import com.google.template.soy.data.SoyMapData;
import play.mvc.Http;

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

    private TemplateRenderer templateRenderer = new DefaultTemplateRenderer();

    private ToSoyDataConverter toSoyDataConverter = new EmptyToSoyDataConverter();

    private LocaleProvider localeProvider = new EmptyLocaleProvider();

    public DefaultSoy(CompiledTemplatesHolder compiledTemplatesHolder, GlobalRuntimeModelResolver globalRuntimeModelResolver, SoyMsgBundleResolver soyMsgBundleResolver, TemplateRenderer templateRenderer, ToSoyDataConverter toSoyDataConverter, LocaleProvider localeProvider) {
        this.compiledTemplatesHolder = compiledTemplatesHolder;
        this.globalRuntimeModelResolver = globalRuntimeModelResolver;
        this.soyMsgBundleResolver = soyMsgBundleResolver;
        this.templateRenderer = templateRenderer;
        this.toSoyDataConverter = toSoyDataConverter;
        this.localeProvider = localeProvider;
    }

    public DefaultSoy() {
    }

    @Override
    public String html(final String view, final Object model) throws Exception {
        final Http.Request request = Http.Context.current().request();
        final Http.Response response = Http.Context.current().response();

        return htmlPriv(request, response, view, toSoyDataConverter.toSoyMap(model));
    }

    @Override
    public String html(final String view, final SoyMapData model) throws Exception {
        final Http.Request request = Http.Context.current().request();
        final Http.Response response = Http.Context.current().response();

        return htmlPriv(request, response, view, Optional.of(model));
    }

    @Override
    public String html(String view) throws Exception {
        final Http.Request request = Http.Context.current().request();
        final Http.Response response = Http.Context.current().response();

        return htmlPriv(request, response, view, Optional.<SoyMapData>absent());
    }

    @Override
    public String html(Http.Request request, Http.Response response, String view, SoyMapData soyMapData) throws Exception {
        return htmlPriv(request, response, view, Optional.fromNullable(soyMapData));
    }

    @Override
    public String html(Http.Request request, Http.Response response, String view, Object model) throws Exception {
        return htmlPriv(request, response, view, toSoyDataConverter.toSoyMap(model));
    }

    @Override
    public String html(Http.Request request, Http.Response response, String view) throws Exception {
        return htmlPriv(request, response, view, Optional.<SoyMapData>absent());
    }

    private String htmlPriv(Http.Request request, Http.Response response, final String viewName, final Optional<SoyMapData> soyMapData) throws Exception {
        final Optional<Locale> localeOptional = localeProvider.resolveLocale(request);

        final RenderRequest renderRequest = new RenderRequest.Builder()
                .request(request)
                .response(response)
                .templateName(viewName)
                .compiledTemplates(compiledTemplatesHolder.compiledTemplates())
                .globalRuntimeModel(globalRuntimeModelResolver.resolveData(request, response, runtimeData(soyMapData)))
                .soyMsgBundle(soyMsgBundleResolver.resolve(localeOptional))
                .soyModel(soyMapData)
                .build();

        return templateRenderer.render(renderRequest);
    }

    private Map runtimeData(final Optional<SoyMapData> soyMapData) {
        Map runtimeData = new HashMap();
        if (soyMapData.isPresent()) {
            runtimeData = soyMapData.get().asMap();
        }

        return runtimeData;
    }

    public CompiledTemplatesHolder getCompiledTemplatesHolder() {
        return compiledTemplatesHolder;
    }

    public void setCompiledTemplatesHolder(CompiledTemplatesHolder compiledTemplatesHolder) {
        this.compiledTemplatesHolder = compiledTemplatesHolder;
    }

    public GlobalRuntimeModelResolver getGlobalRuntimeModelResolver() {
        return globalRuntimeModelResolver;
    }

    public void setGlobalRuntimeModelResolver(GlobalRuntimeModelResolver globalRuntimeModelResolver) {
        this.globalRuntimeModelResolver = globalRuntimeModelResolver;
    }

    public SoyMsgBundleResolver getSoyMsgBundleResolver() {
        return soyMsgBundleResolver;
    }

    public void setSoyMsgBundleResolver(SoyMsgBundleResolver soyMsgBundleResolver) {
        this.soyMsgBundleResolver = soyMsgBundleResolver;
    }

    public TemplateRenderer getTemplateRenderer() {
        return templateRenderer;
    }

    public void setTemplateRenderer(TemplateRenderer templateRenderer) {
        this.templateRenderer = templateRenderer;
    }

    public ToSoyDataConverter getToSoyDataConverter() {
        return toSoyDataConverter;
    }

    public void setToSoyDataConverter(ToSoyDataConverter toSoyDataConverter) {
        this.toSoyDataConverter = toSoyDataConverter;
    }

    public LocaleProvider getLocaleProvider() {
        return localeProvider;
    }

    public void setLocaleProvider(LocaleProvider localeProvider) {
        this.localeProvider = localeProvider;
    }

}
