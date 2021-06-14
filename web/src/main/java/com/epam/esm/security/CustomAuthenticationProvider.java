package com.epam.esm.security;

import com.epam.esm.dto.entityDTO.UserDTO;
import com.epam.esm.service.UserService;
import lombok.AllArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.OidcKeycloakAccount;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.spi.KeycloakAccount;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
public class CustomAuthenticationProvider extends KeycloakAuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) authentication;
        OidcKeycloakAccount account = token.getAccount();
        KeycloakSecurityContext securityContext = account.getKeycloakSecurityContext();
        AccessToken accessToken = securityContext.getToken();

        String email = accessToken.getEmail();
        UserDTO userDTO = userService.findByEmail(email);
        if (userDTO == null) {
            UserDTO newUserDTO = createUserDTO(accessToken);
            userService.register(newUserDTO);
        }

        Principal principal = new KeycloakPrincipal<>(email, account.getKeycloakSecurityContext());
        RefreshableKeycloakSecurityContext refreshableKeycloakSecurityContext = (RefreshableKeycloakSecurityContext) account.getKeycloakSecurityContext();
        KeycloakAccount keycloakAccount = new SimpleKeycloakAccount(principal,
                account.getRoles(), refreshableKeycloakSecurityContext);

        List<GrantedAuthority> grantedAuthorities = getAuthoritiesFromAccount(account);

        return new UsernamePasswordAuthenticationToken(keycloakAccount, token.isInteractive(), grantedAuthorities);
    }

    private List<GrantedAuthority> getAuthoritiesFromAccount(OidcKeycloakAccount account) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        Set<String> roles = account.getRoles();
        for (String role : roles) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + role.toUpperCase());
            grantedAuthorities.add(grantedAuthority);
        }
        return grantedAuthorities;
    }

    private UserDTO createUserDTO(AccessToken accessToken) {
        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setEmail(accessToken.getEmail());
        newUserDTO.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        newUserDTO.setFirstName(accessToken.getGivenName());
        newUserDTO.setLastName(accessToken.getFamilyName());
        return newUserDTO;
    }
}
