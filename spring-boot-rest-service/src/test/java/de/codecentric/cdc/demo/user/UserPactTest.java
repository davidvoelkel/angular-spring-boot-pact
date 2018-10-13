package de.codecentric.cdc.demo.user;

import au.com.dius.pact.model.RequestResponseInteraction;
import de.codecentric.cdc.demo.pacttesthelper.Interaction;
import de.codecentric.cdc.demo.pacttesthelper.ProviderResponse;
import de.codecentric.cdc.demo.pacttesthelper.ProviderSpringBootTestClient;
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

    @MockBean UserRepository userRepositoryMock;
    @Autowired
    ProviderSpringBootTestClient providerClient;

    @Test
    public void getUser() {
        when(userRepositoryMock.findUserByUserName("david79"))
                               .thenReturn(new User()
                                                    .withName("David")
                                                    .withEmail("david@gmail.com"));

        RequestResponseInteraction interaction =
                Interaction.readFromPactFile("angular-user-service-rest-user-service.json");

        ProviderResponse response = providerClient.send(interaction.getRequest());

        response.assertEqualTo(interaction.getResponse());
    }
}
