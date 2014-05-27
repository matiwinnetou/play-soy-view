package com.github.mati1979.play.soyplugin.global.runtime;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.template.soy.data.SoyMapData;
import play.mvc.Http;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mati
 * Date: 14/07/2013
 * Time: 16:46
 *
 * A default implementation of GlobalModelResolver contains a list of resolvers,
 * each resolver can decide what data should be injected.
 */
public class DefaultGlobalRuntimeModelResolver implements GlobalRuntimeModelResolver {

    private static final play.Logger.ALogger logger = play.Logger.of("play.soy.view");

    private List<RuntimeDataResolver> resolvers = Lists.newArrayList();

    private List<RuntimeDataResolver> userResolvers = Lists.newArrayList();

    public DefaultGlobalRuntimeModelResolver(final List<RuntimeDataResolver> resolvers) {
        this.resolvers = resolvers;
    }

    public DefaultGlobalRuntimeModelResolver(final List<RuntimeDataResolver> resolvers,
                                             final List<RuntimeDataResolver> userResolvers) {
        this.resolvers = resolvers;
        this.userResolvers = userResolvers;
    }

    @Override
    public Optional<SoyMapData> resolveData(final Http.Request request, final Http.Response response, final Map<String, ? extends Object> model) {
        final SoyMapData root = new SoyMapData();

        for (final RuntimeDataResolver runtimeDataResolver : resolvers) {
            logger.debug("resolving:{}", runtimeDataResolver);
            runtimeDataResolver.resolveData(request, response, model, root);
        }

        for (final RuntimeDataResolver runtimeDataResolver : userResolvers) {
            logger.debug("user data resolving:{}", runtimeDataResolver);
            runtimeDataResolver.resolveData(request, response, model, root);
        }

        return Optional.of(root);
    }

}
