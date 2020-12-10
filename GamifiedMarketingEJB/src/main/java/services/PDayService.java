package services;

import entities.PDay;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TemporalType;
import java.util.Date;

public class PDayService {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("GamifiedMarketing");

    private static final EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

    public PDayService() {
    }

    public PDay getTodayProduct() {
        return (PDay) em.createNamedQuery("PDay.getTodayProduct")
                .setParameter(1, (new Date()), TemporalType.DATE)
                .getResultList()
                .get(0);
    }

}
