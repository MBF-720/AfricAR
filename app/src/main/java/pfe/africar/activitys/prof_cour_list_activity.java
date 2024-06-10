package pfe.africar.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import pfe.africar.R;
import pfe.africar.helpers.ProfNavBar;

public class prof_cour_list_activity extends Activity {

	private ListView listView;
	private List<String> courseList;
	private ArrayAdapter<String> adapter;
	private String ecoleId, classeId, profId, matiereId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prof_cour_list);

		listView = findViewById(R.id.course_list_view);
		courseList = new ArrayList<>();
		adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseList);
		listView.setAdapter(adapter);

		//the nav bar code
		BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
		ProfNavBar.setupBottomNavigation(this, bottomNavigationView);


		// Retrieve the Intent extras
		Intent intent = getIntent();
		String ecoleId = "Vgv1obkaHUASn7Z8rI7I";
		String classeId = "CPY5KGWxBex1B5rHnFEb";

		FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

		String profId =currentUser.getEmail() ;

		if (ecoleId == null || classeId == null || profId == null) {
			Toast.makeText(this, "Missing parameters cour list", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}

		// Fetch matiere for the professor and then the courses
		fetchMatiereForProfessor(ecoleId, classeId);

		// Initialize other UI components
		TextView courses_ek8 = findViewById(R.id.courses_ek8);
		View _bg__component_1_ek15 = findViewById(R.id._bg__component_1_ek15);
		TextView button_ek11 = findViewById(R.id.button_ek11);
		_bg__component_1_ek15.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(prof_cour_list_activity.this, add_cours_activity.class);
				intent.putExtra("ecoleId",ecoleId);
				intent.putExtra("classeId",classeId);
				intent.putExtra("matiereId",matiereId);
				startActivity(intent);

			}
		});

		ImageView x_1_ek3 = findViewById(R.id.x_1_ek3);

		View _bg__group_52_ek11 = findViewById(R.id._bg__group_52_ek11);
	}
	private void fetchMatiereForProfessor(String ecoleId, String classeId) {
		FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
		if (currentUser == null) {
			Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
			return;
		}

		String uid = currentUser.getEmail();
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		CollectionReference profRef = db.collection("Ecoles").document(ecoleId).collection("Professeurs");

		profRef.whereEqualTo("uid", uid).get().addOnCompleteListener(task -> {
			if (task.isSuccessful()) {
				if (!task.getResult().isEmpty()) {
					DocumentSnapshot document = task.getResult().getDocuments().get(0);
					String profId = document.getId();
					String matiereName = document.getString("matiere");
					Toast.makeText(prof_cour_list_activity.this, matiereName, Toast.LENGTH_SHORT).show();


					if (matiereName != null) {
						// Fetch courses for the matiere
						fetchCoursesForMatiere(ecoleId, classeId, matiereName);
					} else {
						Toast.makeText(prof_cour_list_activity.this, "Matiere not found for professor", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(prof_cour_list_activity.this, "Professor document not found", Toast.LENGTH_SHORT).show();
				}
			} else {
				Log.e("ProfCourListActivity", "Error fetching professor document", task.getException());
				Toast.makeText(prof_cour_list_activity.this, "Error fetching professor document", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void fetchCoursesForMatiere(String ecoleId, String classeId, String matiereName) {
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		CollectionReference matieresRef = db.collection("Ecoles").document(ecoleId)
				.collection("Classes").document(classeId)
				.collection("Matieres");

		matieresRef.whereEqualTo("nom", matiereName).get().addOnCompleteListener(task -> {
			if (task.isSuccessful()) {
				if (!task.getResult().isEmpty()) {
					for (DocumentSnapshot matiereSnapshot : task.getResult()) {
						 matiereId = matiereSnapshot.getId();
						Toast.makeText(prof_cour_list_activity.this, "Matiere id " + matiereId, Toast.LENGTH_SHORT).show();

						CollectionReference coursesRef = matiereSnapshot.getReference().collection("Cours");
						fetchCourses(coursesRef);
					}
				} else {
					Toast.makeText(prof_cour_list_activity.this, "Matiere not found in class", Toast.LENGTH_SHORT).show();
				}
			} else {
				Log.e("ProfCourListActivity", "Error fetching matiere from class", task.getException());
				Toast.makeText(prof_cour_list_activity.this, "Error fetching matiere from class", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void fetchCourses(CollectionReference coursesRef) {
		coursesRef.get().addOnCompleteListener(task -> {
			if (task.isSuccessful()) {
				courseList.clear(); // Clear the course list before adding new items
				for (DocumentSnapshot courseSnapshot : task.getResult()) {
					String courseName = courseSnapshot.getString("title");
					if (courseName != null) {
						courseList.add(courseName);
						Toast.makeText(prof_cour_list_activity.this, "cour finded: " + courseName, Toast.LENGTH_SHORT).show();
					}
				}
				runOnUiThread(() -> adapter.notifyDataSetChanged());
			} else {
				Log.e("ProfCourListActivity", "Error fetching course list", task.getException());
				Toast.makeText(prof_cour_list_activity.this, "Error fetching course list", Toast.LENGTH_SHORT).show();
			}
		});
	}



}
