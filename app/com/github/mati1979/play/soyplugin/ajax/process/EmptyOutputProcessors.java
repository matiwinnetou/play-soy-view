package com.github.mati1979.play.soyplugin.ajax.process;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by mati on 27/08/2014.
 */
public class EmptyOutputProcessors implements OutputProcessors {

    @Override
    public List<OutputProcessor> processors() {
        return Lists.newLinkedList();
    }

}
