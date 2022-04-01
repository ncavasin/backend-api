package com.sip.api.domains.enums;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Permission;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@AllArgsConstructor
public enum Role {
    ADMIN(Sets.newHashSet()),
    PROFESSOR(Sets.newHashSet()),
    CLIENT(Sets.newHashSet());

    @Getter
    private final Set<Permission> permissions;

    public Set<GrantedAuthority> getGrantedAuthorities(){
        return new HashSet<>();
    }

}
