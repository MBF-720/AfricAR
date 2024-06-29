package pfe.africar.activitys;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import pfe.africar.R;
import pfe.africar.helpers.ProfNavBar;

public class prof_profile extends AppCompatActivity {

    private static final String TAG = "ProfProfile";

    private TextView firstName, lastName, field, phone, email;
    private ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_profile);

        photo = findViewById(R.id.image_view);
        firstName = findViewById(R.id.label_ek54);
        lastName = findViewById(R.id.label_ek55);
        field = findViewById(R.id.label_ek56);
        phone = findViewById(R.id.label_ek57);
        email = findViewById(R.id.email);
        Button updateFieldButton = findViewById(R.id.update_field_button);

// the nav bar code
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        ProfNavBar.setupBottomNavigation(this, bottomNavigationView);
        // Get the current user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            Log.d(TAG, "Current user email: " + userEmail);

            // Fetch the professor details using the current user's email
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Ecoles").document("Vgv1obkaHUASn7Z8rI7I")
                    .collection("Professeurs").whereEqualTo("email", userEmail)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.exists()) {
                                    firstName.setText(document.getString("nom"));
                                    lastName.setText(document.getString("prenom"));
                                    field.setText(document.getString("matiere"));
                                    phone.setText(document.getString("telephone"));
                                    email.setText(document.getString("email"));
                                   //  photo.setImageURI(Uri.parse(document.getString("photoUrl")));
                                    Log.d(TAG, "Professor details loaded successfully");
                                } else {
                                    Toast.makeText(getApplicationContext(), "Prof not found", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "Document exists but Prof details are missing");
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to fetch Prof details", Toast.LENGTH_SHORT).show();
                            if (task.getException() != null) {
                                Log.e(TAG, "Error fetching Prof details: ", task.getException());
                            } else {
                                Log.d(TAG, "No matching documents found for email: " + userEmail);
                            }
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "User not logged in", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "No current user logged in");
        }


        updateFieldButton.setOnClickListener(v -> {
            String newFieldValue = field.getText().toString();
            updateProfessorField(newFieldValue);
        });


    }
    private void updateProfessorField(String fieldValue) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null && !fieldValue.isEmpty()) {
            String userEmail = currentUser.getEmail();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // First, query the documents with the matching email
            db.collection("Ecoles").document("Vgv1obkaHUASn7Z8rI7I")
                    .collection("Professeurs").whereEqualTo("email", userEmail)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        // Check if the query returned any documents
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // Loop through the documents and update each one
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                document.getReference().update("matiere", fieldValue)
                                        .addOnSuccessListener(aVoid -> Toast.makeText(prof_profile.this, "Field updated successfully", Toast.LENGTH_SHORT).show())
                                        .addOnFailureListener(e -> Toast.makeText(prof_profile.this, "Failed to update field", Toast.LENGTH_SHORT).show());
                            }
                        } else {
                            Toast.makeText(prof_profile.this, "No matching professor found", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(prof_profile.this, "Error fetching documents: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            if (currentUser == null) {
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            } else if (fieldValue.isEmpty()) {
                Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
