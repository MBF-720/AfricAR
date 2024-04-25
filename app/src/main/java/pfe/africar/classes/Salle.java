package pfe.africar.classes;

public class Salle {
    private int numero;
    private String type;

    // constructor
    public Salle(int numero, String type) {
        this.numero = numero;
        this.type = type;
    }

    // getters
    public int getNumero() {
        return this.numero;
    }

    public String getType() {
        return this.type;
    }

    // setters
    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setType(String type) {
        this.type = type;
    }
}