package com.sip.api.domains.payment;

import com.sip.api.domains.TimeTrackable;
import com.sip.api.domains.subscription.Subscription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
public class Payment extends TimeTrackable {
    @Column(nullable = false)
    private LocalDate paymentDate;

    private Double amountPaid;

    private String transactionId;

    @OneToMany(mappedBy = "payment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private Set<Subscription> subscriptions = new HashSet<>();
}