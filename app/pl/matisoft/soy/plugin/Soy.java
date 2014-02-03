package pl.matisoft.soy.plugin;

import com.google.template.soy.data.SoyMapData;

/**
 * Created by mati on 02/02/2014.
 */
public interface Soy {

    String html(String view, Object model) throws Exception;

    String html(String view, SoyMapData soyMapData) throws Exception;

    String html(String view) throws Exception;

}
