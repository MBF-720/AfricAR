package pfe.africar.activitys;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import pfe.africar.R;
import pfe.africar.helpers.EleveNavBar;

public class eleve_profile extends AppCompatActivity {

    private TextView firstName, lastName, birthDate, phone, gender, email;
    private ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleve_profile);

        photo = findViewById(R.id.image_view);
        firstName = findViewById(R.id.label_ek54);
        lastName = findViewById(R.id.label_ek55);
        birthDate = findViewById(R.id.label_ek56);
        phone = findViewById(R.id.label_ek57);
        gender = findViewById(R.id.label_ek58);
        email = findViewById(R.id.email);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        EleveNavBar.setupBottomNavigation(this, bottomNavigationView);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            email.setText(userEmail);

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Users").document(currentUser.getUid())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String ecoleId = document.getString("idEcole");

                                if (ecoleId != null) {
                                    fetchStudentDetails(ecoleId, userEmail);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Ecole ID not found", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "User document not found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to fetch user details", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "No authenticated user found", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchStudentDetails(String ecoleId, String userEmail) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Ecoles").document(ecoleId)
                .collection("Eleves")
                .whereEqualTo("email", userEmail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        DocumentSnapshot document = task.getResult().getDocuments().get(0);
                        firstName.setText(document.getString("nom"));
                        lastName.setText(document.getString("prenom"));
                        birthDate.setText(document.getString("birth"));
                        phone.setText(document.getString("telephone"));
                        gender.setText(document.getString("genre"));
                        email.setText(document.getString("email"));
                        // photo.setImageURI(Uri.parse(document.getString("photoUrl")));
                    } else {
                        Toast.makeText(getApplicationContext(), "Student not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Failed to fetch student details", Toast.LENGTH_SHORT).show();
                });
    }
}
