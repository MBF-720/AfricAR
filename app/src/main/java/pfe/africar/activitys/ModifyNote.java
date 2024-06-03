package pfe.africar.activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import pfe.africar.R;
import pfe.africar.classes.Note;

public class ModifyNote extends AppCompatActivity {
    private TextView eleveNameTextView;
    private TextView matiereTextView;
    private EditText controleEditText;
    private EditText controleCoefEditText;
    private EditText syntheseEditText,moyenneEditText;
    private EditText syntheseCoefEditText;
    private EditText tpEditText;
    private EditText tpCoefEditText;
    private EditText oraleEditText;
    private EditText oraleCoefEditText,coef;
    private Button saveButton;

    private FirebaseFirestore db;

    private String eleveId;
    private String ecoleId;
    private String matiereId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_note);

        eleveNameTextView = findViewById(R.id.eleveNameTextView);
        moyenneEditText= findViewById(R.id.moyenneEditText);
        matiereTextView = findViewById(R.id.matiereTextView);
        controleEditText = findViewById(R.id.controleEditText);
        controleCoefEditText = findViewById(R.id.controleCoefEditText);
        syntheseEditText = findViewById(R.id.synthezeEditText);
        syntheseCoefEditText = findViewById(R.id.synthezeCoefEditText);
        tpEditText = findViewById(R.id.tpEditText);
        tpCoefEditText = findViewById(R.id.tpCoefEditText);
        oraleEditText = findViewById(R.id.oraleEditText);
        oraleCoefEditText = findViewById(R.id.oraleCoefEditText);
        saveButton = findViewById(R.id.saveButton);
        coef=findViewById(R.id.coefEdit);

        db = FirebaseFirestore.getInstance();

        eleveId = getIntent().getStringExtra("eleveId");
        matiereId = getIntent().getStringExtra("matiereId");
        ecoleId = getIntent().getStringExtra("ecoleId");



        // Log the retrieved values to ensure they are not null
        Log.d("ModifyNoteActivity", "eleveId: " + eleveId);
        Log.d("ModifyNoteActivity", "matiereId: " + matiereId);

        if (eleveId != null && matiereId != null) {
            loadNoteDetails();
        } else {
            Toast.makeText(this, "Invalid student or subject ID", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if IDs are invalid
        }
        getStudentName(ecoleId,  eleveId);
        matiereTextView.setText(matiereId);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNoteDetails();
            }
        });
    }

    private void loadNoteDetails() {
        db.collection("Ecoles").document("Vgv1obkaHUASn7Z8rI7I").collection("Eleves").document(eleveId).collection("notes").document(matiereId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Note note = document.toObject(Note.class);
                                if (note != null) {
                                    controleEditText.setText(String.valueOf(note.getControle()));
                                    controleCoefEditText.setText(String.valueOf(note.getContolecoeff()));
                                    syntheseEditText.setText(String.valueOf(note.getSynthese()));
                                    syntheseCoefEditText.setText(String.valueOf(note.getSyntheseCoef()));
                                    tpEditText.setText(String.valueOf(note.getTp()));
                                    tpCoefEditText.setText(String.valueOf(note.getTpCoef()));
                                    oraleEditText.setText(String.valueOf(note.getOrale()));
                                    oraleCoefEditText.setText(String.valueOf(note.getOraleCoef()));

                                    moyenneEditText.setText(String.valueOf(note.calculerMoyenne()));
                                    coef.setText(String.valueOf(note.getMatiereCoeff()));

                                }
                            } else {
                                Toast.makeText(ModifyNote.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ModifyNote.this, "Error getting document: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveNoteDetails() {
        double controle = Double.parseDouble(controleEditText.getText().toString());
        double controleCoef = Double.parseDouble(controleCoefEditText.getText().toString());
        double synthese = Double.parseDouble(syntheseEditText.getText().toString());
        double syntheseCoef = Double.parseDouble(syntheseCoefEditText.getText().toString());
        double tp = Double.parseDouble(tpEditText.getText().toString());
        double tpCoef = Double.parseDouble(tpCoefEditText.getText().toString());
        double orale = Double.parseDouble(oraleEditText.getText().toString());
        double oraleCoef = Double.parseDouble(oraleCoefEditText.getText().toString());

        Note note = new Note(controle, controleCoef, synthese, syntheseCoef, tp, tpCoef, orale, oraleCoef);
note.setMoyenne(note.calculerMoyenne());
note.setMatiereCoeff(Double.parseDouble(coef.getText().toString()));
        db.collection("Ecoles").document("Vgv1obkaHUASn7Z8rI7I").collection("Eleves").document(eleveId).collection("notes").document(matiereId)
                .set(note)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ModifyNote.this, "Note updated successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ModifyNote.this, "Error updating note: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void getStudentName(String ecoleId, String eleveId) {
        DocumentReference docRef = db.collection("Ecoles").document(ecoleId).collection("Eleves").document(eleveId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String nom = document.getString("nom");
                        String prenom = document.getString("prenom");
                        if (nom != null && prenom != null) {
                            eleveNameTextView.setText(nom + " " + prenom);
                        } else {
                            Toast.makeText(ModifyNote.this, "Nom or prenom field is empty.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ModifyNote.this, "No such document.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ModifyNote.this, "Failed to get document: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}

