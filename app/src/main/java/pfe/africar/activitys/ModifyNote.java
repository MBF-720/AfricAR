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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import pfe.africar.R;
import pfe.africar.classes.Note;

class ModifyNoteActivity extends AppCompatActivity {
    private TextView eleveNameTextView;
    private TextView matiereTextView;
    private EditText controleEditText;
    private EditText controleCoefEditText;
    private EditText synthezeEditText;
    private EditText synthezeCoefEditText;
    private EditText tpEditText;
    private EditText tpCoefEditText;
    private EditText oraleEditText;
    private EditText oraleCoefEditText;
    private Button saveButton;

    private FirebaseFirestore db;
    private String eleveId;
    private String matiereId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_note);

        eleveNameTextView = findViewById(R.id.eleveNameTextView);
        matiereTextView = findViewById(R.id.matiereTextView);
        controleEditText = findViewById(R.id.controleEditText);
        controleCoefEditText = findViewById(R.id.controleCoefEditText);
        synthezeEditText = findViewById(R.id.synthezeEditText);
        synthezeCoefEditText = findViewById(R.id.synthezeCoefEditText);
        tpEditText = findViewById(R.id.tpEditText);
        tpCoefEditText = findViewById(R.id.tpCoefEditText);
        oraleEditText = findViewById(R.id.oraleEditText);
        oraleCoefEditText = findViewById(R.id.oraleCoefEditText);
        saveButton = findViewById(R.id.saveButton);

        db = FirebaseFirestore.getInstance();

        eleveId = getIntent().getStringExtra("eleveId");
        matiereId = getIntent().getStringExtra("matiereId");

        loadNoteDetails();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNoteDetails();
            }
        });
    }

    private void loadNoteDetails() {
        db.collection("eleves").document(eleveId).collection("notes").document(matiereId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Note note = document.toObject(Note.class);
                                controleEditText.setText(String.valueOf(note.getControle()));
                                controleCoefEditText.setText(String.valueOf(note.getCoef()));
                                synthezeEditText.setText(String.valueOf(note.getSynthese()));
                                synthezeCoefEditText.setText(String.valueOf(note.getCoef()));
                                tpEditText.setText(String.valueOf(note.getTp()));
                                tpCoefEditText.setText(String.valueOf(note.getCoef()));
                                oraleEditText.setText(String.valueOf(note.getOrale()));
                                oraleCoefEditText.setText(String.valueOf(note.getCoef()));
                            } else {
                                Toast.makeText(ModifyNoteActivity.this, "No such document", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ModifyNoteActivity.this, "get failed with " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveNoteDetails() {
        double controle = Double.parseDouble(controleEditText.getText().toString());
        double controleCoef = Double.parseDouble(controleCoefEditText.getText().toString());
        double synthese = Double.parseDouble(synthezeEditText.getText().toString());
        double synthezeCoef = Double.parseDouble(synthezeCoefEditText.getText().toString());
        double tp = Double.parseDouble(tpEditText.getText().toString());
        double tpCoef = Double.parseDouble(tpCoefEditText.getText().toString());
        double orale = Double.parseDouble(oraleEditText.getText().toString());
        double oraleCoef = Double.parseDouble(oraleCoefEditText.getText().toString());
        double moyenne = (controle * controleCoef + synthese * synthezeCoef + tp * tpCoef + orale * oraleCoef) / (controleCoef + synthezeCoef + tpCoef + oraleCoef);

        Note note = new Note(controle, controleCoef, synthese, synthezeCoef, tp, tpCoef, orale, oraleCoef, moyenne);

        db.collection("eleves").document(eleveId).collection("notes").document(matiereId)
                .set(note)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ModifyNoteActivity.this, "Notes saved successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ModifyNoteActivity.this, "Error saving notes: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
