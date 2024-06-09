package pfe.africar.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import pfe.africar.R;
import pfe.africar.helpers.ProfNavBar;

public class prof_class_list extends AppCompatActivity {

    private ListView listViewClasses;
    private ArrayAdapter<String> adapter;
    private List<String> classNames;
    private List<String> classIds;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_class_list);

        listViewClasses = findViewById(R.id.listView);
        classNames = new ArrayList<>();
        classIds = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classNames);
        listViewClasses.setAdapter(adapter);


        //the nav bar code
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        ProfNavBar.setupBottomNavigation(this, bottomNavigationView);


        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        fetchProfessorDetails();

        listViewClasses.setOnItemClickListener((parent, view, position, id) -> {
            String classId = classIds.get(position);
            Intent intent = new Intent(prof_class_list.this, prof_note_listeDeClasse.class);
            intent.putExtra("CLASS_ID", classId);
            startActivity(intent);
        });
    }

    private void fetchProfessorDetails() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = currentUser.getUid();
        db.collection("Users").document(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String ecoleId = document.getString("idEcole");
                    if (ecoleId != null) {
                        fetchProfessorClasses(ecoleId, uid);
                    } else {
                        Toast.makeText(this, "Ecole ID not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "User document not found", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Failed to fetch user details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchProfessorClasses(String ecoleId, String profUid) {
        db.collection("Ecoles").document(ecoleId)
                .collection("Professeurs")
                .whereEqualTo("uid", profUid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            List<String> classIds = (List<String>) document.get("idClasses");
                            if (classIds != null && !classIds.isEmpty()) {
                                fetchClassNames(ecoleId, classIds);
                            } else {
                                Toast.makeText(this, "No classes found for professor", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(this, "Failed to fetch professor details", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fetchClassNames(String ecoleId, List<String> classIds) {
        classNames.clear();
        this.classIds.clear();
        for (String classId : classIds) {
            db.collection("Ecoles").document(ecoleId)
                    .collection("Classes").document(classId)
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String className = document.getString("nom");
                                if (className != null) {
                                    classNames.add(className);
                                    this.classIds.add(classId);
                                    adapter.notifyDataSetChanged();
                                }
                            } else {
                                Toast.makeText(this, "Class document not found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Failed to fetch class details", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
