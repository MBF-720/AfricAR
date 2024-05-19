package pfe.africar.activitys;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import pfe.africar.R;

public class Reclamations extends AppCompatActivity {

    private ListView reclamationsListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> reclamationsTitles;
    private ArrayList<String> reclamationIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamations);

        reclamationsListView = findViewById(R.id.reclamations_list);
        reclamationsTitles = new ArrayList<>();
        reclamationIds = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reclamationsTitles);
        reclamationsListView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Ecoles/Vgv1obkaHUASn7Z8rI7I/Reclamations")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String title = document.getString("titre");
                            reclamationsTitles.add(title);
                            reclamationIds.add(document.getId());
                        }
                        adapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(getApplicationContext(), "can't find list", Toast.LENGTH_SHORT).show();

                    }
                });

        reclamationsListView.setOnItemClickListener((parent, view, position, id) -> {
            String reclamationId = reclamationIds.get(position);
            Intent intent = new Intent(Reclamations.this, ReclamationDetailsActivity.class);
            intent.putExtra("RECLAMATION_ID", reclamationId);
            startActivity(intent);
        });
    }
}
