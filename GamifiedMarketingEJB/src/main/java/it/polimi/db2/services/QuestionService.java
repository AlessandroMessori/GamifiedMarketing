package it.polimi.db2.services;

import it.polimi.db2.entities.Answer;
import it.polimi.db2.entities.PDay;
import it.polimi.db2.entities.Question;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

@Stateless
public class QuestionService {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("GamifiedMarketing");

    private static final EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

    @EJB(name = "PDayService")
    PDayService pDayService;

    public QuestionService() {

    }

    public List<Question> getTodayStatisticalQuestions() {

        List<Question> qList = em.createNamedQuery("Question.getStatisticalQuestions", Question.class)
                .setParameter(1, (new Date()), TemporalType.DATE)
                .getResultList();

        return qList;
    }

    public List<Question> getTodayMarketingQuestions() {

        List<Question> qList = em.createNamedQuery("Question.getMarketingQuestions", Question.class)
                .setParameter(1, (new Date()), TemporalType.DATE)
                .getResultList();

        return qList;
    }

    /*public void saveQuestions(Date day, List<String> questionsText) throws Exception {
        PDay pday;

        for (String s : questionsText) {
            Question question = new Question();
            question.setInputType("text");
            question.setIsMarketing(false);
            question.setDay(day);
            question.setText(s);
        }
    }*/


    public static void main(String[] args) {
        System.out.println(new QuestionService().getTodayStatisticalQuestions());
    }


}
