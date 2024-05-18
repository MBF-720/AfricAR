package pfe.africar.activitys;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import pfe.africar.R;

public class Prof_details extends AppCompatActivity {

    private TextView firstName,lastName,filed,phone,gender,email;
    private ImageView photo ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_details);



        photo=findViewById(R.id.image_view);
        firstName = findViewById(R.id.label_ek54);
        lastName = findViewById(R.id.label_ek55);
        filed = findViewById(R.id.label_ek56);
        phone = findViewById(R.id.label_ek57);

        email = findViewById(R.id.email);



        String profId = getIntent().getStringExtra("profId");


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Ecoles").document("Vgv1obkaHUASn7Z8rI7I")
                .collection("Professeurs").document(profId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            firstName.setText(document.getString("nom"));
                            lastName.setText(document.getString("prenom"));
                            filed.setText(document.getString("matiere"));
                            phone.setText(document.getString("telephone"));
                            email.setText(document.getString("email"));
                            // photo.setImageURI(Uri.parse(document.getString("photoUrl")));



                        } else {
                            Toast.makeText(getApplicationContext(), "Prof not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to fetch Prof details", Toast.LENGTH_SHORT).show();
                    }
                });





    }
}