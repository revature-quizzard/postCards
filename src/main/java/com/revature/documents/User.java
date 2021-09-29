package com.revature.documents;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.List;

@Data
@DynamoDbBean
public class User {

    private String id;
    private String username;
    private String profile_picture;
    private int points;
    private int wins;
    private int losses;
    private String registration_date;
    private List<String> gameRecords;
    private List<UserSetDoc> created_sets;
    private List<UserSetDoc> favorite_sets;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    @Data
    @DynamoDbBean
    //this may set things to 0 that should not be
    public static class UserSetDoc{
        private String id;
        private String name;
        private List<Tag> tags;
        private String author;
        private boolean is_public;
        private int views = 0;
        private int plays = 0;
        private int studies = 0;
        private int favorites = 0;

        public UserSetDoc(Set set){
            id = set.getId();
            name = set.getName();
            tags = set.getTags();
            author = set.getAuthor();
            is_public = set.getIs_public();
        }

        public UserSetDoc() { super(); }
    }

}