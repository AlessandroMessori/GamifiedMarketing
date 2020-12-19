package it.polimi.db2.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Question", schema = "GamifiedMarketing")
@NamedQuery(name = "Question.getStatisticalQuestions", query = "SELECT q FROM Question  q WHERE q.isMarketing = false AND q.day = ?1")
@NamedQuery(name = "Question.getMarketingQuestions", query = "SELECT q FROM Question  q WHERE q.isMarketing = true AND q.day = ?1")
public class Question {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;

    private String inputType;

    private String text;

    private boolean isMarketing;

    private Date day;

    public Question() {

    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getInputType() {
        return inputType;
    }

    public boolean getIsMarketing() {
        return isMarketing;
    }

    public Date getDay() {
        return day;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public void setIsMarketing(boolean marketing) {
        isMarketing = marketing;
    }
}
