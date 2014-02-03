package pl.matisoft.soy.plugin;

import com.google.common.base.Optional;
import com.google.template.soy.data.SoyMapData;
import pl.matisoft.soy.bundle.EmptySoyMsgBundleResolver;
import pl.matisoft.soy.bundle.SoyMsgBundleResolver;
import pl.matisoft.soy.data.EmptyToSoyDataConverter;
import pl.matisoft.soy.data.ToSoyDataConverter;
import pl.matisoft.soy.global.runtime.EmptyGlobalRuntimeModelResolver;
import pl.matisoft.soy.global.runtime.GlobalRuntimeModelResolver;
import pl.matisoft.soy.holder.CompiledTemplatesHolder;
import pl.matisoft.soy.holder.EmptyCompiledTemplatesHolder;
import pl.matisoft.soy.locale.EmptyLocaleProvider;
import pl.matisoft.soy.locale.LocaleProvider;
import pl.matisoft.soy.render.DefaultTemplateRenderer;
import pl.matisoft.soy.render.RenderRequest;
import pl.matisoft.soy.render.TemplateRenderer;
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

    @Override
    public String html(final String view, Object model) throws Exception {
        return html(view, toSoyDataConverter.toSoyMap(model));
    }

    @Override
    public String html(final String view, final SoyMapData model) throws Exception {
        return html(view, Optional.of(model));
    }

    @Override
    public String html(String view) throws Exception {
        return html(view, Optional.<SoyMapData>absent());
    }

    private String html(final String viewName, final Optional<SoyMapData> soyMapData) throws Exception {
        final Http.Request request = Http.Context.current().request();
        final Http.Response response = Http.Context.current().response();
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
