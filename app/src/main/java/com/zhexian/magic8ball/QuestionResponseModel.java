package com.zhexian.magic8ball;

import java.io.Serializable;

/**
 * Created by ZheXian on 04/04/2016.
 */
public class QuestionResponseModel implements Serializable {

    private String question;
    private String response;

    public QuestionResponseModel(String question, String response) {
        this.question = question;
        this.response = response;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
