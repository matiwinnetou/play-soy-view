package com.github.mati1979.play.soyplugin.ajax.process;

import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * Created with IntelliJ IDEA.
 * User: mszczap
 * Date: 16.10.13
 * Time: 18:11
 *
 * An implementation of an output processor that will prependText and appendText something
 * once a compilation of JavaScript completed.
 *
 * A good example for the reason this class may be needed is requirejs, one may prependText
 * a requirejs module definition and appendText closing, such that a JavaScript response can be wrapped.
 *
 * It is up to a frontend developer to work with a backend developer to define what exactly could be in "prependText"
 * and "appendText" configuration.
 */
public class PrependAppendOutputProcessor implements OutputProcessor {

    /** text to prepend before a compiled JavaScript template */
    private String prependText = "";

    /** whether to add a new line in prepend case */
    private boolean prependNewLine = true;

    /** text to append after a compiled JavaScript template */
    private String appendText = "";

    /** whether to add a new line in append case */
    private boolean appendNewLine = true;

    public PrependAppendOutputProcessor(final String prependText,
                                        final boolean prependNewLine,
                                        final String appendText,
                                        boolean appendNewLine) {
        this.prependText = prependText;
        this.prependNewLine = prependNewLine;
        this.appendText = appendText;
        this.appendNewLine = appendNewLine;
    }

    @Override
    public void process(final Reader reader, final Writer writer) throws IOException {
        final StringBuilder builder = new StringBuilder();

        final String content = IOUtils.toString(reader);

        if (!StringUtils.isEmpty(prependText)) {
            builder.append(prependText);
            if (prependNewLine) {
                builder.append("\n");
            }
        }
        builder.append(content);
        if (!StringUtils.isEmpty(appendText)) {
            if (appendNewLine) {
                builder.append("\n");
            }
            builder.append(appendText);
        }

        writer.write(builder.toString());
    }

}
