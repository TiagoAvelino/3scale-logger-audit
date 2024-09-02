package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuditableDataRepository implements PanacheRepository<Audits> {
    // You can add custom queries here if needed
}