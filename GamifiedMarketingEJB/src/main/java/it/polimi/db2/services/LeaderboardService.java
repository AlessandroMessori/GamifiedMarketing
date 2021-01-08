package it.polimi.db2.services;

import it.polimi.db2.entities.Points;
import it.polimi.db2.exceptions.NoPointsException;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Stateless
public class LeaderboardService {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("GamifiedMarketing");

    private static final EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

    public LeaderboardService() {
    }

    public List<Points> getTodayLeaderboard() throws NoPointsException {
        List<Points> result = em.createNamedQuery("Points.getTodayLeaderboard", Points.class)
                .setParameter(1, (new Date()), TemporalType.DATE)
                .getResultList();

        if (result.size() == 0) {
            throw new NoPointsException("No one has completed today's questionnaire yet!");
        }

        return result;
    }

    public void cancelQuestionnaire(String userEmail) {
        EntityTransaction transaction = em.getTransaction();
        Points points = new Points();

        transaction.begin();

        points.setUserEmail(userEmail);
        points.setQuestionnaireDate(new Date());
        points.setVal(0);

        em.persist(points);
        em.flush();

        transaction.commit();
    }

    public static void main(String[] args) throws NoPointsException {
        List leaderboard = new LeaderboardService().getTodayLeaderboard();
        Points points = (Points) leaderboard.get(0);
        System.out.println(points.getUser().getUsername() + " " + points.getQuestionnaireDate() + " " + points.getVal());
    }


}
