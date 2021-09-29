package com.revature.post_cards;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import com.revature.documents.Set;
import com.revature.documents.User;
import com.revature.exceptions.ResourceNotFoundException;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private final DynamoDbTable<User> userTable;

    public UserRepository(){
        DynamoDbClient db = DynamoDbClient.builder().httpClient(ApacheHttpClient.create()).build();
        DynamoDbEnhancedClient dbClient = DynamoDbEnhancedClient.builder().dynamoDbClient(db).build();
        userTable = dbClient.table("Users", TableSchema.fromBean(User.class));
    }

    public User getUser(String name){
        AttributeValue val = AttributeValue.builder().s(name).build();
        Expression filter = Expression.builder().expression("#a = :b") .putExpressionName("#a", "username") .putExpressionValue(":b", val).build();
        ScanEnhancedRequest request = ScanEnhancedRequest.builder().filterExpression(filter).build();

        User user = userTable.scan(request).stream().findFirst().orElseThrow(ResourceNotFoundException::new).items().get(0);
        System.out.println("USER WITH ID: " + user);
        return user;
    }

    public User addSet(Set newSet, User user){
        System.out.println(" ");
        System.out.println(newSet);
        System.out.println(user);

        User.UserSetDoc doc = new User.UserSetDoc(newSet);

        if(user.getCreated_sets() == null){
            user.setCreated_sets(new ArrayList<User.UserSetDoc>());
        }

        List<User.UserSetDoc> temp = user.getCreated_sets();
        temp.add(doc);
        user.setCreated_sets(temp);

        user = userTable.updateItem(user);

        System.out.println(user);
        return user;
    }

}
