package it.polimi.db2.services;

import it.polimi.db2.entities.Offensive_Words;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.*;

@Stateless
public class WordsService {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("GamifiedMarketing");

    private static final EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

    public WordsService() {
    }

    public List<Offensive_Words> getAll() {
        return em.createNamedQuery("Offensive_Words.findAll", Offensive_Words.class)
                .getResultList();
    }

    public static void main(String[] args) throws Exception {
        WordsService wordsService = new WordsService();
        wordsService
                .getAll()
                .forEach(offensive_words -> System.out.println(offensive_words.getWord()));
    }


}
