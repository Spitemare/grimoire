package org.grimoire.ingest.mtgjson;

import static org.grimoire.repo.DataFeedRepo.Specs.byGame;
import static org.grimoire.repo.DataFeedRepo.Specs.byName;
import java.util.Date;
import org.apache.maven.artifact.versioning.ComparableVersion;
import org.grimoire.ingest.support.CamelEndpoint;
import org.grimoire.model.DataFeed;
import org.grimoire.repo.DataFeedRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@CamelEndpoint("mtgjson-data-feed-updater")
@Component
public class MtgJsonDataFeedUpdater {

    private final DataFeedRepo dataFeedRepo;

    @Autowired
    public MtgJsonDataFeedUpdater(DataFeedRepo dataFeedRepo) {
        this.dataFeedRepo = dataFeedRepo;
    }

    public void update(ComparableVersion newVersion) {
        DataFeed dataFeed = dataFeedRepo
                .findOne(byName("mtgjson.com").and(byGame("Magic: the Gathering")));
        dataFeed.setVersion(newVersion);
        dataFeed.setLastUpdated(new Date());
        dataFeedRepo.save(dataFeed);
    }

}
