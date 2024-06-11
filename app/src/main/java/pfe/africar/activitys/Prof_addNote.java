package pfe.africar.activitys;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import pfe.africar.R;
import pfe.africar.classes.Note;

public class Prof_addNote extends AppCompatActivity {

    private EditText coefEdit, controleEditText, controleCoefEditText, synthezeEditText, synthezeCoefEditText, tpEditText, tpCoefEditText, oraleEditText, oraleCoefEditText, moyenneEditText;
    private TextView eleveNameTextView;
    private Button saveButton;
    private FirebaseFirestore db;
    private String studentId;
    private String matiereName;
    private String profUid;
    private String ecoleId = "Vgv1obkaHUASn7Z8rI7I";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_add_note);

        eleveNameTextView = findViewById(R.id.eleveNameTextView);
        coefEdit = findViewById(R.id.coefEdit);
        controleEditText = findViewById(R.id.controleEditText);
        controleCoefEditText = findViewById(R.id.controleCoefEditText);
        synthezeEditText = findViewById(R.id.synthezeEditText);
        synthezeCoefEditText = findViewById(R.id.synthezeCoefEditText);
        tpEditText = findViewById(R.id.tpEditText);
        tpCoefEditText = findViewById(R.id.tpCoefEditText);
        oraleEditText = findViewById(R.id.oraleEditText);
        oraleCoefEditText = findViewById(R.id.oraleCoefEditText);
        moyenneEditText = findViewById(R.id.moyenneEditText);
        saveButton = findViewById(R.id.saveButton);

        db = FirebaseFirestore.getInstance();

        studentId = getIntent().getStringExtra("STUDENT_ID");
        profUid = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        if (studentId != null) {
            getStudentName(studentId);
        } else {
            Toast.makeText(this, "Student ID not found", Toast.LENGTH_SHORT).show();
        }

        getProfessorMatiere();

        saveButton.setOnClickListener(v -> saveNoteDetails());
    }

    private void getStudentName(String studentId) {
        DocumentReference docRef = db.collection("Ecoles").document(ecoleId).collection("Eleves").document(studentId);
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
                            Toast.makeText(Prof_addNote.this, "Nom or prenom field is empty.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Prof_addNote.this, "No such document.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Prof_addNote.this, "Failed to get document: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getProfessorMatiere() {
        db.collection("Ecoles").document(ecoleId)
                .collection("Professeurs")
                .whereEqualTo("uid", profUid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                matiereName = document.getString("matiere");
                                loadExistingNote();
                                break;
                            }
                        } else {
                            Toast.makeText(Prof_addNote.this, "Failed to fetch professor details", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void loadExistingNote() {
        DocumentReference noteRef = db.collection("Ecoles").document(ecoleId)
                .collection("Eleves").document(studentId)
                .collection("notes").document(matiereName);

        noteRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Note note = document.toObject(Note.class);
                        if (note != null) {
                            controleEditText.setText(String.valueOf(note.getControle()));
                            controleCoefEditText.setText(String.valueOf(note.getContolecoeff()));
                            synthezeEditText.setText(String.valueOf(note.getSynthese()));
                            synthezeCoefEditText.setText(String.valueOf(note.getSyntheseCoef()));
                            tpEditText.setText(String.valueOf(note.getTp()));
                            tpCoefEditText.setText(String.valueOf(note.getTpCoef()));
                            oraleEditText.setText(String.valueOf(note.getOrale()));
                            oraleCoefEditText.setText(String.valueOf(note.getOraleCoef()));
                            coefEdit.setText(String.valueOf(note.getMatiereCoeff()));
                            moyenneEditText.setText(String.valueOf(note.getMoyenne()));
                        }
                    } else {
                        Toast.makeText(Prof_addNote.this, "No existing note for this student and matiere.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Prof_addNote.this, "Failed to get note: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveNoteDetails() {
        double controle = Double.parseDouble(controleEditText.getText().toString());
        double controleCoef = Double.parseDouble(controleCoefEditText.getText().toString());
        double synthese = Double.parseDouble(synthezeEditText.getText().toString());
        double syntheseCoef = Double.parseDouble(synthezeCoefEditText.getText().toString());
        double tp = Double.parseDouble(tpEditText.getText().toString());
        double tpCoef = Double.parseDouble(tpCoefEditText.getText().toString());
        double orale = Double.parseDouble(oraleEditText.getText().toString());
        double oraleCoef = Double.parseDouble(oraleCoefEditText.getText().toString());

        Note note = new Note(controle, controleCoef, synthese, syntheseCoef, tp, tpCoef, orale, oraleCoef);
        note.setMoyenne(note.calculerMoyenne());
        note.setMatiereCoeff(Double.parseDouble(coefEdit.getText().toString()));
        note.setMatiere(matiereName);

        db.collection("Ecoles").document(ecoleId).collection("Eleves").document(studentId).collection("notes").document(matiereName)
                .set(note)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Prof_addNote.this, "Note updated successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(Prof_addNote.this, "Error updating note: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
