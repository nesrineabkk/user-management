package es.ibm.usermanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "user_audit")
public class UserAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_uuid", nullable = false)
    private String userUuid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditAction action;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public enum AuditAction {
        CREATE,
        UPDATE,
        DELETE
    }

    public UserAudit() {
    }

    public UserAudit(String userUuid, AuditAction action, LocalDateTime timestamp) {
        this.userUuid = userUuid;
        this.action = action;
        this.timestamp = timestamp;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public AuditAction getAction() {
        return action;
    }

    public void setAction(AuditAction action) {
        this.action = action;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }


}
