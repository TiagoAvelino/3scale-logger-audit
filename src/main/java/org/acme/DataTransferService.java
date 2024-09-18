package org.acme;

import java.util.List;

import org.acme.audiPersistent.Audits3scale;
import org.acme.audiPersistent.Audits3scaleRepository;
import org.acme.audits.AuditableDataRepository;
import org.acme.audits.Audits;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DataTransferService {

    @Inject
    AuditableDataRepository auditsRepository;

    @Inject
    Audits3scaleRepository audits3scaleRepository;

    // Use the source persistence unit's transaction manager
    // @Transactional()
    public void transferData() {
        List<Audits> auditsList = auditsRepository.listAll();

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
