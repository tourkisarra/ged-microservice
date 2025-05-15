package com.example.ged_microservice.repository;


import com.example.ged_microservice.model.DocumentIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface DocumentIndexRepository extends ElasticsearchRepository<DocumentIndex, String> {
    List<DocumentIndex> findByTitleContaining(String keyword);
}
