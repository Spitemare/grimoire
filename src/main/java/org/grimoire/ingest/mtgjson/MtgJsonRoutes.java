package org.grimoire.ingest.mtgjson;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MtgJsonRoutes {

    private static final Logger LOG = LoggerFactory.getLogger(MtgJsonRoutes.class);

    @Bean
    public RouteBuilder mtgJsonStart() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                //@formatter:off
                from("direct:mtgjson-start").transacted().
                log(LoggingLevel.INFO, LOG, "Downloading ${body}").
                to("spring:downloader").
                unmarshal().zipFile().
                log(LoggingLevel.INFO, LOG, "Unmarshalling mtgjson.com data").
                to("spring:mtgjson-unmarshaller").
                log(LoggingLevel.INFO, LOG, "Converting mtgjson.com data").
                to("spring:mtgjson-converter").
                log(LoggingLevel.INFO, LOG, "Persisting ${body.size} MTG expansions").
                to("spring:mtg-persister").
                setBody(header(MtgJsonConstants.HEADER_REMOTE_VERSION)).
                log(LoggingLevel.INFO, LOG, "Updating mtgjson.com data feed version to ${body}").
                to("spring:mtgjson-data-feed-updater").
                log(LoggingLevel.INFO, LOG, "Done ingesting new mtgjson.com data");
                //@formatter:on

            }
        };
    }

    @Bean
    public RouteBuilder mtgJsonVersionCheck() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                //@formatter:off
                from("direct:mtgjson-version-check").
                log(LoggingLevel.INFO, LOG, "Checking mtgjson.com version").
                to("spring:mtgjson-version-checker").
                to("direct:mtgjson-start");
                //@formatter:on
            }
        };
    }

}
