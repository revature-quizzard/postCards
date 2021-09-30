package com.revature.documents;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@DynamoDbBean
public class Tag {

    private String tagName;
    private String tagColor;

    @DynamoDbPartitionKey
    public String getName(){ return tagName; }

    @DynamoDbAttribute("color")
    public String getColor(){ return tagColor; }

    public Tag(String tagName){
        this.tagName = tagName;
    }

    public Tag() {
        super();
    }
}