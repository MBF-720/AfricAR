package pfe.africar.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import pfe.africar.R;
import pfe.africar.classes.Note;

public class AjouterMatiere extends AppCompatActivity {

    private EditText matiereTextView;
    private EditText controleEditText, controleCoefEditText;
    private EditText synthezeEditText, synthezeCoefEditText;
    private EditText tpEditText, tpCoefEditText;
    private EditText oraleEditText, oraleCoefEditText;
    private EditText moyenneEditText;
    private TextView eleveNameTextView;
    private Button saveButton;

    private FirebaseFirestore db;

    private String ecoleId = "Vgv1obkaHUASn7Z8rI7I";
    private String eleveId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grade2);

        db = FirebaseFirestore.getInstance();

        matiereTextView = findViewById(R.id.matiereTextView);
        controleEditText = findViewById(R.id.controleEditText);
        controleCoefEditText = findViewById(R.id.controleCoefEditText);
        synthezeEditText = findViewById(R.id.synthezeEditText);
        synthezeCoefEditText = findViewById(R.id.synthezeCoefEditText);
        tpEditText = findViewById(R.id.tpEditText);
        tpCoefEditText = findViewById(R.id.tpCoefEditText);
        oraleEditText = findViewById(R.id.oraleEditText);
        oraleCoefEditText = findViewById(R.id.oraleCoefEditText);
        moyenneEditText = findViewById(R.id.moyenneEditText);
        saveButton = findViewById(R.id.saveButton);
        eleveNameTextView=findViewById(R.id.eleveNameTextView);

       eleveId = getIntent().getStringExtra("eleveId");

        getStudentName(ecoleId, eleveId);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGrade();
                finish();
            }
        });
    }

    private void saveGrade() {
        try {
            String matiere = matiereTextView.getText().toString().trim();
            double controle = Double.parseDouble(controleEditText.getText().toString().trim());
            double controleCoef = Double.parseDouble(controleCoefEditText.getText().toString().trim());
            double syntheze = Double.parseDouble(synthezeEditText.getText().toString().trim());
            double synthezeCoef = Double.parseDouble(synthezeCoefEditText.getText().toString().trim());
            double tp = Double.parseDouble(tpEditText.getText().toString().trim());
            double tpCoef = Double.parseDouble(tpCoefEditText.getText().toString().trim());
            double orale = Double.parseDouble(oraleEditText.getText().toString().trim());
            double oraleCoef = Double.parseDouble(oraleCoefEditText.getText().toString().trim());

            if (matiere.isEmpty()) {
                Toast.makeText(AjouterMatiere.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            Note note = new Note(matiere, controle, syntheze, tp, orale, controleCoef, synthezeCoef, tpCoef, oraleCoef);
            note.setMoyenne(note.calculerMoyenne());

            DocumentReference docRef = db.collection("Ecoles").document(ecoleId)
                    .collection("Eleves").document(eleveId)
                    .collection("notes").document(matiere);

            docRef.set(note)
                    .addOnSuccessListener(aVoid -> Toast.makeText(AjouterMatiere.this, "Note enregistrée avec succès", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(AjouterMatiere.this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show());
        } catch (NumberFormatException e) {
            Toast.makeText(AjouterMatiere.this, "Veuillez entrer des valeurs numériques valides", Toast.LENGTH_SHORT).show();
        }
    }

    private void getStudentName(String ecoleId, String eleveId) {
        DocumentReference docRef = db.collection("Ecoles").document(ecoleId).collection("Eleves").document(eleveId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String nom = document.getString("nom");
                        String prenom = document.getString("prenom");
                        if (nom != null && prenom != null) {
                            eleveNameTextView.setText(nom + " " + prenom);
                        } else {
                            Toast.makeText(AjouterMatiere.this, "Nom or prenom field is empty.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AjouterMatiere.this, "No such document.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AjouterMatiere.this, "Failed to get document: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
