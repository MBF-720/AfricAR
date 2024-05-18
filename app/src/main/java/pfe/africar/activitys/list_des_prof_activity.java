package pfe.africar.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pfe.africar.R;

public class list_des_prof_activity extends Activity {

	private View addTeacher;
	private ListView listeProf;
	private ArrayList<String> profListnom;
	private Map<String, String> profMap;
	private ArrayAdapter<String> adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_des_prof);

		listeProf = findViewById(R.id.listeProf);
		addTeacher = findViewById(R.id._bg__frame_132_ek3);

		profMap = new HashMap<>();
		profListnom = new ArrayList<>();

		String classeId = getIntent().getStringExtra("classeId");

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

						profListnom.addAll(profMap.keySet());
						adapter = new ArrayAdapter<>(list_des_prof_activity.this, android.R.layout.simple_list_item_1, profListnom);
						listeProf.setAdapter(adapter);

						listeProf.setOnItemClickListener((parent, view, position, id) -> {
							String profName = profListnom.get(position);
							String profId = profMap.get(profName);
							Intent intent = new Intent(list_des_prof_activity.this, Prof_details.class);
							intent.putExtra("profId", profId);
							startActivity(intent);
						});

						listeProf.setOnItemLongClickListener((parent, view, position, id) -> {
							String profName = profListnom.get(position);
							showDeleteConfirmationDialog(profName, position);
							return true;
						});

					} else {
						Toast.makeText(getApplicationContext(), "can't find list", Toast.LENGTH_SHORT).show();
					}
				});

		addTeacher.setOnClickListener(v -> {
			Intent intent = new Intent(list_des_prof_activity.this, add_prof_activity.class);
			startActivity(intent);
		});
	}

	private void showDeleteConfirmationDialog(String profName, int position) {
		new AlertDialog.Builder(this)
				.setTitle("Delete Confirmation")
				.setMessage("Are you sure you want to delete " + profName + "?")
				.setPositiveButton(android.R.string.yes, (dialog, which) -> deleteProfessor(profName, position))
				.setNegativeButton(android.R.string.no, null)
				.show();
	}

	private void deleteProfessor(String profName, int position) {
		String profId = profMap.get(profName);
		FirebaseFirestore db = FirebaseFirestore.getInstance();

		db.collection("Ecoles").document("Vgv1obkaHUASn7Z8rI7I")
				.collection("Professeurs").document(profId)
				.delete()
				.addOnSuccessListener(aVoid -> {
					Toast.makeText(getApplicationContext(), "Professor deleted", Toast.LENGTH_SHORT).show();
					profListnom.remove(position);
					profMap.remove(profName);
					adapter.notifyDataSetChanged();
				})
				.addOnFailureListener(e -> {
					Toast.makeText(getApplicationContext(), "Failed to delete professor", Toast.LENGTH_SHORT).show();
				});
	}
}
