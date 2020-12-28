package it.polimi.db2.services;

import it.polimi.db2.entities.PDay;
import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.Question;
import it.polimi.db2.exceptions.NoPDayException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


    public PDay createPday(String productName, String day, List<String> questionsText) throws Exception {
        PDay pDay = new PDay();
        Product product;
        List<Question> questions = new ArrayList<>();

        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        product = productService.findProductByName(productName);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(day);
        pDay.setDay(date);

        pDay.setProduct(product);

        em.persist(pDay);

        for (String q : questionsText) {
            Question question = new Question();
            question.setText(q);
            question.setInputType("text");
            question.setDay(pDay.getDate());
            question.setIsMarketing(true);
            questions.add(question);
            em.persist(question);
        }

        transaction.commit();

        return pDay;

    }

    public List<PDay> getAllPDays() {
        return em.createNamedQuery("PDay.getAllProducts", PDay.class)
                .getResultList();
    }


}
