package pfe.africar.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import pfe.africar.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Ajout d'un document à la collection Personnes
        Map<String, Object> personne1 = new HashMap<>();
        personne1.put("nom", "Nom de la personne");
        personne1.put("prenom", "Prénom de la personne");
        personne1.put("email", "Email de la personne");
        personne1.put("motDePasse", "Mot de passe de la personne");

        db.collection("Personnes")
                .document("personneId1")
                .set(personne1)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Document Personnes ajouté avec succès"))
                .addOnFailureListener(e -> Log.w(TAG, "Erreur lors de l'ajout du document Personnes", e));

        // Ajout d'un document à la collection Eleves
        Map<String, Object> eleve1 = new HashMap<>();
        eleve1.put("personne", db.collection("Personnes").document("personneId1"));
        eleve1.put("notes", Arrays.asList());
        eleve1.put("moyenne", 0.0);

        db.collection("Eleves")
                .document("eleveId1")
                .set(eleve1)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Document Eleves ajouté avec succès"))
                .addOnFailureListener(e -> Log.w(TAG, "Erreur lors de l'ajout du document Eleves", e));

        // Ajout d'un document à la collection Professeurs
        Map<String, Object> professeur1 = new HashMap<>();
        professeur1.put("personne", db.collection("Personnes").document("personneId2"));
        professeur1.put("matieres", Arrays.asList());

        db.collection("Professeurs")
                .document("professeurId1")
                .set(professeur1)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Document Professeurs ajouté avec succès"))
                .addOnFailureListener(e -> Log.w(TAG, "Erreur lors de l'ajout du document Professeurs", e));

        // Ajout d'un document à la collection Admins
        Map<String, Object> admin1 = new HashMap<>();
        admin1.put("personne", db.collection("Personnes").document("personneId3"));

        db.collection("Admins")
                .document("adminId1")
                .set(admin1)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Document Admins ajouté avec succès"))
                .addOnFailureListener(e -> Log.w(TAG, "Erreur lors de l'ajout du document Admins", e));

        // Ajout d'un document à la collection Ecoles
        Map<String, Object> ecole1 = new HashMap<>();
        ecole1.put("nom", "Nom de l'école");
        ecole1.put("classes", Arrays.asList());
        ecole1.put("salles", Arrays.asList());
        ecole1.put("actualites", "");

        db.collection("Ecoles")
                .document("ecoleId1")
                .set(ecole1)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Document Ecoles ajouté avec succès"))
                .addOnFailureListener(e -> Log.w(TAG, "Erreur lors de l'ajout du document Ecoles", e));

        // Ajout d'un document à la collection Classes
        Map<String, Object> classe1 = new HashMap<>();
        classe1.put("niveau", 1);
        classe1.put("eleves", Arrays.asList());
        classe1.put("professeurs", Arrays.asList());

        db.collection("Classes")
                .document("classeId1")
                .set(classe1)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Document Classes ajouté avec succès"))
                .addOnFailureListener(e -> Log.w(TAG, "Erreur lors de l'ajout du document Classes", e));

        // Ajout d'un document à la collection Salles
        Map<String, Object> salle1 = new HashMap<>();
        salle1.put("numero", 101);

        db.collection("Salles")
                .document("salleId1")
                .set(salle1)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Document Salles ajouté avec succès"))
                .addOnFailureListener(e -> Log.w(TAG, "Erreur lors de l'ajout du document Salles", e));

        // Ajout d'un document à la collection Cours
        Map<String, Object> cours1 = new HashMap<>();
        cours1.put("intitule", "Mathématiques");
        cours1.put("matiere", db.collection("Matieres").document("matiereId1"));

        db.collection("Cours")
                .document("coursId1")
                .set(cours1)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Document Cours ajouté avec succès"))
                .addOnFailureListener(e -> Log.w(TAG, "Erreur lors de l'ajout du document Cours", e));

        // Ajout d'un document à la collection Matieres
        Map<String, Object> matiere1 = new HashMap<>();
        matiere1.put("nom", "Mathématiques");

        db.collection("Matieres")
                .document("matiereId1")
                .set(matiere1)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Document Matieres ajouté avec succès"))
                .addOnFailureListener(e -> Log.w(TAG, "Erreur lors de l'ajout du document Matieres", e));

        // Ajout d'un document à la collection TPs
        Map<String, Object> tp1 = new HashMap<>();
        tp1.put("realiteAugmentee", true);

        db.collection("TPs")
                .document("tpId1")
                .set(tp1)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Document TPs ajouté avec succès"))
                .addOnFailureListener(e -> Log.w(TAG, "Erreur lors de l'ajout du document TPs", e));

        // Ajout d'un document à la collection Quizzes
        Map<String, Object> quiz1 = new HashMap<>();
        quiz1.put("questions", Arrays.asList());

        db.collection("Quizzes")
                .document("quizId1")
                .set(quiz1)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Document Quizzes ajouté avec succès"))
                .addOnFailureListener(e -> Log.w(TAG, "Erreur lors de l'ajout du document Quizzes", e));

    }}