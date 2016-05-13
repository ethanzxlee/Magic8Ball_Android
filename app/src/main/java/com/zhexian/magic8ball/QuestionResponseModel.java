package com.zhexian.magic8ball;

import java.io.Serializable;

/**
 * Created by ZheXian on 04/04/2016.
 */
public class QuestionResponseModel implements Serializable {

    public static final String QUESTION = "question";
    public static final String ANSWER = "answer";
    public static final String USERNAME = "username";
    public static final String IMAGE_URL = "imageURL";

    private String question;
    private String response;
    private String username;
    private String imageUrl;

    public QuestionResponseModel(String question, String response, String username, String imageUrl) {
        this.question = question;
        this.response = response;
        this.username = username;
        this.imageUrl = imageUrl;
    }

    public String getQuestion() {
        return question;
    }

    public String getResponse() {
        return response;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUsername() {
        return username;
    }
}
