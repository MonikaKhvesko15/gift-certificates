package com.epam.esm.auth.util;

import com.epam.esm.auth.ApplicationUser;
import com.epam.esm.auth.ApplicationUserPermission;
import com.epam.esm.auth.ApplicationUserRole;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserDetailsConverter {
    public ApplicationUser convertToUserDetails(User user){
        String email = user.getEmail();
        String password = user.getPassword();
        ApplicationUserRole role = convertUserRole(user.getRole());
        boolean isBlocked = user.getIsBlocked();
        Set<GrantedAuthority> grantedAuthorities = getGrantedAuthoritiesSetAccordingToRole(role);
        return new ApplicationUser(
                grantedAuthorities,
                email,
                password,
                !isBlocked,
                !isBlocked,
                !isBlocked,
                !isBlocked);
    }

    private ApplicationUserRole convertUserRole(UserRole role) {
        return UserRole.ADMIN.equals(role) ? ApplicationUserRole.ADMIN : ApplicationUserRole.USER;
    }

    private Set<GrantedAuthority> getGrantedAuthoritiesSetAccordingToRole(ApplicationUserRole role) {
        Set<ApplicationUserPermission> permissions = role.getPermissions();
        Set<GrantedAuthority> grantedAuthorities = permissions
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());

        GrantedAuthority roleAuthority = new SimpleGrantedAuthority("ROLE_" +role.name());
        grantedAuthorities.add(roleAuthority);

        return grantedAuthorities;
    }
}
