package org.project.loja_virtual.repository;

import org.project.loja_virtual.model.EntityPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<EntityPerson, Long> {


}
