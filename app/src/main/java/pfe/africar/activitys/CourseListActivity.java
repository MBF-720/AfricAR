// CourseListActivity.java
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

public class CourseListActivity extends AppCompatActivity {

    private ListView courseListView;
    private ArrayAdapter<String> adapter;
    private List<String> courses = new ArrayList<>();
    private List<String> courseIds = new ArrayList<>();
    private FirebaseFirestore db;
    private String ecoleId;
    private String classId;
    private String subjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        courseListView = findViewById(R.id.courseListView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courses);
        courseListView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        ecoleId = getIntent().getStringExtra("ecoleId");
        classId = getIntent().getStringExtra("classId");
        subjectId = getIntent().getStringExtra("subjectId");

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        EleveNavBar.setupBottomNavigation(this, bottomNavigationView);

        loadCourses();

        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String courseId = courseIds.get(position);
                Intent intent = new Intent(CourseListActivity.this, CourseViewActivity.class);
                intent.putExtra("ecoleId", ecoleId);
                intent.putExtra("classId", classId);
                intent.putExtra("subjectId", subjectId);
                intent.putExtra("courseId", courseId);
                startActivity(intent);
            }
        });
    }

    private void loadCourses() {
        db.collection("Ecoles").document(ecoleId).collection("Classes").document(classId).collection("Matieres").document(subjectId).collection("Cours")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                courses.add(document.getString("title"));
                                courseIds.add(document.getId());
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(CourseListActivity.this, "Failed to fetch courses", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
