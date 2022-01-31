package org.fatmansoft.teach.repository;


import java.util.Optional;

import org.fatmansoft.teach.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
    Optional<User> findByUserId(Integer userId);
    Boolean existsByUserName(String userName);
}