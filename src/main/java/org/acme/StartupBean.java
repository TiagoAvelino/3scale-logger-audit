package org.acme;

import io.quarkus.runtime.Startup;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.spi.CDI;

@ApplicationScoped
public class StartupBean {

    @Startup
    void onStart(@Observes StartupEvent ev) {
        LogReaderService logReaderService = CDI.current().select(LogReaderService.class).get();
        logReaderService.checkAndReadLogs(); // Trigger the log reading process immediately
    }
}
