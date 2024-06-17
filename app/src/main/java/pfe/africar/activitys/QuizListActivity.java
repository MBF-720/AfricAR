package pfe.africar.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import pfe.africar.R;
import pfe.africar.helpers.EleveNavBar;

public class QuizListActivity extends AppCompatActivity {

    private ListView quizListView;
    private FirebaseFirestore db;
    private List<String> quizTitles = new ArrayList<>();
    private List<String> quizIds = new ArrayList<>();
    private String ecoleId;
    private String classId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);

        quizListView = findViewById(R.id.quizListView);
        db = FirebaseFirestore.getInstance();

        ecoleId = "Vgv1obkaHUASn7Z8rI7I";
        classId = "CPY5KGWxBex1B5rHnFEb";

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        EleveNavBar.setupBottomNavigation(this, bottomNavigationView);

        loadQuizzes();

        quizListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(QuizListActivity.this, QuestionViewActivity.class);
                intent.putExtra("ecoleId", ecoleId);
                intent.putExtra("classId", classId);
                intent.putExtra("quizId", quizIds.get(position));
                startActivity(intent);
            }
        });
    }

    private void loadQuizzes() {
        db.collection("Ecoles").document(ecoleId)
                .collection("Classes").document(classId)
                .collection("Quizzes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                quizTitles.add(document.getString("title"));
                                quizIds.add(document.getId());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(QuizListActivity.this, android.R.layout.simple_list_item_1, quizTitles);
                            quizListView.setAdapter(adapter);
                        } else {
                            Toast.makeText(QuizListActivity.this, "Failed to load quizzes", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
