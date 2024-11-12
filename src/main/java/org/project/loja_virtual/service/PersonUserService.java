package org.project.loja_virtual.service;

import org.project.loja_virtual.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonUserService {

    @Autowired
    private UserRepository userRepository;
}
