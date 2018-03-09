package de.codecentric.cdc.demo;

import au.com.dius.pact.model.*;
import au.com.dius.pact.provider.ConsumerInfo;
import au.com.dius.pact.provider.ProviderClient;
import au.com.dius.pact.provider.ProviderInfo;
import au.com.dius.pact.provider.ResponseComparison;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Component
public class PactTestSetup implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {

    private ProviderClient client;
    private Pact testConsumerPact;
    private ProviderInfo serviceProvider;

    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        int port = event.getEmbeddedServletContainer()
                        .getPort();
        serviceProvider = serviceProviderRunningOn(port);

        client = new ProviderClient(serviceProvider, dontCare -> HttpClients.custom().build());
    }

    public void loadPactFile(String pactFile) {
        ConsumerInfo consumer = new ConsumerInfo();
        consumer.setPactFile(new File("pacts/" + pactFile));

        testConsumerPact = (Pact) new PactReader().loadPact(consumer.getPactFile());

        serviceProvider.setName(testConsumerPact.getProvider().getName());
    }

    public List<RequestResponseInteraction> getInteractions() {
        return testConsumerPact.getInteractions().stream()
                                                 .map(interaction -> (RequestResponseInteraction) interaction)
                                                 .collect(toList());
    }

    public void requestShouldLeadToInteraction(Request request, RequestResponseInteraction expectedInteraction) {
        Map<String, Object> actualResponse = client.makeRequest(request);

        Map<String, Object> comparison = compare(expectedInteraction, actualResponse);

        assertNoHeaderErrors(comparison);

        assertEquals("response body comparison errors", noErrors(), bodyErrors(comparison));
    }

    private void assertNoHeaderErrors(Map<String, Object> comparison) {
        headerErrors(comparison).forEach( (headerName, headerValue) ->
                assertTrue("Header: " + headerName + " does not match", (boolean) headerValue)
        );
    }

    private ProviderInfo serviceProviderRunningOn(int port) {
        ProviderInfo serviceProvider = new ProviderInfo();
        serviceProvider.setProtocol("http");
        serviceProvider.setHost("localhost");
        serviceProvider.setPort(port);
        serviceProvider.setPath("/");
        return serviceProvider;
    }

    private Map<String, Object> compare(RequestResponseInteraction expectedInteraction, Map<String, Object> actualResponse) {
        Response expectedResponse = expectedInteraction.getResponse();
        return (Map<String, Object>) ResponseComparison.compareResponse(expectedResponse,
                                                                        actualResponse, (int)
                                                                        actualResponse.get("statusCode"), (Map)
                                                                        actualResponse.get("headers"), (String)
                                                                        actualResponse.get("data"));
    }

    private Map headerErrors(Map<String, Object> comparison) {
        return (Map) comparison.get("headers");
    }

    private Object bodyErrors(Map<String, Object> comparison) {
        return comparison.get("body");
    }

    public ProviderClient getClient() {
        return client;
    }

    private HashMap noErrors() {
        return new HashMap();
    }
}