package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "User", schema = "GamifiedMarketing")
@NamedQuery(name = "User.getAll", query = "SELECT u FROM User u")

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
