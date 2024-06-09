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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import pfe.africar.R;
import pfe.africar.helpers.ProfNavBar;

public class prof_cour_list_activity extends Activity {

	private ListView listView;
	private List<String> courseList;
	private ArrayAdapter<String> adapter;

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
		String ecoleId = intent.getStringExtra("ecoleId");
		String classeId = intent.getStringExtra("classeId");
		String profId = intent.getStringExtra("profId");

		if (ecoleId == null || classeId == null || profId == null) {
			Toast.makeText(this, "Missing parameters", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}

		// Fetch matiere for the professor and then the courses
		fetchMatiereForProfessor(ecoleId, classeId, profId);

		// Initialize other UI components
		initUIComponents();
	}

	private void fetchMatiereForProfessor(String ecoleId, String classeId, String profId) {
		DatabaseReference matiereReference = FirebaseDatabase.getInstance().getReference("Ecoles")
				.child(ecoleId).child("Professeurs").child(profId).child("Matieres");

		matiereReference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				if (dataSnapshot.exists()) {
					String matiereName = dataSnapshot.getValue(String.class);
					if (matiereName != null) {
						fetchCoursesForMatiere(ecoleId, classeId, matiereName);
					} else {
						Toast.makeText(prof_cour_list_activity.this, "Matiere not found for professor", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(prof_cour_list_activity.this, "Matiere not found for professor", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				Log.e("ProfCourListActivity", "Error fetching matiere", databaseError.toException());
			}
		});
	}

	private void fetchCoursesForMatiere(String ecoleId, String classeId, String matiereName) {
		DatabaseReference matieresRef = FirebaseDatabase.getInstance().getReference("Ecoles")
				.child(ecoleId).child("Classes").child(classeId).child("Matieres");

		matieresRef.orderByChild("name").equalTo(matiereName).addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				if (dataSnapshot.exists()) {
					for (DataSnapshot matiereSnapshot : dataSnapshot.getChildren()) {
						DatabaseReference coursesRef = matiereSnapshot.getRef().child("Cours");
						fetchCourses(coursesRef);
					}
				} else {
					Toast.makeText(prof_cour_list_activity.this, "Matiere not found in class", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				Log.e("ProfCourListActivity", "Error fetching matiere from class", databaseError.toException());
			}
		});
	}

	private void fetchCourses(DatabaseReference coursesRef) {
		coursesRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				courseList.clear();
				for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
					String courseName = snapshot.child("courseName").getValue(String.class);
					if (courseName != null) {
						courseList.add(courseName);
					}
				}
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				Log.e("ProfCourListActivity", "Error fetching course list", databaseError.toException());
			}
		});
	}

	private void initUIComponents() {
		// Initialize other UI components
		TextView courses_ek8 = findViewById(R.id.courses_ek8);
		View _bg__component_1_ek15 = findViewById(R.id._bg__component_1_ek15);
		TextView button_ek11 = findViewById(R.id.button_ek11);
		button_ek11.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(prof_cour_list_activity.this, add_cours_activity.class);
				startActivity(intent);
			}
		});

		ImageView x_1_ek3 = findViewById(R.id.x_1_ek3);

		View _bg__group_52_ek11 = findViewById(R.id._bg__group_52_ek11);

	}
}
