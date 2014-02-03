package com.github.mati1979.play.soyplugin.plugin;

import play.Application;
import play.Plugin;

/**
 * Created by mati on 02/02/2014.
 */
public class ClosurePlugin extends Plugin {

    private Application app;

    public ClosurePlugin(final Application app) {
        this.app = app;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public boolean enabled() {
        return true;
    }

}
