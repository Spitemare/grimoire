package org.grimoire.ingest.mtgjson;

import static org.grimoire.ingest.mtgjson.MtgJsonConstants.HEADER_CURRENT_VERSION;
import static org.grimoire.ingest.mtgjson.MtgJsonConstants.HEADER_NEW_VERSION;
import static org.grimoire.repo.DataFeedRepo.Specs.byGame;
import static org.grimoire.repo.DataFeedRepo.Specs.byName;
import static org.grimoire.util.Maps.entry;
import static org.grimoire.util.Maps.mapOf;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.StartupListener;
import org.apache.maven.artifact.versioning.ComparableVersion;
import org.grimoire.repo.DataFeedRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

@Component
public class MtgJsonVersionChecker implements StartupListener {

    private static final Logger LOG = LoggerFactory.getLogger(MtgJsonVersionChecker.class);

    private final AsyncRestTemplate restTemplate;

    private final MtgJsonProps mtgJsonProps;

    private final DataFeedRepo dataFeedRepo;

    private final ProducerTemplate producerTemplate;

    @Autowired
    public MtgJsonVersionChecker(AsyncRestTemplate restTemplate, MtgJsonProps mtgJsonProps,
            DataFeedRepo dataFeedRepo, ProducerTemplate producerTemplate) {
        this.restTemplate = restTemplate;
        this.mtgJsonProps = mtgJsonProps;
        this.dataFeedRepo = dataFeedRepo;
        this.producerTemplate = producerTemplate;
    }

    @Override
    public void onCamelContextStarted(CamelContext context, boolean alreadyStarted)
            throws Exception {
        if (alreadyStarted) return;

        ListenableFuture<ResponseEntity<ComparableVersion>> future = restTemplate
                .getForEntity(mtgJsonProps.getVersionUri(), ComparableVersion.class);
        ComparableVersion currentVersion = dataFeedRepo
                .findOne(byName("mtgjson.com").and(byGame("Magic: the Gathering"))).getVersion();

        LOG.info("Current mtgjson.com version = {}", currentVersion);

        future.addCallback(response -> {
            ComparableVersion newVersion = response.getBody();
            if (currentVersion.compareTo(newVersion) < 0)
                fireMtgJsonUpdate(currentVersion, newVersion);
        } , e -> LOG.warn(e.getMessage(), e));
    }

    private void fireMtgJsonUpdate(ComparableVersion currentVersion, ComparableVersion newVersion) {
        LOG.info("Found new mtgjson.com version = {}", newVersion);

        producerTemplate.sendBodyAndHeaders("direct:mtgjson-start", null,
                mapOf(entry(Exchange.HTTP_URI, mtgJsonProps.getDownloadUri()),
                        entry(HEADER_CURRENT_VERSION, currentVersion),
                        entry(HEADER_NEW_VERSION, newVersion)));
    }

}
