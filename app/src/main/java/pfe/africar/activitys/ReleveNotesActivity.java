package pfe.africar.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pfe.africar.R;
import pfe.africar.classes.GradeAdapter;
import pfe.africar.classes.GradeInfo;

public class ReleveNotesActivity extends AppCompatActivity {
    private ListView gradesListView;

    private TextView studentName;

    private List<GradeInfo> gradesList;
    private GradeAdapter adapter;
    private FirebaseFirestore db;
    private double overallAverage;
    private TextView moyenne;
    private FirebaseAuth mAuth;


//	private String selectedEleveId = "UNfQ0AtYQugZ8eXrENVe";

    private String eleveId ;
    private String ecoleId="Vgv1obkaHUASn7Z8rI7I";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_releve_notes);

        gradesListView = findViewById(R.id.gradesListView);
        studentName = findViewById(R.id.studentName);
        moyenne = findViewById(R.id.moyenne);

        db = FirebaseFirestore.getInstance();
        gradesList = new ArrayList<>();
        adapter = new GradeAdapter(this, gradesList);
        gradesListView.setAdapter(adapter);


        getStudentData();

        loadGrades();





        gradesListView.setOnItemClickListener((parent, view, position, id) -> {
            GradeInfo selectedGradeInfo = gradesList.get(position);
            Intent intent = new Intent(ReleveNotesActivity.this, Note_detail.class);
            intent.putExtra("eleveId", eleveId);
            intent.putExtra("ecoleId", ecoleId);
            intent.putExtra("matiereId", selectedGradeInfo.getSubjectName());
            startActivity(intent);
        });

        calculateOverallAverage();


    }
    private void loadGrades() {
        db.collection("Ecoles").document(ecoleId).collection("Eleves").document(eleveId).collection("notes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            gradesList.clear();
                            for (DocumentSnapshot document : task.getResult()) {
                                String matiereId = document.getId();
                                double averageGrade = document.getDouble("moyenne"); // Assuming you have an "average" field
                                gradesList.add(new GradeInfo(matiereId, averageGrade));
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(ReleveNotesActivity.this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void getStudentData() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String email = currentUser.getEmail();
        if (email == null) {
            Toast.makeText(this, "User email not found", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("Ecoles").document(ecoleId).collection("Eleves")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null && !task.getResult().isEmpty()) {
                            DocumentSnapshot document = task.getResult().getDocuments().get(0);
                             eleveId = document.getId();
                            String nom = document.getString("nom");
                            String prenom = document.getString("prenom");
                            if (nom != null && prenom != null) {
                                studentName.setText(nom + " " + prenom);
                                // You can now use eleveId for other operations as needed
                            } else {
                                Toast.makeText(this, "Nom or prenom field is empty.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "No such document.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Failed to get document: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void calculateOverallAverage() {
        db.collection("Ecoles").document(ecoleId).collection("Eleves").document(eleveId).collection("notes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            double totalWeightedSum = 0.0;
                            double totalCoefficientSum = 0.0;

                            for (DocumentSnapshot document : task.getResult()) {
                                Double moyenne = document.getDouble("moyenne");
                                Double matiereCoeff = document.getDouble("matiereCoeff");

                                if (moyenne != null && matiereCoeff != null) {
                                    totalWeightedSum += moyenne * matiereCoeff;
                                    totalCoefficientSum += matiereCoeff;
                                }
                            }

                            overallAverage = 0.0;
                            if (totalCoefficientSum != 0) {
                                overallAverage = totalWeightedSum / totalCoefficientSum;
                                String overallAverageStr = String.format("%.2f", overallAverage);
                                moyenne.setText(overallAverageStr);

                                // Update the student's overall average in the database
                                updateStudentMoyenne(overallAverage, eleveId);
                            }

                            // You can now use overallAverage as needed, for example:
                            Toast.makeText(ReleveNotesActivity.this, "Overall Average: " + overallAverage, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ReleveNotesActivity.this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateStudentMoyenne(double newMoyenne, String eleveId) {
        DocumentReference studentDocRef = db.collection("Ecoles").document(ecoleId).collection("Eleves").document(eleveId);

        // Create a map to hold the new value for the 'moyenne' field
        Map<String, Object> updates = new HashMap<>();
        updates.put("moyenne", newMoyenne);

        // Update the document
        studentDocRef.update(updates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Update successful
                            Toast.makeText(ReleveNotesActivity.this, "Moyenne updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            // Update failed
                            Toast.makeText(ReleveNotesActivity.this, "Failed to update moyenne: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }




    @Override
    protected void onResume() {
        super.onResume();
        loadGrades(); // Reload grades when the activity resumes
    }
}