package org.grimoire.json;

import java.io.IOException;
import org.apache.maven.artifact.versioning.ComparableVersion;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class ComparableVersionDeserializer extends StdDeserializer<ComparableVersion> {

    public ComparableVersionDeserializer() {
        super(ComparableVersion.class);
    }

    @Override
    public ComparableVersion deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        return new ComparableVersion(jp.getValueAsString());
    }

}
