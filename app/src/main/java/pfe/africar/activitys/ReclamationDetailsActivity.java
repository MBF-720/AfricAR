package pfe.africar.activitys;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import pfe.africar.R;

public class ReclamationDetailsActivity extends AppCompatActivity {

    private TextView tvTitle, tvDate, tvNom, tvPrenom, tvClasse,etEtat,tv_description;
    private EditText  etReponse;
    private Button btnUpdate;
    private FirebaseFirestore db;
    private DocumentReference reclamationRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamation_details);

        tvTitle = findViewById(R.id.tv_title);
        tvDate = findViewById(R.id.tv_date);
        tvNom = findViewById(R.id.tv_nom);
        tvPrenom = findViewById(R.id.tv_prenom);
        tvClasse = findViewById(R.id.tv_classe);
        etEtat = findViewById(R.id.et_etat);
        etReponse = findViewById(R.id.et_reponse);
        btnUpdate = findViewById(R.id.btn_update);
        tv_description = findViewById(R.id.tv_description);


        db = FirebaseFirestore.getInstance();

        String reclamationId = getIntent().getStringExtra("RECLAMATION_ID");
        reclamationRef = db.document("Ecoles/Vgv1obkaHUASn7Z8rI7I/Reclamations/" + reclamationId);

        reclamationRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    tvTitle.setText(document.getString("titre"));
                    tvDate.setText(document.getString("date"));
                    tvNom.setText(document.getString("nom"));
                    tvPrenom.setText(document.getString("prenom"));
                    tvClasse.setText(document.getString("classe"));
                    etEtat.setText(document.getString("etat"));
                    etReponse.setText(document.getString("reponse"));
                    tv_description.setText(document.getString("description"));

                }
            }
        });

        etEtat.setOnClickListener(v -> showEtatDialog());

        btnUpdate.setOnClickListener(v -> {
            String etat = etEtat.getText().toString().trim();
            String reponse = etReponse.getText().toString().trim();

            if (etat.isEmpty() || reponse.isEmpty()) {
                Toast.makeText(ReclamationDetailsActivity.this, "Please fill in both Etat and Reponse", Toast.LENGTH_SHORT).show();
                return;
            }

            reclamationRef.update("etat", etat, "reponse", reponse)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(ReclamationDetailsActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(ReclamationDetailsActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                    });
        });
    }

    private void showEtatDialog() {
        String[] etatOptions = {"En cours", "Terminer"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Etat")
                .setItems(etatOptions, (dialog, which) -> {
                    etEtat.setText(etatOptions[which]);
                });
        builder.create().show();
    }
}
