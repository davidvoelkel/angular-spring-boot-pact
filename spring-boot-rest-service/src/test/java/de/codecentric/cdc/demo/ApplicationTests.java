package de.codecentric.cdc.demo;

import au.com.dius.pact.model.Interaction;
import au.com.dius.pact.model.Pact;
import au.com.dius.pact.model.PactReader;
import au.com.dius.pact.model.RequestResponseInteraction;
import au.com.dius.pact.provider.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	@LocalServerPort
	private int port;

	private ProviderInfo serviceProvider;
	private Pact testConsumerPact;

	@Before
	public void setupProvider() {
		serviceProvider = new ProviderInfo("Spring Boot User REST-Endpoint");
		serviceProvider.setProtocol("http");
		serviceProvider.setHost("localhost");
		serviceProvider.setPort(port);
		serviceProvider.setPath("/");

		new RestTemplate();

		ConsumerInfo consumer = new ConsumerInfo();
		consumer.setName("angular-user-service");
		consumer.setPactFile(new File("pacts/angular-user-service-rest-user-service.json"));

		//  serviceProvider.getConsumers().add(consumer);
		testConsumerPact = (Pact) new PactReader().loadPact(consumer.getPactFile());
	}

	@Test
	public void runConsumerPacts() {

		//grab the first interaction from the pact with consumer
		List<Interaction> interactions = testConsumerPact.getInteractions();
		RequestResponseInteraction interaction1 = (RequestResponseInteraction) interactions.get(0);

		//setup any provider state

		//setup the client and interaction to fire against the provider
		ProviderClient client = new ProviderClient(serviceProvider, new HttpClientFactory());
		Map<String, Object> clientResponse = (Map<String, Object>) client.makeRequest(interaction1.getRequest());



		Map<String, Object> result = (Map<String, Object>) ResponseComparison.compareResponse(interaction1.getResponse(),
				clientResponse, (int) clientResponse.get("statusCode"), (Map) clientResponse.get("headers"), (String) clientResponse.get("data"));

//		//assert all good
//		assertThat(result.get("method"), is(true)); // method type matches
//
//		Map headers = (Map) result.get("headers"); //headers match
//		headers.forEach( (k, v) ->
//				assertThat(format("Header: [%s] does not match", k), v, org.hamcrest.Matchers.equalTo(true))
//		);
//
//		assertThat((Collection<Object>)((Map)result.get("body")).values(), org.hamcrest.Matchers.hasSize(0)); // empty list of body mismatches
	}
}
