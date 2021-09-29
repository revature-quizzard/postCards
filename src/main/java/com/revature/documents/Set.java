package com.revature.documents;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.List;

@Data
@DynamoDbBean
public class Set {

    private String id;
    private String set_name;
    private List<Tag> tags;
    private List<Card> cards;
    private String author;
    private boolean is_public;
    private int views;
    private int plays;
    private int studies;
    private int favorites;

    @DynamoDbPartitionKey
    public String getId(){
        return id;
    }

    @DynamoDbAttribute("name")
    public String getName() {
        return set_name;
    }

    @DynamoDbAttribute("tags")
    public List<Tag> getTags() {
        return tags;
    }

    @DynamoDbAttribute("cards")
    public List<Card> getCards() {
        return cards;
    }

    @DynamoDbAttribute("author")
    public String getAuthor() {
        return author;
    }

    @DynamoDbAttribute("is_public")
    public boolean getIs_public() {
        return is_public;
    }

    @DynamoDbAttribute("views")
    public int getViews() {
        return views;
    }

    @DynamoDbAttribute("plays")
    public int getPlays() {
        return plays;
    }

    @DynamoDbAttribute("studies")
    public int getStudies() {
        return studies;
    }

    @DynamoDbAttribute("favorites")
    public int getFavorites() {
        return favorites;
    }

}
