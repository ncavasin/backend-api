package com.sip.api.migration;

import org.springframework.boot.context.event.ApplicationReadyEvent;

public interface StartUpMigration {
    void run(ApplicationReadyEvent event);

    String getName();

    boolean isActive();
}
