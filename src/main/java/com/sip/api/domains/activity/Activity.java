package com.sip.api.domains.activity;

import com.sip.api.domains.TimeTrackable;
import lombok.*;
import org.checkerframework.checker.index.qual.NonNegative;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "activity")
public class Activity extends TimeTrackable {
    @Column(nullable = false)
    private String name;

    @NonNegative
    @Column(nullable = false)
    private Double basePrice;

    private int atendeesLimit;
}
