package com.itmo.server;

import java.io.Serializable;

public class Response implements Serializable {
    private String answer;

    public Response(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
