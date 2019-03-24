package com.mimacom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mimacom.model.FavouriteItemDocument;
import com.mimacom.model.JobAdDocument;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

import static com.mimacom.DemoApplication.INDEX_NAME;
import static com.mimacom.DemoApplication.MAPPING_TYPE;

@Service
public class JobAdServiceImpl implements JobAdService {

    private final JobAdRepository jobAdRepository;

    private RestHighLevelClient client;

    private final ElasticsearchTemplate elasticsearchTemplate;

    private final static Logger LOGGER = LoggerFactory.getLogger(JobAdServiceImpl.class);

    public JobAdServiceImpl(JobAdRepository jobAdRepository, ElasticsearchTemplate elasticsearchTemplate) {
        this.jobAdRepository = jobAdRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
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
        } catch (ElasticsearchException e) {
            LOGGER.error("unknown mystery", e);
        } catch (IOException e) {
            LOGGER.error("Could not index child document.", e);
        }
    }

    @Override
    public JobAdDocument find(String id) {
        Optional<JobAdDocument> jobAd = this.jobAdRepository.findById(id);
        return jobAd.orElseGet(JobAdDocument::new);
    }

    @Override
    public void springSave(FavouriteItemDocument favouriteItemDocument, String parent) {
        UpdateRequest updateRequest = new UpdateRequest(INDEX_NAME, MAPPING_TYPE, favouriteItemDocument.getId());
        updateRequest.routing(parent);

        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(favouriteItemDocument);

            LOGGER.debug("json dump: {}", json);

            updateRequest.doc(json, XContentType.JSON);

            UpdateQuery updateQuery = new UpdateQuery();
            updateQuery.setUpdateRequest(updateRequest);
            updateQuery.setClazz(FavouriteItemDocument.class);
            updateQuery.setId(favouriteItemDocument.getId());
            updateQuery.setType(MAPPING_TYPE);
            updateQuery.setIndexName(INDEX_NAME);
            updateQuery.setDoUpsert(true);

            UpdateResponse response = elasticsearchTemplate.update(updateQuery);
            LOGGER.info("Response {}", response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAllIndexes() {
        this.jobAdRepository.deleteAll();
    }


}
