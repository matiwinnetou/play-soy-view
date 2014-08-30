package com.github.mati1979.play.soyplugin.ajax.process;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by mati on 27/08/2014.
 */
public class DefaultOutputProcessors implements OutputProcessors {

    private List<OutputProcessor> outputProcessors = Lists.newArrayList();

    @Override
    public List<OutputProcessor> processors() {
        return outputProcessors;
    }

}
