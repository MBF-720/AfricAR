package pfe.africar.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import pfe.africar.R;
import pfe.africar.helpers.EleveNavBar;

public class acceille_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

	private String ecoleID;
	private TextView nomEleve;
	private FirebaseFirestore db;
	private FirebaseAuth mAuth;
	private ArrayAdapter<String> adapter;
	private List<String> titles = new ArrayList<>();
	private ListView listViewActualites;
	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle toggle;
	private ImageView vectorEk22;
	private List<String> details = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acceille);

		listViewActualites = findViewById(R.id.listViewActualites);
		adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles);
		listViewActualites.setAdapter(adapter);

		db = FirebaseFirestore.getInstance();
		mAuth = FirebaseAuth.getInstance();
		nomEleve = findViewById(R.id.nom_eleve);
		vectorEk22 = findViewById(R.id.vector_ek22);

		BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
		EleveNavBar.setupBottomNavigation(this, bottomNavigationView);

		drawerLayout = findViewById(R.id.drawer_layout);
		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawerLayout.addDrawerListener(toggle);
		toggle.syncState();

		vectorEk22.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				drawerLayout.openDrawer(navigationView);
			}
		});

		fetchEcoleIdAndLoadActualites();
		listViewActualites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String selectedTitle = titles.get(position);
				String selectedDetail = details.get(position);

				Intent intent = new Intent(acceille_activity.this, DetailActivity.class);
				intent.putExtra("selectedTitle", selectedTitle);
				intent.putExtra("selectedId", selectedDetail);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.nav_consulter_releve_notes) {
			startActivity(new Intent(this, ReleveNotesActivity.class));
		} else if (id == R.id.nav_consulter_emploi_temps) {
			startActivity(new Intent(this, eleve_timetable.class));
		} else if (id == R.id.nav_contacter_administration) {
			startActivity(new Intent(this, comunication_list_activity.class));
		}
		else if (id == R.id.deconnecter) {
			FirebaseAuth.getInstance().signOut();
			Intent intent = new Intent(acceille_activity.this, onboarding_screen_activity.class);
			startActivity(intent);

		}
		drawerLayout.closeDrawers();
		return true;
	}


	private void fetchEcoleIdAndLoadActualites() {
		FirebaseUser currentUser = mAuth.getCurrentUser();
		if (currentUser == null) {
			Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
			return;
		}

		String uid = currentUser.getUid();
		db.collection("Users").document(uid).get()
				.addOnCompleteListener(task -> {
					if (task.isSuccessful() && task.getResult() != null) {
						DocumentSnapshot documentSnapshot = task.getResult();
						ecoleID = documentSnapshot.getString("idEcole");
						if (ecoleID != null) {
							loadActualites();
							fetchUserDetails(currentUser.getEmail(), ecoleID);
						} else {
							Toast.makeText(acceille_activity.this, "Ecole ID not found", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(acceille_activity.this, "Failed to fetch ecole ID", Toast.LENGTH_SHORT).show();
					}
				});
	}

	private void loadActualites() {
		db.collection("Ecoles").document(ecoleID).collection("Actualités")
				.get()
				.addOnCompleteListener(task -> {
					if (task.isSuccessful()) {
						for (QueryDocumentSnapshot document : task.getResult()) {
							titles.add(document.getString("title"));
							details.add(document.getId());// Ensure 'title' field exists
						}
						adapter.notifyDataSetChanged();
					} else {
						Toast.makeText(this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
					}
				});
	}

	private void fetchUserDetails(String email, String ecoleId) {
		db.collection("Ecoles").document(ecoleId).collection("Eleves")
				.whereEqualTo("email", email)
				.get()
				.addOnCompleteListener(task -> {
					if (task.isSuccessful()) {
						for (QueryDocumentSnapshot document : task.getResult()) {
							String nom = document.getString("nom");
							String prenom = document.getString("prenom");
							if (nom != null && prenom != null) {
								nomEleve.setText(nom + " " + prenom);
							} else {
								Toast.makeText(acceille_activity.this, "Nom or prenom not found", Toast.LENGTH_SHORT).show();
							}
						}
					} else {
						Toast.makeText(this, "Error fetching user details: " + task.getException(), Toast.LENGTH_SHORT).show();
					}
				});
	}
}
