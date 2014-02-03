package com.github.mati1979.play.soyplugin.plugin;

public class ModelView {

    private Object model;
    private String view;

    public ModelView(String view, Object model) {
        this.model = model;
        this.view = view;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

}
