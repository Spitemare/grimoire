package org.grimoire.model.support;

import org.apache.maven.artifact.versioning.ComparableVersion;
import org.jadira.usertype.spi.shared.AbstractStringColumnMapper;

public class StringColumnComparableVersionMapper
        extends AbstractStringColumnMapper<ComparableVersion> {

    @Override
    public ComparableVersion fromNonNullValue(String s) {
        return new ComparableVersion(s);
    }

    @Override
    public String toNonNullValue(ComparableVersion value) {
        return value.toString();
    }

}
