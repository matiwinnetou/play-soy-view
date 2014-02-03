package com.github.mati1979.play.soyplugin.render;

/**
 * Created with IntelliJ IDEA.
 * User: mati
 * Date: 23/06/2013
 * Time: 12:20
 *
 * An empty implementation that implements a Null Object Pattern, it will render nothing.
 */
public class EmptyTemplateRenderer implements TemplateRenderer {

    @Override
    public String render(final RenderRequest renderRequest) throws Exception {
        return "";
    }

}
