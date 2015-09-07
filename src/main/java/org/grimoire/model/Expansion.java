package org.grimoire.model;

import static org.grimoire.util.Maps.entry;
import static org.grimoire.util.Maps.unmodifiableMapOf;
import java.util.List;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Expansion extends BaseEntity {

    @Basic(optional = false)
    private String name;

    @ManyToOne(optional = false)
    private Game game;

    @OneToMany(mappedBy = "expansion", cascade = CascadeType.ALL)
    private List<Card> cards;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    protected Map<String, Object> toStringParts() {
        //@formatter:off
        return unmodifiableMapOf(
                entry("name", name),
                entry("game", game.getName()));
        //@formatter:on
    }

}
