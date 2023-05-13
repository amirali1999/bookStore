package com.example.bookStore.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
@Document(indexName = "elasticindex")
@Getter
@Setter
public class Elastic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Field(type = FieldType.Keyword,  name = "id")
    private String id;
    @Field(type = FieldType.Text,  name = "status")
    private String status;
    @Field(type = FieldType.Text,  name = "type")
    private String type;
    @Field(type = FieldType.Date,  name = "date")
    private String date;
    @Field(type = FieldType.Text,  name = "logDescription")
    private String logDescription;
    public Elastic(String date, String status, String type, String logDescription) {
        this.status = status;
        this.type = type;
        this.date = date;
        this.logDescription = logDescription;
    }
}
