package com.mimacom;

import com.mimacom.model.JobAdDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface JobAdRepository extends ElasticsearchRepository<JobAdDocument, String> {

}
