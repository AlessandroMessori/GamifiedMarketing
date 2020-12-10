package entities;

import javax.persistence.*;

@Entity
@Table(name = "Review")
@IdClass(ReviewKey.class)
@NamedQuery(name="Review.getReviewsByProductId" ,query = "SELECT r FROM Review r WHERE r.productId = ?1")

public class Review {

    @Id
    private String userEmail;

    @Id
    private int productId;

    private String text;

    public Review() {

    }

    public String getUserEmail() {
        return userEmail;
    }

    public int getProductId() {
        return productId;
    }

    public String getText() {
        return text;
    }
}
