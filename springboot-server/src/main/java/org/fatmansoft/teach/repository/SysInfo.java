package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.Sys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SysInfo extends JpaRepository<Sys, String> {
    @Override
    Optional<Sys> findById(String s);

    List<Sys> findAllByState(int state);
}
