package it.polimi.db2.entities;

import java.io.Serializable;

public class AnswerKey implements Serializable {
    private String userEmail;
    private int questionId;
     
    public int hashCode() {
    	return super.hashCode();
    }
    
    public boolean equals(Object obj) {
    	return super.equals(obj);
    }
}
