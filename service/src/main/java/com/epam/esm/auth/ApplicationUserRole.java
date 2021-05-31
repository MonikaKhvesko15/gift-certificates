package com.epam.esm.auth;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum ApplicationUserRole {
    USER(Sets.newHashSet(
            ApplicationUserPermission.USER_MODIFY,
            ApplicationUserPermission.USER_ORDER_READ,
            ApplicationUserPermission.USER_POPULAR_TAG,
            ApplicationUserPermission.USER_ORDER_CREATE)),
    ADMIN(Sets.newHashSet(
            ApplicationUserPermission.CERTIFICATE_CREATE,
            ApplicationUserPermission.CERTIFICATE_MODIFY,
            ApplicationUserPermission.CERTIFICATE_DELETE,
            ApplicationUserPermission.TAG_CREATE,
            ApplicationUserPermission.TAG_DELETE,
            ApplicationUserPermission.ORDER_READ,
            ApplicationUserPermission.USER_READ,
            ApplicationUserPermission.USER_REGISTER,
            ApplicationUserPermission.USER_MODIFY,
            ApplicationUserPermission.USER_DELETE,
            ApplicationUserPermission.USER_ORDER_READ,
            ApplicationUserPermission.USER_POPULAR_TAG));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority(this.name()));
        return authorities;
    }
}
