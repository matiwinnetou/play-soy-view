package com.github.mati1979.play.soyplugin.render;

import com.github.mati1979.play.soyplugin.config.ConfigDefaults;
import com.google.common.base.Optional;
import com.google.template.soy.data.SoyMapData;
import com.google.template.soy.msgs.SoyMsgBundle;
import com.google.template.soy.tofu.SoyTofu;
import com.github.mati1979.play.soyplugin.config.ConfigKeys;
import play.Play;

public class DefaultTemplateRenderer implements TemplateRenderer {

    public DefaultTemplateRenderer() {
    }

    private static final play.Logger.ALogger logger = play.Logger.of(DefaultTemplateRenderer.class);

    /**
     * whether debug is on, in case it is on - Soy's Renderer Don't Add To Cache will be turned on, which means
     * renderer caching will be disabled */
    private boolean hotReloadMode = Play.application().configuration().getBoolean(ConfigKeys.GLOBAL_HOT_RELOAD_MODE, ConfigDefaults.GLOBAL_HOT_RELOAD_MODE);

    private String charsetEncoding = Play.application().configuration().getString(ConfigKeys.GLOBAL_CHARSET_ENCODING, ConfigDefaults.GLOBAL_CHARSET_ENCODING);

    @Override
    public String render(final RenderRequest renderRequest) throws Exception {
        final SoyTofu compiledTemplates = renderRequest.getCompiledTemplates().get();

        final String templateName = renderRequest.getTemplateName();
        final SoyTofu.Renderer renderer = compiledTemplates.newRenderer(templateName);

        final Optional<SoyMapData> soyModel = renderRequest.getSoyModel();
        setupRenderer(renderer, renderRequest, soyModel);

        renderRequest.getResponse().setContentType("text/html; charset=" + charsetEncoding);

        return renderer.render();
    }

    protected void setupRenderer(final SoyTofu.Renderer renderer, final RenderRequest renderRequest, final Optional<SoyMapData> model) throws Exception {
        if (model.isPresent()) {
            renderer.setData(model.get());
        }
        final Optional<SoyMapData> globalModel = renderRequest.getGlobalRuntimeModel();
        if (globalModel.isPresent()) {
            renderer.setIjData(globalModel.get());
        }
        final Optional<SoyMsgBundle> soyMsgBundleOptional = renderRequest.getSoyMsgBundle();
        if (soyMsgBundleOptional.isPresent()) {
            renderer.setMsgBundle(soyMsgBundleOptional.get());
            if (isHotReloadModeOff()) {
                if (renderRequest.getCompiledTemplates().isPresent()) {
                    renderRequest.getCompiledTemplates().get().addToCache(soyMsgBundleOptional.get(), null);
                }
            }
        }
        if (isHotReloadMode()) {
            renderer.setDontAddToCache(true);
        }
    }

    public void setHotReloadMode(boolean hotReloadMode) {
        this.hotReloadMode = hotReloadMode;
    }

    public boolean isHotReloadMode() {
        return hotReloadMode;
    }

    public boolean isHotReloadModeOff() {
        return !hotReloadMode;
    }

}
