package org.grimoire.support;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

public class ContextReadyEvent extends ApplicationEvent {

    public ContextReadyEvent(ApplicationContext context) {
        super(context);
    }

}
