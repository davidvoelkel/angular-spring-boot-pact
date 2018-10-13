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
        assertNoMethodDifferencesTo(expectedResponse);
        assertNoHeaderDifferencesTo(expectedResponse);
        assertNoBodyDifferencesTo(expectedResponse);
    }

    private void assertNoMethodDifferencesTo(Response expectedResponse) {
        assertEquals("response http method",
                true,
                differencesComparedTo(expectedResponse).get("method"));
    }

    private void assertNoBodyDifferencesTo(Response expectedResponse) {
        assertEquals("response body comparison errors",
                noDifferences(),
                differencesComparedTo(expectedResponse).get("body"));
    }

    private void assertNoHeaderDifferencesTo(Response expectedResponse) {
        ((Map) differencesComparedTo(expectedResponse).get("headers"))
                .forEach( (headerName, headerValue) ->
                            assertTrue("Header: " + headerName + " does not match", (boolean) headerValue)
        );
    }

    private Map<String, Object> differencesComparedTo(Response expectedResponse) {
        return (Map<String, Object>) compareResponse(expectedResponse,  actualResponse, (int)
                actualResponse.get("statusCode"), (Map)
                actualResponse.get("headers"), (String)
                actualResponse.get("data"));
    }

    private HashMap noDifferences() {
        return new HashMap();
    }
}
