package es.ibm.usermanagement.repository;

import es.ibm.usermanagement.entity.UserAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuditRepository  extends JpaRepository<UserAudit, Long> {
}
