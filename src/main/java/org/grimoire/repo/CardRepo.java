package org.grimoire.repo;

import org.grimoire.model.Card;
import org.grimoire.model.Card_;
import org.grimoire.model.Expansion;
import org.grimoire.model.Expansion_;
import org.grimoire.repo.support.Spec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CardRepo extends JpaRepository<Card, Long>, JpaSpecificationExecutor<Card> {

    public static interface Specs {

        static Spec<Card> byName(String name) {
            return (root, query, cb) -> cb.equal(root.get(Card_.name), name);
        }

        static Spec<Card> byExpansion(Expansion expansion) {
            return (root, query, cb) -> cb.equal(root.get(Card_.expansion), expansion);
        }

        static Spec<Card> byExpansion(String expansion) {
            return (root, query, cb) -> cb.equal(root.get(Card_.expansion).get(Expansion_.name),
                    expansion);
        }

    }

}
