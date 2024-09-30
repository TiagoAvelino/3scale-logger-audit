package org.acme.audiPersistent;

import java.util.Optional;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Audits3scaleRepository implements PanacheRepository<Audits3scale> {

    public Optional<Long> findMaxIdAuditable() {
        // Use JPQL to fetch the maximum value for the id_audit
        return find("SELECT MAX(a.idAudit) FROM Audits3scale a").project(Long.class).singleResultOptional();
    }
}
