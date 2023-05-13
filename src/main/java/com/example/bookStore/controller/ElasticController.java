package com.example.bookStore.controller;

import com.example.bookStore.document.Elastic;
import com.example.bookStore.service.ElasticService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/elastic")
public class ElasticController {
    private final ElasticService elasticService;

    public ElasticController(ElasticService elasticService) {
        this.elasticService = elasticService;
    }
    public void save(Elastic elastic){
        elasticService.save(elastic);
    }
    @GetMapping(path = "findall")
    public List<Elastic> findAll(){
        return elasticService.findAll();
    }
}
