package pfe.africar.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import pfe.africar.R;
import pfe.africar.classes.AbsenceAdapter;

public class AbsenceList extends AppCompatActivity {

    private ListView absenceListView;
    private List<DocumentSnapshot> absencesList;
    private AbsenceAdapter adapter;
    private FirebaseFirestore db;


    @Override
    protected void onResume() {
        super.onResume();
        // Clear the list and reload the data
        absencesList.clear();
        loadAbsences();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absence_list);

        db = FirebaseFirestore.getInstance();
        absenceListView = findViewById(R.id.absenceListView);
        absencesList = new ArrayList<>();
        adapter = new AbsenceAdapter(this, absencesList);
        absenceListView.setAdapter(adapter);


      //  loadAbsences();

        absenceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DocumentSnapshot selectedSnapshot = (DocumentSnapshot) view.getTag();
                Intent intent = new Intent(AbsenceList.this, AbsenceDetailActivity.class);
                intent.putExtra("absenceDocument", selectedSnapshot.getId());
                startActivity(intent);
            }
        });
    }

    private void loadAbsences() {
        CollectionReference absencesRef = db.collection("Ecoles").document("Vgv1obkaHUASn7Z8rI7I")
                .collection("Reclamations").document("Absences").collection("Liste absence");
        absencesRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    absencesList.add(document);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }





}
