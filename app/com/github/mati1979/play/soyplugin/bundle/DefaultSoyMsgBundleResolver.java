package com.github.mati1979.play.soyplugin.bundle;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.github.mati1979.play.soyplugin.config.ConfigDefaults;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.template.soy.msgs.SoyMsgBundle;
import com.google.template.soy.msgs.SoyMsgBundleHandler;
import com.google.template.soy.msgs.restricted.SoyMsg;
import com.google.template.soy.msgs.restricted.SoyMsgBundleImpl;
import com.google.template.soy.xliffmsgplugin.XliffMsgPlugin;
import com.github.mati1979.play.soyplugin.config.ConfigKeys;
import play.Play;

public class DefaultSoyMsgBundleResolver implements com.github.mati1979.play.soyplugin.bundle.SoyMsgBundleResolver {

    private static final play.Logger.ALogger logger = play.Logger.of(DefaultSoyMsgBundleResolver.class);

    /** a cache of soy msg bundles */
    /** friendly */ Map<Locale, SoyMsgBundle> msgBundles = new ConcurrentHashMap<Locale, SoyMsgBundle>();

    /** a path to a bundle */
    private String messagesPath = Play.application().configuration().getString(ConfigKeys.I18N_MESSAGES_PATH, ConfigDefaults.I18N_MESSAGES_PATH);

    /** will cache msgBundles if a hotReloadMode is off, if debug is on,
     *  will compile msg bundles each time it is invoked */
    private boolean hotReloadMode = Play.application().configuration().getBoolean(ConfigKeys.GLOBAL_HOT_RELOAD_MODE, ConfigDefaults.GLOBAL_HOT_RELOAD_MODE);

    /** in case translation is missing for a passed in locale,
     *  whether the implementation should fallback to English returning
     *  an english translation if available */
    private boolean fallbackToEnglish = Play.application().configuration().getBoolean(ConfigKeys.I18N_FALLBACK_TO_ENGLISH, ConfigDefaults.I18N_FALLBACK_TO_ENGLISH);

    /**
     * Based on a provided locale return a SoyMsgBundle file.
     *
     * If a passed in locale object is "Optional.absent()",
     * the implementation will return Optional.absent() as well
     *
     * @param locale
     * @return
     * @throws IOException in case there is an i/o error reading msg bundle
     */
    public Optional<SoyMsgBundle> resolve(final Optional<Locale> locale) throws IOException {
        if (!locale.isPresent()) {
            return Optional.absent();
        }

        synchronized (msgBundles) {
            SoyMsgBundle soyMsgBundle = null;
            if (isHotReloadModeOff()) {
                soyMsgBundle = msgBundles.get(locale.get());
            }
            if (soyMsgBundle == null) {
                soyMsgBundle = createSoyMsgBundle(locale.get());
                if (soyMsgBundle == null) {
                    soyMsgBundle = createSoyMsgBundle(new Locale(locale.get().getLanguage()));
                }

                if (soyMsgBundle == null && fallbackToEnglish) {
                    soyMsgBundle = createSoyMsgBundle(Locale.ENGLISH);
                }

                if (soyMsgBundle == null) {
                    return Optional.absent();
                }

                if (isHotReloadModeOff()) {
                    msgBundles.put(locale.get(), soyMsgBundle);
                }
            }

            return Optional.fromNullable(soyMsgBundle);
        }
    }

    /**
     * An implementation that using a ContextClassLoader iterates over all urls it finds
     * based on a messagePath and locale, e.g. messages_de_DE.xlf and returns a merged
     * SoyMsgBundle of SoyMsgBundle matching a a pattern it finds in a class path.
     *
     * @param locale
     * @return
     * @throws IOException
     */
    protected SoyMsgBundle createSoyMsgBundle(final Locale locale) throws IOException {
        Preconditions.checkNotNull(messagesPath, "messagesPath cannot be null!");

        final String path2 = messagesPath  + "_" + locale.toString() + ".xlf";

        final List<SoyMsgBundle> msgBundles = Lists.newArrayList();

        final File file = Play.application().getFile(path2);

        final SoyMsgBundleHandler msgBundleHandler = new SoyMsgBundleHandler(new XliffMsgPlugin());

        if (file.exists()) {
            final SoyMsgBundle soyMsgBundle = msgBundleHandler.createFromFile(file);
            msgBundles.add(soyMsgBundle);
        }

        return mergeMsgBundles(locale, msgBundles).orNull();
    }

    /**
     * Merge msg bundles together, creating new MsgBundle with merges msg bundles passed in as a method argument
     *
     * @param locale
     * @param soyMsgBundles
     * @return
     */
    private Optional<? extends SoyMsgBundle> mergeMsgBundles(final Locale locale, final List<SoyMsgBundle> soyMsgBundles) {
        if (soyMsgBundles.isEmpty()) {
            return Optional.absent();
        }

        final List<SoyMsg> msgs = Lists.newArrayList();
        for (final SoyMsgBundle smb : soyMsgBundles) {
            for (final Iterator<SoyMsg> it = smb.iterator(); it.hasNext();) {
                msgs.add(it.next());
            }
        }

        return Optional.of(new SoyMsgBundleImpl(locale.toString(), msgs));
    }

    public void setMessagesPath(final String messagesPath) {
        this.messagesPath = messagesPath;
    }

    public void setHotReloadMode(final boolean hotReloadMode) {
        this.hotReloadMode = hotReloadMode;
    }

    public void setFallbackToEnglish(boolean fallbackToEnglish) {
        this.fallbackToEnglish = fallbackToEnglish;
    }

    public String getMessagesPath() {
        return messagesPath;
    }

    public boolean isHotReloadMode() {
        return hotReloadMode;
    }

    private boolean isHotReloadModeOff() {
        return !hotReloadMode;
    }

    public boolean isFallbackToEnglish() {
        return fallbackToEnglish;
    }

}
