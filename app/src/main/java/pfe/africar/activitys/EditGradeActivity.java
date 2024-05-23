package pfe.africar.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pfe.africar.R;
import pfe.africar.activitys.Eleve_Details;
import pfe.africar.activitys.ProfClasse;
import pfe.africar.activitys.add_student_activity;

public class EditGradeActivity extends Activity {

    private TextView listeProf, addStudent;
    private ListView listeView;
    private String ecoleId = "Vgv1obkaHUASn7Z8rI7I";
    private ArrayList<String> elevesList;
    private Map<String, String> elevesMap;
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_eleve_);

        addStudent = findViewById(R.id.add_new_student);
        listeProf = findViewById(R.id.listeProf);
        listeView = findViewById(R.id.ListView);

        elevesMap = new HashMap<>();
        elevesList = new ArrayList<>();

        String classeId = getIntent().getStringExtra("classId");
        String className = getIntent().getStringExtra("className");

        // Show the class ID in a popup dialog
        new AlertDialog.Builder(pfe.africar.activitys.EditGradeActivity.this)
                .setTitle("Class ID")
                .setMessage("ID: " + classeId)
                .setPositiveButton(android.R.string.ok, null)
                .show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Ecoles").document(ecoleId)
                .collection("Eleves").whereEqualTo("idClasse", classeId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            String nom = document.getString("nom");
                            String prenom = document.getString("prenom");
                            String id = document.getId();
                            String name = String.format("%s %s", nom, prenom);
                            elevesMap.put(name, id);
                        }

                        elevesList.addAll(elevesMap.keySet());
                        adapter = new ArrayAdapter<>(pfe.africar.activitys.EditGradeActivity.this, android.R.layout.simple_list_item_1, elevesList);
                        listeView.setAdapter(adapter);

                        listeView.setOnItemClickListener((parent, view, position, id) -> {
                            String studentName = elevesList.get(position);
                            String studentId = elevesMap.get(studentName);
                            Intent intent = new Intent(pfe.africar.activitys.EditGradeActivity.this, grade_sheet_activity.class);
                            intent.putExtra("studentId", studentId);
                            startActivity(intent);
                        });

                        listeView.setOnItemLongClickListener((parent, view, position, id) -> {
                            String studentName = elevesList.get(position);
                            return true;
                        });

                    } else {
                        Toast.makeText(getApplicationContext(), "can't find list", Toast.LENGTH_SHORT).show();
                    }
                });


    }




}