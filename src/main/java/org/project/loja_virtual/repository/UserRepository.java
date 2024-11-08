package org.project.loja_virtual.repository;

import org.project.loja_virtual.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    @Query(value = "select u from Users u where u.login = ?1")
    Users findUserByLogin(String login);
}
