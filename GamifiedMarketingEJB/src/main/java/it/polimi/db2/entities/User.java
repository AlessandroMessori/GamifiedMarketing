package it.polimi.db2.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "User", schema = "GamifiedMarketing")
@NamedQuery(name = "User.getAll", query = "SELECT u FROM User u")
@NamedQuery(name = "User.checkCredentials", query = "SELECT u from User u WHERE u.email =?1 AND u.password = ?2")

public class User {

    @Id
    private String email;

    private String username;

    private String password;

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
