package org.grimoire.repo;

import org.grimoire.model.User;
import org.grimoire.model.User_;
import org.grimoire.repo.support.Spec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepo extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    public static interface Specs {

        static Spec<User> byUsername(String username) {
            return (root, query, cb) -> cb.equal(root.get(User_.username), username);
        }

    }

}
