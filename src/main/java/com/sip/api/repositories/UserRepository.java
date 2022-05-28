package com.sip.api.repositories;

import com.sip.api.domains.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @Query(nativeQuery = true,
            value = "SELECT u.*" +
                    "FROM user_data u " +
                    "JOIN user_data_role udr ON u.id = udr.user_id " +
                    "JOIN role r ON udr.role_id = r.id " +
                    "WHERE r.id = :roleId")
    List<User> findAllByRoles(String roleId);

    boolean existsByDni(Integer dni);

    boolean existsByEmail(String email);

    @Query("select u from User u where u.dni = ?1")
    Optional<User> findByDni(int dni);

    @Query("select u from User u where u.email = ?1")
    Optional<User> findByEmail(String email);
}
