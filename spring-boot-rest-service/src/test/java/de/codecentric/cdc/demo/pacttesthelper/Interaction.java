package de.codecentric.cdc.demo.pacttesthelper;

import au.com.dius.pact.model.Pact;
import au.com.dius.pact.model.PactReader;
import au.com.dius.pact.model.RequestResponseInteraction;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Interaction {
    public static RequestResponseInteraction readFromPactFile(String pactFileName) {
        return readInteractionsFrom(pactFileName).get(0);
    }

    public static List<RequestResponseInteraction> readInteractionsFrom(String pactFileName) {
        return readInteractionsFrom(pactFileWith(pactFileName));
    }

    @NotNull
    private static File pactFileWith(String pactFileName) {
        return new File("pacts/" + pactFileName);
    }

    private static List<RequestResponseInteraction> readInteractionsFrom(File pactFile) {
        Pact pact = new PactReader().loadPact(pactFile);

        return pact.getInteractions().stream()
                .map(interaction -> (RequestResponseInteraction) interaction)
                .collect(toList());
    }
}
