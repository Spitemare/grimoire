package org.grimoire.ingest.mtgjson;

import java.util.Collection;
import java.util.stream.Collectors;
import org.grimoire.ingest.support.CamelEndpoint;
import org.grimoire.model.Card;
import org.grimoire.model.Expansion;
import org.springframework.stereotype.Component;

@CamelEndpoint("mtgjson-converter")
@Component
public class MtgJsonConverter {

    public Collection<Expansion> convert(Collection<MtgJsonExpansion> mtgJsonExpansions) {
        return mtgJsonExpansions.stream().map(m -> {
            Expansion e = new Expansion();
            e.setName(m.getName());
            e.setCards(m.getCards().stream().map(mc -> {
                Card c = new Card();
                c.setName(mc.getName());
                c.setExpansion(e);
                return c;
            }).collect(Collectors.toList()));
            return e;
        }).collect(Collectors.toList());
    }

}
