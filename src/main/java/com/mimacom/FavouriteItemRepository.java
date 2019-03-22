package com.mimacom;

import com.mimacom.model.FavouriteItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface FavouriteItemRepository extends ElasticsearchRepository<FavouriteItem, String> {


}
