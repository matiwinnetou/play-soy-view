package com.github.mati1979.play.soyplugin.compile;

import com.github.mati1979.play.soyplugin.config.SoyViewConf;
import com.github.mati1979.play.soyplugin.global.compile.CompileTimeGlobalModelResolver;
import com.github.mati1979.play.soyplugin.global.compile.EmptyCompileTimeGlobalModelResolver;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.template.soy.SoyFileSet;
import com.google.template.soy.data.SoyMapData;
import com.google.template.soy.jssrc.SoyJsSrcOptions;
import com.google.template.soy.msgs.SoyMsgBundle;
import com.google.template.soy.tofu.SoyTofu;
import com.google.template.soy.tofu.SoyTofuOptions;
import play.Logger;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * User: mszczap
 * Date: 20.06.13
 * Time: 17:40
 */
public class DefaultTofuCompiler implements TofuCompiler {

    private static final Logger.ALogger logger = Logger.of(DefaultTofuCompiler.class);

    private CompileTimeGlobalModelResolver compileTimeGlobalModelResolver = new EmptyCompileTimeGlobalModelResolver();

    private SoyJsSrcOptions soyJsSrcOptions = new SoyJsSrcOptions();

    private SoyTofuOptions soyTofuOptions = new SoyTofuOptions();

    private SoyViewConf soyViewConf;

    @Inject
    public DefaultTofuCompiler(final CompileTimeGlobalModelResolver compileTimeGlobalModelResolver,
                               final SoyViewConf soyViewConf,
                               final SoyJsSrcOptions soyJsSrcOptions,
                               final SoyTofuOptions soyTofuOptions) {
        this.compileTimeGlobalModelResolver = compileTimeGlobalModelResolver;
        this.soyViewConf = soyViewConf;
        this.soyJsSrcOptions = soyJsSrcOptions;
        this.soyTofuOptions = soyTofuOptions;
    }

    @Override
    public SoyTofu compile(@Nullable final Collection<URL> urls) throws IOException {
        Preconditions.checkNotNull("compileTimeGlobalModelResolver", compileTimeGlobalModelResolver);

        if (urls == null || urls.isEmpty()) {
            throw new IOException("Unable to compile, no urls found");
        }

        logger.debug("SoyTofu compilation of templates:" + urls.size());
        final long time1 = System.currentTimeMillis();

        final SoyFileSet.Builder sfsBuilder = new SoyFileSet.Builder();

        for (final URL url : urls) {
            sfsBuilder.add(url);
        }

        addCompileTimeGlobalModel(sfsBuilder);

        final SoyFileSet soyFileSet = sfsBuilder.build();

        final SoyTofuOptions soyTofuOptions = createSoyTofuOptions();
        final SoyTofu soyTofu = soyFileSet.compileToTofu(soyTofuOptions);

        final long time2 = System.currentTimeMillis();

        logger.debug("SoyTofu compilation complete." + (time2 - time1) + " ms");

        return soyTofu;
    }

    private void addCompileTimeGlobalModel(final SoyFileSet.Builder sfsBuilder) {
        final Optional<SoyMapData> soyMapData = compileTimeGlobalModelResolver.resolveData();
        if (soyMapData.isPresent()) {
            final Map<String, ?> mapData = soyMapData.get().asMap();
            if (mapData.size() > 0) {
                logger.debug("Setting compile time globals, entries number:" + mapData.size());
                sfsBuilder.setCompileTimeGlobals(mapData);
            }
        }
    }

    private SoyTofuOptions createSoyTofuOptions() {
        final SoyTofuOptions soyTofuOptions = new SoyTofuOptions();
        soyTofuOptions.setUseCaching(!soyViewConf.globalHotReloadMode());

        return soyTofuOptions;
    }

    @Override
    public final Optional<String> compileToJsSrc(@Nullable final URL url, @Nullable final SoyMsgBundle soyMsgBundle) {
        if (url == null) {
            return Optional.empty();
        }

        final Collection<String> compiledTemplates = compileToJsSrc(Lists.newArrayList(url), soyMsgBundle);
        if (compiledTemplates.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(compiledTemplates.iterator().next());
    }

    @Override
    public Collection<String> compileToJsSrc(final Collection<URL> templates, @Nullable SoyMsgBundle soyMsgBundle) {
        Preconditions.checkNotNull("soyJsSrcOptions", soyJsSrcOptions);
        logger.debug("SoyJavaScript compilation of template:" + templates);
        final long time1 = System.currentTimeMillis();

        final SoyFileSet soyFileSet = buildSoyFileSetFrom(templates);

        final List<String> compiled = soyFileSet.compileToJsSrc(soyJsSrcOptions, soyMsgBundle);

        final long time2 = System.currentTimeMillis();

        logger.debug("SoyJavaScript compilation complete." + (time2 - time1) + " ms");

        return compiled;
    }

    private SoyFileSet buildSoyFileSetFrom(final Collection<URL> urls) {
        logger.debug("Building soy file set from urls.count:" + urls.size());
        final SoyFileSet.Builder builder = new SoyFileSet.Builder();

        for (final URL url : urls) {
            builder.setAllowExternalCalls(true);
            builder.add(url);
        }

        addCompileTimeGlobalModel(builder);

        return builder.build();
    }

    public SoyJsSrcOptions getSoyJsSrcOptions() {
        return soyJsSrcOptions;
    }

    public SoyTofuOptions getSoyTofuOptions() {
        return soyTofuOptions;
    }

}
