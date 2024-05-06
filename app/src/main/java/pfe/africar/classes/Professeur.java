package pfe.africar.classes;

import java.util.ArrayList;
import java.util.List;
public class Professeur extends Personne {
    private String matiere;
    private List<String> idClasses;

    // constructor
    public Professeur(String iDecole, String n, String p, String e, String t) {
        super(iDecole, n, p, e, t);

        this.idClasses = new ArrayList<>();
    }

    // getters
    public String getMatiere() {
        return this.matiere;
    }

    public List<String> getIdClasses() {
        return this.idClasses;
    }

    // setters
    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public void setIdClasses(List<String> idClasses) {
        this.idClasses = idClasses;
    }

    // methods
    public void addIdClass(String idClass) {
        this.idClasses.add(idClass);
    }

    public void removeIdClass(String idClass) {
        this.idClasses.remove(idClass);
    }
}