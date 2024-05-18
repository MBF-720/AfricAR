package pfe.africar.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import pfe.africar.R;

public class ProfClasse extends AppCompatActivity {

    private TextView className;
    private View addTeacher;
    private ListView listeProf;

    private static final String TAG = "ProfClasse";
    private ArrayAdapter<String> adapter;
    private List<String> profNames;
    private List<String> profIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_classe);

        listeProf = findViewById(R.id.listeProf);
        addTeacher = findViewById(R.id._bg__frame_132_ek3);
        className = findViewById(R.id.classroom);

        String classId = getIntent().getStringExtra("classeId");
        String classNameText = getIntent().getStringExtra("className");

        className.setText(classNameText);

        if (classId == null || classId.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Class ID is missing", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Class ID is null or empty");
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference classRef = db.collection("Ecoles").document("Vgv1obkaHUASn7Z8rI7I")
                .collection("Classes").document(classId);

        classRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    profIds = (List<String>) document.get("listeProfesseurs");
                    if (profIds != null && !profIds.isEmpty()) {
                        fetchAndDisplayProfessors(profIds, classId);
                    } else {
                        Toast.makeText(getApplicationContext(), "No professors found for this class", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Class document does not exist", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Class document does not exist");
                }
            } else {
                Toast.makeText(getApplicationContext(), "Failed to get class document", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failed to get class document", task.getException());
            }
        });

        addTeacher.setOnClickListener(v -> {
            Intent intent = new Intent(ProfClasse.this, ajouter_profToClasse.class);
            intent.putExtra("classId", classId);
            startActivity(intent);
        });
    }

    private void fetchAndDisplayProfessors(List<String> profIds, String classId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        profNames = new ArrayList<>();
        for (String profId : profIds) {
            DocumentReference profRef = db.collection("Ecoles").document("Vgv1obkaHUASn7Z8rI7I")
                    .collection("Professeurs").document(profId);
            profRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String nom = document.getString("nom");
                        String prenom = document.getString("prenom");
                        String fullName = String.format("%s %s", nom, prenom);
                        profNames.add(fullName);

                        // Update the ListView after fetching each professor
                        adapter = new ArrayAdapter<>(ProfClasse.this, android.R.layout.simple_list_item_1, profNames);
                        listeProf.setAdapter(adapter);

                        listeProf.setOnItemLongClickListener((parent, view, position, id) -> {
                            String selectedProfId = profIds.get(position);
                            showDeleteConfirmationDialog(selectedProfId, classId, position);
                            return true;
                        });
                    } else {
                        Log.e(TAG, "Professor document does not exist for ID: " + profId);
                    }
                } else {
                    Log.e(TAG, "Failed to get professor document for ID: " + profId, task.getException());
                }
            });
        }
    }

    private void showDeleteConfirmationDialog(String profId, String classId, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Professor")
                .setMessage("Are you sure you want to delete this professor from the class?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> deleteProfessor(profId, classId, position))
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deleteProfessor(String profId, String classId, int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Remove professor from class document
        DocumentReference classRef = db.collection("Ecoles").document("Vgv1obkaHUASn7Z8rI7I")
                .collection("Classes").document(classId);
        classRef.update("listeProfesseurs", FieldValue.arrayRemove(profId))
                .addOnSuccessListener(aVoid -> {
                    // Remove class from professor document
                    DocumentReference profRef = db.collection("Ecoles").document("Vgv1obkaHUASn7Z8rI7I")
                            .collection("Professeurs").document(profId);
                    profRef.update("idClasses", FieldValue.arrayRemove(classId))
                            .addOnSuccessListener(aVoid1 -> {
                                // Update ListView
                                profNames.remove(position);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), "Professor removed from class successfully", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Failed to update professor", Toast.LENGTH_SHORT).show());
                })
                .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Failed to update class", Toast.LENGTH_SHORT).show());
    }
}
