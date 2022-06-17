package com.sip.api.domains.payment;

import com.sip.api.domains.TimeTrackable;
import com.sip.api.domains.subscription.Subscription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
public class Payment extends TimeTrackable {
    private LocalDate paymentDate;

    @PositiveOrZero
    private Double amountPaid;

    private String transactionId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "payment")
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    private Set<PaymentStatus> paymentStatuses = new HashSet<>();

    public void addPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatuses.forEach(ps -> {
            ps.setEndTimestamp(LocalDateTime.now());
            ps.setCurrent(false);
        });
        paymentStatus.setPayment(this);
        paymentStatuses.add(paymentStatus);
    }

    public void removePaymentStatus(PaymentStatus paymentStatus) {
        paymentStatuses.remove(paymentStatus);
        paymentStatus.setPayment(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Payment payment = (Payment) o;
        return id != null && Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}