package com.sip.api.domains.enums;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.sip.api.domains.enums.Permission.*;

@AllArgsConstructor
public enum Role {
    ADMIN(Sets.newHashSet(ACTIVITY_READ, ACTIVITY_WRITE)),
    PROFESSOR(Sets.newHashSet()),
    CLIENT(Sets.newHashSet());

    @Getter
    private final Set<Permission> permissions;

    public Set<GrantedAuthority> getGrantedAuthorities(){
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }

}
