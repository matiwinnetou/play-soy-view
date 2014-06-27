package com.github.mati1979.play.soyplugin.render;

import com.github.mati1979.play.soyplugin.config.SoyViewConf;
import java.util.Optional;
import com.google.template.soy.data.SoyMapData;
import com.google.template.soy.msgs.SoyMsgBundle;
import com.google.template.soy.tofu.SoyTofu;

public class DefaultTemplateRenderer implements TemplateRenderer {

    private SoyViewConf soyViewConf;

    public DefaultTemplateRenderer(final SoyViewConf soyViewConf) {
        this.soyViewConf = soyViewConf;
    }

    @Override
    public String render(final RenderRequest renderRequest) {
        final SoyTofu compiledTemplates = renderRequest.getCompiledTemplates().get();

        final String templateName = renderRequest.getTemplateName();
        final SoyTofu.Renderer renderer = compiledTemplates.newRenderer(templateName);

        final Optional<SoyMapData> soyModel = renderRequest.getSoyModel();
        setupRenderer(renderer, renderRequest, soyModel);

        return renderer.render();
    }

    protected void setupRenderer(final SoyTofu.Renderer renderer, final RenderRequest renderRequest, final Optional<SoyMapData> model) {
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
            if (!soyViewConf.globalHotReloadMode()) {
                if (renderRequest.getCompiledTemplates().isPresent()) {
                    renderRequest.getCompiledTemplates().get().addToCache(soyMsgBundleOptional.get(), null);
                }
            }
        }
        if (soyViewConf.globalHotReloadMode()) {
            renderer.setDontAddToCache(true);
        }
    }

}
