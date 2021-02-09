package it.polimi.db2.services;

import it.polimi.db2.entities.Offensive_Words;
import it.polimi.db2.entities.User;
import it.polimi.db2.exceptions.BannedWordException;
import it.polimi.db2.pairs.AnswersPoints;
import it.polimi.db2.entities.Answer;
import it.polimi.db2.entities.Points;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class AnswerService {
	
	@PersistenceContext(unitName = "GamifiedMarketingEJB")
	public EntityManager em;

    public AnswerService() {
    }

    public HashMap<String,  AnswersPoints> findAnswersByDate(Date date) throws Exception {

        HashMap<String, AnswersPoints> hashMap = new HashMap<>();
        List<Points> points;
        List<Answer> answers;

        points = em.createNamedQuery("Points.getTodayLeaderboard", Points.class)
        		.setHint("javax.persistence.cache.storeMode", "REFRESH")
                .setParameter(1, (date), TemporalType.DATE)
                .getResultList();

        answers = em.createNamedQuery("Answer.findAnswerByDate", Answer.class)
        		.setHint("javax.persistence.cache.storeMode", "REFRESH")
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
                    	AnswersPoints answerPoints = new AnswersPoints(list,currentValue);
                        hashMap.put(currentUserEmail, answerPoints);
                    }
                } else {
                    throw new Exception("Synchronization Error");
                }
            } else {
                hashMap.get(currentUserEmail).getAnswers().add(a);
            }

        }

        //adds user with deleted questionnaire to the map
        points = points.stream().filter(p -> p.getVal() == 0).collect(Collectors.toList());

        for (Points p : points) {
        	AnswersPoints answerPoints = new AnswersPoints(null,p.getVal());
            hashMap.put(p.getUserEmail(), answerPoints);
        }

        return hashMap;

    }

    public void saveUserAnswers(String userEmail, List<Integer> questionsIds, List<String> answersText) throws Exception {
        if (questionsIds.size() != answersText.size()) {
            throw new Exception("question and answers have different sizes!");
        }

        List<String> offensiveWords = em.createNamedQuery("Offensive_Words.findAll", Offensive_Words.class)
                .getResultList()
                .stream()
                .map(Offensive_Words::getWord)
                .collect(Collectors.toList());


        ListIterator<Integer> idsIterator = questionsIds.listIterator();
        ListIterator<String> answersIterator = answersText.listIterator();
        List<Answer> answers = new ArrayList();

        while (idsIterator.hasNext() && answersIterator.hasNext()) {

            String currentAnswer = answersIterator.next();

            if (checkWordInList(offensiveWords, currentAnswer)) {
                User user;

                user = em.find(User.class, userEmail);

                user.setIsBanned(true);
                em.flush();

                throw new BannedWordException("You used an offensive word,you are now banned");
            } else if (!currentAnswer.equals("")) {
                Answer answer = new Answer();
                answer.setUserEmail(userEmail);
                answer.setText(currentAnswer);
                answer.setQuestionId(idsIterator.next());
                answers.add(answer);
            }

        }
        
        for (Answer a:answers) {
        	em.persist(a);
        }
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
