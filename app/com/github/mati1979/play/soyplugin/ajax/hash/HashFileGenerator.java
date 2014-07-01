package com.github.mati1979.play.soyplugin.ajax.hash;

import java.util.Optional;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: mszczap
 * Date: 29.06.13
 * Time: 23:57
 *
 * An interface which for a given url calculates a hash checksum
 */
public interface HashFileGenerator {

   Optional<String> hash() throws IOException;

}
