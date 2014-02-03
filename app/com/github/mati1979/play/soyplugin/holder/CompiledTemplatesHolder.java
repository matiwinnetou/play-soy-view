package com.github.mati1979.play.soyplugin.holder;

import com.google.common.base.Optional;
import com.google.template.soy.tofu.SoyTofu;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: mati
 * Date: 02/11/2013
 * Time: 14:15
 */
public interface CompiledTemplatesHolder {

    Optional<SoyTofu> compiledTemplates() throws IOException;

}
