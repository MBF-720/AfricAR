package pfe.africar.classes;

public class Note {
    private String matiere;
    private double controle;
    private double synthese;
    private double tp;
    private double orale;
    private double moyenne;
    private double coeff;

    // constructor
    public Note(String m, double c, double s, double t,double orale, double moyenne, double coef) {
        this.matiere = m;
        this.controle = c;
        this.synthese = s;
        this.tp = t;
        this.orale = orale;
        this.moyenne = moyenne;
        this.coeff = coef;
    }

    // getters
    public String getMatiere() {
        return this.matiere;
    }

    public double getControle() {
        return this.controle;
    }

    public double getSynthese() {
        return this.synthese;
    }

    public double getTp() {
        return this.tp;
    }
    public double getOrale() { return orale; }
    public double getMoyenne() { return moyenne; }
    public double getCoeff() {
        return this.coeff;
    }

    // method to calculate the weighted grade
    public double getValeur() {
        return (this.controle * this.coeff + this.synthese * this.coeff + this.tp * this.coeff);
    }
    public void setControle(double controle) { this.controle = controle; }
    public void setSynthese(double synthese) { this.synthese = synthese; }
    public void setMatiere(String matiere) { this.matiere = matiere; }
    public void setTp(double tp) { this.tp = tp; }
    public void setOrale(double orale) { this.orale = orale; }
    public void setMoyenne(double moyenne) { this.moyenne = moyenne; }
    public void setCoeff(double coeff) { this.coeff = coeff; }


}