package br.com.alan.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.alan.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

    Optional<User> findByLogin(String login);

    boolean existsByLogin(String login);

}
