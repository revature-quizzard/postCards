package com.revature.documents;

import lombok.Data;

import java.util.List;

@Data
public class SetDto {

    private String id;
    private String set_name;
    private List<String> tags;
    private List<Card> cards;
    private String author;
    private boolean is_public;
    private int views;
    private int plays;
    private int studies;
    private int favorites;

}
