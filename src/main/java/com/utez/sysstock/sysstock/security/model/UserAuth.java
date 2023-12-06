package com.utez.sysstock.sysstock.security.model;

import com.utez.sysstock.sysstock.models.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserAuth implements UserDetails {
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserAuth(String email, String password,
                    Collection<? extends GrantedAuthority> authorities){
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserAuth build(User user) {
        List<GrantedAuthority> authorities =
                Collections.singletonList(
                        new SimpleGrantedAuthority(user.getRole().getName())
                );

        return new UserAuth(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
//Developed by: Jose Eduardo Arroyo Palafox