package com.revature.post_cards;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.MalformedJsonException;
import com.revature.documents.*;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
/*
//////////////////////////////////////////////////////
    public APIGatewayProxyResponseEvent getOldSet(APIGatewayProxyRequestEvent requestEvent, Context context){

        LambdaLogger logger = context.getLogger();
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();

        logger.log("Deployment successful.");

        Map<String, String> queryParams = requestEvent.getQueryStringParameters();

        PageIterable<Set> sets;

        sets = setRepo.searchSets(queryParams, logger);

        List<SetResponse> respBody = setService.mapResponse(sets, logger);
        responseEvent.setBody(mapper.toJson(respBody));
        responseEvent.setStatusCode(200);

        return responseEvent;

    }
    //////////////////////////////////////////////////////*/

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        LambdaLogger logger = context.getLogger();
        logger.log("Received Event: " + requestEvent);


        CardDto cardDto = null;
        Set set = null;

        try {
            cardDto = mapper.fromJson(requestEvent.getBody(), CardDto.class);
        }catch (Exception e){
            responseEvent.setStatusCode(400);
            return responseEvent;
        }

        try{
            set = setRepo.getSet(cardDto.getSetId());
        }catch (Exception e){
            responseEvent.setStatusCode(404);
            return responseEvent;
        }

        Card card = new Card();
        card.setSetId(cardDto.getSetId());
        card.setQuestion(cardDto.getQuestion());
        card.setAnswer(cardDto.getAnswer());
        UUID uuid = UUID.randomUUID();
        card.setId(uuid.toString());

        set.getCards().add(card);

        setRepo.addSet(set);

        /*User author = userRepo.getUser(oldSet.getAuthor());

        newSet.setSet_name(oldSet.getSet_name());
        newSet.setAuthor(oldSet.getAuthor());
        newSet.set_public(oldSet.is_public());
        //TODO add all the fields
        //TODO add card to card array

        List<Tag> trans_Tags = tagRepo.findTags(oldSet.getTags());
        logger.log("Tags: " + trans_Tags + "\n");

        newSet.setTags(trans_Tags);
        newSet.setCards(new ArrayList<Card>());

        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();

        newSet = setRepo.addSet(newSet);

        userRepo.addSet(newSet, author);*/



        responseEvent.setBody(mapper.toJson(card));

        return responseEvent;

    }
}