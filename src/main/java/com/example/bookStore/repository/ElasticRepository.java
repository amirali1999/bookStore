package com.example.bookStore.repository;

import com.example.bookStore.document.Elastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticRepository extends ElasticsearchRepository<Elastic, String> {
}
