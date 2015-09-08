package org.grimoire.model.support;

import org.apache.maven.artifact.versioning.ComparableVersion;
import org.jadira.usertype.spi.shared.AbstractSingleColumnUserType;

public class ComparableVersionUserType extends
        AbstractSingleColumnUserType<ComparableVersion, String, StringColumnComparableVersionMapper> {}
