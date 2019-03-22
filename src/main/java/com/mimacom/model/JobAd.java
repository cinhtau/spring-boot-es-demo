package com.mimacom.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "demo", type = "jobad", shards = 1)
@Data
@ToString
public class JobAd {

    private String id;
    private String jobTitle;

}
