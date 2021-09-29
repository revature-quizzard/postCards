package com.revature.post_cards;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.revature.documents.Set;

public class SetRepository {

    private final DynamoDBMapper dbReader;

    public SetRepository(){
        dbReader = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    }

    public void updateSet(Set updatedSet){
        dbReader.save(updatedSet);
    }

}
