package pfe.africar.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import pfe.africar.R;
import pfe.africar.helpers.ProfNavBar;

public class prof_comunication_list extends AppCompatActivity {

    private ListView listViewReclamations;
    private ReclamationAdapter adapter;
    private Button button;
    private List<Reclamation> reclamationList;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_comunication_list);

        listViewReclamations = findViewById(R.id.list_view_reclamations);
        button=findViewById(R.id.addReclamation);
        reclamationList = new ArrayList<>();
        adapter = new ReclamationAdapter(this, R.layout.list_item_comunication_prof, reclamationList);
        listViewReclamations.setAdapter(adapter);

        //the nav bar code
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        ProfNavBar.setupBottomNavigation(this, bottomNavigationView);


        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        fetchProfessorDetails();

        listViewReclamations.setOnItemClickListener((parent, view, position, id) -> {
            Reclamation reclamation = reclamationList.get(position);
            Intent intent = new Intent(prof_comunication_list.this, Prof_comunication_reponse.class);
            intent.putExtra("RECLAMATION_ID", reclamation.id);
            startActivity(intent);
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(prof_comunication_list.this, prof_comunication.class);
                startActivity(intent);
            }
        });
    }

    private void fetchProfessorDetails() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = currentUser.getUid();
        db.collection("Users").document(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String ecoleId = document.getString("idEcole");
                    if (ecoleId != null) {
                        fetchProfessorInfo(ecoleId, uid);
                    } else {
                        Toast.makeText(this, "Ecole ID not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "User document not found", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Failed to fetch user details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchProfessorInfo(String ecoleId, String profUid) {
        db.collection("Ecoles").document(ecoleId)
                .collection("Professeurs")
                .whereEqualTo("uid", profUid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String profNom = document.getString("nom");
                            String profPrenom = document.getString("prenom");
                            if (profNom != null && profPrenom != null) {
                                fetchReclamations(ecoleId, profNom, profPrenom);
                            } else {
                                Toast.makeText(this, "Professor details not found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(this, "Failed to fetch professor details", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fetchReclamations(String ecoleId, String profNom, String profPrenom) {
        db.collection("Ecoles").document(ecoleId)
                .collection("Reclamations")
                .whereEqualTo("nom", profNom)
                .whereEqualTo("prenom", profPrenom)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            return;
                        }

                        reclamationList.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            String id = doc.getId();
                            String titre = doc.getString("titre");
                            String etat = doc.getString("etat");
                            Reclamation reclamation = new Reclamation(id, titre, etat);
                            reclamationList.add(reclamation);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private static class Reclamation {
        String id;
        String titre;
        String etat;

        Reclamation(String id, String titre, String etat) {
            this.id = id;
            this.titre = titre;
            this.etat = etat;
        }
    }

    private static class ReclamationAdapter extends ArrayAdapter<Reclamation> {
        private int resourceLayout;
        private List<Reclamation> items;

        ReclamationAdapter(@NonNull android.content.Context context, int resource, @NonNull List<Reclamation> items) {
            super(context, resource, items);
            this.resourceLayout = resource;
            this.items = items;
        }

        @NonNull
        @Override
        public android.view.View getView(int position, @Nullable android.view.View convertView, @NonNull android.view.ViewGroup parent) {
            if (convertView == null) {
                convertView = android.view.LayoutInflater.from(getContext()).inflate(resourceLayout, parent, false);
            }

            Reclamation reclamation = items.get(position);

            android.widget.TextView tvTitle = convertView.findViewById(R.id.tv_title);
            android.widget.TextView tvEtat = convertView.findViewById(R.id.tv_etat);

            if (reclamation != null) {
                tvTitle.setText(reclamation.titre);
                tvEtat.setText(reclamation.etat);
            }

            return convertView;
        }
    }
}
