package com.github.mati1979.play.soyplugin.exception;

import com.google.common.base.Optional;
import com.google.template.soy.base.SoySyntaxException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mati on 16/05/2014.
 */
public class ExceptionInTemplate extends play.api.PlayException.ExceptionSource {

    private final static Pattern PATTERN = Pattern.compile("^.+\\[line\\040(\\d{1,3}),\\040column\\040(\\d{1,3})\\].+$");

    final File templateFile;
    final Integer line;
    final Integer position;

    final String fileAsString;

    public ExceptionInTemplate(final File templateFile, final Integer line, final Integer position, final String description, final Throwable cause) {
        super("Soy error", description, cause);
        this.templateFile = templateFile;
        this.line = line;
        this.position = position;
        try {
            this.fileAsString = FileUtils.readFileToString(templateFile);
        } catch (IOException e) {
            throw new RuntimeException("unable to read file:" + templateFile, e);
        }
    }

    public Integer line() {
        return line;
    }

    public Integer position() {
        return position;
    }

    public String input() {
        return fileAsString;
    }

    public String sourceName() {
        return templateFile.getName();
    }

    public static ExceptionInTemplate createExceptionInTemplate(final SoySyntaxException e) {
        final Optional<File> fileOpt = createFile(e.getSourceLocation().getFilePath());
        if (!fileOpt.isPresent()) {
            throw new RuntimeException("file not found:" + e.getSourceLocation().getFilePath());
        }
        final Matcher matcher = PATTERN.matcher(e.getOriginalMessage());
        if (matcher.matches()) {
            final Integer lineNo = Integer.parseInt(matcher.group(1).trim());
            final Integer position = Integer.parseInt(matcher.group(2).trim());

            return new ExceptionInTemplate(fileOpt.get(), lineNo, position, e.getMessage(), e);
        }

        return new ExceptionInTemplate(fileOpt.get(), 0, 0, e.getMessage(), e);
    }

    private static Optional<File> createFile(final String fileUri) {
        try {
            final URL url = new URL(fileUri);

            return Optional.of(new File(url.toURI()));
        } catch(final URISyntaxException ex) {
        } catch (MalformedURLException ex) {
        }

        return Optional.absent();
    }

}