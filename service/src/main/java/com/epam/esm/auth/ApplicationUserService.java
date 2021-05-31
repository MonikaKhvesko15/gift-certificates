package com.epam.esm.auth;

import com.epam.esm.entity.User;
import com.epam.esm.entity.UserRole;
import com.epam.esm.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ApplicationUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        User user = optionalUser.orElseThrow(
                () -> new UsernameNotFoundException(String.format("Username %s not found", username))
        );
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
        GrantedAuthority roleAuthority = new SimpleGrantedAuthority(role.name());
        grantedAuthorities.add(roleAuthority);
        return grantedAuthorities;
    }


}
