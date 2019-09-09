package be.heh.automation.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

    private int id;
    private String lastname;
    private String firstname;
    private String password;
    private String email;
    private int right;

    public User(int id, String lastname, String firstname, String password, String email, int right) throws Exception {
        this.id = id;
        setLastname(lastname);
        setFirstname(firstname);
        setPassword(password);
        setEmail(email);
        this.right = right;
    }

    public boolean validateName(String name){
            return name.matches("[A-Z][a-zA-Z]*");
    }

    public boolean validatePassword(String password){
        Pattern pattern = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public boolean validateEmail(String email){
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) throws Exception {
        if(!validateName(lastname)){
            throw new Exception("Lastname invalide format");
        }
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) throws Exception {
        if(!validateName(lastname)){
            throw new Exception("Firstname invalide format");
        }
        this.firstname = firstname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws Exception {
        if(!validatePassword(password)){
            throw new Exception("\n" +
                    "The password must contain at least one lowercase, " +
                    "one uppercase, one number, one special character and " +
                    "between six and sixteen characters");
        }
        this.password = password;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right){
        this.right = right;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Exception {
        if(!validateEmail(email)){
            throw new Exception("\n" +
                    "The email address must be of type name@domain.com");
        }
        this.email = email;
    }
}
