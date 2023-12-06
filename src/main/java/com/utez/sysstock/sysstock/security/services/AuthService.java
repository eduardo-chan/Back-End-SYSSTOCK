package com.utez.sysstock.sysstock.security.services;

import com.utez.sysstock.sysstock.security.model.UserAuth;
import com.utez.sysstock.sysstock.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService implements UserDetailsService {
    @Autowired
    UserService service;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return UserAuth.build(service.getUserByEmail(email));
    }
}
//Developed by: Jose Eduardo Arroyo Palafox