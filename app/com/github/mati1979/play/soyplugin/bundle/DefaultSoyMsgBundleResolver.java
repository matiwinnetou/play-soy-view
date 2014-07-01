package com.github.mati1979.play.soyplugin.bundle;

import com.github.mati1979.play.soyplugin.config.SoyViewConf;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.template.soy.msgs.SoyMsgBundle;
import com.google.template.soy.msgs.SoyMsgBundleHandler;
import com.google.template.soy.msgs.restricted.SoyMsg;
import com.google.template.soy.msgs.restricted.SoyMsgBundleImpl;
import com.google.template.soy.xliffmsgplugin.XliffMsgPlugin;
import play.Play;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class DefaultSoyMsgBundleResolver implements SoyMsgBundleResolver {

    private static final play.Logger.ALogger logger = play.Logger.of(DefaultSoyMsgBundleResolver.class);

    /** a cache of soy msg bundles */
    /** friendly */ Map<Locale, SoyMsgBundle> msgBundles = new ConcurrentHashMap<>();

    private SoyViewConf soyViewConf = null;

    private ReentrantLock lock = new ReentrantLock(true);

    public DefaultSoyMsgBundleResolver(final SoyViewConf soyViewConf) {
        this.soyViewConf = soyViewConf;
    }

    /**
     * Based on a provided locale return a SoyMsgBundle file.
     *
     * If a passed in locale object is "Optional.empty()",
     * the implementation will return Optional.empty() as well
     */
    public Optional<SoyMsgBundle> resolve(final Optional<Locale> locale) throws IOException {
        logger.debug("Resolving soy msg bundle for locale:{}", locale);
        if (!locale.isPresent()) {
            return Optional.empty();
        }

        try {
            lock.lock();
            SoyMsgBundle soyMsgBundle = null;
            if (!soyViewConf.globalHotReloadMode()) {
                logger.debug("global hot reload off - using cached version for locale:{}", locale);
                soyMsgBundle = msgBundles.get(locale.get());
            }
            if (soyMsgBundle == null) {
                soyMsgBundle = createSoyMsgBundle(locale.get());
                if (soyMsgBundle == null) {
                    logger.debug("creating soy msg bundle for locale:{}", locale);
                    soyMsgBundle = createSoyMsgBundle(new Locale(locale.get().getLanguage()));
                }

                if (soyMsgBundle == null && soyViewConf.i18nFallbackToEnglish()) {
                    logger.debug("creating soy msg bundle and fallback to english enabled for locale:{}", locale);

                    soyMsgBundle = createSoyMsgBundle(Locale.ENGLISH);
                }

                if (soyMsgBundle == null) {
                    logger.debug("could not create msg bundle bundle, fallback to english disabled? for locale:{}", locale);
                    return Optional.empty();
                }

                if (!soyViewConf.globalHotReloadMode()) {
                    logger.debug("globalHotReloadMode is off, caching soy msg bundle for locale:{}", locale);
                    msgBundles.put(locale.get(), soyMsgBundle);
                }
                logger.debug("resolved soy msg bundle:{}", soyMsgBundle);

                return Optional.ofNullable(soyMsgBundle);
            }

            return Optional.ofNullable(soyMsgBundle);
        } finally {
            lock.unlock();
        }
    }

    /**
     * An implementation that using a ContextClassLoader iterates over all urls it finds
     * based on a messagePath and locale, e.g. messages_de_DE.xlf and returns a merged
     * SoyMsgBundle of SoyMsgBundle matching a a pattern it finds in a class path.
     */
    protected SoyMsgBundle createSoyMsgBundle(final Locale locale) throws IOException {
        Preconditions.checkNotNull(soyViewConf.i18nMessagesPath(), "messagesPath cannot be null!");

        final String path2 = soyViewConf.i18nMessagesPath()  + "_" + locale.toString() + ".xlf";

        final List<SoyMsgBundle> msgBundles = Lists.newArrayList();

        final File file = Play.application().getFile(path2);

        final SoyMsgBundleHandler msgBundleHandler = new SoyMsgBundleHandler(new XliffMsgPlugin());

        if (file.exists()) {
            final SoyMsgBundle soyMsgBundle = msgBundleHandler.createFromFile(file);
            msgBundles.add(soyMsgBundle);
        }

        return mergeMsgBundles(locale, msgBundles).orElse(null);
    }

    /**
     * Merge msg bundles together, creating new MsgBundle with merges msg bundles passed in as a method argument
     */
    private Optional<? extends SoyMsgBundle> mergeMsgBundles(final Locale locale, final List<SoyMsgBundle> soyMsgBundles) {
        if (soyMsgBundles.isEmpty()) {
            return Optional.empty();
        }

        final List<SoyMsg> msgs = Lists.newArrayList();
        for (final SoyMsgBundle smb : soyMsgBundles) {
            for (final Iterator<SoyMsg> it = smb.iterator(); it.hasNext();) {
                msgs.add(it.next());
            }
        }

        return Optional.of(new SoyMsgBundleImpl(locale.toString(), msgs));
    }

}
