package org.grimoire.security;

import java.util.Collection;
import java.util.stream.Collectors;
import org.grimoire.model.Role;
import org.grimoire.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class GrimoireUserDetails implements UserDetails {

    private final User user;

    public GrimoireUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //@formatter:off
        return user.getRoles().stream()
                    .map(Role::getRole)
                    .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        //@formater:on
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
