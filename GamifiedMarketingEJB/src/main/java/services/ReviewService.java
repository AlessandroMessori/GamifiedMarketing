package services;

import entities.Review;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@Stateless
public class ReviewService {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("GamifiedMarketing");

    private static final EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

    public ReviewService() {

    }

    public List<Review> getReviewsByProductId(int productId) {

        List<Review> rList = em.createNamedQuery("Review.getReviewsByProductId", Review.class).setParameter(1, productId)
                .getResultList();

        return rList;
    }

    public static void main(String[] args) {
        System.out.println(new ReviewService().getReviewsByProductId(1));
    }


}
