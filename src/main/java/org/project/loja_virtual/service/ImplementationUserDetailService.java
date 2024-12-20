package org.project.loja_virtual.service;

import org.project.loja_virtual.model.Users;
import org.project.loja_virtual.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ImplementationUserDetailService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findUserByLogin(username);
        if (user == null) {
            System.out.println("Senha fornecida: " + user.getPassword());

            throw new UsernameNotFoundException("User not found: " + username);
        }
        System.out.println("Senha fornecida: " + user.getPassword());

        return user;
    }
}
