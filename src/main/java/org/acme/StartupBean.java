package org.acme;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Inject;

@ApplicationScoped
public class StartupBean {

    @Inject
    DataTransferService dataTransferService;

    void onStart(@Observes StartupEvent ev) {
        dataTransferService.transferData(); // Trigger the data transfer process
    }
}
