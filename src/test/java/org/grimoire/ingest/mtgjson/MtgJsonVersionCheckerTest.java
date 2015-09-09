package org.grimoire.ingest.mtgjson;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.maven.artifact.versioning.ComparableVersion;
import org.grimoire.model.DataFeed;
import org.grimoire.repo.DataFeedRepo;
import org.grimoire.web.support.ComparableVersionHttpMessageConverter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.AsyncRestTemplate;

public class MtgJsonVersionCheckerTest {

    private AsyncRestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private MtgJsonProps mtgJsonProps;

    @Before
    public void setUp() {
        mtgJsonProps = new MtgJsonProps();
        mtgJsonProps.setVersionUri(URI.create("http://www.example.com/version"));
        mtgJsonProps.setDownloadUri(URI.create("http://www.example.com/download"));

        restTemplate = new AsyncRestTemplate();
        restTemplate.setMessageConverters(
                Collections.singletonList(new ComparableVersionHttpMessageConverter()));

        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testCheck() throws Exception {
        mockServer.expect(requestTo(mtgJsonProps.getVersionUri())).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("1.0", MediaType.TEXT_PLAIN));

        DataFeed dataFeed = new DataFeed();
        dataFeed.setVersion(new ComparableVersion("0.0"));
        DataFeedRepo dataFeedRepo = Mockito.mock(DataFeedRepo.class);
        Mockito.when(dataFeedRepo.findOne(Matchers.<Specification<DataFeed>> any()))
                .thenReturn(dataFeed);

        Map<String, Object> headers = new HashMap<>();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getHeaders()).thenReturn(headers);
        Mockito.doAnswer(invocation -> {
            assertThat(invocation.getArgumentAt(0, URI.class),
                    is(sameInstance(mtgJsonProps.getDownloadUri())));
            return null;
        }).when(message).setBody(Mockito.anyObject());

        Exchange exchange = Mockito.mock(Exchange.class);
        Mockito.when(exchange.getIn()).thenReturn(message);

        Mockito.doAnswer(invocation -> {
            assertThat(invocation.getArgumentAt(1, Boolean.class), is(false));
            return null;
        }).when(exchange).setProperty(Mockito.eq(Exchange.ROUTE_STOP), Mockito.anyBoolean());

        MtgJsonVersionChecker mtgJsonVersionChecker = new MtgJsonVersionChecker(restTemplate,
                mtgJsonProps, dataFeedRepo);
        mtgJsonVersionChecker.check(exchange);

        assertThat(headers,
                hasEntry(MtgJsonConstants.HEADER_LOCAL_VERSION, new ComparableVersion("0.0")));
        assertThat(headers,
                hasEntry(MtgJsonConstants.HEADER_REMOTE_VERSION, new ComparableVersion("1.0")));

        mockServer.verify();
    }

    @Test
    public void testCheckOlderVersion() throws Exception {
        mockServer.expect(requestTo(mtgJsonProps.getVersionUri())).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("0.0", MediaType.TEXT_PLAIN));

        DataFeed dataFeed = new DataFeed();
        dataFeed.setVersion(new ComparableVersion("1.0"));
        DataFeedRepo dataFeedRepo = Mockito.mock(DataFeedRepo.class);
        Mockito.when(dataFeedRepo.findOne(Matchers.<Specification<DataFeed>> any()))
                .thenReturn(dataFeed);

        Map<String, Object> headers = new HashMap<>();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getHeaders()).thenReturn(headers);
        Exchange exchange = Mockito.mock(Exchange.class);
        Mockito.when(exchange.getIn()).thenReturn(message);

        Mockito.doAnswer(invocation -> {
            assertThat(invocation.getArgumentAt(1, Boolean.class), is(true));
            return null;
        }).when(exchange).setProperty(Mockito.eq(Exchange.ROUTE_STOP), Mockito.anyBoolean());

        MtgJsonVersionChecker mtgJsonVersionChecker = new MtgJsonVersionChecker(restTemplate,
                mtgJsonProps, dataFeedRepo);
        mtgJsonVersionChecker.check(exchange);

        assertThat(headers,
                hasEntry(MtgJsonConstants.HEADER_LOCAL_VERSION, new ComparableVersion("1.0")));
        assertThat(headers,
                hasEntry(MtgJsonConstants.HEADER_REMOTE_VERSION, new ComparableVersion("0.0")));

        mockServer.verify();
    }

}
