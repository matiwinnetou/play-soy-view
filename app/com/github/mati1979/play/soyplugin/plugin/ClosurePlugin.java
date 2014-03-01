package com.github.mati1979.play.soyplugin.plugin;

import com.github.mati1979.play.soyplugin.ajax.config.PlaySoyViewAjaxConfig;
import com.github.mati1979.play.soyplugin.spring.PlaySoyConfig;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import play.Application;
import play.Plugin;

/**
 * Created by mati on 02/02/2014.
 */
public class ClosurePlugin extends Plugin {

    private AnnotationConfigApplicationContext ctx;

    private Application app;

    public ClosurePlugin(final Application app) {
        this.app = app;
    }

    @Override
    public void onStart() {
        if (isAdvancedMode()) {
            this.ctx = new AnnotationConfigApplicationContext(PlaySoyViewAjaxConfig.class);
            this.ctx.registerBeanDefinition("playApp", new GenericBeanDefinition());
        }
        if (isSimpleMode()) {
            this.ctx = new AnnotationConfigApplicationContext(PlaySoyConfig.class);
        }
    }

    private boolean isAdvancedMode() {
        final String type = app.configuration().getString("ClosurePlugin.mode", "simple");
        if (type.equals("advanced")) {
            return false;
        }

        return true;
    }

    private boolean isSimpleMode() {
        final String type = app.configuration().getString("ClosurePlugin.mode", "simple");
        if (type.equals("simple")) {
            return false;
        }

        return true;
    }

    public ApplicationContext getContext() {
        return ctx;
    }

    @Override
    public boolean enabled() {
        return app.configuration().getBoolean("ClosurePlugin.enabled", true);
    }

}
