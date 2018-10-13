package de.codecentric.cdc.demo.pacttesthelper;

import au.com.dius.pact.model.Response;

import java.util.HashMap;
import java.util.Map;

import static au.com.dius.pact.provider.ResponseComparison.compareResponse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProviderResponse {
    private Map<String, Object> actualResponse;

    public ProviderResponse(Map<String, Object> actualResponse) {
        this.actualResponse = actualResponse;
    }

    public void assertEqualTo(Response expectedResponse) {
        assertNoHeaderDifferencesTo(expectedResponse);
        assertNoBodyDifferencesTo(expectedResponse);
    }

    private void assertNoBodyDifferencesTo(Response expectedResponse) {
        assertEquals("response body comparison errors",
                noErrors(),
                bodyDifferences(between(actualResponse, expectedResponse)));
    }

    private void assertNoHeaderDifferencesTo(Response expectedResponse) {
        headerDifferences(between(actualResponse, expectedResponse))
                .forEach( (headerName, headerValue) ->
                            assertTrue("Header: " + headerName + " does not match", (boolean) headerValue)
        );
    }

    private Map<String, Object> between(Map<String, Object> actualResponse, Response expectedResponse) {
        return (Map<String, Object>) compareResponse(expectedResponse,  actualResponse, (int)
                actualResponse.get("statusCode"), (Map)
                actualResponse.get("headers"), (String)
                actualResponse.get("data"));
    }

    private Map headerDifferences(Map<String, Object> comparison) {
        return (Map) comparison.get("headers");
    }

    private Object bodyDifferences(Map<String, Object> comparison) {
        return comparison.get("body");
    }

    private HashMap noErrors() {
        return new HashMap();
    }
}
