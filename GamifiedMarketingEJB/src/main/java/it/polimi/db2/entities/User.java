package it.polimi.db2.entities;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "User", schema = "GamifiedMarketing")
@NamedQuery(name = "User.getAll", query = "SELECT u FROM User u")
@NamedQuery(name = "User.checkCredentials", query = "SELECT u from User u WHERE u.email =?1 AND u.password = ?2")
@NamedQuery(name = "User.checkUnique", query = "SELECT u from User u WHERE u.email = ?1 OR u.username = ?2")
public class User {

    @Id
    private String email;

    private String username;

    private String password;

    private boolean isAdmin;

    private boolean isBanned;

    @OneToMany(mappedBy = "user")
    private List<Answer> answers;

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

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public boolean getIsBanned() {
        return isBanned;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password)  {
      
        this.password = password;
    }

    public void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;

    }

    public List<Answer> getAnswers() {
        return answers;
    }
}
