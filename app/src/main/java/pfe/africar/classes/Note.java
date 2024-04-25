package pfe.africar.classes;

public class Note {
    private String matiere;
    private double controle;
    private double synthese;
    private double tp;
    private double coeff;

    // constructor
    public Note(String m, double c, double s, double t, double coef) {
        this.matiere = m;
        this.controle = c;
        this.synthese = s;
        this.tp = t;
        this.coeff = coef;
    }

    // getters
    public String getMatiere() {
        return this.matiere;
    }

    public double getControle() {
        return this.controle;
    }

    public double getSintrace() {
        return this.synthese;
    }

    public double getTp() {
        return this.tp;
    }

    public double getCoeff() {
        return this.coeff;
    }

    // method to calculate the weighted grade
    public double getValeur() {
        return (this.controle * this.coeff + this.synthese * this.coeff + this.tp * this.coeff);
    }
}