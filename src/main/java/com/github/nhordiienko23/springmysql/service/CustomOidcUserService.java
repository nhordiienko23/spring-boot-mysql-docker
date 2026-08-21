package com.github.nhordiienko23.springmysql.service;

import com.github.nhordiienko23.springmysql.model.Role;
import com.github.nhordiienko23.springmysql.model.User;
import com.github.nhordiienko23.springmysql.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomOidcUserService extends OidcUserService {

    private final UserRepository userRepository;

    public CustomOidcUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        String email = oidcUser.getEmail();
        String username = oidcUser.getAttribute("name");

        if (username == null) {
            username = oidcUser.getAttribute("given_name");
        }

        if (username == null) {
            username = oidcUser.getFullName();
        }

        if (username == null) {
            username = oidcUser.getName();
        }
        String finalUsername = username;
        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = User.builder().
                    firstName(finalUsername)
                    .email(email)
                    .roles(List.of(Role.ROLE_USER))
                    .registeredAt(LocalDateTime.now())
                    .build();
            return userRepository.save(newUser);
        });
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());

        return new CustomUserDetails(user, oidcUser.getAttributes());
    }
}
