package com.springapps.shop.services;

import com.springapps.shop.dtos.AuthRequestDTO;
import com.springapps.shop.entities.User;
import com.springapps.shop.exceptions.ResourceNotFoundException;
import com.springapps.shop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;



    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new ResourceNotFoundException("user not found"));

        return new org.springframework.security.core.userdetails.User (user.getUsername(), user.getPassword(), buildSimpleGrantedAuthorities(user));
    }

    @Transactional
    public List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(User user){
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleType().name()))
                .collect(Collectors.toList());
    }



}
