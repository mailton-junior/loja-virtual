package org.project.loja_virtual.repository;

import org.project.loja_virtual.model.Access;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AccessRepository extends JpaRepository<Access, Long> {
}
