package com.mimacom;

import com.mimacom.model.JobAd;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface JobAdRepository extends ElasticsearchRepository<JobAd, String> {

}
