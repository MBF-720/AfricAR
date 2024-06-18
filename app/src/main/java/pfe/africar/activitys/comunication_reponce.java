package pfe.africar.activitys;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import pfe.africar.R;
import pfe.africar.helpers.ProfNavBar;

public class comunication_reponce extends AppCompatActivity {
    private FirebaseFirestore db;
    private TextView etNom, etPrenom, etTitre, etDescription;
    private TextView tvReponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunication_reponce);

        etNom = findViewById(R.id.et_nom);
        etPrenom = findViewById(R.id.et_prenom);
        etTitre = findViewById(R.id.et_titre);
        etDescription = findViewById(R.id.et_description);
        tvReponse = findViewById(R.id.Reponse);
//the nav bar code
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        ProfNavBar.setupBottomNavigation(this, bottomNavigationView);

        db = FirebaseFirestore.getInstance();

        String reclamationId = getIntent().getStringExtra("RECLAMATION_ID");
        fetchReclamationDetails(reclamationId);

    }
    private void fetchReclamationDetails(String reclamationId) {
        db.collection("Ecoles").document("Vgv1obkaHUASn7Z8rI7I") // Use actual ecoleId
                .collection("Reclamations").document(reclamationId)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            etNom.setText(document.getString("nom"));
                            etPrenom.setText(document.getString("prenom"));
                            etTitre.setText(document.getString("titre"));
                            etDescription.setText(document.getString("description"));
                            tvReponse.setText(document.getString("reponse"));
                        } else {
                            Toast.makeText(this, "Reclamation not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Failed to fetch reclamation details", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}