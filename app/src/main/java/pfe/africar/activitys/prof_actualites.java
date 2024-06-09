package pfe.africar.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_actualites);

        listViewActualites = findViewById(R.id.listViewActualites);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles);
        listViewActualites.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        ProfNavBar.setupBottomNavigation(this, bottomNavigationView);

        loadActualites();

        listViewActualites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedTitle = titles.get(position);
                String selectedDetail = details.get(position);

                Intent intent = new Intent(prof_actualites.this, DetailActivity.class);
                intent.putExtra("title", selectedTitle);
                intent.putExtra("detail", selectedDetail);
                startActivity(intent);
            }
        });
    }

    private void loadActualites() {
        db.collection("Ecoles").document("Vgv1obkaHUASn7Z8rI7I").collection("Actualites")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            titles.add(document.getString("title"));
                            details.add(document.getString("detail"));
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
