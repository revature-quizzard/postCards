package com.revature.post_cards;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.revature.documents.*;

import java.util.ArrayList;
import java.util.List;

public class PostCardsHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final TagRepository tagRepo;
    private final SetRepository setRepo;
    private final UserRepository userRepo;

    private static final Gson mapper = new GsonBuilder().setPrettyPrinting().create();

    public PostCardsHandler(){
        this.userRepo = new UserRepository();
        this.setRepo = new SetRepository();
        this.tagRepo = new TagRepository();
    }

    public PostCardsHandler(TagRepository tagRepo, SetRepository setRepo, UserRepository userRepo){
        this.userRepo = new UserRepository();
        this.setRepo = new SetRepository();
        this.tagRepo = new TagRepository();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {
        Set newSet = new Set();
        LambdaLogger logger = context.getLogger();
        logger.log("Received Event: " + requestEvent);

        SetDto oldSet = null;

        try{
            oldSet = mapper.fromJson(requestEvent.getBody(), SetDto.class);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        User author = userRepo.getUser(oldSet.getAuthor());

        newSet.setSet_name(oldSet.getSet_name());
        newSet.setAuthor(oldSet.getAuthor());
        newSet.set_public(oldSet.is_public());

        List<Tag> trans_Tags = tagRepo.findTags(oldSet.getTags());
        logger.log("Tags: " + trans_Tags + "\n");

        newSet.setTags(trans_Tags);
        newSet.setCards(new ArrayList<Card>());

        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();

        newSet = setRepo.addSet(newSet);

        userRepo.addSet(newSet, author);

        responseEvent.setBody(mapper.toJson(newSet));

        return responseEvent;

    }
}