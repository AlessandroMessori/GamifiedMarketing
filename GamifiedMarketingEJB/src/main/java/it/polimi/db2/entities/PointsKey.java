package it.polimi.db2.entities;

import java.io.Serializable;
import java.util.Date;

public class PointsKey implements Serializable {
    private String userEmail;
    private Date questionnaireDate;
    
   public int hashCode() {
   	return super.hashCode();
   }
   
   public boolean equals(Object obj) {
   	return super.equals(obj);
   }
}

