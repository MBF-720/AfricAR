
	 
	/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		enter_id
	 *	@date 		Thursday 25th of April 2024 10:03:48 AM
	 *	@title 		Page 1
	 *	@author 	
	 *	@keywords 	
	 *	@generator 	Export Kit v1.3.figma
	 *
	 */


	package pfe.africar.activitys;

	import android.app.Activity;
	import android.content.Intent;
	import android.os.Bundle;
	import android.view.View;
	import android.widget.Button;
	import android.widget.ListView;
	import android.widget.Toast;

	import androidx.annotation.Nullable;

	import com.google.android.material.bottomnavigation.BottomNavigationView;
	import com.google.firebase.auth.FirebaseAuth;
	import com.google.firebase.auth.FirebaseUser;
	import com.google.firebase.firestore.DocumentSnapshot;
	import com.google.firebase.firestore.EventListener;
	import com.google.firebase.firestore.FirebaseFirestore;
	import com.google.firebase.firestore.FirebaseFirestoreException;
	import com.google.firebase.firestore.QueryDocumentSnapshot;
	import com.google.firebase.firestore.QuerySnapshot;

	import java.util.ArrayList;
	import java.util.List;

	import pfe.africar.R;
	import pfe.africar.helpers.EleveNavBar;

	public class comunication_list_activity extends Activity {

		private ListView listViewReclamations;
		private prof_comunication_list.ReclamationAdapter adapter;
		private Button button;
		private List<prof_comunication_list.Reclamation> reclamationList;
		private FirebaseFirestore db;
		private FirebaseAuth auth;



		@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.comunication_list);

			listViewReclamations = findViewById(R.id.list_view_reclamations);
			button=findViewById(R.id.addReclamation);
			reclamationList = new ArrayList<>();
			adapter = new prof_comunication_list.ReclamationAdapter(this, R.layout.list_item_comunication_prof, reclamationList);
			listViewReclamations.setAdapter(adapter);

			//the nav bar code
			BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
			EleveNavBar.setupBottomNavigation(this, bottomNavigationView);


			db = FirebaseFirestore.getInstance();
			auth = FirebaseAuth.getInstance();

			fetchUserDetails();

			listViewReclamations.setOnItemClickListener((parent, view, position, id) -> {
				prof_comunication_list.Reclamation reclamation = reclamationList.get(position);
				Intent intent = new Intent(comunication_list_activity.this, comunication_reponce.class);
				intent.putExtra("RECLAMATION_ID", reclamation.id);
				startActivity(intent);
			});

			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(comunication_list_activity.this, comunication_activity.class);
					startActivity(intent);
				}
			});

		

	}
		private void fetchUserDetails() {
			FirebaseUser currentUser = auth.getCurrentUser();
			if (currentUser == null) {
				Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
				return;
			}

			String uid = currentUser.getUid();
			db.collection("Users").document(uid).get().addOnCompleteListener(task -> {
				if (task.isSuccessful()) {
					DocumentSnapshot document = task.getResult();
					if (document.exists()) {
						String ecoleId = document.getString("idEcole");
						if (ecoleId != null) {
							fetchReclamations(ecoleId, currentUser.getEmail());
						} else {
							Toast.makeText(this, "Ecole ID not found", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(this, "User document not found", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(this, "Failed to fetch user details", Toast.LENGTH_SHORT).show();
				}
			});
		}


		private void fetchReclamations(String ecoleId, String userEmail) {
			db.collection("Ecoles").document(ecoleId)
					.collection("Reclamations")
					.whereEqualTo("user", userEmail)
					.addSnapshotListener(new EventListener<QuerySnapshot>() {
						@Override
						public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
							if (error != null) {
								return;
							}

							reclamationList.clear();
							for (QueryDocumentSnapshot doc : value) {
								String id = doc.getId();
								String titre = doc.getString("titre");
								String etat = doc.getString("etat");
								prof_comunication_list.Reclamation reclamation = new prof_comunication_list.Reclamation(id, titre, etat);
								reclamationList.add(reclamation);
							}
							adapter.notifyDataSetChanged();
						}
					});
		}
}
	
	