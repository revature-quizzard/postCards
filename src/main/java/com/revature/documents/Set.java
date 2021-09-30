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
    private String setName;
    private List<Tag> tags;
    private List<Card> cards;
    private String author;
    private boolean isPublic;
    private int views;
    private int plays;
    private int studies;
    private int favorites;


    @DynamoDbPartitionKey
    public String getId(){
        return id;
    }

    @DynamoDbAttribute("setName")
    public String getSetName() {
        return setName;
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

    @DynamoDbAttribute("isPublic")
    public boolean isPublic() {
        return isPublic;
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
