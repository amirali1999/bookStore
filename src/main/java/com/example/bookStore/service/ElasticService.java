package com.example.bookStore.service;

import com.example.bookStore.document.Elastic;
import com.example.bookStore.repository.ElasticRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ElasticService {
    private final ElasticRepository elasticRepository;

    public ElasticService(ElasticRepository elasticRepository) {
        this.elasticRepository = elasticRepository;
    }
    public void save(Elastic elastic){
        elasticRepository.save(elastic);
    }
    public List<Elastic> findAll(){
        return StreamSupport.stream(
                elasticRepository.findAll().spliterator(),
                false).collect(Collectors.toList());
    }
}
