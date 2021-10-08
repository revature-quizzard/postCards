package com.revature.post_cards;

import com.amazonaws.lambda.thirdparty.com.google.gson.Gson;
import com.amazonaws.lambda.thirdparty.com.google.gson.GsonBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.documents.CardDto;
import com.revature.documents.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;
import com.revature.post_cards.stubs.TestLogger;


import java.io.DataInput;
import java.io.IOException;
import java.util.*;

public class PostCardHandlerTestSuite {

    static TestLogger testLogger;
    static final Gson mapper = new GsonBuilder().setPrettyPrinting().create();
    final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    PostCardsHandler sut;
    Context mockContext;
    SetRepository mockSetRepo;
    CardDto stubbedCardResponse;
    CardDto stubbedOldCard;
    List stubbedOldCards = new ArrayList<CardDto>();
    Set stubbedSetResponse;
    UUID uuid;

    @BeforeAll
    public static void suiteSetUp() {
        testLogger = new TestLogger();
    }

    @BeforeEach
    public void caseSetUp(){
        mockSetRepo = mock(SetRepository.class);
        sut = new PostCardsHandler(mockSetRepo);

        mockContext = mock(Context.class);

        when(mockContext.getLogger()).thenReturn(testLogger);

        stubbedCardResponse = new CardDto();
        stubbedCardResponse = CardDto.builder()
                .setId("1234")
                .question("Who is your leader?")
                .answer("Sir Wezley Obama")
                .build();

        stubbedOldCard = new CardDto();
        stubbedOldCard = CardDto.builder()
                .setId("12345")
                .question("Who is your friend?")
                .answer("Wezley Bush")
                .build();

        stubbedOldCards.add(stubbedOldCard);

        stubbedSetResponse = new Set();
        stubbedSetResponse = Set.builder()
                .id("1234")
                .setName("math")
                .tags(null)
                .cards(stubbedOldCards)
                .author("Sir Wezley Obama")
                .isPublic(false)
                .views(1)
                .plays(1)
                .studies(1)
                .favorites(1)
                .build();
    }

    @AfterEach
    public void caseTearDown() {
        sut = null;
        reset(mockContext, mockSetRepo);
    }

    @AfterAll
    public static void suiteCleanUp() {
        testLogger.close();
    }

    @Test
    public void given_ValidRequest_cardIsAddedToSet() throws IOException {
        //arrange
        APIGatewayProxyRequestEvent mockRequestEvent = new APIGatewayProxyRequestEvent();
        mockRequestEvent.withPath("/cards");
        mockRequestEvent.withHttpMethod("POST");
        mockRequestEvent.withBody("{\"setId\": \"1234\", \"question\": \"Who is your leader?\", \"answer\": \"Sir Wezley Obama\"}");
        mockRequestEvent.withQueryStringParameters(null);

        CardDto card = new CardDto();
        card = stubbedCardResponse;

        when(mockSetRepo.getSet(any())).thenReturn(stubbedSetResponse);
        when(mockSetRepo.addSet(any())).thenReturn(null);

        APIGatewayProxyResponseEvent expectedResponse = new APIGatewayProxyResponseEvent();
        Map<String, String> headers = new HashMap<>();
        headers.put("Access-Control-Allow-Headers", "Content-Type, X-Amz-Date, Authorization");
        headers.put("Access-Control-Allow-Origin", "*");
        expectedResponse.setHeaders(headers);
        expectedResponse.setStatusCode(200);
        expectedResponse.setBody(mapper.toJson(card));

        //act
        APIGatewayProxyResponseEvent actualResponse = sut.handleRequest(mockRequestEvent, mockContext);
        //break json up
        CardDto actualResponseConverted = objectMapper.readValue( actualResponse.getBody(), CardDto.class);
        CardDto expectedResponseConverted = objectMapper.readValue( expectedResponse.getBody(), CardDto.class);

        //assert
        verify(mockSetRepo, times(1)).getSet(any());
        verify(mockSetRepo, times(1)).addSet(any());
        System.out.println("expected: " + expectedResponse);
        System.out.println("actual: " + actualResponse);

        //assert each field
        assertEquals(expectedResponseConverted.getAnswer(), actualResponseConverted.getAnswer());
        assertEquals(expectedResponseConverted.getSetId(), actualResponseConverted.getSetId());
        assertEquals(expectedResponseConverted.getQuestion(), actualResponseConverted.getQuestion());
    }



}