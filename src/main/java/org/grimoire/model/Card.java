package org.grimoire.model;

import static org.grimoire.util.Maps.entry;
import static org.grimoire.util.Maps.unmodifiableMapOf;
import java.util.List;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Card extends BaseEntity {

    @Basic(optional = false)
    private String name;

    @ManyToOne(optional = false)
    private Expansion expansion;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Stock> stocks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Expansion getExpansion() {
        return expansion;
    }

    public void setExpansion(Expansion expansion) {
        this.expansion = expansion;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    @Override
    protected Map<String, Object> toStringParts() {
        //@formatter:off
        return unmodifiableMapOf(
                entry("name", name),
                entry("expansion", expansion.getName()));
        //@formatter:on
    }

}
