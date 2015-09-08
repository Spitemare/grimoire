package org.grimoire.ingest.mtgjson;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
class MtgJsonExpansion {

    private String name;

    private List<MtgJsonCard> cards;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MtgJsonCard> getCards() {
        return cards;
    }

    public void setCards(List<MtgJsonCard> cards) {
        this.cards = cards;
    }

}
