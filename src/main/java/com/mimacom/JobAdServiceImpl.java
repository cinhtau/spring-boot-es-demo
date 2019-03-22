package com.mimacom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mimacom.model.FavouriteItemDocument;
import com.mimacom.model.JobAdDocument;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

import static com.mimacom.DemoApplication.INDEX_NAME;
import static com.mimacom.DemoApplication.MAPPING_TYPE;

@Service
public class JobAdServiceImpl implements JobAdService {

    private final JobAdRepository jobAdRepository;

    private RestHighLevelClient client;

    private final static Logger LOGGER = LoggerFactory.getLogger(JobAdServiceImpl.class);

    public JobAdServiceImpl(JobAdRepository jobAdRepository) {
        this.jobAdRepository = jobAdRepository;
        this.client = createClient();
    }

    private RestHighLevelClient createClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));

    }


    @Override
    public JobAdDocument save(JobAdDocument jobAd) {
        return this.jobAdRepository.save(jobAd);
    }

    @Override
    public void save(FavouriteItemDocument child, String parent) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(child);

            LOGGER.debug("json dump: {}", json);

            IndexRequest indexRequest = new IndexRequest(INDEX_NAME, MAPPING_TYPE, child.getId());
            indexRequest.source(json, XContentType.JSON);
            indexRequest.routing(parent);

            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            LOGGER.info(indexResponse.status().name());
            LOGGER.info("Child created with HTTP code {}", indexResponse.status().getStatus());
        } catch (ElasticsearchException e)  {
            LOGGER.error("unknown mystery", e);
        }
        catch (IOException e) {
            LOGGER.error("Could not index child document.", e);
        }
    }


    @Override
    public JobAdDocument find(String id) {
        Optional<JobAdDocument> jobAd = this.jobAdRepository.findById(id);
        if (jobAd.isPresent()) {
            return jobAd.get();
        } else {
            return new JobAdDocument();
        }
    }


}
