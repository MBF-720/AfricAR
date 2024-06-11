package pfe.africar.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import pfe.africar.R;
import pfe.africar.helpers.ProfNavBar;

public class prof_quiz_list_activity extends Activity {

	private TextView quizzes_ek8;
	private TextView button_ek12;
	private FirebaseFirestore db;
	private ListView quizListView;
	private ArrayList<String> quizList;
	private ArrayAdapter<String> quizAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prof_quiz_list);

		quizzes_ek8 = findViewById(R.id.quizzes_ek8);
		button_ek12 = findViewById(R.id.button_ek12);

		db = FirebaseFirestore.getInstance();
		quizListView = findViewById(R.id.quiz_list_view);
		quizList = new ArrayList<>();
		quizAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, quizList);
		quizListView.setAdapter(quizAdapter);

		// Setup navigation bar
		BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
		ProfNavBar.setupBottomNavigation(this, bottomNavigationView);

		// Load quizzes from Firestore
		loadQuizzes();

		button_ek12.setOnClickListener(v -> startActivity(new Intent(prof_quiz_list_activity.this, add_quiz_activity.class)));
	}

	private void loadQuizzes() {
		FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
		if (currentUser == null) {
			Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
			return;
		}

		String userEmail = currentUser.getEmail();
		CollectionReference quizzesRef = db.collection("Ecoles")
				.document("Vgv1obkaHUASn7Z8rI7I")
				.collection("Classes")
				.document("CPY5KGWxBex1B5rHnFEb")
				.collection("Quizzes");

		quizzesRef.whereEqualTo("profId", userEmail).get().addOnCompleteListener(task -> {
			if (task.isSuccessful()) {
				quizList.clear(); // Clear the list before adding new items
				for (QueryDocumentSnapshot document : task.getResult()) {
					String quizTitle = document.getString("title");
					if (quizTitle != null) {
						quizList.add(quizTitle);
					}
				}
				quizAdapter.notifyDataSetChanged();
			} else {
				Toast.makeText(this, "Error loading quizzes: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
			}
		});
	}
	@Override
	protected void onResume() {
		super.onResume();
		// Reload quizzes when the activity resumes
		loadQuizzes();
	}
}
