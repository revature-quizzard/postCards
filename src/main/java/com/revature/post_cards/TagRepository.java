package com.revature.post_cards;

import com.revature.documents.Tag;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TagRepository {

    private final DynamoDbTable<Tag> tagTable;

    public TagRepository(){
        DynamoDbClient db = DynamoDbClient.builder().httpClient(ApacheHttpClient.create()).build();
        DynamoDbEnhancedClient dbClient = DynamoDbEnhancedClient.builder().dynamoDbClient(db).build();
        tagTable = dbClient.table("Tags", TableSchema.fromBean(Tag.class));
    }

    public List<Tag> findTags(List<String> tag_names){

        List<Tag> tagQuery = tag_names.stream().map(e -> (new Tag(e))).collect(Collectors.toList());

        List<Tag> result = new ArrayList<>();
        for(Tag t : tagQuery){
            result.add(tagTable.getItem(t));
        }
        return result;
    }
}
