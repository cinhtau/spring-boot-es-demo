package com.mimacom.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import static com.mimacom.DemoApplication.INDEX_NAME;
import static com.mimacom.DemoApplication.MAPPING_TYPE;

@Document(indexName = INDEX_NAME, type = MAPPING_TYPE, shards = 1, replicas = 0)
@Data
public class FavouriteItemDocument {

    private String id;

    private FavouriteItem favouriteItem;

    private ChildRelation jobAdRelations;
}
