package com.exemplo.auditor.adapters.out.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "audit_logs")
public class AuditLogEntity {
    @Id
    private UUID errorId;
    private String queueName;

    @Lob
    private String payload;
    private Instant timestamp;
    private String status;
    private String severity;

}