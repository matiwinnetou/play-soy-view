package com.github.mati1979.play.soyplugin.render;

import java.util.Optional;
import com.google.template.soy.data.SoyMapData;
import com.google.template.soy.msgs.SoyMsgBundle;
import com.google.template.soy.tofu.SoyTofu;

/**
 * Created with IntelliJ IDEA.
 * User: mszczap
 * Date: 12.07.13
 * Time: 09:28
 *
 * An objects that wraps parameters needed to render a template
 */
public class RenderRequest {

    private final Optional<SoyTofu> compiledTemplates;

    private final String templateName;

    private final Optional<SoyMapData> soyModel;

    private final Optional<SoyMapData> globalRuntimeModel;

    private final Optional<SoyMsgBundle> soyMsgBundle;

    private RenderRequest(final Builder builder) {
        this.compiledTemplates = builder.compiledTemplates;
        this.templateName = builder.templateName;
        this.globalRuntimeModel = builder.globalRuntimeModel;
        this.soyMsgBundle = builder.soyMsgBundle;
        this.soyModel = builder.soyModel;
    }

    public Optional<SoyMapData> getSoyModel() {
        return soyModel;
    }

    public Optional<SoyMapData> getGlobalRuntimeModel() {
        return globalRuntimeModel;
    }

    public Optional<SoyMsgBundle> getSoyMsgBundle() {
        return soyMsgBundle;
    }

    public Optional<SoyTofu> getCompiledTemplates() {
        return compiledTemplates;
    }

    public String getTemplateName() {
        return templateName;
    }

    public static class Builder {

        private Optional<SoyTofu> compiledTemplates;
        private String templateName;

        private Optional<SoyMapData> soyModel;

        private Optional<SoyMapData> globalRuntimeModel = Optional.empty();
        private Optional<SoyMsgBundle> soyMsgBundle = Optional.empty();

        public Builder compiledTemplates(final Optional<SoyTofu> compiledTemplates) {
            this.compiledTemplates = compiledTemplates;
            return this;
        }

        public Builder soyModel(final Optional<SoyMapData> soyModel) {
            this.soyModel = soyModel;

            return this;
        }

        public Builder templateName(final String templateName) {
            this.templateName = templateName;
            return this;
        }

        public Builder globalRuntimeModel(final Optional<SoyMapData> globalRuntimeModel) {
            this.globalRuntimeModel = globalRuntimeModel;
            return this;
        }

        public Builder soyMsgBundle(final Optional<SoyMsgBundle> soyMsgBundle) {
            this.soyMsgBundle = soyMsgBundle;
            return this;
        }

        public RenderRequest build() {
            return new RenderRequest(this);
        }

    }

}
