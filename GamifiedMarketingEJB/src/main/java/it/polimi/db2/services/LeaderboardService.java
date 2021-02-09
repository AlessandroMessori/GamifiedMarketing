package it.polimi.db2.services;

import it.polimi.db2.entities.Points;
import it.polimi.db2.exceptions.NoPointsException;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Stateless
public class LeaderboardService {

	@PersistenceContext(unitName = "GamifiedMarketingEJB")
	public EntityManager em;

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


    public Points getUserPoints(String userEmail) {
        List<Points> result = em.createNamedQuery("Points.getTodayPoints", Points.class)
                .setParameter(1, (new Date()), TemporalType.DATE)
                .setParameter(2, userEmail)
                .getResultList();

        return result.size() == 0 ? null : result.get(0);
    }

    public void cancelQuestionnaire(String userEmail) {
        Points points = new Points();
        points.setUserEmail(userEmail);
        points.setQuestionnaireDate(new Date());
        points.setVal(0);

        em.persist(points);
        em.flush();

    }

    public static void main(String[] args) throws NoPointsException {
        List leaderboard = new LeaderboardService().getTodayLeaderboard();
        Points points = (Points) leaderboard.get(0);
        System.out.println(points.getUser().getUsername() + " " + points.getQuestionnaireDate() + " " + points.getVal());
    }


}
