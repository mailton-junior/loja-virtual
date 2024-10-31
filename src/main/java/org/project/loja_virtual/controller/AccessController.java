package org.project.loja_virtual.controller;

import org.project.loja_virtual.model.Access;
import org.project.loja_virtual.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccessController {

    @Autowired
    private AccessService accessService;

    @PostMapping("/saveAccess")
    public Access save(Access access) {
        return accessService.save(access);
    }
}