package it.polimi.db2.services;

import it.polimi.db2.entities.PDay;
import it.polimi.db2.entities.Product;
import it.polimi.db2.exceptions.NoPDayException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Stateless
public class PDayService {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("GamifiedMarketing");

    private static final EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

    @EJB(name = "ProductService")
    ProductService productService;

    public PDayService() {
    }

    public PDay getTodayProduct() throws NoPDayException {
        List<PDay> result = em.createNamedQuery("PDay.getTodayProduct", PDay.class)
                .setParameter(1, (new Date()), TemporalType.DATE)
                .getResultList();

        if (result.size() == 0) {
            throw new NoPDayException("There is not a product of the day yet!");
        }

        return result.get(0);
    }


    public PDay createPday(String productName, String day) throws Exception {
        PDay pDay = new PDay();
        Product product;
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        product = productService.findProductByName(productName);
        System.out.println(day);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(day);
        pDay.setDay(date);

        pDay.setProduct(product);
        em.persist(pDay);

        transaction.commit();
        return pDay;

    }


}
