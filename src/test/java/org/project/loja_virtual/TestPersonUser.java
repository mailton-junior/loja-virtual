package org.project.loja_virtual;

import org.junit.jupiter.api.Test;
import org.project.loja_virtual.model.EntityPerson;
import org.project.loja_virtual.repository.PersonRepository;
import org.project.loja_virtual.service.PersonUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@Profile("test")
@SpringBootTest(classes = LojaVirtualApplication.class)
public class TestPersonUser{

    @Autowired
    private PersonUserService personUserService;

    @Autowired
    private PersonRepository entityPersonRepository;

    @Test
    public void testCadasterPerson() {
        EntityPerson entityPerson = new EntityPerson();

        entityPerson.setName("João");
        entityPerson.setEmail("joao@joao.com");
        entityPerson.setPhone("123456789");
        entityPerson.setCnpj("12345678901234");
        entityPerson.setStateRegistration("123456789");
        entityPerson.setMunicipalRegistration("123456789");
        entityPerson.setFantasyName("João LRDA");
        entityPerson.setSocialReason("João LTDA");

        entityPersonRepository.save(entityPerson);

//        IndividualPerson individualPerson = new IndividualPerson();
//
//        individualPerson.setName("João");
//        individualPerson.setCpf("12345678901");
//        individualPerson.setEmail("joao@joao.com");
//        individualPerson.setPhone("123456789");
//
//        individualPerson.setEnterprise(individualPerson);
    }
}
