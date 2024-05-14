package pfe.africar.classes;

import java.util.ArrayList;
import java.util.List;

public class Classe {


    private String classeID;
    private String ecoleID;
    private String niveau;
    private List<Eleve> listeEleve;
    private List<Professeur> listeProfesseurs;
    private List<String> matieres;
    private List<Quiz> quiz;


    public Classe(String niveau, String ecoleID) {
        this.niveau = niveau;
        this.listeEleve = new ArrayList<>();
        this.listeProfesseurs = new ArrayList<>();
        this.matieres = new ArrayList<>();
        this.quiz = new ArrayList<>();
    }

    public void addEleve(Eleve eleve) {
        this.listeEleve.add(eleve);
    }

    public void removeEleve(String eleve) {
        this.listeEleve.remove(eleve);
    }

    public void addProfesseur(Professeur professeur) {
        this.listeProfesseurs.add(professeur);
    }

    public void removeProfesseur(Professeur professeur) {
        this.listeProfesseurs.remove(professeur);
    }

    public void addMatiere(String matiere) {
        this.matieres.add(matiere);
    }

    public void removeMatiere(String matiere) {
        this.matieres.remove(matiere);
    }

    public void addQuiz(Quiz quiz) {
        this.quiz.add(quiz);
    }

    public void removeQuiz(Quiz quiz) {
        this.quiz.remove(quiz);
    }

    public String getNiveau() {
        return niveau;
    }

    public List<Eleve> getEleves() {
        return listeEleve;
    }

    public List<Professeur> getProfesseurs() {
        return listeProfesseurs;
    }

    public List<String> getMatieres() {
        return matieres;
    }

    public List<Quiz> getQuiz() {
        return quiz;
    }

    public String getClasseID() {
        return classeID;
    }

    public String getEcoleID() {
        return ecoleID;
    }

    public void setClasseID(String classeID) {
        this.classeID = classeID;
    }

    public void setEcoleID(String ecoleID) {
        this.ecoleID = ecoleID;
    }



   /* public void getListeEleves(String ecoleId, String classeId, ResultCallback<Map<String, String>> callback) {
        Map<String, String> elevesMap = new HashMap<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Ecoles").document(ecoleId).collection("Classes")
                .document(classeId)
                .collection("Eleves")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            String nom = document.getString("nom");
                            String prenom = document.getString("prenom");
                            String id = document.getId();
                            String name = String.format("%s %s", nom, prenom);
                            elevesMap.put(name, id);
                        }
                        callback.onResult(elevesMap);
                    } else {
                        // Gérer les erreurs
                        callback.onError(task.getException());
                    }
                });
    }*/


}