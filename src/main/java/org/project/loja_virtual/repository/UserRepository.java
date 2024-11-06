package org.project.loja_virtual.repository;

import org.project.loja_virtual.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {

    @Query(value = "SELECT * FROM users u WHERE u.login = ?1")
    Users findUserByLogin(String login);

}
