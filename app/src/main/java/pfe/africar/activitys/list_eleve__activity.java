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

public class list_eleve__activity extends Activity {

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
		new AlertDialog.Builder(list_eleve__activity.this)
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
						adapter = new ArrayAdapter<>(list_eleve__activity.this, android.R.layout.simple_list_item_1, elevesList);
						listeView.setAdapter(adapter);

						listeView.setOnItemClickListener((parent, view, position, id) -> {
							String studentName = elevesList.get(position);
							String studentId = elevesMap.get(studentName);
							Intent intent = new Intent(list_eleve__activity.this, Eleve_Details.class);
							intent.putExtra("studentId", studentId);
							startActivity(intent);
						});

						listeView.setOnItemLongClickListener((parent, view, position, id) -> {
							String studentName = elevesList.get(position);
							showDeleteConfirmationDialog(studentName, position);
							return true;
						});

					} else {
						Toast.makeText(getApplicationContext(), "can't find list", Toast.LENGTH_SHORT).show();
					}
				});

		addStudent.setOnClickListener(v -> {
			Intent intent = new Intent(list_eleve__activity.this, add_student_activity.class);
			startActivity(intent);
		});

		listeProf.setOnClickListener(v -> {
			Intent intent = new Intent(list_eleve__activity.this, ProfClasse.class);
			intent.putExtra("classeId", classeId);
			intent.putExtra("className", className);
			startActivity(intent);
		});
	}

	private void showDeleteConfirmationDialog(String studentName, int position) {
		new AlertDialog.Builder(this)
				.setTitle("Delete Confirmation")
				.setMessage("Are you sure you want to delete " + studentName + "?")
				.setPositiveButton(android.R.string.yes, (dialog, which) -> deleteStudent(studentName, position))
				.setNegativeButton(android.R.string.no, null)
				.show();
	}

	private void deleteStudent(String studentName, int position) {
		String studentId = elevesMap.get(studentName);
		FirebaseFirestore db = FirebaseFirestore.getInstance();

		db.collection("Ecoles").document(ecoleId)
				.collection("Eleves").document(studentId)
				.delete()
				.addOnSuccessListener(aVoid -> {
					Toast.makeText(getApplicationContext(), "Student deleted", Toast.LENGTH_SHORT).show();
					elevesList.remove(position);
					elevesMap.remove(studentName);
					adapter.notifyDataSetChanged();
				})
				.addOnFailureListener(e -> {
					Toast.makeText(getApplicationContext(), "Failed to delete student", Toast.LENGTH_SHORT).show();
				});
	}
}
