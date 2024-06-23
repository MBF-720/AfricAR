package pfe.africar.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import pfe.africar.R;
import pfe.africar.helpers.ProfNavBar;

public class prof_actualites extends AppCompatActivity {

    private ListView listViewActualites;
    private ArrayAdapter<String> adapter;
    private List<String> titles = new ArrayList<>();
    private List<String> details = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "prof_actualites";
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_actualites);

        listViewActualites = findViewById(R.id.listViewActualites);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles);
        listViewActualites.setAdapter(adapter);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_add_note) {
                    Intent intent = new Intent(prof_actualites.this, prof_class_list.class);
                    startActivity(intent);
                } else if (id == R.id.menu_absence_list) {
                    Intent intent = new Intent(prof_actualites.this, prof_abscence_classes.class);
                    startActivity(intent);
                } else if (id == R.id.menu_contact_admin) {
                    Intent intent = new Intent(prof_actualites.this, prof_comunication_list.class);
                    startActivity(intent);
                }
                else if (id == R.id.deconnecter) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(prof_actualites.this, onboarding_screen_activity.class);
                    startActivity(intent);

            }
                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        ProfNavBar.setupBottomNavigation(this, bottomNavigationView);

        loadActualites();

        listViewActualites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedTitle = titles.get(position);
                String selectedDetail = details.get(position);

                Intent intent = new Intent(prof_actualites.this, DetailActivity.class);
                intent.putExtra("selectedTitle", selectedTitle);
                intent.putExtra("selectedId", selectedDetail);
                startActivity(intent);
            }
        });
    }

    public void openDrawer(View view) {
        drawerLayout.openDrawer(navigationView);
    }

    private void loadActualites() {
        db.collection("Ecoles").document("Vgv1obkaHUASn7Z8rI7I").collection("Actualités")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            titles.add(document.getString("title"));
                            details.add(document.getId());
                            Log.d(TAG, "Document fetched: " + document.getId());
                        }
                        adapter.notifyDataSetChanged();
                        if (titles.isEmpty()) {
                            Log.d(TAG, "No documents found.");
                            Toast.makeText(this, "No actualites found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e(TAG, "Error getting documents: ", task.getException());
                        Toast.makeText(this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
