package org.grimoire.ingest.mtgjson;

import static org.grimoire.ingest.mtgjson.MtgJsonConstants.HEADER_LOCAL_VERSION;
import static org.grimoire.ingest.mtgjson.MtgJsonConstants.HEADER_REMOTE_VERSION;
import static org.grimoire.repo.DataFeedRepo.Specs.byGame;
import static org.grimoire.repo.DataFeedRepo.Specs.byName;
import static org.grimoire.util.Maps.entry;
import static org.grimoire.util.Maps.mapOf;
import java.util.concurrent.ExecutionException;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.maven.artifact.versioning.ComparableVersion;
import org.grimoire.ingest.support.CamelEndpoint;
import org.grimoire.repo.DataFeedRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

@CamelEndpoint("mtgjson-version-checker")
@Component
public class MtgJsonVersionChecker {

    private static final Logger LOG = LoggerFactory.getLogger(MtgJsonVersionChecker.class);

    private final AsyncRestTemplate restTemplate;

    private final MtgJsonProps mtgJsonProps;

    private final DataFeedRepo dataFeedRepo;

    @Autowired
    public MtgJsonVersionChecker(AsyncRestTemplate restTemplate, MtgJsonProps mtgJsonProps,
            DataFeedRepo dataFeedRepo) {
        this.restTemplate = restTemplate;
        this.mtgJsonProps = mtgJsonProps;
        this.dataFeedRepo = dataFeedRepo;
    }

    public void check(Exchange exchange) {
        ListenableFuture<ResponseEntity<ComparableVersion>> future = restTemplate
                .getForEntity(mtgJsonProps.getVersionUri(), ComparableVersion.class);
        ComparableVersion localVersion = dataFeedRepo
                .findOne(byName("mtgjson.com").and(byGame("Magic: the Gathering"))).getVersion();

        LOG.info("Local mtgjson.com version = {}", localVersion);

        ComparableVersion remoteVersion;
        try {
            remoteVersion = future.get().getBody();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        LOG.info("Remote mtgjson.com version = {}", remoteVersion);

        Message message = exchange.getIn();
        message.setBody(mtgJsonProps.getDownloadUri());
        message.getHeaders().putAll(mapOf(entry(HEADER_LOCAL_VERSION, localVersion),
                entry(HEADER_REMOTE_VERSION, remoteVersion)));
        if (localVersion.compareTo(remoteVersion) >= 0) {
            exchange.setProperty(Exchange.ROUTE_STOP, true);
        }
    }

}
