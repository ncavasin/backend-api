package com.sip.api.domains.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sip.api.domains.Role;
import com.sip.api.domains.TimeTrackable;
import com.sip.api.domains.enums.UserStatus;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_data")
public class User extends TimeTrackable implements UserDetails {

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

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    UserStatus status;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_data_user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<Role> roles = new LinkedHashSet<>();

    // Use by UserFactory to convert from UserDTO to User
    public User(int dni, String password, String email, String firstName, String lastName, int age, int phone) {
        super();
        this.dni = dni;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phone = phone;
    }

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
    }

    @Override // is the email
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !(getStatus() == UserStatus.DEACTIVATED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getStatus() == UserStatus.ACTIVE;
    }
}
