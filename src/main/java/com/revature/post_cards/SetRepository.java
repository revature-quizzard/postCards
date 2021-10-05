package com.revature.post_cards;


import com.revature.documents.Set;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class SetRepository {

    private final DynamoDbTable<Set> setTable;

    public SetRepository() {
        DynamoDbClient db = DynamoDbClient.builder().httpClient(ApacheHttpClient.create()).build();
        DynamoDbEnhancedClient dbClient = DynamoDbEnhancedClient.builder().dynamoDbClient(db).build();
        setTable = dbClient.table("Sets", TableSchema.fromBean(Set.class));
    }

    public Set addSet(Set updatedSet) {
        System.out.println("updatedSet" + updatedSet);
        setTable.putItem(updatedSet);
        System.out.println("SET WITH ID: " + updatedSet);
        return updatedSet;
    }

    public Set getSet(String setId) {
        Set query = new Set();
        query.setId(setId);
        Set result = setTable.getItem(query);
        return result;
    }

}
