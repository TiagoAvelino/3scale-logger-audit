package org.acme;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.acme.audiPersistent.Audits3scale;
import org.acme.audiPersistent.Audits3scaleRepository;
import org.acme.audits.AuditableDataRepository;
import org.acme.audits.Audits;
import org.jboss.logging.Logger;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DataTransferService {
    private static final Logger LOGGER = Logger.getLogger(DataTransferService.class);
    @Inject
    AuditableDataRepository auditsRepository;

    @Inject
    Audits3scaleRepository audits3scaleRepository;

    private static final String LAST_READ_ID_FILE = "mnt/last_read_id.txt";

    private Long lastReadId;

    private void ensureDirectoryExists() {
        File directory = new File("mnt");
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                LOGGER.info("Directory 'mnt' created successfully.");
            } else {
                LOGGER.error("Failed to create 'mnt' directory.");
            }
        }
    }

    private Long loadLastReadIdFromFile() {
        File file = new File(LAST_READ_ID_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line = reader.readLine();
                if (line != null) {
                    return Long.parseLong(line);
                }
            } catch (IOException e) {
                LOGGER.error("Error reading last read ID from file", e);
            }
        }
        return 0L; // Default to 0 if the file doesn't exist or can't be read
    }

    @Scheduled(every = "2m")
    public void transferData() {
        ensureDirectoryExists();
        this.lastReadId = loadLastReadIdFromFile();

        List<Audits> auditsList = auditsRepository.find("id > ?1", lastReadId).list();
        LOGGER.info("Starting data transfer of " + auditsList.size() + "New records.");

        for (Audits audits : auditsList) {
            Audits3scale audits3scale = mapAuditsToAudits3scale(audits);
            saveToTargetDatabase(audits3scale);
        }
    }

    // Use the target persistence unit's transaction manager
    @Transactional()
    void saveToTargetDatabase(Audits3scale audits3scale) {
        audits3scaleRepository.persist(audits3scale);
    }

    private Audits3scale mapAuditsToAudits3scale(Audits audits) {
        Audits3scale audits3scale = new Audits3scale();

        // Remove this line
        // audits3scale.id = audits.id;

        // Set other fields as before
        audits3scale.auditableType = audits.auditableType;
        audits3scale.userId = audits.userId;
        audits3scale.userType = audits.userType;
        audits3scale.action = audits.action;
        audits3scale.version = audits.version;
        audits3scale.createdAt = audits.createdAt;
        audits3scale.tenantId = audits.tenantId;
        audits3scale.providerId = audits.providerId;
        audits3scale.kind = audits.kind;
        audits3scale.auditableId = audits.auditableId;
        audits3scale.username = audits.username;
        audits3scale.auditedChanges = audits.auditedChanges;
        audits3scale.comment = audits.comment;
        audits3scale.associatedId = audits.associatedId;
        audits3scale.associatedType = audits.associatedType;
        audits3scale.remoteAddress = audits.remoteAddress;
        audits3scale.requestUuid = audits.requestUuid;
        return audits3scale;
    }
}
