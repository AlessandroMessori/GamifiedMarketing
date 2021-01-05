package it.polimi.db2.services;

import it.polimi.db2.entities.Offensive_Words;
import it.polimi.db2.entities.User;
import it.polimi.db2.exceptions.BannedWordException;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import it.polimi.db2.entities.Answer;
import it.polimi.db2.entities.Points;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class AnswerService {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("GamifiedMarketing");

    private static final EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

    public AnswerService() {
    }

    public HashMap<String, Pair<List<Answer>, Integer>> findAnswersByDate(Date date) throws Exception {

        HashMap<String, Pair<List<Answer>, Integer>> hashMap = new HashMap<>();
        List<Points> points;
        List<Answer> answers;

        points = em.createNamedQuery("Points.getTodayLeaderboard", Points.class)
                .setParameter(1, (date), TemporalType.DATE)
                .getResultList();

        answers = em.createNamedQuery("Answer.findAnswerByDate", Answer.class)
                .setParameter(1, (date), TemporalType.DATE)
                .getResultList();

        for (Answer a : answers) {
            String currentUserEmail = a.getUserEmail();
            if (!hashMap.containsKey(currentUserEmail)) {
                List<Answer> list = new ArrayList<>();
                Optional<Points> currentUserPoints = points.stream().filter(p -> p.getUserEmail().equals(currentUserEmail)).findFirst();
                Integer currentValue = null;

                if (currentUserPoints.isPresent()) {
                    currentValue = currentUserPoints.get().getVal();
                    list.add(a);

                    if (currentValue > 0) {
                        Pair<List<Answer>, Integer> currentValuePair = new ImmutablePair<>(list, currentValue);
                        hashMap.put(currentUserEmail, currentValuePair);
                    }
                } else {
                    throw new Exception("Synchronization Error");
                }
            } else {
                hashMap.get(currentUserEmail).getLeft().add(a);
            }

        }

        //adds user with deleted questionnaire to the map
        points = points.stream().filter(p -> p.getVal() == 0).collect(Collectors.toList());

        for (Points p : points) {
            Pair<List<Answer>, Integer> currentValuePair = new ImmutablePair<>(null, p.getVal());
            hashMap.put(p.getUserEmail(), currentValuePair);
        }

        return hashMap;

    }

    public void saveUserAnswers(String userEmail, List<Integer> questionsIds, List<String> answersText) throws Exception {
        EntityTransaction transaction = em.getTransaction();

        if (questionsIds.size() != answersText.size()) {
            throw new Exception("question and answers have different sizes!");
        }

        transaction.begin();

        List<String> offensiveWords = em.createNamedQuery("Offensive_Words.findAll", Offensive_Words.class)
                .getResultList()
                .stream()
                .map(Offensive_Words::getWord)
                .collect(Collectors.toList());


        ListIterator<Integer> idsIterator = questionsIds.listIterator();
        ListIterator<String> answersIterator = answersText.listIterator();

        while (idsIterator.hasNext() && answersIterator.hasNext()) {

            String currentAnswer = answersIterator.next();

            if (checkWordInList(offensiveWords,currentAnswer)) {
                User user;
                EntityTransaction banUserTransaction;

                transaction.rollback();
                banUserTransaction = em.getTransaction();
                banUserTransaction.begin();

                user = em.find(User.class, userEmail);

                user.setIsBanned(true);
                em.flush();

                banUserTransaction.commit();

                throw new BannedWordException("You used an offensive word,you are now banned");
            }

            Answer answer = new Answer();
            answer.setUserEmail(userEmail);
            answer.setText(currentAnswer);
            answer.setQuestionId(idsIterator.next());
            em.persist(answer);
        }

        transaction.commit();
    }

    private boolean checkWordInList(List<String> wordList, String word) {
        for (String w : wordList) {
            if (word.contains(w)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        //new AnswerService().saveUserAnswers("Mail", Arrays.asList(0, 1, 2), Arrays.asList("sljkdld", "lkòdsjksd", "skjfk"));
        System.out.println(new AnswerService().findAnswersByDate(new Date()));

    }


}
