package pfe.africar.classes;

public class Note {
    private String matiere;



    private Double matiereCoeff;
    private double controle;
    private double synthese;
    private double tp;
    private double orale;
    private double moyenne;
    private double contolecoeff;
   private double syntheseCoef;
   private double tpCoef;
    private double oraleCoef;
    // constructor
    public Note(String m, double c, double s, double t,double orale, double contolecoef,double syntheseCoef,double tpCoef,double oraleCoef) {
        this.matiere = m;
        this.controle = c;
        this.synthese = s;
        this.tp = t;
        this.orale = orale;
        this.moyenne = moyenne;
        this.contolecoeff = contolecoef;
        this.syntheseCoef = syntheseCoef;
        this.tpCoef = tpCoef;
        this.oraleCoef = oraleCoef;
    }

    public Note(double controle, double controleCoef, double synthese, double syntheseCoef, double tp, double tpCoef, double orale, double oraleCoef) {
        this.controle = controle;
        this.synthese = synthese;
        this.tp = tp;
        this.orale = orale;
        this.moyenne = calculerMoyenne();
        this.contolecoeff = controleCoef;
        this.syntheseCoef = syntheseCoef;
        this.tpCoef = tpCoef;
        this.oraleCoef = oraleCoef;
    }

    public Note() {
        // Constructeur vide
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

    // method to calculate the weighted grade

   double s=(this.contolecoeff+this.syntheseCoef+this.tpCoef+this.oraleCoef);
    public double getValeur() {
        return ((this.controle * this.contolecoeff + this.synthese * this.syntheseCoef + this.tp * this.tpCoef+ this.orale* this.oraleCoef)/s);
    }
    public void setControle(double controle) { this.controle = controle; }
    public void setSynthese(double synthese) { this.synthese = synthese; }
    public void setMatiere(String matiere) { this.matiere = matiere; }
    public void setTp(double tp) { this.tp = tp; }
    public void setOrale(double orale) { this.orale = orale; }
    public void setMoyenne(double moyenne) { this.moyenne = moyenne; }
    public void setContolecoeff(double contolecoeff) { this.contolecoeff = contolecoeff; }
    public void setSyntheseCoef(double syntheseCoef) { this.syntheseCoef = syntheseCoef; }
    public void setTpCoef(double tpCoef) { this.tpCoef = tpCoef; }
    public void setOraleCoef(double oraleCoef) { this.oraleCoef = oraleCoef; }
    public double getContolecoeff() {
        return this.contolecoeff;
    }
    public double getSyntheseCoef() {
        return this.syntheseCoef;
    }
    public double getTpCoef() {
        return this.tpCoef;
    }
    public double getOraleCoef() {
        return this.oraleCoef;
    }

    public Double getMatiereCoeff() {
        return matiereCoeff;
    }

    public void setMatiereCoeff(Double matiereCoeff) {
        this.matiereCoeff = matiereCoeff;
    }

    public double calculerMoyenne() {
        double total = 0.0;
        double poidsTotal = 0.0;

        total += this.controle * this.contolecoeff;
        poidsTotal += this.contolecoeff;

        total += this.synthese * this.syntheseCoef;
        poidsTotal += this.syntheseCoef;

        total += this.tp * this.tpCoef;
        poidsTotal += this.tpCoef;

        total += this.orale * this.oraleCoef;
        poidsTotal += this.oraleCoef;

        if (poidsTotal == 0.0) {
            return 0.0;
        } else {

            return total / poidsTotal;
        }
    }

}