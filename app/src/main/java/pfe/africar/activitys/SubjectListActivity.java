// SubjectListActivity.java
package pfe.africar.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import pfe.africar.R;
import pfe.africar.helpers.EleveNavBar;

public class SubjectListActivity extends AppCompatActivity {

    private ListView subjectListView;
    private ArrayAdapter<String> adapter;
    private List<String> subjects = new ArrayList<>();
    private List<String> subjectIds = new ArrayList<>();
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String ecoleId;
    private String classId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);

        subjectListView = findViewById(R.id.subjectListView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subjects);
        subjectListView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        EleveNavBar.setupBottomNavigation(this, bottomNavigationView);

        fetchEcoleIdAndClassId();

        subjectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String subjectId = subjectIds.get(position);
                Intent intent = new Intent(SubjectListActivity.this, CourseListActivity.class);
                intent.putExtra("ecoleId", ecoleId);
                intent.putExtra("classId", classId);
                intent.putExtra("subjectId", subjectId);
                startActivity(intent);
            }
        });
    }

    private void fetchEcoleIdAndClassId() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = currentUser.getUid();
        db.collection("Users").document(uid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            ecoleId = documentSnapshot.getString("idEcole");
                            if (ecoleId != null) {
                                fetchClassId(currentUser.getEmail(), ecoleId);
                            } else {
                                Toast.makeText(SubjectListActivity.this, "Ecole ID not found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SubjectListActivity.this, "Failed to fetch ecole ID", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void fetchClassId(String email, String ecoleId) {
        db.collection("Ecoles").document(ecoleId).collection("Eleves")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                classId = document.getString("idClasse");
                                if (classId != null) {
                                    loadSubjects();
                                } else {
                                    Toast.makeText(SubjectListActivity.this, "Class ID not found", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(SubjectListActivity.this, "Error fetching class ID: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void loadSubjects() {
        db.collection("Ecoles").document(ecoleId).collection("Classes").document(classId).collection("Matieres")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                subjects.add(document.getString("nom"));
                                subjectIds.add(document.getId());
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(SubjectListActivity.this, "Failed to fetch subjects", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
