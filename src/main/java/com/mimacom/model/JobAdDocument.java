package com.mimacom.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;

import static com.mimacom.DemoApplication.*;

@Document(indexName = INDEX_NAME, type = MAPPING_TYPE, shards = 1, replicas = 0)
@Mapping(mappingPath = "mappings/jobad.json")
@Data
public class JobAdDocument {

    private String id;

    private JobAd jobAd;

    private JobAdRelations jobAdRelations;

}
