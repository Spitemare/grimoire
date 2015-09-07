package org.grimoire.repo;

import org.grimoire.model.Expansion;
import org.grimoire.model.Expansion_;
import org.grimoire.repo.support.Spec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExpansionRepo
        extends JpaRepository<Expansion, Long>, JpaSpecificationExecutor<Expansion> {

    public static interface Specs {

        static Spec<Expansion> byName(String name) {
            return (root, query, cb) -> cb.equal(root.get(Expansion_.name), name);
        }

    }

}
