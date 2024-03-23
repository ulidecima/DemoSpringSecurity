package com.ulide.demospringsecurity.repository;

import com.ulide.demospringsecurity.model.UserModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author ulide
 */
public interface UserRepository extends CrudRepository<UserModel, Long> {
    Optional<UserModel> findUserModelByUsername(String username);

    @Query("FROM UserModel u WHERE u.username = ?1")
    Optional<UserModel> findUser(String username);
}
