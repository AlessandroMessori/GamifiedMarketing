package it.polimi.db2.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PDay", schema = "GamifiedMarketing")
@NamedQuery(name = "PDay.getTodayProduct", query = "SELECT p FROM PDay p WHERE p.day= ?1")
public class PDay {

    @Id
    private Date day;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    public PDay() {

    }

    public Product getProduct() {
        return product;
    }

    public Date getDate() {
        return day;
    }
}
