package it.polimi.db2.services;

import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.User;
import it.polimi.db2.exceptions.ProductNotFoundException;
import it.polimi.db2.exceptions.UserAlreadyExistsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class ProductService {
	@PersistenceContext(unitName = "GamifiedMarketingEJB")
	public EntityManager em;

    public ProductService() {
    }

    public List<Product> getAllProducts() {
        return em.createNamedQuery("Product.getAll", Product.class)
                .getResultList();
    }

    public Product findProductByName(String productName) throws ProductNotFoundException {
        List<Product> foundProducts = em.createNamedQuery("Product.findProductByName",Product.class)
                .setParameter(1, productName)
                .getResultList();

        if (foundProducts.size() > 0)  {
            return foundProducts.get(0);
        }
        else {
            throw new ProductNotFoundException("There is no product with that name");
        }

    }

}
