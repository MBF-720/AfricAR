package pfe.africar.classes;

public class Agent extends Personne {
    private String poste;

    // constructor
    public Agent(String iDecole, String n, String p, String e, String t, String poste) {
        super(iDecole, n, p, e, t);
        this.poste = poste;
    }

    // getter
    public String getPoste() {
        return this.poste;
    }

    // setter
    public void setPoste(String poste) {
        this.poste = poste;
    }
}
