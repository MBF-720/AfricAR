package pfe.africar.activitys;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import pfe.africar.R;
import pfe.africar.helpers.ProfNavBar;

public class prof_eleves extends AppCompatActivity {

    private ListView listViewStudents;
    private ArrayAdapter<String> adapter;
    private List<String> studentNames;
    private List<String> studentIds;
    private FirebaseFirestore db;

    private String ecoleId = "Vgv1obkaHUASn7Z8rI7I"; // Replace with actual ecole ID
    private String classId;
    private String profName;
    private String profSurname;
    private String className;
    private String profId;
    private String matiereName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profeleves);

        listViewStudents = findViewById(R.id.listViewStudents);
        studentNames = new ArrayList<>();
        studentIds = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentNames);
        listViewStudents.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        ProfNavBar.setupBottomNavigation(this, bottomNavigationView);

        db = FirebaseFirestore.getInstance();

        classId = getIntent().getStringExtra("CLASS_ID");
        if (classId != null) {
            fetchClassDetails(classId);
            fetchStudents(classId);
        } else {
            Toast.makeText(this, "Class ID not found", Toast.LENGTH_SHORT).show();
        }

        getCurrentUserDetails();

        listViewStudents.setOnItemLongClickListener((parent, view, position, id) -> {
            String studentId = studentIds.get(position);
            showAttendanceDialog(studentId);
            return true;
        });
    }

    private void getCurrentUserDetails() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            profId = currentUser.getEmail();
            fetchProfessorDetails(profId);
            getProfessorMatiere(profId); // Fetch the professor's subject
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchClassDetails(String classId) {
        db.collection("Ecoles").document(ecoleId).collection("Classes").document(classId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        className = documentSnapshot.getString("nom");
                    } else {
                        Toast.makeText(this, "Class details not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error fetching class details", Toast.LENGTH_SHORT).show());
    }

    private void fetchProfessorDetails(String uid) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Ecoles").document("Vgv1obkaHUASn7Z8rI7I")  // replace with your actual ecoleId
                .collection("Professeurs").whereEqualTo("uid", uid).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                             profName = documentSnapshot.getString("nom");
                             profSurname = documentSnapshot.getString("prenom");


                        }
                    } else {
                        Toast.makeText(this, "Professor details not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FetchProfessorDetails", "Error fetching professor details", e);
                    Toast.makeText(this, "Error fetching professor details", Toast.LENGTH_SHORT).show();
                });
    }


    private void fetchStudents(String classId) {
        db.collection("Ecoles").document(ecoleId).collection("Eleves")
                .whereEqualTo("idClasse", classId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Toast.makeText(prof_eleves.this, "Failed to fetch students", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        studentNames.clear();
                        studentIds.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            String studentName = doc.getString("nom") + " " + doc.getString("prenom");
                            String studentId = doc.getId();
                            if (studentName != null && studentId != null) {
                                studentNames.add(studentName);
                                studentIds.add(studentId);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void showAttendanceDialog(String studentId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mark Attendance");
        builder.setMessage("Mark attendance for the selected student:");

        builder.setPositiveButton("Present", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveAttendance(studentId, true);
            }
        });

        builder.setNegativeButton("Absent", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveAttendance(studentId, false);
            }
        });

        builder.show();
    }

    private void getProfessorMatiere(String profUid) {
        db.collection("Ecoles").document(ecoleId)
                .collection("Professeurs")
                .whereEqualTo("uid", profUid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            matiereName = document.getString("matiere");
                            break;
                        }
                    } else {
                        Toast.makeText(prof_eleves.this, "Failed to fetch professor details", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveAttendance(String studentId, boolean isPresent) {
        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());
        String studentFullName = studentNames.get(studentIds.indexOf(studentId));
        String[] studentNameParts = studentFullName.split(" ");
        String studentLastName = studentNameParts[0];
        String studentFirstName = studentNameParts.length > 1 ? studentNameParts[1] : "";

        Map<String, Object> attendance = new HashMap<>();
        attendance.put("classe", className);
        attendance.put("date", date);
        attendance.put("matiere", matiereName); // Use the fetched matiereName
        attendance.put("nom", studentLastName);
        attendance.put("prenom", studentFirstName);
        attendance.put("nom du prof", profName + " " + profSurname);
        attendance.put("isPresent", isPresent);

        db.collection("Ecoles").document(ecoleId).collection("Reclamations").document("Absences")
                .collection("Liste absence").document()
                .set(attendance)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Attendance marked successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Error marking attendance: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
