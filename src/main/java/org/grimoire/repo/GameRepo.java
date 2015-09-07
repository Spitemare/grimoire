package org.grimoire.repo;

import org.grimoire.model.Game;
import org.grimoire.model.Game_;
import org.grimoire.repo.support.Spec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GameRepo extends JpaRepository<Game, Long>, JpaSpecificationExecutor<Game> {

    public static interface Specs {

        static Spec<Game> byName(String name) {
            return (root, query, cb) -> cb.equal(root.get(Game_.name), name);
        }

    }

}
