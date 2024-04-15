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

        // Ajouter une collection "Ecoles"
        ajouterEcoles();
    }

    // Méthode pour ajouter des écoles
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
                            ajouterPersonnes(ecoleRef);
                        } else {
                            Log.w(TAG, "Erreur lors de l'ajout de l'école", task.getException());
                        }
                    }
                });
    }

    // Méthode pour ajouter des collections "Personnes" pour chaque école
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
                .document("personneId1")
                .set(personne)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Personne ajoutée avec succès !");
                            ajouterEleve(ecoleRef);
                            ajouterAdmin(ecoleRef);
                            ajouterProfesseur(ecoleRef);
                            ajouterClasse(ecoleRef);
                            ajouterMatiere(ecoleRef);
                            ajouterSalle(ecoleRef);
                            ajouterStatistique(ecoleRef);
                        } else {
                            Log.w(TAG, "Erreur lors de l'ajout de la personne", task.getException());
                        }
                    }
                });
    }

    // Méthode utilitaire pour créer un objet élève
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

    // Méthode utilitaire pour créer un objet admin
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

    // Méthode utilitaire pour créer un objet professeur
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

    // Méthode utilitaire pour créer un objet classe
    private void ajouterClasse(DocumentReference ecoleRef) {
        Map<String, Object> classe = new HashMap<>();
        classe.put("nom", "Nom de la classe");
        classe.put("professeurPrincipal", "professeurId1");
        classe.put("eleves", Arrays.asList("eleveId1", "eleveId2"));

        ecoleRef.collection("Classes")
                .add(classe)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Classe ajoutée avec succès !");
                        } else {
                            Log.w(TAG, "Erreur lors de l'ajout de la classe", task.getException());
                        }
                    }
                });
    }

    // Méthode utilitaire pour créer un objet matière
    private void ajouterMatiere(DocumentReference ecoleRef) {
        Map<String, Object> matiere = new HashMap<>();
        matiere.put("nom", "Nom de la matière");

        ecoleRef.collection("Matieres")
                .add(matiere)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Matière ajoutée avec succès !");
                        } else {
                            Log.w(TAG, "Erreur lors de l'ajout de la matière", task.getException());
                        }
                    }
                });
    }

    // Méthode utilitaire pour créer un objet salle
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

    // Méthode utilitaire pour créer un objet statistique
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
