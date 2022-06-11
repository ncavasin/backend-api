package com.sip.api.domains.payment;

import com.sip.api.domains.TimeTrackable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_status")
public class PaymentStatus extends TimeTrackable {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    @Fetch(FetchMode.JOIN)
    private Payment payment;

    private Timestamp initTimestamp;

    private String paymentStatus;

    private Timestamp endTimestamp;

    private boolean isCurrent;
}