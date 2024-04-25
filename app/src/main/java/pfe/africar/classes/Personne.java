package pfe.africar.classes;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Personne {
    private String IDecole;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;

    // constructor
    // constructor
    public Personne(String iDecole, String n, String p, String e, String t) {
        this.IDecole = iDecole;
        this.nom = n;
        this.prenom = p;
        if (isValidEmail(e)) {
            this.email = e;
        } else {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.telephone = t;
    }

    // email validation method
    private boolean isValidEmail(String email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // getters
    public String getIDecole() {
        return this.IDecole;
    }

    public String getNom() {
        return this.nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public String getEmail() {
        return this.email;
    }

    public String getTelephone() {
        return this.telephone;
    }

    // setters
    public void setIDecole(String iDecole) {
        this.IDecole = iDecole;
    }

    public void setNom(String n) {
        this.nom = n;
    }

    public void setPrenom(String p) {
        this.prenom = p;
    }

    public void setEmail(String e) {
        this.email = e;
    }

    public void setTelephone(String t) {
        this.telephone = t;
    }
}