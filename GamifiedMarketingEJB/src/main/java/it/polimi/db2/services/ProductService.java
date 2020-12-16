package it.polimi.db2.services;

import it.polimi.db2.entities.Product;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@Stateless
public class ProductService {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("GamifiedMarketing");

    private static final EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

    public ProductService() {
    }

    public List<Product> getAllProducts() {
        return em.createNamedQuery("Product.getAll", Product.class)
                .getResultList();
    }

}
