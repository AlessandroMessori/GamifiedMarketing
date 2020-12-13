package it.polimi.db2.services;

import it.polimi.db2.entities.PDay;
import it.polimi.db2.exceptions.NoPDayException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Stateless
public class PDayService {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("GamifiedMarketing");

    private static final EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

    public PDayService() {
    }

    public PDay getTodayProduct() throws NoPDayException {
        List result = em.createNamedQuery("PDay.getTodayProduct")
                .setParameter(1, (new Date()), TemporalType.DATE)
                .getResultList();

        if (result.size() == 0) {
            throw new NoPDayException("There is not a product of the day yet!");
        }

        return (PDay) result.get(0);
    }

}
