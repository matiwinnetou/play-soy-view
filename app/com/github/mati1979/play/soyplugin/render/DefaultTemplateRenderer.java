package com.github.mati1979.play.soyplugin.render;

import com.github.mati1979.play.soyplugin.config.SoyViewConf;
import com.google.template.soy.data.SoyMapData;
import com.google.template.soy.msgs.SoyMsgBundle;
import com.google.template.soy.tofu.SoyTofu;

import javax.inject.Inject;
import java.util.Optional;

public class DefaultTemplateRenderer implements TemplateRenderer {

    private static final play.Logger.ALogger logger = play.Logger.of(DefaultTemplateRenderer.class);

    private SoyViewConf soyViewConf;

    @Inject
    public DefaultTemplateRenderer(final SoyViewConf soyViewConf) {
        this.soyViewConf = soyViewConf;
    }

    @Override
    public String render(final RenderRequest renderRequest) {
        final SoyTofu compiledTemplates = renderRequest.getCompiledTemplates().get();

        final String templateName = renderRequest.getTemplateName();
        logger.debug("rendering templateName:" + templateName);

        final SoyTofu.Renderer renderer = compiledTemplates.newRenderer(templateName);

        final Optional<SoyMapData> soyModel = renderRequest.getSoyModel();
        setupRenderer(renderer, renderRequest, soyModel);

        return renderer.render();
    }

    protected void setupRenderer(final SoyTofu.Renderer renderer, final RenderRequest renderRequest, final Optional<SoyMapData> model) {
        if (model.isPresent()) {
            logger.debug("renderer - model is available.");
            renderer.setData(model.get());
        }
        final Optional<SoyMapData> globalModel = renderRequest.getGlobalRuntimeModel();
        if (globalModel.isPresent()) {
            logger.debug("renderer - global runtime model is available.");
            renderer.setIjData(globalModel.get());
        }
        final Optional<SoyMsgBundle> soyMsgBundleOpt = renderRequest.getSoyMsgBundle();
        if (soyMsgBundleOpt.isPresent()) {
            logger.debug("renderer - soyMsgBundle is available.");
            renderer.setMsgBundle(soyMsgBundleOpt.get());
            if (!soyViewConf.globalHotReloadMode()) {
                if (renderRequest.getCompiledTemplates().isPresent()) {
                    renderRequest.getCompiledTemplates().get().addToCache(soyMsgBundleOpt.get(), null);
                }
            }
        }
        if (soyViewConf.globalHotReloadMode()) {
            renderer.setDontAddToCache(true);
        }
    }

}
