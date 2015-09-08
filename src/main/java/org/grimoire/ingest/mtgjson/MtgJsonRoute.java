package org.grimoire.ingest.mtgjson;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MtgJsonRoute extends RouteBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(MtgJsonRoute.class);

    @Override
    public void configure() throws Exception {
        //@formatter:off
        from("direct:mtgjson-start").
        log(LoggingLevel.INFO, LOG, "Downloading ${header." + Exchange.HTTP_URI + "}").
        to("ahc:http://www.example.com"). // will be replaced by sent in body
        unmarshal().zipFile().
        log(LoggingLevel.INFO, LOG, "Unmarshalling mtgjson.com data").
        to("spring:mtgjson-unmarshaller").
        log(LoggingLevel.INFO, LOG, "Converting mtgjson.com data").
        to("spring:mtgjson-converter").
        log(LoggingLevel.INFO, LOG, "Persisting ${body.size} MTG expansions").
        transacted().
            to("spring:mtg-persister").
            setBody(header(MtgJsonConstants.HEADER_NEW_VERSION)).
            log(LoggingLevel.INFO, LOG, "Updating mtgjson.com data feed version to ${body}").
            to("spring:mtgjson-data-feed-updater").
        end().
        log(LoggingLevel.INFO, LOG, "Done ingesting new mtgjson.com data");
        //@formatter:on

    }

}
