package com.github.mati1979.play.soyplugin.ajax.process;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * Created with IntelliJ IDEA.
 * User: mati
 * Date: 06/10/2013
 * Time: 15:55
 *
 * An interface that defines a generic processing of compiled JavaScript output, e.g.
 * to perform JavaScript obfuscation.
 */
public interface OutputProcessor {

    void process(Reader reader, Writer writer) throws IOException;

}
