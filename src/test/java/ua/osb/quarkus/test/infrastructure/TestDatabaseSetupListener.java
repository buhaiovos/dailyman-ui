package ua.osb.quarkus.test.infrastructure;

import io.agroal.api.AgroalDataSource;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
@Slf4j
public class TestDatabaseSetupListener {
    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    AgroalDataSource dataSource;

    private EmbeddedMysqlServer server;

    void onStart(@Observes StartupEvent e) {
        this.server = EmbeddedMysqlServer.start();
        log.info("Launching flyway on start");

        Flyway.configure()
                .dataSource(dataSource)
                .load()
                .migrate();
    }

    void onClose(@Observes ShutdownEvent e) {
        this.server.stop();
    }
}
