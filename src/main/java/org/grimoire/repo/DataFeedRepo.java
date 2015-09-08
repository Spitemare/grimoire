package org.grimoire.repo;

import org.grimoire.model.DataFeed;
import org.grimoire.model.DataFeed_;
import org.grimoire.model.Game;
import org.grimoire.model.Game_;
import org.grimoire.repo.support.Spec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DataFeedRepo
        extends JpaRepository<DataFeed, Long>, JpaSpecificationExecutor<DataFeed> {

    public static interface Specs {

        static Spec<DataFeed> byName(String name) {
            return (root, query, cb) -> cb.equal(root.get(DataFeed_.name), name);
        }

        static Spec<DataFeed> byGame(Game game) {
            return (root, query, cb) -> cb.equal(root.get(DataFeed_.game), game);
        }

        static Spec<DataFeed> byGame(String game) {
            return (root, query, cb) -> cb.equal(root.get(DataFeed_.game).get(Game_.name), game);
        }

    }

}
