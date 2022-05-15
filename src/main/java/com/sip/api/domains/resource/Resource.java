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
@Table(name = "resource")
public class Resource extends TimeTrackable {
    @Column(nullable = false, unique = true)
    private String url;

    @Column(nullable = false, unique = true)
    private String name;

    // TODO: ADD HTTP METHODS
    // IT WORKS BUT I NEED TO IMPLEMENT THE CREATION OF THE RESOURCE ASSOCIATED TO A ROLE

}
