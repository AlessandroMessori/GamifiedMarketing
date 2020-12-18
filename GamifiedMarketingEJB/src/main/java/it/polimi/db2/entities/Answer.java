package it.polimi.db2.entities;

import javax.persistence.*;

@Entity
@Table(name = "Answer")
@IdClass(AnswerKey.class)
public class Answer {

    @Id
    private String userEmail;

    @Id
    private int questionId;

    private String text;

    public Answer() {

    }

    public String getUserEmail() {
        return userEmail;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getText() {
        return text;
    }
}
