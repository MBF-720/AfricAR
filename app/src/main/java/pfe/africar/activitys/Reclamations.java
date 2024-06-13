package pfe.africar.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import pfe.africar.R;



public class Reclamations extends AppCompatActivity {

    private ListView reclamationsListView;

    private TextView absece;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> reclamationsTitles;
    private ArrayList<String> reclamationIds;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamations);

        absece=(TextView) findViewById(R.id.absence);


        reclamationsListView = findViewById(R.id.reclamations_list);
        reclamationsTitles = new ArrayList<>();
        reclamationIds = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reclamationsTitles);
        reclamationsListView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
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
                    } else {
                        Toast.makeText(getApplicationContext(), "Can't find list", Toast.LENGTH_SHORT).show();
                    }
                });

        reclamationsListView.setOnItemClickListener((parent, view, position, id) -> {
            String reclamationId = reclamationIds.get(position);
            Intent intent = new Intent(Reclamations.this, ReclamationDetailsActivity.class);
            intent.putExtra("RECLAMATION_ID", reclamationId);
            startActivity(intent);
        });

        reclamationsListView.setOnItemLongClickListener((parent, view, position, id) -> {
            showDeleteConfirmationDialog(position);
            return true;
        });


        absece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reclamations.this, AbsenceList.class);//todo hethy
                startActivity(intent);            }
        });



















    }

    private void showDeleteConfirmationDialog(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Reclamation")
                .setMessage("Are you sure you want to delete this reclamation?")
                .setPositiveButton("Delete", (dialog, which) -> deleteReclamation(position))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteReclamation(int position) {
        String reclamationId = reclamationIds.get(position);
        db.collection("Ecoles/Vgv1obkaHUASn7Z8rI7I/Reclamations").document(reclamationId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    reclamationsTitles.remove(position);
                    reclamationIds.remove(position);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(Reclamations.this, "Reclamation deleted successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> Toast.makeText(Reclamations.this, "Failed to delete reclamation", Toast.LENGTH_SHORT).show());
    }
}
