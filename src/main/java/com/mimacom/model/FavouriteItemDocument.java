package com.mimacom.model;

import lombok.Data;

@Data
public class FavouriteItemDocument {

    private String id;

    private FavouriteItem favouriteItem;

    private ChildRelation jobAdRelations;
}
