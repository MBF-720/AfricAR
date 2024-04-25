package pfe.africar.activitys;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pfe.africar.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        // Ajouter une collection "Ecoles"
        ajouterEcoles();
    }

    private void ajouterEcoles() {
        ajouterEcole("Ecole 1", "Actualités de l'école 1");
        ajouterEcole("Ecole 2", "Actualités de l'école 2");
    }

    private void ajouterEcole(String nom, String actualites) {
        Map<String, Object> ecole = new HashMap<>();
        ecole.put("nom", nom);
        ecole.put("actualites", actualites);

        db.collection("Ecoles")
                .add(ecole)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Ecole ajoutée avec succès !");
                            DocumentReference ecoleRef = task.getResult();
                            ajouterEleve(ecoleRef);
                            ajouterAdmin(ecoleRef);
                            ajouterProfesseur(ecoleRef);
                            ajouterClasses(ecoleRef);
                            ajouterSalle(ecoleRef);
                            ajouterStatistique(ecoleRef);
                        } else {
                            Log.w(TAG, "Erreur lors de l'ajout de l'école", task.getException());
                        }
                    }
                });
    }

    private void ajouterEleve(DocumentReference ecoleRef) {
        Map<String, Object> eleve = new HashMap<>();
        eleve.put("nom", "Nom de l'élève");
        eleve.put("prenom", "Prénom de l'élève");
        eleve.put("email", "Email de l'élève");
        eleve.put("motDePasse", "Mot de passe de l'élève");

        ecoleRef.collection("Eleves")
                .add(eleve)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Élève ajouté avec succès !");
                        } else {
                            Log.w(TAG, "Erreur lors de l'ajout de l'élève", task.getException());
                        }
                    }
                });
    }

    private void ajouterAdmin(DocumentReference ecoleRef) {
        Map<String, Object> admin = new HashMap<>();
        admin.put("nom", "Nom de l'admin");
        admin.put("prenom", "Prénom de l'admin");
        admin.put("email", "Email de l'admin");
        admin.put("motDePasse", "Mot de passe de l'admin");

        ecoleRef.collection("Admins")
                .add(admin)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Admin ajouté avec succès !");
                        } else {
                            Log.w(TAG, "Erreur lors de l'ajout de l'admin", task.getException());
                        }
                    }
                });
    }

    private void ajouterProfesseur(DocumentReference ecoleRef) {
        Map<String, Object> professeur = new HashMap<>();
        professeur.put("nom", "Nom du professeur");
        professeur.put("prenom", "Prénom du professeur");
        professeur.put("email", "Email du professeur");
        professeur.put("motDePasse", "Mot de passe du professeur");

        ecoleRef.collection("Professeurs")
                .add(professeur)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Professeur ajouté avec succès !");
                        } else {
                            Log.w(TAG, "Erreur lors de l'ajout du professeur", task.getException());
                        }
                    }
                });
    }

    private void ajouterClasses(DocumentReference ecoleRef) {
        ajouterClasse(ecoleRef, "Classe 1");
        ajouterClasse(ecoleRef, "Classe 2");
    }

    private void ajouterClasse(DocumentReference ecoleRef, String nomClasse) {
        Map<String, Object> classe = new HashMap<>();
        classe.put("nom", nomClasse);
        classe.put("listeEleves", new ArrayList<DocumentReference>());
        classe.put("listeProfesseurs", new ArrayList<DocumentReference>());

        ecoleRef.collection("Classes")
                .add(classe)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Classe ajoutée avec succès !");
                            DocumentReference classeRef = task.getResult();
                            ajouterMatieres(classeRef);
                        } else {
                            Log.w(TAG, "Erreur lors de l'ajout de la classe", task.getException());
                        }
                    }
                });
    }

    private void ajouterMatieres(DocumentReference classeRef) {
        ajouterMatiere(classeRef, "Mathématiques");
        ajouterMatiere(classeRef, "Français");
    }

    private void ajouterMatiere(DocumentReference classeRef, String nomMatiere) {
        Map<String, Object> matiere = new HashMap<>();
        matiere.put("nom", nomMatiere);

        classeRef.collection("Matieres")
                .add(matiere)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Matière ajoutée avec succès !");
                            DocumentReference matiereRef = task.getResult();
                            ajouterCoursMatiere(matiereRef);
                        } else {
                            Log.w(TAG, "Erreur lors de l'ajout de la matière", task.getException());
                        }
                    }
                });
    }

    private void ajouterCoursMatiere(DocumentReference matiereRef) {
        ajouterCours(matiereRef);
    }

    private void ajouterCours(DocumentReference matiereRef) {
        Map<String, Object> cours = new HashMap<>();
        cours.put("nom", "Cours de la matière");

        matiereRef.collection("Cours")
                .add(cours)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Cours ajouté avec succès !");
                            DocumentReference coursRef = task.getResult();
                            ajouterTP(coursRef);
                            ajouterQuiz(coursRef);
                        } else {
                            Log.w(TAG, "Erreur lors de l'ajout du cours", task.getException());
                        }
                    }
                });
    }

    private void ajouterTP(DocumentReference coursRef) {
        Map<String, Object> tp = new HashMap<>();
        tp.put("description", "Description du TP");
        tp.put("date", "Date du TP");

        coursRef.collection("TPs")
                .add(tp)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "TP ajouté avec succès !");
                        } else {
                            Log.w(TAG, "Erreur lors de l'ajout du TP", task.getException());
                        }
                    }
                });
    }

    private void ajouterQuiz(DocumentReference coursRef) {
        Map<String, Object> quiz = new HashMap<>();
        quiz.put("description", "Description du quiz");
        quiz.put("date", "Date du quiz");

        coursRef.collection("Quizzes")
                .add(quiz)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Quiz ajouté avec succès !");
                            DocumentReference quizRef = task.getResult();
                            ajouterQuestionsQuiz(quizRef);
                        } else {
                            Log.w(TAG, "Erreur lors de l'ajout du quiz", task.getException());
                        }
                    }
                });
    }

    private void ajouterQuestionsQuiz(DocumentReference quizRef) {
        ajouterQuestion(quizRef, "Question 1", Arrays.asList("Réponse 1", "Réponse 2", "Réponse 3", "Réponse 4"), 2);
        ajouterQuestion(quizRef, "Question 2", Arrays.asList("Réponse 1", "Réponse 2", "Réponse 3", "Réponse 4"), 3);
    }

    private void ajouterQuestion(DocumentReference quizRef, String question, List<String> reponses, int reponseCorrecte) {
        Map<String, Object> questionMap = new HashMap<>();
        questionMap.put("question", question);
        questionMap.put("reponses", reponses);
        questionMap.put("reponseCorrecte", reponseCorrecte);

        quizRef.collection("Questions")
                .add(questionMap)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Question ajoutée avec succès !");
                        } else {
                            Log.w(TAG, "Erreur lors de l'ajout de la question", task.getException());
                        }
                    }
                });
    }

    private void ajouterSalle(DocumentReference ecoleRef) {
        Map<String, Object> salle = new HashMap<>();
        salle.put("numero", 1);

        ecoleRef.collection("Salles")
                .add(salle)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Salle ajoutée avec succès !");
                        } else {
                            Log.w(TAG, "Erreur lors de l'ajout de la salle", task.getException());
                        }
                    }
                });
    }

    private void ajouterStatistique(DocumentReference ecoleRef) {
        Map<String, Object> statistique = new HashMap<>();
        statistique.put("moyenneGenerale", 16.5);
        statistique.put("tauxReussite", 0.85);

        ecoleRef.collection("Statistiques")
                .add(statistique)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Statistique ajoutée avec succès !");
                        } else {
                            Log.w(TAG, "Erreur lors de l'ajout de la statistique", task.getException());
                        }
                    }
                });
    }
}
