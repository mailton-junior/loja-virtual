package org.project.loja_virtual.service;

import org.project.loja_virtual.model.Access;
import org.project.loja_virtual.repository.AccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessService {

    @Autowired
    private AccessRepository accessRepository;

    public Access save(Access access) {
        return accessRepository.save(access);
    }

}
