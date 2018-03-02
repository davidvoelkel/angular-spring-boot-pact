package de.codecentric.cdc.demo;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MyServiceTest {
    private static final Map<String, Object> SOME_HTTP_PARAMS = new HashMap<>();
    private static final Map<String, Object> SOME_OTHER_HTTP_PARAMS = new HashMap<>();
    private static final Map<String, Object> SOME_WRONG_HTTP_PARAMS = new HashMap<>();
    private static final String TEST_URL = "";
    private static ResponseEntity<MyResponseClass> ERROR_RESPONSE =
            new ResponseEntity<>(new MyResponseClass(), HttpStatus.NOT_FOUND);
    private static ResponseEntity<MyResponseClass> EMPTY_RESPONSE =
            new ResponseEntity<>(new MyResponseClass(), HttpStatus.OK);
    private static ResponseEntity<MyResponseClass> ITEM_RESPONSE =
            new ResponseEntity<>(new MyResponseClass(Arrays.asList("some", "thing")), HttpStatus.OK);

    private RestTemplate restTemplate;
    private MyService myServiceUnderTest;

    @Before
    public void setUp() {
        restTemplate = mock(RestTemplate.class);
        myServiceUnderTest = new MyService(restTemplate);
    }

    @Test
    public void testSomething() {
        restEndpointShouldAnswer(SOME_HTTP_PARAMS, () -> ITEM_RESPONSE);
        // some more setup

        myServiceUnderTest.doSomething();
        // some assertions
    }

    @Test
    public void testSomeError() {
        restEndpointShouldAnswer(SOME_WRONG_HTTP_PARAMS, () ->
        { throw new ResourceAccessException("I/O error"); });
        // some more setup

        myServiceUnderTest.doSomething();
        // some assertions
    }

    @Test
    public void testSomethingElse() {
        restEndpointShouldAnswer(SOME_OTHER_HTTP_PARAMS, () -> EMPTY_RESPONSE);
        // some more setup

        myServiceUnderTest.doSomething();
        // some assertions
    }

    public void restEndpointShouldAnswer(Map<String, Object> httpParams,
                                         Supplier<ResponseEntity<MyResponseClass>> response) {
        when(restTemplate.getForEntity(TEST_URL, MyResponseClass.class, httpParams))
                         .thenAnswer((invocation) -> response.get());
    }
}
