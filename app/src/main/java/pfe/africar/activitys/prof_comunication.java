


package pfe.africar.activitys;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import pfe.africar.R;

public class prof_comunication extends AppCompatActivity {

    private EditText etNom, etPrenom, etTitre, etDescription;
    private Button btnSave;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_comunication);

        etNom = findViewById(R.id.et_nom);
        etPrenom = findViewById(R.id.et_prenom);
        etTitre = findViewById(R.id.et_titre);
        etDescription = findViewById(R.id.et_description);
        btnSave = findViewById(R.id.btn_save);

        db = FirebaseFirestore.getInstance();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveReclamation();
            }
        });
    }

    private void saveReclamation() {
        String nom = etNom.getText().toString().trim();
        String prenom = etPrenom.getText().toString().trim();
        String titre = etTitre.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (TextUtils.isEmpty(nom) || TextUtils.isEmpty(prenom) || TextUtils.isEmpty(titre) || TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> reclamation = new HashMap<>();
        reclamation.put("nom", nom);
        reclamation.put("prenom", prenom);
        reclamation.put("titre", titre);
        reclamation.put("description", description);
        reclamation.put("etat", "En Attente");

        db.collection("Ecoles").document("Vgv1obkaHUASn7Z8rI7I") // Replace with your ecoleId if dynamic
                .collection("Reclamations")
                .add(reclamation)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(prof_comunication.this, "Reclamation added successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(prof_comunication.this, "Error adding reclamation", Toast.LENGTH_SHORT).show();
                });
    }
}
