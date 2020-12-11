package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Question", schema = "GamifiedMarketing")
@NamedQuery(name = "Question.getStatisticalQuestions", query = "SELECT q FROM Question  q WHERE q.isMarketing = false ")
@NamedQuery(name = "Question.getMarketingQuestions", query = "SELECT q FROM Question  q WHERE q.isMarketing = true AND q.day = ?1")
public class Question {

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
}
