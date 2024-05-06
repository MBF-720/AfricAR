package pfe.africar.activitys;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import pfe.africar.R;


public class enter_id_classe_activity extends AppCompatActivity {
ImageView backbtn;
EditText classeID;
View button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_id_classe);

        backbtn=(ImageView) findViewById(R.id.classe_id_back);
        classeID=(EditText) findViewById(R.id.classe_id);
        button=(View) findViewById(R.id.classe_id_btn);

        classeID.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Masquer le clavier
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(classeID.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });


//recuperer le statu
        String statu = getIntent().getStringExtra("statu");
        String schoolId = getIntent().getStringExtra("school_id");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer le texte entré dans le champ "label"
                String id_ClasseText = classeID.getText().toString().trim();

                // Vérifier si le champ "label" n'est pas vide
                if (!id_ClasseText.isEmpty()) {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    DocumentReference docRef = db.collection("Ecoles").document(schoolId ).collection("Classes").document(id_ClasseText);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {



                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                                    //-------------------- Le champ n'est pas vide, vous pouvez continue-------------------------------------------------------------
                                    Intent intent = new Intent(enter_id_classe_activity.this, fill_profile_activity.class);
                                    intent.putExtra("statu", statu);
                                    intent.putExtra("school_id", schoolId);
                                    intent.putExtra("classe_id", id_ClasseText);

                                    startActivity(intent);

                                } else {
                                    Log.d(TAG, "No such document");
                                    Toast.makeText(enter_id_classe_activity.this, "Veuillez verifier votre ID", Toast.LENGTH_SHORT).show();


                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });



                } else {
                    // Le champ est vide, afficher un message d'erreur à l'utilisateur
                    Toast.makeText(enter_id_classe_activity.this, "Veuillez entrer une valeur pour le champ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
















    }
}