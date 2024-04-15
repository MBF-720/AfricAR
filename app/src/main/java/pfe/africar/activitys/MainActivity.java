package pfe.africar.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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

        // Appeler la méthode pour créer une nouvelle école
        creerNouvelleEcole();
    }

    private void creerNouvelleEcole() {
        ajouterEcole("Nouvelle Ecole", "Actualités de la nouvelle école");
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
                            ajouterPersonnes(task.getResult());
                        } else {
                            Log.w(TAG, "Erreur lors de l'ajout de l'école", task.getException());
                        }
                    }
                });
    }

    private void ajouterPersonnes(DocumentReference ecoleRef) {
        ajouterPersonne(ecoleRef, "Nom de la personne 1", "Prénom de la personne 1", "Email de la personne 1", "Mot de passe de la personne 1");
    }

    private void ajouterPersonne(DocumentReference ecoleRef, String nom, String prenom, String email, String motDePasse) {
        Map<String, Object> personne = new HashMap<>();
        personne.put("nom", nom);
        personne.put("prenom", prenom);
        personne.put("email", email);
        personne.put("motDePasse", motDePasse);

        ecoleRef.collection("Personnes")
                .add(personne)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Personne ajoutée avec succès !");
                            ajouterEleve(ecoleRef);
                            ajouterAdmin(ecoleRef);
                            ajouterProfesseur(ecoleRef);
                        } else {
                            Log.w(TAG, "Erreur lors de l'ajout de la personne", task.getException());
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
                            ajouterClasse(ecoleRef);
                        } else {
                            Log.w(TAG, "Erreur lors de l'ajout du professeur", task.getException());
                        }
                    }
                });
    }

    private void ajouterClasse(DocumentReference ecoleRef) {
        Map<String, Object> classe = new HashMap<>();
        classe.put("nom", "Nom de la classe");

        ecoleRef.collection("Classes")
                .add(classe)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Classe ajoutée avec succès !");
                            DocumentReference classeRef = task.getResult();
                            ajouterMatiere(classeRef);
                            ajouterMatiere(classeRef); // Ajouter une deuxième matière pour la classe
                        } else {
                            Log.w(TAG, "Erreur lors de l'ajout de la classe", task.getException());
                        }
                    }
                });
    }

    private void ajouterMatiere(DocumentReference classeRef) {
        Map<String, Object> matiere = new HashMap<>();
        matiere.put("nom", "Nom de la matière");

        classeRef.collection("Matieres")
                .add(matiere)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Matière ajoutée avec succès !");
                            DocumentReference matiereRef = task.getResult();
                            ajouterCours(matiereRef);
                        } else {
                            Log.w(TAG, "Erreur lors de l'ajout de la matière", task.getException());
                        }
                    }
                });
    }

    private void ajouterCours(DocumentReference matiereRef) {
        Map<String, Object> cours = new HashMap<>();
        cours.put("nom", "Nom du cours");

        matiereRef.collection("Cours")
                .add(cours)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Cours ajouté avec succès !");
                            ajouterTP(task.getResult());
                            ajouterQuiz(task.getResult());
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

        coursRef.collection("Quizzes")
                .add(quiz)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Quiz ajouté avec succès !");
                            ajouterQuestion(task.getResult());
                        } else {
                            Log.w(TAG, "Erreur lors de l'ajout du quiz", task.getException());
                        }
                    }
                });
    }

    private void ajouterQuestion(DocumentReference quizRef) {
        Map<String, Object> question = new HashMap<>();
        question.put("enonce", "Enoncé de la question ?");
        question.put("reponses", Arrays.asList("Réponse 1", "Réponse 2", "Réponse 3", "Réponse 4"));
        question.put("reponseCorrecte", "Réponse 1");

        quizRef.collection("Questions")
                .add(question)
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
}
