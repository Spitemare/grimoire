package org.grimoire.web.support;

import java.io.IOException;
import org.apache.maven.artifact.versioning.ComparableVersion;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.StringHttpMessageConverter;

public class ComparableVersionHttpMessageConverter
        extends AbstractHttpMessageConverter<ComparableVersion> {

    private final StringHttpMessageConverter stringHttpMessageConverter;

    public ComparableVersionHttpMessageConverter() {
        super(MediaType.TEXT_PLAIN);
        stringHttpMessageConverter = new StringHttpMessageConverter();
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return ComparableVersion.class.equals(clazz);
    }

    @Override
    protected ComparableVersion readInternal(Class<? extends ComparableVersion> clazz,
            HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return new ComparableVersion(stringHttpMessageConverter.read(String.class, inputMessage));
    }

    @Override
    protected void writeInternal(ComparableVersion t, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        stringHttpMessageConverter.write(t.toString(), MediaType.TEXT_PLAIN, outputMessage);
    }

}
