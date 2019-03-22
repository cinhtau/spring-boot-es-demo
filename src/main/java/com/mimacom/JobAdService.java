package com.mimacom;

import com.mimacom.model.FavouriteItem;
import com.mimacom.model.JobAd;

public interface JobAdService {

    JobAd save(JobAd jobAd);

    FavouriteItem save(FavouriteItem favouriteItem);

    JobAd find(String id);

    Iterable<JobAd> findAll();

    Iterable<FavouriteItem> findFavourites();
}
