package it.polimi.db2.services;

import it.polimi.db2.entities.Answer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

@Stateless
public class AnswerService {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("GamifiedMarketing");

    private static final EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

    public AnswerService() {

    }

    public List<Answer> findAnswersByEmailAndId(String userEmail, int questionId) {
        return em.createNamedQuery("Answer.findAnswerByEmailAndId", Answer.class)
                .setParameter(1, userEmail)
                .setParameter(2, questionId)
                .getResultList();
    }

    public void saveUserAnswers(String userEmail, List<Integer> questionsIds, List<String> answersText) throws Exception {
        EntityTransaction transaction = em.getTransaction();

        if (questionsIds.size() != answersText.size()) {
            throw new Exception("question and answers have different sizes!");
        }

        transaction.begin();

        ListIterator<Integer> idsIterator = questionsIds.listIterator();
        ListIterator<String> answersIterator = answersText.listIterator();


        while (idsIterator.hasNext() && answersIterator.hasNext()) {
            Answer answer = new Answer();
            answer.setUserEmail(userEmail);
            answer.setText(answersIterator.next());
            answer.setQuestionId(idsIterator.next());
            em.persist(answer);
        }

        transaction.commit();
    }

    public static void main(String[] args) throws Exception {
        new AnswerService().saveUserAnswers("Mail", Arrays.asList(0, 1, 2), Arrays.asList("sljkdld", "lkòdsjksd", "skjfk"));
    }


}
