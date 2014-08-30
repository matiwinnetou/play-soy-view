package com.github.mati1979.play.soyplugin.ajax.process;

import com.google.common.collect.Lists;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by mati on 27/08/2014.
 */
public class DefaultOutputProcessors implements OutputProcessors {

    private List<OutputProcessor> outputProcessors = Lists.newArrayList();

    @Inject
    public DefaultOutputProcessors(@Named("google") OutputProcessor outputProcessor) {
        this.outputProcessors = Lists.newArrayList(outputProcessor);
    }

    @Override
    public List<OutputProcessor> processors() {
        return outputProcessors;
    }

}
