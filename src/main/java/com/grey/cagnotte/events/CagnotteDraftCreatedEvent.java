package com.grey.cagnotte.events;

import com.grey.cagnotte.entity.Cagnotte;
import org.springframework.context.ApplicationEvent;

public class CagnotteDraftCreatedEvent extends ApplicationEvent {
    private final Cagnotte cagnotte;

    public CagnotteDraftCreatedEvent(Object source, Cagnotte cagnotte) {
        super(source);
        this.cagnotte = cagnotte;
    }

    public Cagnotte getCagnotte() {
        return cagnotte;
    }
}

