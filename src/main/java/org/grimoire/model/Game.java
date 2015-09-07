package org.grimoire.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Game extends BaseEntity {

    @Basic(optional = false)
    private String name;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Expansion> expansions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Expansion> getExpansions() {
        return expansions;
    }

    public void setExpansions(List<Expansion> expansions) {
        this.expansions = expansions;
    }

    @Override
    protected Map<String, Object> toStringParts() {
        return Collections.singletonMap("name", name);
    }

}
