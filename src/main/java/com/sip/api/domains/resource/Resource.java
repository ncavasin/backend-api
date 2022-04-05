package com.sip.api.domains.resource;


import com.sip.api.domains.TimeTrackable;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "resources")
public class Resource extends TimeTrackable {
    @Column(nullable = false, unique = true)
    private String name;

    private String url;
}
