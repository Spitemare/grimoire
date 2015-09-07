package org.grimoire.model;

import static java.util.stream.Collectors.joining;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if (null == o) return false;
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;
        return Objects.equals(id, ((BaseEntity) o).id);
    }

    @Override
    public String toString() {
        Map<String, Object> parts = new HashMap<>();
        parts.put("id", id);
        parts.putAll(toStringParts());

        //@formatter:off
        return parts.entrySet().stream()
                    .map(e -> e.getKey() + "=" + Objects.toString(e.getValue()))
                .collect(joining(",", getClass().getSimpleName() + "[", "]"));
        //@formatter:on
    }

    protected Map<String, Object> toStringParts() {
        return Collections.emptyMap();
    }

}
