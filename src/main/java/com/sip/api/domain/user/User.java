package com.sip.api.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sip.api.domain.TimeTrackable;
import com.sip.api.domain.enums.UserStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_data")
public class User extends TimeTrackable {

    @Column(nullable = false, unique = true)
    private int dni;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private int phone;

    private String firstName;

    private String lastName;

    private int age;

    private String zipCode;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    UserStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return getId().equals(user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.id);
    }

}
