package it.polimi.db2.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Question", schema = "GamifiedMarketing")
@NamedQuery(name = "Question.getAllQuestions", query = "SELECT q FROM Question  q WHERE q.day = ?1")
@NamedQuery(name = "Question.getStatisticalQuestions", query = "SELECT q FROM Question  q WHERE q.isMarketing = false AND q.day = ?1")
@NamedQuery(name = "Question.getMarketingQuestions", query = "SELECT q FROM Question  q WHERE q.isMarketing = true AND q.day = ?1")
@NamedQuery(name = "Question.deleteQuestions", query = "DELETE FROM Question q WHERE q.day = ?1")
public class Question {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    private String inputType;

    private String text;

    private boolean isMarketing;

    @Temporal(TemporalType.DATE)
    private Date day;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "question")
    private List<Answer> answers;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day",insertable = false,updatable = false)
    private PDay pday;
    

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
