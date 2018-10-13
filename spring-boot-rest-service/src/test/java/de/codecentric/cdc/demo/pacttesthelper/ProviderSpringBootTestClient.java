package de.codecentric.cdc.demo.pacttesthelper;

import au.com.dius.pact.model.Request;
import au.com.dius.pact.provider.ProviderClient;
import au.com.dius.pact.provider.ProviderInfo;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ProviderSpringBootTestClient implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {

    private ProviderClient client;

    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        int port = event.getEmbeddedServletContainer()
                        .getPort();

        client = new ProviderClient(
                serviceProviderRunningOn(port),
                dontCare -> HttpClients.custom().build()
        );
    }

    public ProviderResponse send(Request request) {
        return new ProviderResponse(client.makeRequest(request));
    }

    private ProviderInfo serviceProviderRunningOn(int port) {
        ProviderInfo serviceProvider = new ProviderInfo();
        serviceProvider.setProtocol("http");
        serviceProvider.setHost("localhost");
        serviceProvider.setPort(port);
        serviceProvider.setPath("/");
        return serviceProvider;
    }

}