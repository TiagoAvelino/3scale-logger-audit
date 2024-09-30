package org.acme.audiPersistent;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "audits_3scale")
public class Audits3scale extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @JsonProperty("id_audit")
    @Column(name = "id_audit")
    public Long idAudit;

    @JsonProperty("auditable_type")
    @Column(name = "auditable_type")
    public String auditableType;

    @JsonProperty("user_id")
    @Column(name = "user_id")
    public Long userId;

    @JsonProperty("auditable_id")
    @Column(name = "auditable_id")
    public Long auditableId;

    @JsonProperty("username")
    @Column(name = "username")
    public String username;

    @JsonProperty("user_type")
    @Column(name = "user_type")
    public String userType;

    @JsonProperty("action")
    public String action;

    @JsonProperty("version")
    public Integer version;

    @JsonProperty("created_at")
    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @JsonProperty("tenant_id")
    @Column(name = "tenant_id")
    public Long tenantId;

    @JsonProperty("provider_id")
    @Column(name = "provider_id")
    public Long providerId;

    @JsonProperty("kind")
    public String kind;

    @JsonProperty("audited_changes")
    @Column(name = "audited_changes", columnDefinition = "TEXT")
    public String auditedChanges;

    @JsonProperty("comment")
    public String comment;

    @JsonProperty("associated_id")
    @Column(name = "associated_id")
    public Integer associatedId;

    @JsonProperty("associated_type")
    @Column(name = "associated_type")
    public String associatedType;

    @JsonProperty("remote_address")
    @Column(name = "remote_address")
    public String remoteAddress;

    @JsonProperty("request_uuid")
    @Column(name = "request_uuid")
    public String requestUuid;

}
