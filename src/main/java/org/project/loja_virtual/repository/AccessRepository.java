package org.project.loja_virtual.repository;

import org.project.loja_virtual.model.Access;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AccessRepository extends JpaRepository<Access, Long> {

    @Query("SELECT a FROM Access a WHERE upper (trim(a.description)) like %?1%")
    List<Access> findByDescription(String description);
}
