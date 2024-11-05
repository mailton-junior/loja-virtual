package org.project.loja_virtual.controller;

import org.project.loja_virtual.model.Access;
import org.project.loja_virtual.repository.AccessRepository;
import org.project.loja_virtual.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ResponseBody
    @DeleteMapping(value = "/deleteAccessById/{id}")
    public ResponseEntity<Access> deleteAccessById(@PathVariable Long id) {

        accessRepository.deleteById(id);

        return new ResponseEntity("Acesso Deletado",HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/getAccessById/{id}")
    public ResponseEntity<Access> getAccessById(@PathVariable Long id) {

        Access access = accessRepository.findById(id).get();

        return new ResponseEntity<Access>(access,HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/getAccessByDescription/{description}")
    public ResponseEntity<List<Access>> getAccessByDescription(@PathVariable String description) {

        List<Access> access = accessRepository.findByDescription(description);

        return new ResponseEntity<List<Access>>(access,HttpStatus.OK);
    }

}