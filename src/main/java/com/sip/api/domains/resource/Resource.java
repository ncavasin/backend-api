package com.sip.api.domains.resource;


import com.sip.api.domains.TimeTrackable;
import lombok.*;
import org.springframework.http.HttpMethod;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

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

    // TODO: ADD HTTP METHODS
    // IT WORKS BUT I NEED TO IMPLEMENT THE CREATION OF THE RESOURCE ASSOCIATED TO A ROLE

}
