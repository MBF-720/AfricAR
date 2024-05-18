package pfe.africar.activitys;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import pfe.africar.R;


public class Eleve_Details extends AppCompatActivity {


    private TextView firstName,lastName,birthDate,phone,gender,email;
    private ImageView photo ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleve_details);





        photo=findViewById(R.id.image_view);
        firstName = findViewById(R.id.label_ek54);
        lastName = findViewById(R.id.label_ek55);
        birthDate = findViewById(R.id.label_ek56);
        phone = findViewById(R.id.label_ek57);
        gender = findViewById(R.id.label_ek58);
        email = findViewById(R.id.email);

        String studentId = getIntent().getStringExtra("studentId");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Ecoles").document("Vgv1obkaHUASn7Z8rI7I")
                .collection("Eleves").document(studentId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
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
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to fetch student details", Toast.LENGTH_SHORT).show();
                    }
                });
















    }
}