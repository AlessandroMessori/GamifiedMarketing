package it.polimi.db2.services;

import it.polimi.db2.entities.PDay;
import it.polimi.db2.entities.Points;
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
	@PersistenceContext(unitName = "GamifiedMarketingEJB")
	public EntityManager em;

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


    public void createPday(String productName, String day, List<String> questionsText) throws Exception {
        PDay pDay = new PDay();
        Product product;
        List<Question> questions = new ArrayList<>();

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

    }

    public List<PDay> getAllPDays() {
        return em.createNamedQuery("PDay.getAllProducts", PDay.class)
        		.setParameter(1, new Date(), TemporalType.DATE)
                .getResultList();
    }

    public void deletePDayData(Date day) {

        em.createNamedQuery("Question.deleteQuestions")
                .setParameter(1, day, TemporalType.DATE)
                .executeUpdate();

        em.createNamedQuery("Points.deleteLeaderboard")
                .setParameter(1, day, TemporalType.DATE)
                .executeUpdate();
    }

    public static void main(String[] args) {
        new PDayService().deletePDayData(new Date());
    }


}
