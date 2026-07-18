package com.bodhlens.bodhlens_ai.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bodhlens.bodhlens_ai.entity.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID> {

}
