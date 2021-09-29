package com.revature.documents;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@DynamoDbBean
public class Tag {

    private String name;
    private String color;

    @DynamoDbPartitionKey
    public String getName(){ return name; }

    @DynamoDbAttribute("color")
    public String getColor(){ return color; }

    public Tag(String name){
        this.name = name;
    }

    public Tag() {
        super();
    }
}