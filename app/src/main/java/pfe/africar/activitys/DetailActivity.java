package pfe.africar.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import pfe.africar.R;



public class DetailActivity extends Activity {
  FirebaseFirestore db;
    String description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);

        db = FirebaseFirestore.getInstance();

        String title = getIntent().getStringExtra("selectedTitle");
        String id = getIntent().getStringExtra("selectedId");

        db.collection("Ecoles").document("Vgv1obkaHUASn7Z8rI7I").collection("Actualités").document(id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    description = document.getString("description");

                    descriptionTextView.setText(description);
                } else {
                    Toast.makeText(this, "Document does not exist", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Failed to get description", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });

        titleTextView.setText(title);



    }
}