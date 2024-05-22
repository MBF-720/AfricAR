package pfe.africar.activitys;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import pfe.africar.R;

public class AbsenceDetailActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private Button suppAbsence;
    private TextView detailClass, detailDate, detailMatiere, detailNom, detailProf, detailPrenom;
    private String documentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absence_detail);

        db = FirebaseFirestore.getInstance();
        detailClass = findViewById(R.id.detailClass);
        detailDate = findViewById(R.id.detailDate);
        detailMatiere = findViewById(R.id.detailMatiere);
        detailNom = findViewById(R.id.detailNom);
        detailProf = findViewById(R.id.detailProf);
        detailPrenom = findViewById(R.id.detailPrenom);
        suppAbsence = findViewById(R.id.btn_supprimer_absence);

        documentId = getIntent().getStringExtra("absenceDocument");
        loadAbsenceDetails(documentId);

        suppAbsence.setOnClickListener(view -> deleteAbsence(documentId));
    }

    private void loadAbsenceDetails(String documentId) {
        DocumentReference docRef = db.collection("Ecoles").document("Vgv1obkaHUASn7Z8rI7I")
                .collection("Reclamations").document("Absence").collection("Liste absence").document(documentId);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    detailClass.setText(document.getString("classe"));
                    detailDate.setText(document.getString("date"));
                    detailMatiere.setText(document.getString("matiere"));
                    detailNom.setText(document.getString("nom"));
                    detailProf.setText(document.getString("nom du prof"));
                    detailPrenom.setText(document.getString("prenom"));
                }
            }
        });
    }

    private void deleteAbsence(String documentId) {
        DocumentReference docRef = db.collection("Ecoles").document("Vgv1obkaHUASn7Z8rI7I")
                .collection("Reclamations").document("Absence").collection("Liste absence").document(documentId);
        docRef.delete().addOnSuccessListener(aVoid -> {
            Toast.makeText(AbsenceDetailActivity.this, "Absence supprimée", Toast.LENGTH_SHORT).show();
            finish(); // Close the current activity
        }).addOnFailureListener(e -> {
            Toast.makeText(AbsenceDetailActivity.this, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
        });
    }
}
