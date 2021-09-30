package com.revature.post_cards;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.revature.documents.*;

import java.util.UUID;

public class PostCardsHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final SetRepository setRepo;

    private static final Gson mapper = new GsonBuilder().setPrettyPrinting().create();

    public PostCardsHandler(){
        this.setRepo = new SetRepository();
    }

    public PostCardsHandler(SetRepository setRepo){
        this.setRepo = new SetRepository();
    }

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

        responseEvent.setBody(mapper.toJson(card));

        System.out.println(responseEvent);

        return responseEvent;

    }
}