package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.Stoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface StocRepository extends JpaRepository<Stoc, String> {
    Optional<Stoc> findByUserId(String userId);

    Optional<Stoc> findByUserIdAndAndTerm(String uid, String term);
}
