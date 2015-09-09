package org.grimoire.ingest.mtgjson;

import org.apache.camel.ProducerTemplate;
import org.grimoire.support.ContextReadyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MtgJsonContextReadyListener implements ApplicationListener<ContextReadyEvent> {

    private final ProducerTemplate producerTemplate;

    @Autowired
    public MtgJsonContextReadyListener(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }

    @Override
    public void onApplicationEvent(ContextReadyEvent event) {
        producerTemplate.sendBody("direct:mtgjson-version-check", new Object());
    }

}
