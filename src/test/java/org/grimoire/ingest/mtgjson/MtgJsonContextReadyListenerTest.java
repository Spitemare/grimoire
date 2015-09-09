package org.grimoire.ingest.mtgjson;

import org.apache.camel.ProducerTemplate;
import org.grimoire.support.ContextReadyEvent;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;

public class MtgJsonContextReadyListenerTest {

    @Test
    public void testOnCamelContextStarted() throws Exception {
        ProducerTemplate producerTemplate = Mockito.mock(ProducerTemplate.class);
        MtgJsonContextReadyListener mtgJsoncontextReadyListener = new MtgJsonContextReadyListener(
                producerTemplate);
        mtgJsoncontextReadyListener
                .onApplicationEvent(new ContextReadyEvent(Mockito.mock(ApplicationContext.class)));

        Mockito.verify(producerTemplate).sendBody(Mockito.eq("direct:mtgjson-version-check"),
                Mockito.anyObject());
    }

}
