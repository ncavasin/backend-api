package com.sip.api.repositories;

import com.sip.api.domains.registration.MailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MailTokenRepository extends JpaRepository<MailToken, String> {

    @Query("SELECT t FROM MailToken t WHERE t.token = ?1")
    Optional<MailToken> findByToken(String token);

    @Query("SELECT t FROM MailToken t WHERE t.user.id = ?1")
    List<MailToken> findByUserId(String userId);
}
