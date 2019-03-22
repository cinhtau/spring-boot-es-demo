package com.mimacom;

import com.mimacom.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class JobAdServiceImplTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(JobAdServiceImplTest.class);

    @Autowired
    private JobAdService jobAdService;

    @Autowired
    private ElasticsearchTemplate esTemplate;

    @Before
    public void setup() {
    }


    @Test
    public void saveChild() {
        //given
        JobAdDocument jobAdDocument = new JobAdDocument();
        jobAdDocument.setId("4711");
        JobAd jobAd = new JobAd();
        jobAd.setJobTitle("Software Engineer");
        jobAdDocument.setJobAd(jobAd);

        JobAdRelations parentRelation = new JobAdRelations();
        parentRelation.setName("jobAd");
        jobAdDocument.setJobAdRelations(parentRelation);

        jobAdService.save(jobAdDocument);

        if (jobAdService.find("4711") != null) {
            LOGGER.info("parent found");
        }


        FavouriteItemDocument favDoc = new FavouriteItemDocument();
        favDoc.setId("child-1");


        FavouriteItem favouriteItem = new FavouriteItem();
        favouriteItem.setNote("my pleasure");

        favDoc.setFavouriteItem(favouriteItem);

        ChildRelation jobAdRelations = new ChildRelation();
        jobAdRelations.setName("favouriteItem");
        jobAdRelations.setParent("4711");

        favDoc.setJobAdRelations(jobAdRelations);

        //when
        jobAdService.save(favDoc, "4711");
        //then
    }


}