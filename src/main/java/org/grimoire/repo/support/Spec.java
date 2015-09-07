package org.grimoire.repo.support;

import org.springframework.data.jpa.domain.Specification;

public interface Spec<T> extends Specification<T> {

    default Spec<T> and(Specification<T> spec) {
        return (root, query, cb) -> cb.and(toPredicate(root, query, cb),
                spec.toPredicate(root, query, cb));
    }

    default Spec<T> or(Specification<T> spec) {
        return (root, query, cb) -> cb.or(toPredicate(root, query, cb),
                spec.toPredicate(root, query, cb));
    }

    static <T> Spec<T> not(Specification<T> spec) {
        return (root, query, cb) -> spec.toPredicate(root, query, cb).not();
    }

}
