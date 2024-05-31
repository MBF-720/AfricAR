package pfe.africar.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import pfe.africar.R;

public class prof_classes_activity  extends AppCompatActivity {

	private FirebaseAuth mAuth;
	private FirebaseFirestore db;
	private ListView lvClasses;
	private TextView tvNoClasses;
	private ArrayAdapter<String> adapter;
	private List<String> classNames;
	private List<String> classIds;

	private String ecoleId;
	private String profId;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prof_classes);

		mAuth = FirebaseAuth.getInstance();
		db = FirebaseFirestore.getInstance();

		lvClasses = findViewById(R.id.lvClasses);
		tvNoClasses = findViewById(R.id.tvNoClasses);

		classNames = new ArrayList<>();
		classIds = new ArrayList<>();
		adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classNames);
		lvClasses.setAdapter(adapter);

		fetchProfessorClasses();

		lvClasses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String classId = classIds.get(position);
				Intent intent = new Intent(prof_classes_activity .this, prof_cour_list_activity.class);
				intent.putExtra("ecoleId", ecoleId);
				intent.putExtra("classeId", classId);
				intent.putExtra("profId", profId);
				startActivity(intent);
			}
		});
	}

	private void fetchProfessorClasses() {
		FirebaseUser currentUser = mAuth.getCurrentUser();
		if (currentUser == null) {
			Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
			return;
		}

		String uid = currentUser.getUid();
		db.collection("Users").document(uid).get()
				.addOnSuccessListener(documentSnapshot -> {
					if (documentSnapshot.exists()) {
						ecoleId = documentSnapshot.getString("idEcole");
						fetchProfessorClassesFromEcole(uid, ecoleId);
					} else {
						Toast.makeText(this, "User document not found", Toast.LENGTH_SHORT).show();
					}
				})
				.addOnFailureListener(e -> {
					Toast.makeText(this, "Failed to fetch user document", Toast.LENGTH_SHORT).show();
					Log.e("ProfClassesActivity", "Error fetching user document", e);
				});
	}

	private void fetchProfessorClassesFromEcole(String uid, String ecoleId) {
		db.collection("Ecoles").document(ecoleId).collection("Professeurs")
				.whereEqualTo("uid", uid).get()
				.addOnSuccessListener(queryDocumentSnapshots -> {
					if (!queryDocumentSnapshots.isEmpty()) {
						for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
							profId = documentSnapshot.getId();
							List<String> ids = (List<String>) documentSnapshot.get("idClasses");
							if (ids != null && !ids.isEmpty()) {
								fetchClassNames(ids);
							} else {
								tvNoClasses.setVisibility(View.VISIBLE);
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
		for (String classId : ids) {
			db.collection("Classes").document(classId).get()
					.addOnSuccessListener(documentSnapshot -> {
						if (documentSnapshot.exists()) {
							String className = documentSnapshot.getString("nom");
							if (className != null) {
								classNames.add(className);
								classIds.add(classId);
								adapter.notifyDataSetChanged();
							}
						}
					})
					.addOnFailureListener(e -> {
						Toast.makeText(this, "Failed to fetch class document", Toast.LENGTH_SHORT).show();
						Log.e("ProfClassesActivity", "Error fetching class document", e);
					});
		}
		if (classNames.isEmpty()) {
			tvNoClasses.setVisibility(View.VISIBLE);
		}
	}
}
