package org.grimoire.ingest.mtgjson;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MtgJsonUnmarshallerTest {

    @Test
    public void testUnmarshall() throws IOException {
        MtgJsonUnmarshaller mtgJsonUnmarshaller = new MtgJsonUnmarshaller(new ObjectMapper());

        Collection<MtgJsonExpansion> expansions;
        try (InputStream input = getClass().getResourceAsStream("test.json")) {
            expansions = mtgJsonUnmarshaller.unmarshall(input);
        }
        assertThat(expansions, hasSize(1));

        MtgJsonExpansion expansion = expansions.iterator().next();
        assertThat(expansion.getName(), is("Test Expansion"));
        assertThat(expansion.getCards(), hasSize(1));

        MtgJsonCard card = expansion.getCards().get(0);
        assertThat(card.getName(), is("Test Card"));
    }

}
