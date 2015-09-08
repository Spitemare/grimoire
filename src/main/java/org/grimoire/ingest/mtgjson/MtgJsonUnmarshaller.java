package org.grimoire.ingest.mtgjson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.grimoire.ingest.support.CamelEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

@CamelEndpoint("mtgjson-unmarshaller")
@Component
public class MtgJsonUnmarshaller {

    private final ObjectMapper objectMapper;

    @Autowired
    public MtgJsonUnmarshaller(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Collection<MtgJsonExpansion> unmarshall(InputStream input) throws IOException {
        List<MtgJsonExpansion> expansions = new ArrayList<>();

        JsonParser parser = objectMapper.getFactory().createParser(input);

        parser.nextValue(); // advance into top level object
        while (parser.nextValue() != null) {
            MtgJsonExpansion expansion = parser.readValueAs(MtgJsonExpansion.class);
            if (expansion == null) continue;
            expansions.add(expansion);
        }

        return expansions;
    }

}
