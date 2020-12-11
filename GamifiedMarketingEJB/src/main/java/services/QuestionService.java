package services;

import entities.Question;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Stateless
public class QuestionService {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("GamifiedMarketing");

    private static final EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

    public QuestionService() {

    }

    public List<Question> getStatisticalQuestions() {

        List<Question> qList = em.createNamedQuery("Question.getStatisticalQuestions", Question.class)
                .getResultList();

        return qList;
    }

    public List<Question> getTodayMarketingQuestions() {

        List<Question> qList = em.createNamedQuery("Question.getMarketingQuestions", Question.class)
                .setParameter(1, (new Date()), TemporalType.DATE)
                .getResultList();

        return qList;
    }

    public static void main(String[] args) {
        System.out.println(new QuestionService().getTodayMarketingQuestions());
    }


}
