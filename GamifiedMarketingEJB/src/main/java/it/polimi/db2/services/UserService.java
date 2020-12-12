package it.polimi.db2.services;

import it.polimi.db2.entities.User;
import it.polimi.db2.exceptions.WrongCredentialsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@Stateless
public class UserService {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("GamifiedMarketing");

    private static final EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

    public UserService() {
    }

    public User getFirstUser() {


        List<User> uList = em.createNamedQuery("User.getAll", User.class)
                .getResultList();

        return uList.get(0);
    }

    public User checkCredentials(String email, String pwd) throws WrongCredentialsException {
        return null;
    }

}