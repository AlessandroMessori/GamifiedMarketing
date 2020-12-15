package it.polimi.db2.services;

import it.polimi.db2.entities.Points;
import it.polimi.db2.exceptions.NoPointsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TemporalType;
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
        List<Points> result = em.createNamedQuery("Points.getTodayLeaderboard",Points.class)
                .setParameter(1, (new Date()), TemporalType.DATE)
                .getResultList();

        if (result.size() == 0) {
            throw new NoPointsException("No one has completed today's questionnaire yet!");
        }

        return result;
    }

    public static void main(String[] args) throws NoPointsException {
        List leaderboard = new LeaderboardService().getTodayLeaderboard();
        Points points = (Points) leaderboard.get(0);
        System.out.println(points.getUser().getUsername() + " " + points.getQuestionnaireDate() + " " + points.getVal());
    }


}
