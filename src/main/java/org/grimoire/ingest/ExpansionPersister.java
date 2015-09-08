package org.grimoire.ingest;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.PostConstruct;
import org.grimoire.ingest.support.CamelEndpoint;
import org.grimoire.model.Expansion;
import org.grimoire.model.Game;
import org.grimoire.repo.GameRepo;
import org.grimoire.repo.support.Spec;

@CamelEndpoint
public class ExpansionPersister {

    private final GameRepo gameRepo;

    private final Spec<Game> gameSpec;

    private Game game;

    public ExpansionPersister(GameRepo gameRepo, Spec<Game> gameSpec) {
        this.gameRepo = gameRepo;
        this.gameSpec = gameSpec;
    }

    @PostConstruct
    public void init() {
        game = gameRepo.findOne(gameSpec);
    }

    public Collection<Expansion> persist(Collection<Expansion> expansions) {
        if (expansions.isEmpty()) return expansions;

        expansions.forEach(e -> e.setGame(game));
        game.setExpansions(new ArrayList<>(expansions));
        gameRepo.save(game);
        return expansions;
    }

}
