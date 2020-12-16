package it.polimi.db2.entities;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        this.password = factory.generateSecret(spec).getEncoded().toString();
    }
}
