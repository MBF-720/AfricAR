
package pfe.africar.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import pfe.africar.R;

public class prof_note_listeDeClasse extends AppCompatActivity {

    private ListView listViewStudents;
    private ArrayAdapter<String> adapter;
    private List<String> studentNames;
    private List<String> studentIds;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_note_liste_de_classe);

        listViewStudents = findViewById(R.id.listViewStudents);
        studentNames = new ArrayList<>();
        studentIds = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentNames);
        listViewStudents.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        String classId = getIntent().getStringExtra("CLASS_ID");
        if (classId != null) {
            fetchStudents(classId);
        } else {
            Toast.makeText(this, "Class ID not found", Toast.LENGTH_SHORT).show();
        }

        listViewStudents.setOnItemClickListener((parent, view, position, id) -> {
            String studentId = studentIds.get(position);
            Intent intent = new Intent(prof_note_listeDeClasse.this, Prof_addNote.class);
            intent.putExtra("STUDENT_ID", studentId);
            startActivity(intent);
        });
    }

    private void fetchStudents(String classId) {
        db.collection("Eleves")
                .whereEqualTo("idClasse", classId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Toast.makeText(prof_note_listeDeClasse.this, "Failed to fetch students", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        studentNames.clear();
                        studentIds.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            String studentName = doc.getString("nom");
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
}
