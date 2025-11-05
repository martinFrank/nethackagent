package com.github.martinfrank.nethackagent.embedding;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WikiDocumentRepository extends JpaRepository<WikiDocumentEntity, Long> {
    Optional<WikiDocumentEntity> findByUrl(String url);
}
