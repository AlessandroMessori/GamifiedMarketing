package it.polimi.db2.entities;

import javax.persistence.*;

@Entity
@Table(name = "Offensive_Words")
@NamedQuery(name = "Offensive_Words.findAll", query = "SELECT o FROM Offensive_Words o")
public class Offensive_Words {

    @Id
    private int id;

    private String word;

    public Offensive_Words() {

    }

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }
}

