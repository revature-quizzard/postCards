package com.revature.documents;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SetResponse {

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
}