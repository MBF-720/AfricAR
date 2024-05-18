package pfe.africar.classes;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Personne {
    private String IDecole;
    private String nom;
    private String prenom;
    private String email;
    private String phone;

    private String uid;


    private String genre;

    private String birth;

    private String photoUrl ;


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
        this.phone = t;
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
        return this.phone;
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
        this.phone = t;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }



    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}