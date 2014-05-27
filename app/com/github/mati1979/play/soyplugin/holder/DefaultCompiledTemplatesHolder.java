package com.github.mati1979.play.soyplugin.holder;

import com.github.mati1979.play.soyplugin.compile.EmptyTofuCompiler;
import com.github.mati1979.play.soyplugin.compile.TofuCompiler;
import com.github.mati1979.play.soyplugin.config.SoyViewConf;
import com.github.mati1979.play.soyplugin.template.EmptyTemplateFilesResolver;
import com.github.mati1979.play.soyplugin.template.TemplateFilesResolver;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.template.soy.tofu.SoyTofu;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: mati
 * Date: 02/11/2013
 * Time: 14:04
 */
public class DefaultCompiledTemplatesHolder implements CompiledTemplatesHolder {

    private static final play.Logger.ALogger logger = play.Logger.of("play.soy.view");

    private TofuCompiler tofuCompiler = new EmptyTofuCompiler();

    private TemplateFilesResolver templatesFileResolver = new EmptyTemplateFilesResolver();

    private Optional<SoyTofu> compiledTemplates = Optional.absent();

    private SoyViewConf soyViewConf = null;

    public DefaultCompiledTemplatesHolder(final TofuCompiler tofuCompiler,
                                          final TemplateFilesResolver templatesFileResolver,
                                          final SoyViewConf soyViewConf) throws IOException {
        this.tofuCompiler = tofuCompiler;
        this.templatesFileResolver = templatesFileResolver;
        this.soyViewConf = soyViewConf;
        init();
    }

    public Optional<SoyTofu> compiledTemplates() throws IOException {
        if (shouldCompileTemplates()) {
            this.compiledTemplates = Optional.fromNullable(compileTemplates());
        }

        return compiledTemplates;
    }

    private boolean shouldCompileTemplates() {
        return soyViewConf.globalHotReloadMode() || !compiledTemplates.isPresent();
    }

    public void init() throws IOException {
        logger.debug("TemplatesHolder init...");
        if (soyViewConf.compilePrecompileTemplates()) {
            this.compiledTemplates = Optional.fromNullable(compileTemplates());
        }
    }

    private SoyTofu compileTemplates() throws IOException {
        Preconditions.checkNotNull(templatesFileResolver, "templatesRenderer cannot be null!");
        Preconditions.checkNotNull(tofuCompiler, "tofuCompiler cannot be null!");

        final Collection<URL> templateFiles = templatesFileResolver.resolve();
        if (templateFiles.size() > 0) {
            logger.debug("Compiling templates, no:{}", templateFiles.size());

            return tofuCompiler.compile(templateFiles);
        }

        throw new IOException("0 template files have been found, check your templateFilesResolver!");
    }

}
