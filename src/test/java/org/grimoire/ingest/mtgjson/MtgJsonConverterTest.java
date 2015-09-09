package org.grimoire.ingest.mtgjson;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import java.util.Collection;
import java.util.Collections;
import org.grimoire.model.Card;
import org.grimoire.model.Expansion;
import org.junit.Test;

public class MtgJsonConverterTest {

    @Test
    public void testConvert() {
        MtgJsonConverter converter = new MtgJsonConverter();

        MtgJsonCard mtgJsonCard = new MtgJsonCard();
        mtgJsonCard.setName("test");

        MtgJsonExpansion mtgJsonExpansion = new MtgJsonExpansion();
        mtgJsonExpansion.setName("test");
        mtgJsonExpansion.setCards(Collections.singletonList(mtgJsonCard));

        Collection<Expansion> expansions = converter
                .convert(Collections.singletonList(mtgJsonExpansion));
        assertThat(expansions, hasSize(1));

        Expansion expansion = expansions.iterator().next();
        assertThat(expansion.getName(), is("test"));
        assertThat(expansion.getCards(), hasSize(1));

        Card card = expansion.getCards().get(0);
        assertThat(card.getName(), is("test"));
        assertThat(card.getExpansion(), is(sameInstance(expansion)));
    }

    @Test
    public void testConvertEmptyCollection() {
        MtgJsonConverter converter = new MtgJsonConverter();
        Collection<Expansion> expansions = converter.convert(Collections.emptyList());
        assertThat(expansions, is(empty()));
    }

}
