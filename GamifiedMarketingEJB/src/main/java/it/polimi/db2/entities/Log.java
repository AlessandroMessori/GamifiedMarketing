package it.polimi.db2.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Log")
public class Log {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;

    private String email;

    private Date timestamp;

    public Log() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserEmail(String email) {
        this.email = email;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
