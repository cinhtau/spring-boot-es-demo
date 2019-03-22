package com.mimacom;

import com.mimacom.model.FavouriteItem;
import com.mimacom.model.JobAd;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class JobAdServiceImplTest {

    @Autowired
    private JobAdService jobAdService;

    @Autowired
    private ElasticsearchTemplate esTemplate;


    @Test
    public void saveParent() {

        //given

        JobAd jobAd = new JobAd();
        jobAd.setId("4711");
        jobAd.setJobTitle("Software Engineer");
        //when
        jobAdService.save(jobAd);
        //then

    }

    @Test
    public void saveChild() {

        //given
        FavouriteItem favouriteItem = new FavouriteItem();
        favouriteItem.setId("child-1");
        favouriteItem.setParentId("4711");
        //when
        jobAdService.save(favouriteItem);
        //then
    }


}