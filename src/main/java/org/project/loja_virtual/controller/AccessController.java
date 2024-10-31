package org.project.loja_virtual.controller;

import org.project.loja_virtual.model.Access;
import org.project.loja_virtual.repository.AccessRepository;
import org.project.loja_virtual.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
public class AccessController {

    @Autowired
    private AccessService accessService;

    @Autowired
    private AccessRepository accessRepository;



    @ResponseBody
    @PostMapping(value = "/saveAccess")
    public ResponseEntity<Access> saveAccess(@RequestBody Access access) {

        Access accessSaved = accessService.save(access);

        return new ResponseEntity<Access>(accessSaved, HttpStatus.CREATED);
    }

    @ResponseBody
    @PostMapping(value = "/deleteAccess")
    public ResponseEntity<Access> deleteAccess(@RequestBody Access access) {

        accessRepository.deleteById(access.getId());
        return new ResponseEntity("Acesso Deletado",HttpStatus.OK);
    }
}