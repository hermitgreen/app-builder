package com.baidubce.appbuilder.model.appbuilderclient;

import java.util.Iterator;
import java.util.stream.IntStream;

public class AppBuilderClientIterator {
    private final Iterator<AppBuilderClientResponse> iterator;

    public AppBuilderClientIterator(Iterator<AppBuilderClientResponse> iterator) {
        this.iterator = iterator;
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public AppBuilderClientResult next() {
        AppBuilderClientResponse response = iterator.next();
        if(response.getContent() == null) {
            return new AppBuilderClientResult().setAnswer(response.getAnswer()).setMessageId(response.getMessageId()).setRequestId(response.getRequestId()).setCode(response.getCode()).setMessage(response.getMessage());
        }
        Event[] events = new Event[response.getContent().length];
        EventContent[] contents = response.getContent();
        IntStream.range(0, contents.length).forEach(i -> {
            events[i] = new Event().setCode(contents[i].getEventCode())
                    .setMessage(contents[i].getEnentMessage())
                    .setStatus(contents[i].getEventStatus())
                    .setEventType(contents[i].getEventType())
                    .setContentType(contents[i].getContentType())
                    .setDetail(contents[i].getOutputs()).setUsage(contents[i].getUsage())
                    .setToolCalls(contents[i].getToolCalls());
        });
        return new AppBuilderClientResult().setAnswer(response.getAnswer()).setMessageId(response.getMessageId()).setEvents(events).setRequestId(response.getRequestId());
    }
}
