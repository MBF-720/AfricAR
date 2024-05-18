package pfe.africar.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pfe.africar.R;

public class ajouter_profToClasse extends AppCompatActivity {

    private View addTeacher;
    private ListView listeProf;

    private String  profName,profId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_prof_to_classe);

        listeProf = findViewById(R.id.listeProf);
        addTeacher = findViewById(R.id._bg__frame_132_ek3);

        Map<String, String> profMap = new HashMap<>();

        // Retrieve the classeId from the intent
        String classeId = getIntent().getStringExtra("classId");

        if (classeId == null || classeId.isEmpty()) {
            Toast.makeText(this, "Class ID is missing", Toast.LENGTH_SHORT).show();


        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Ecoles").document("Vgv1obkaHUASn7Z8rI7I")
                .collection("Professeurs")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            String nom = document.getString("nom");
                            String prenom = document.getString("prenom");
                            String id = document.getId();
                            String name = String.format("%s %s", nom, prenom);
                            profMap.put(name, id);
                        }

                        // Create a list of professor names from the map
                        ArrayList<String> profListnom = new ArrayList<>(profMap.keySet());

                        // Create an adapter to bind the list of professor names to the ListView
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(ajouter_profToClasse.this, android.R.layout.simple_list_item_1, profListnom);

                        // Set the adapter for the ListView
                        listeProf.setAdapter(adapter);

                        listeProf.setOnItemClickListener((parent, view, position, id) -> {
                             profName = profListnom.get(position);
                             profId = profMap.get(profName);





                            // Update the professor document
                            DocumentReference profRef = db.collection("Ecoles").document("Vgv1obkaHUASn7Z8rI7I")
                                    .collection("Professeurs").document(profId);
                            profRef.update("idClasses", FieldValue.arrayUnion(classeId))
                                    .addOnSuccessListener(aVoid -> {
                                        // Update the class document
                                        DocumentReference classRef = db.collection("Ecoles").document("Vgv1obkaHUASn7Z8rI7I")
                                                .collection("Classes").document(classeId);
                                        classRef.update("listeProfesseurs", FieldValue.arrayUnion(profId))
                                                .addOnSuccessListener(aVoid1 -> {
                                                    Toast.makeText(getApplicationContext(), "Professor added to class successfully", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                })
                                                .addOnFailureListener(e -> {
                                                    Toast.makeText(getApplicationContext(), "Failed to update class", Toast.LENGTH_SHORT).show();
                                                });
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(getApplicationContext(), "Failed to update professor", Toast.LENGTH_SHORT).show();
                                    });
                        });

                    } else {
                        Toast.makeText(getApplicationContext(), "Can't find list", Toast.LENGTH_SHORT).show();
                    }
                });

        addTeacher.setOnClickListener(v -> {
            Intent intent = new Intent(ajouter_profToClasse.this, add_prof_activity.class);
            startActivity(intent);
        });
    }
}
