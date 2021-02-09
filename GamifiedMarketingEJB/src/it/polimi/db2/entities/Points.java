package it.polimi.db2.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Points", schema = "GamifiedMarketing")
@IdClass(PointsKey.class)
@NamedQuery(name = "Points.getTodayLeaderboard", query = "SELECT p FROM Points p WHERE p.questionnaireDate = ?1")
@NamedQuery(name = "Points.deleteLeaderboard", query = "DELETE FROM Points p WHERE p.questionnaireDate = ?1")
@NamedQuery(name = "Points.getTodayPoints", query = "SELECT p FROM Points p WHERE p.questionnaireDate = ?1 AND p.userEmail = ?2")
public class Points {

    @Id
    private String userEmail;

    @Id
    @Temporal(TemporalType.DATE)
    private Date questionnaireDate;

    private int val;

    @ManyToOne
    @JoinColumn(name = "userEmail", insertable = false, updatable = false)
    private User user;

    public Points() {

    }

    public String getUserEmail() {
        return userEmail;
    }

    public Date getQuestionnaireDate() {
        return questionnaireDate;
    }

    public int getVal() {
        return val;
    }

    public User getUser() {
        return user;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setQuestionnaireDate(Date questionnaireDate) {
        this.questionnaireDate = questionnaireDate;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
