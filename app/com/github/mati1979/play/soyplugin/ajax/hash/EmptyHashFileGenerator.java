package com.github.mati1979.play.soyplugin.ajax.hash;

import java.io.IOException;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * User: mati
 * Date: 21/10/2013
 * Time: 20:15
 */
public class EmptyHashFileGenerator implements HashFileGenerator {

    @Override
    public Optional<String> hash() throws IOException {
        return Optional.empty();
    }

}

