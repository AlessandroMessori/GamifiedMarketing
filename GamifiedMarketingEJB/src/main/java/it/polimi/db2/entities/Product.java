package it.polimi.db2.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQuery(name="Product.getAll",query = "SELECT p from Product p")
@Table(name = "Product", schema = "GamifiedMarketing")

public class Product {

    @Id
    private int id;

    private String name;

    private String image;

    public Product() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
