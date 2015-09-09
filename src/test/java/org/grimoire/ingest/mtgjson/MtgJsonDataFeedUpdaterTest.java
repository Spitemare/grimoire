package org.grimoire.ingest.mtgjson;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import org.apache.maven.artifact.versioning.ComparableVersion;
import org.grimoire.model.DataFeed;
import org.grimoire.repo.DataFeedRepo;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.data.jpa.domain.Specification;

public class MtgJsonDataFeedUpdaterTest {

    @Test
    public void testUpdate() {
        DataFeed dataFeed = new DataFeed();
        DataFeedRepo dataFeedRepo = Mockito.mock(DataFeedRepo.class);
        Mockito.when(dataFeedRepo.findOne(Matchers.<Specification<DataFeed>> any()))
                .thenReturn(dataFeed);

        ComparableVersion version = new ComparableVersion("1.0");
        MtgJsonDataFeedUpdater dataFeedUpdater = new MtgJsonDataFeedUpdater(dataFeedRepo);
        dataFeedUpdater.update(version);

        assertThat(dataFeed.getLastUpdated(), is(not(nullValue())));
        assertThat(dataFeed.getVersion(), is(sameInstance(version)));
    }

}
