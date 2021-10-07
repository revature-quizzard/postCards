package com.revature.documents;

import lombok.Builder;
import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@DynamoDbBean
@Builder
public class CardDto {
    private String setId;
    private String question;
    private String answer;

    public CardDto() {
    }

    public CardDto(String setId, String question, String answer) {
        this.setId = setId;
        this.question = question;
        this.answer = answer;
    }

    @DynamoDbPartitionKey
    public String getSetId() {
        return setId;
    }

    @DynamoDbAttribute("question")
    public String getQuestion() {
        return question;
    }

    @DynamoDbAttribute("answer")
    public String getAnswer() {
        return answer;
    }
}
