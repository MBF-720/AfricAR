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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import pfe.africar.R;
import pfe.africar.classes.GradeAdapter;
import pfe.africar.classes.GradeInfo;
import pfe.africar.helpers.EleveNavBar;

public class ReleveNotesActivity extends AppCompatActivity {
    private ListView gradesListView;
    private TextView studentName;
    private List<GradeInfo> gradesList;
    private GradeAdapter adapter;
    private FirebaseFirestore db;
    private double overallAverage;
    private TextView moyenne;
    private FirebaseAuth mAuth;

    private String eleveId;//="UNfQ0AtYQugZ8eXrENVe"
    private String ecoleId = "Vgv1obkaHUASn7Z8rI7I";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_releve_notes);

        gradesListView = findViewById(R.id.gradesListView);
        studentName = findViewById(R.id.studentName);
        moyenne = findViewById(R.id.moyenne);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();  // Initialize Firebase Auth

        gradesList = new ArrayList<>();
        adapter = new GradeAdapter(this, gradesList);
        gradesListView.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        EleveNavBar.setupBottomNavigation(this, bottomNavigationView);

        checkUserAuthentication();  // Check user authentication and proceed

        getStudentData();
       // loadGrades();
        //calculateOverallAverage();

        gradesListView.setOnItemClickListener((parent, view, position, id) -> {
            GradeInfo selectedGradeInfo = gradesList.get(position);
            Intent intent = new Intent(ReleveNotesActivity.this, Note_detail.class);
            intent.putExtra("eleveId", eleveId);
            intent.putExtra("ecoleId", ecoleId);
            intent.putExtra("matiereId", selectedGradeInfo.getSubjectName());
            startActivity(intent);
        });


    }

    private void checkUserAuthentication() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // Redirect to login activity
            Toast.makeText(ReleveNotesActivity.this, "user not loged in " , Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loadGrades() {
        if (eleveId != null) {
            db.collection("Ecoles").document(ecoleId).collection("Eleves").document(eleveId).collection("notes")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                             //   gradesList.clear();
                                for (DocumentSnapshot document : task.getResult()) {
                                    String matiereId = document.getId();
                                   // Toast.makeText(ReleveNotesActivity.this, "matier id= " +matiereId, Toast.LENGTH_SHORT).show();

                                    double averageGrade = document.getDouble("moyenne");
                                    gradesList.add(new GradeInfo(matiereId, averageGrade));
                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(ReleveNotesActivity.this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else {
            Toast.makeText(ReleveNotesActivity.this, "eleve id null " , Toast.LENGTH_SHORT).show();

        }
    }

    private void getStudentData() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String email = currentUser.getEmail();
            if (email != null) {
                db.collection("Ecoles").document(ecoleId).collection("Eleves")
                        .whereEqualTo("email", email)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                if (task.getResult() != null && !task.getResult().isEmpty()) {
                                    DocumentSnapshot document = task.getResult().getDocuments().get(0);
                                    eleveId = document.getId();
                                    Toast.makeText(this, "id = "+eleveId, Toast.LENGTH_SHORT).show();
                                     loadGrades();
                                    calculateOverallAverage();
                                    String nom = document.getString("nom");
                                    String prenom = document.getString("prenom");
                                    if (nom != null && prenom != null) {
                                        studentName.setText(nom + " " + prenom);
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
            } else {
                Toast.makeText(this, "User email not found", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }

    private void calculateOverallAverage() {
        if (eleveId != null) {
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


                                }

                                Toast.makeText(ReleveNotesActivity.this, "Overall Average: " + overallAverage, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ReleveNotesActivity.this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }




}
