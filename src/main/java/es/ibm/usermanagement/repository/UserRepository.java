package es.ibm.usermanagement.repository;



import es.ibm.usermanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface  UserRepository   extends JpaRepository<User, String>{

    @Query("SELECT u FROM User u WHERE " +
            "(:firstName IS NULL OR u.firstName LIKE %:firstName%) AND " +
            "(:age IS NULL OR u.age = :age)")
    Page<User> searchUsers(@Param("firstName") String firstName,
                           @Param("age") Integer age,
                           Pageable pageable);


}

