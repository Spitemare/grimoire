package org.grimoire.json;

import java.io.IOException;
import org.apache.maven.artifact.versioning.ComparableVersion;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ComparableVersionSerializer extends StdSerializer<ComparableVersion> {

    public ComparableVersionSerializer() {
        super(ComparableVersion.class);
    }

    @Override
    public void serialize(ComparableVersion value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonGenerationException {
        jgen.writeString(value.toString());
    }

}
