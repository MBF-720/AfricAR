package pfe.africar.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import pfe.africar.R;
import pfe.africar.helpers.ProfNavBar;

public class prof_classes_activity extends AppCompatActivity {

	private FirebaseAuth mAuth;
	private FirebaseFirestore db;
	private ListView lvClasses;
	private TextView tvNoClasses;
	private ArrayAdapter<String> adapter;
	private List<String> classNames;
	private List<String> classIds;

	private static final String ECOLE_ID = "Vgv1obkaHUASn7Z8rI7I";  // Directly use the Ecole ID
	private String profId;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prof_classes);

		mAuth = FirebaseAuth.getInstance();
		db = FirebaseFirestore.getInstance();

		lvClasses = findViewById(R.id.lvClasses);
		tvNoClasses = findViewById(R.id.tvNoClasses);

		// the nav bar code
		BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
		ProfNavBar.setupBottomNavigation(this, bottomNavigationView);

		classNames = new ArrayList<>();
		classIds = new ArrayList<>();
		adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classNames);
		lvClasses.setAdapter(adapter);

		lvClasses.setOnItemClickListener((parent, view, position, id) -> {
			String classId = classIds.get(position);
			Intent intent = new Intent(prof_classes_activity.this, prof_cour_list_activity.class);
			intent.putExtra("ecoleId", ECOLE_ID);
			intent.putExtra("classeId", classId);
			intent.putExtra("profId", profId);

			startActivity(intent);
		});

		fetchProfessorClasses();
	}

	private void fetchProfessorClasses() {
		FirebaseUser currentUser = mAuth.getCurrentUser();
		if (currentUser == null) {
			Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
			return;
		}

		String uid = currentUser.getEmail();
		Log.d("ProfClassesActivity", "Fetching classes for user with UID: " + uid);
		fetchProfessorClassesFromEcole(uid);
	}

	private void fetchProfessorClassesFromEcole(String uid) {
		db.collection("Ecoles").document(ECOLE_ID).collection("Professeurs")
				.whereEqualTo("uid", uid).get()
				.addOnSuccessListener(queryDocumentSnapshots -> {
					if (!queryDocumentSnapshots.isEmpty()) {
						for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
							profId = documentSnapshot.getId();
							List<String> ids = (List<String>) documentSnapshot.get("idClasses");
							Log.d("ProfClassesActivity", "Found professor document with ID: " + profId);

							if (ids != null && !ids.isEmpty()) {
								Log.d("ProfClassesActivity", "Found classes: " + ids);
								fetchClassNames(ids);
							} else {
								tvNoClasses.setVisibility(View.VISIBLE);
								Toast.makeText(this, "No classes found for professor", Toast.LENGTH_SHORT).show();
							}
						}
					} else {
						Toast.makeText(this, "Professor document not found", Toast.LENGTH_SHORT).show();
					}
				})
				.addOnFailureListener(e -> {
					Toast.makeText(this, "Failed to fetch professor document", Toast.LENGTH_SHORT).show();
					Log.e("ProfClassesActivity", "Error fetching professor document", e);
				});
	}

	private void fetchClassNames(List<String> ids) {
		classIds.clear();
		classNames.clear();
		Log.d("ProfClassesActivity", "Fetching class names for IDs: " + ids);
		for (String classId : ids) {
			db.collection("Ecoles").document(ECOLE_ID).collection("Classes").document(classId).get()
					.addOnSuccessListener(documentSnapshot -> {
						if (documentSnapshot.exists()) {
							String className = documentSnapshot.getString("nom");
							Log.d("ProfClassesActivity", "Found class with name: " + className);
							tvNoClasses.setVisibility(View.INVISIBLE);
							if (className != null) {
								classNames.add(className);
								classIds.add(classId);
								Log.d("ProfClassesActivity", "Added class name: " + className);
								runOnUiThread(() -> adapter.notifyDataSetChanged());
							} else {
								Log.d("ProfClassesActivity", "Class name is null for ID: " + classId);
							}
						} else {
							Log.d("ProfClassesActivity", "Class document does not exist for ID: " + classId);
						}
					})
					.addOnFailureListener(e -> {
						Toast.makeText(this, "Failed to fetch class document", Toast.LENGTH_SHORT).show();
						Log.e("ProfClassesActivity", "Error fetching class document", e);
					});
		}
		if (classNames.isEmpty()) {
			runOnUiThread(() -> tvNoClasses.setVisibility(View.VISIBLE));
		}
	}
}
