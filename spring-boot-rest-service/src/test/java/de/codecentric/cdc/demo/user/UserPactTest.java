package de.codecentric.cdc.demo.user;

import au.com.dius.pact.model.Request;
import au.com.dius.pact.model.RequestResponseInteraction;
import de.codecentric.cdc.demo.PactTestSetup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserPactTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    PactTestSetup pactSetup;

    @Before
    public void setup() {
        pactSetup.loadPactFile("angular-user-service-rest-user-service.json");
    }

    @Test
    public void requestUser() {

        // mock stuff below the controller
        User user = new User();
        user.setName("David");
        user.setEmail("david@gmail.com");
        when(userRepository.findUserByAlias("david79"))
                           .thenReturn(user);

        RequestResponseInteraction interaction = pactSetup.getInteractions()
                                                          .get(0);
        Request userRequest = interaction.getRequest();

        // You can add here additional stuff to your request, if you don't want to let them be part of the contract.
        // E.g. authentication stuff from your Auth-Proxy:

        // userRequest.getHeaders().put("Authorization", "Bearer $TEST_AUTH_TOKEN");

        pactSetup.requestShouldLeadToInteraction(userRequest, interaction);
    }
}
