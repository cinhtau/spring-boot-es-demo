package com.mimacom;

import com.mimacom.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class JobAdServiceImplTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(JobAdServiceImplTest.class);

    @Autowired
    private JobAdService jobAdService;

    @Before
    public void setup() {
        jobAdService.deleteAllIndexes();
    }

    @Test
    public void saveChildWithIndexRequestTest() {
        // given
        JobAdDocument parent = saveParent();
        FavouriteItemDocument child = createChild();

        // when
        jobAdService.save(child, parent.getId());

    }

    @Test
    public void saveChildWithUpdateRequestTest() {
        // given
        JobAdDocument parent = saveParent();
        FavouriteItemDocument child = createChild();

        // when
        jobAdService.springSave(child, parent.getId());
    }

    private JobAdDocument saveParent() {
        JobAdDocument jobAdDocument = new JobAdDocument();
        jobAdDocument.setId("4711");
        JobAd jobAd = new JobAd();
        jobAd.setJobTitle("Software Engineer");
        jobAdDocument.setJobAd(jobAd);

        JobAdRelations parentRelation = new JobAdRelations();
        parentRelation.setName("jobAd");
        jobAdDocument.setJobAdRelations(parentRelation);

        jobAdService.save(jobAdDocument);

        JobAdDocument parent = jobAdService.find("4711");
        if (parent != null) {
            LOGGER.info("parent found");
        }

        return parent;
    }

    private FavouriteItemDocument createChild() {
        FavouriteItemDocument child = new FavouriteItemDocument();
        child.setId("child-1");

        FavouriteItem favouriteItem = new FavouriteItem();
        favouriteItem.setNote("my pleasure");

        child.setFavouriteItem(favouriteItem);

        ChildRelation jobAdRelations = new ChildRelation();
        jobAdRelations.setName("favouriteItem");
        jobAdRelations.setParent("4711");

        child.setJobAdRelations(jobAdRelations);

        return child;
    }

}