package org.grimoire.ingest.mtgjson;

import java.net.URI;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("mtgjson")
public class MtgJsonProps {

    private URI versionUri;

    private URI downloadUri;

    public URI getVersionUri() {
        return versionUri;
    }

    public void setVersionUri(URI versionUri) {
        this.versionUri = versionUri;
    }

    public URI getDownloadUri() {
        return downloadUri;
    }

    public void setDownloadUri(URI downloadUri) {
        this.downloadUri = downloadUri;
    }

}
