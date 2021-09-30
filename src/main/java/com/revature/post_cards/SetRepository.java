package com.revature.post_cards;


import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.revature.documents.Set;
import lombok.SneakyThrows;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.*;

public class SetRepository {

    private final DynamoDbTable<Set> setTable;

    public SetRepository() {
        DynamoDbClient db = DynamoDbClient.builder().httpClient(ApacheHttpClient.create()).build();
        DynamoDbEnhancedClient dbClient = DynamoDbEnhancedClient.builder().dynamoDbClient(db).build();
        setTable = dbClient.table("Sets", TableSchema.fromBean(Set.class));
    }

    public Set addSet(Set updatedSet) {
        System.out.println("updatedSet" + updatedSet);
        //UUID uuid = UUID.randomUUID();
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


/*
    //////////////////////////////////////////////////////
    @SneakyThrows
    public PageIterable<Set>  searchSets(Map<String, String> queryParams, LambdaLogger logger) {
        StringBuilder filterExprBuilder = new StringBuilder();
        Map<String, AttributeValue> attributeValues = new HashMap<>();
        List<String> paramKeys = new ArrayList<>(queryParams.keySet());

        List<String> bookFieldNames = Set.getFieldNameStrings();

        for (int i = 0; i < paramKeys.size(); i++){

            filterExprBuilder.append("(");
            if (i != 0) filterExprBuilder.append(" and ");

            String paramKey = paramKeys.get(i);

            if (!bookFieldNames.contains(paramKey)){
                String msg = "The field, " + paramKey + ", was not found on resource type: Book";
                logger.log(msg);
                throw new RuntimeException(msg);
            }

            String fieldType = Set.class.getDeclaredField(paramKey).getType().getSimpleName();
            String paramVal = Optional.ofNullable(queryParams.get(paramKey))
                    .orElseThrow(() -> {
                        String msg = "Unexpected null value found in parameter map.";
                        logger.log(msg);
                        return new RuntimeException(msg);
                    });


        }
    }
    //////////////////////////////////////////////////////
}*/
}
