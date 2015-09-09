package org.grimoire.ingest;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.grimoire.model.Expansion;
import org.grimoire.model.Game;
import org.grimoire.repo.GameRepo;
import org.grimoire.repo.support.Spec;
import org.junit.Test;
import org.mockito.Mockito;

public class ExpansionPersisterTest {

    @Test
    public void testPersist() {
        Game game = new Game();
        List<Expansion> in = Collections.singletonList(new Expansion());

        Spec<Game> gameSpec = GameRepo.Specs.byName("");
        GameRepo gameRepo = Mockito.mock(GameRepo.class);
        Mockito.when(gameRepo.findOne(gameSpec)).thenReturn(game);

        ExpansionPersister expansionPersister = new ExpansionPersister(gameRepo, gameSpec);
        expansionPersister.init();
        Collection<Expansion> out = expansionPersister.persist(in);

        Mockito.verify(gameRepo).save(game);
        assertThat(out, is(sameInstance(in)));
        assertThat(game.getExpansions(), is(not(empty())));
    }

    @Test
    public void testPersistEmptyCollection() {
        GameRepo gameRepo = Mockito.mock(GameRepo.class);
        ExpansionPersister expansionPersister = new ExpansionPersister(gameRepo, null);
        expansionPersister.persist(Collections.emptyList());

        Mockito.verifyZeroInteractions(gameRepo);
    }

}
