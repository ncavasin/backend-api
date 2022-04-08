package com.sip.api.domains.registration;

import com.sip.api.domains.AbstractEntity;
import com.sip.api.domains.user.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "mail_token")
public class MailToken extends AbstractEntity {
    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private Timestamp createdAt;

    @Column(nullable = false)
    private Timestamp expiresAt;

    private Timestamp confirmedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

