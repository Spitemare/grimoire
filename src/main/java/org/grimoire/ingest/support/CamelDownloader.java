package org.grimoire.ingest.support;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@CamelEndpoint("downloader")
@Component
public class CamelDownloader {

    private final RestTemplate restTemplate;

    @Autowired
    public CamelDownloader(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public byte[] download(URI uri) throws IOException {
        if ("file".equals(uri.getScheme())) return readFile(uri);
        return restTemplate.getForObject(uri, byte[].class);
    }

    private static byte[] readFile(URI uri) throws IOException {
        Path path = Paths.get(uri);
        return Files.readAllBytes(path);
    }

}
