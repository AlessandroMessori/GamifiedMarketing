package it.polimi.db2.services;

import it.polimi.db2.entities.Offensive_Words;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.*;

@Stateless
public class WordsService {
	
	@PersistenceContext(unitName = "GamifiedMarketingEJB")
	public EntityManager em;

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
