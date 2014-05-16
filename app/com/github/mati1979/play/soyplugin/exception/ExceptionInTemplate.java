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

    private Integer line;
    private Integer position;
    private String viewName;
    private String fileAsString = "";

    public ExceptionInTemplate(final Optional<File> templateFile,
                               final String viewName,
                               final Integer line,
                               final Integer position,
                               final String description,
                               final Throwable cause) {
        super("Soy error", description, cause);
        this.viewName = viewName;
        this.line = line;
        this.position = position;
        this.fileAsString = fileAsString(templateFile);
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
        return viewName;
    }

    private String fileAsString(final Optional<File> templateFile) {
        if (templateFile.isPresent()) {
            try {
                return FileUtils.readFileToString(templateFile.get());
            } catch (IOException e) {
            }
        }

        return "missing file:" + templateFile;
    }

    public static ExceptionInTemplate createExceptionInTemplate(final SoySyntaxException e) {
        final Optional<File> fileOpt = createFile(e.getSourceLocation().getFilePath());
        final Matcher matcher = PATTERN.matcher(e.getOriginalMessage());
        final String viewName = e.getTemplateName();
        final String message = e.getMessage();
        if (matcher.matches()) {
            final Integer lineNo = Integer.parseInt(matcher.group(1).trim());
            final Integer position = Integer.parseInt(matcher.group(2).trim());

            return new ExceptionInTemplate(fileOpt, viewName, lineNo, position, message, e);
        }

        return new ExceptionInTemplate(fileOpt, viewName, 0, 0, message, e);
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