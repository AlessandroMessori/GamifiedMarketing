package it.polimi.db2.entities;

import javax.persistence.*;

@Entity
@Table(name = "Answer")
@IdClass(AnswerKey.class)
@NamedQuery(name = "Answer.findAnswerByDate", query = "SELECT a FROM Answer a WHERE a.question.day = ?1")
public class Answer {

    @Id
    private String userEmail;

    @Id
    private int questionId;

    private String text;

    @OneToOne()
    @JoinColumn(name = "questionId", insertable = false, updatable = false)
    private Question question;

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

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Question getQuestion() {
        return question;
    }
}
