package com.sip.api.migration;

import com.google.common.base.Stopwatch;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class MigrationRunner implements ApplicationListener<ApplicationReadyEvent> {

    private final List<StartUpMigration> startUpMigrations;

    @Override
    @Transactional
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        log.info("Migration runner invoked.");
        Stopwatch stopwatch = Stopwatch.createStarted();
        startUpMigrations.forEach(migration -> {
            if (migration.isActive()) {
                log.info("Starting migration: {}", migration.getName());
                migration.run(event);
                log.info("Migration {} successfully applied!", migration.getName());
            }
        });
        stopwatch.stop();
        log.info("Migrations have finished. Took {} seconds.", stopwatch.elapsed(TimeUnit.SECONDS));
    }
}

