package org.acme;

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
@Table(name = "audits")
public class Audits extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @JsonProperty("auditable_type")
    @Column(name = "auditable_type")
    public String auditableType;

    @JsonProperty("user_id")
    @Column(name = "user_id")
    public Long userId;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuditableType() {
        return auditableType;
    }

    public void setAuditableType(String auditableType) {
        this.auditableType = auditableType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getAuditedChanges() {
        return auditedChanges;
    }

    public void setAuditedChanges(String auditedChanges) {
        this.auditedChanges = auditedChanges;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getAssociatedId() {
        return associatedId;
    }

    public void setAssociatedId(Integer associatedId) {
        this.associatedId = associatedId;
    }

    public String getAssociatedType() {
        return associatedType;
    }

    public void setAssociatedType(String associatedType) {
        this.associatedType = associatedType;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public String getRequestUuid() {
        return requestUuid;
    }

    public void setRequestUuid(String requestUuid) {
        this.requestUuid = requestUuid;
    }
}
