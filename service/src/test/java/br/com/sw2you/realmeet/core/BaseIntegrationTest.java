package br.com.sw2you.realmeet.core;

import static br.com.sw2you.realmeet.utils.TestConstants.TEST_CLIENT_API_KEY;
import static br.com.sw2you.realmeet.utils.TestConstants.TEST_CLIENT_DESCRIPTION;
import static org.mockito.BDDMockito.given;

import br.com.sw2you.realmeet.Application;
import br.com.sw2you.realmeet.api.ApiClient;
import br.com.sw2you.realmeet.domain.entity.Client;
import br.com.sw2you.realmeet.domain.repository.ClientRepository;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = "integration-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public abstract class BaseIntegrationTest {
    @Autowired
    private Flyway flyway;

    @LocalServerPort
    private int serverPort;

    @MockBean
    private ClientRepository clientRepository;

    @BeforeEach
    void setup() throws Exception {
        setupFlyway();
        mockApiKey();
        setupEach();
    }

    protected void setupEach() throws Exception {}

    protected void setLocalHostBasePath(ApiClient apiClient, String path) throws MalformedURLException {
        apiClient.setBasePath(new URL("http", "localhost", serverPort, path).toString());
    }

    private void setupFlyway() {
        flyway.clean();
        flyway.migrate();
    }

    private void mockApiKey() {
        given(clientRepository.findById(TEST_CLIENT_API_KEY))
            .willReturn(
                Optional.of(
                    Client
                        .newBuilder()
                        .apiKey(TEST_CLIENT_API_KEY)
                        .description(TEST_CLIENT_DESCRIPTION)
                        .active(true)
                        .build()
                )
            );
    }
}
