package com.sip.api.repositories;

import com.sip.api.domains.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByDni(Integer dni);

    boolean existsByEmail(String email);
}
