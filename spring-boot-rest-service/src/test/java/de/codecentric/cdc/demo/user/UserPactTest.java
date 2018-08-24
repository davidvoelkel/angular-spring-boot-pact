package de.codecentric.cdc.demo.user;

import de.codecentric.cdc.demo.PactTestSetup;
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
    @Autowired PactTestSetup pactSetup;

    @Test
    public void getUser() {
        when(userRepositoryMock.findUserByUserName("david79"))
                               .thenReturn(new User()
                                                    .withName("David")
                                                    .withEmail("david@gmail.com"));

        pactSetup.loadPactFile("angular-user-service-rest-user-service.json");

        pactSetup.requestShouldLeadToInteraction(
                                                 pactSetup.getInteraction().getRequest(),
                                                 pactSetup.getInteraction());
    }
}
