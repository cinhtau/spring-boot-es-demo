package com.mimacom;

import com.mimacom.model.FavouriteItem;
import com.mimacom.model.JobAd;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobAdServiceImpl implements JobAdService {

    private final JobAdRepository jobAdRepository;

    private final FavouriteItemRepository favouriteItemRepository;

    public JobAdServiceImpl(JobAdRepository jobAdRepository, FavouriteItemRepository favouriteItemRepository) {
        this.jobAdRepository = jobAdRepository;
        this.favouriteItemRepository = favouriteItemRepository;
    }

    @Override
    public JobAd save(JobAd jobAd) {
        return this.jobAdRepository.save(jobAd);
    }

    @Override
    public FavouriteItem save(FavouriteItem favouriteItem) {
        return this.favouriteItemRepository.save(favouriteItem);
    }

    @Override
    public JobAd find(String id) {
        Optional<JobAd> jobAd = this.jobAdRepository.findById(id);
        if (jobAd.isPresent()) {
            return jobAd.get();
        }
        else {
            return new JobAd();
        }
    }

    @Override
    public Iterable<JobAd> findAll() {
        return this.jobAdRepository.findAll();
    }

    @Override
    public Iterable<FavouriteItem> findFavourites() {
        return this.favouriteItemRepository.findAll();
    }
}
