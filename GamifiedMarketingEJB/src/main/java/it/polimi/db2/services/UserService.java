package it.polimi.db2.services;

import it.polimi.db2.entities.Log;
import it.polimi.db2.entities.User;
import it.polimi.db2.exceptions.UserAlreadyExistsException;
import it.polimi.db2.exceptions.WrongCredentialsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;
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

    public User login(String email, String pwd) throws WrongCredentialsException {

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        List<User> resultList = em.createNamedQuery("User.checkCredentials", User.class)
                .setParameter(1, email)
                .setParameter(2, pwd)
                .getResultList();

        if (resultList.size() == 0) {
            transaction.rollback();
            throw new WrongCredentialsException("Wrong Credentials");
        } else {

            Log log = new Log();
            log.setUserEmail(email);
            log.setTimestamp(new Date());

            em.persist(log);
            transaction.commit();
            return resultList.get(0);
        }
    }

    public User registerUser(String email, String uname, String pwd) throws Exception {
        boolean alreadyExists;
        User user;

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        // checks if an user with the same credentials already exists
        alreadyExists = em.createNamedQuery("User.checkUnique", User.class)
                .setParameter(1, email)
                .setParameter(2, uname)
                .getResultList()
                .size() > 0;

        if (alreadyExists) {
            transaction.commit();
            throw new UserAlreadyExistsException("Credentials already taken by another user");
        } else {
            user = new User();

            user.setEmail(email);
            user.setUsername(uname);
            user.setPassword(pwd);
            em.persist(user);
            transaction.commit();
        }

        return user;
    }

}