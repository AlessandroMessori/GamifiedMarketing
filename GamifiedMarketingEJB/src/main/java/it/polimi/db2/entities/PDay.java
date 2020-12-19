package it.polimi.db2.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PDay", schema = "GamifiedMarketing")
@NamedQuery(name = "PDay.getTodayProduct", query = "SELECT p FROM PDay p WHERE p.day= ?1")
@NamedQuery(name = "PDay.getAllProducts", query = "SELECT p FROM PDay p ORDER BY p.day")
public class PDay {

    @Id
    private Date day;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @OneToMany(mappedBy = "day")
    private List<Question> questions = new ArrayList<>();

    public PDay() {

    }

    public Product getProduct() {
        return product;
    }

    public Date getDate() {
        return day;
    }

    public Question getQuestion(int i) {
        return questions.get(i);
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
