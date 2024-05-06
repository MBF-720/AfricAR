package pfe.africar.classes;

import java.util.ArrayList;
import java.util.List;

    public class Eleve extends Personne {
        private List<Note> notes;
        private String idClasse;

        // constructor
        public Eleve(String iDecole, String n, String p, String e, String t) {
            super(iDecole, n, p, e, t);
            this.notes = new ArrayList<>();
        }

        // getters
        public List<Note> getNotes() {
            return this.notes;
        }

        // method to calculate the average grade
        public double getMoyenne() {
            double sum = 0;
            for (Note note : this.notes) {
                sum += note.getValeur();
            }
            return sum / this.notes.size();
        }

        // method to add a note
        public void addNote(Note note) {
            this.notes.add(note);
        }




        public String getIdClasse() {
            return idClasse;
        }

        public void setIdClasse(String idClasse) {
            this.idClasse = idClasse;
        }
    }
