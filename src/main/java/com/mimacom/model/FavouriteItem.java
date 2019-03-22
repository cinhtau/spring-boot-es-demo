package com.mimacom.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Parent;

@Document(indexName = "demo", type = "jobad")
@Data
public class FavouriteItem {

    private String id;

    @Field(type = FieldType.Text)
    @Parent(type = "jobad")
    private String parentId;

    @Field(type = FieldType.Text)
    private String note;
}
