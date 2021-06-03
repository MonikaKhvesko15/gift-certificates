package com.epam.esm.auth.util;

import com.epam.esm.auth.ApplicationUser;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserDetailsConverter {

    public ApplicationUser convertToUserDetails(User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        boolean isBlocked = user.getIsBlocked();
        Set<GrantedAuthority> grantedAuthorities = getGrantedAuthoritySetFromRoles(user.getRoles());
        return new ApplicationUser(
                grantedAuthorities,
                email,
                password,
                !isBlocked,
                !isBlocked,
                !isBlocked,
                !isBlocked);
    }

    private Set<GrantedAuthority> getGrantedAuthoritySetFromRoles(Set<Role> roles) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : roles) {
            GrantedAuthority roleAuthority = new SimpleGrantedAuthority("ROLE_" + role.getName());
            grantedAuthorities.add(roleAuthority);
        }
        return grantedAuthorities;
    }
}
