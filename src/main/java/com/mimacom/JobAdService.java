package com.mimacom;

import com.mimacom.model.FavouriteItemDocument;
import com.mimacom.model.JobAdDocument;

public interface JobAdService {

    JobAdDocument save(JobAdDocument jobAd);

    void save(FavouriteItemDocument favouriteItem, String parent);

    JobAdDocument find(String id);

    void springSave(FavouriteItemDocument favouriteItemDocument, String parent);

    void deleteAllIndexes();
}
