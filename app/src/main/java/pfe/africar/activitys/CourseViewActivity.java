package pfe.africar.activitys;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import pfe.africar.R;
import pfe.africar.helpers.EleveNavBar;

public class CourseViewActivity extends AppCompatActivity {

    private WebView webView;
    private Button downloadButton;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private String ecoleId;
    private String classId;
    private String subjectId;
    private String courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view);

        webView = findViewById(R.id.webview);
        downloadButton = findViewById(R.id.download_button);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        EleveNavBar.setupBottomNavigation(this, bottomNavigationView);

        ecoleId = getIntent().getStringExtra("ecoleId");
        classId = getIntent().getStringExtra("classId");
        subjectId = getIntent().getStringExtra("subjectId");
        courseId = getIntent().getStringExtra("courseId");

        loadCourseContent();

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadCourse();
            }
        });
    }

    private void loadCourseContent() {
        db.collection("Ecoles").document(ecoleId)
                .collection("Classes").document(classId)
                .collection("Matieres").document(subjectId)
                .collection("Cours").document(courseId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String courseUrl = document.getString("file");
                                if (courseUrl != null) {
                                    webView.loadUrl(courseUrl);
                                } else {
                                    Toast.makeText(CourseViewActivity.this, "Course URL not found", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(CourseViewActivity.this, "No such course document", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CourseViewActivity.this, "Failed to fetch course content", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void downloadCourse() {
        db.collection("Ecoles").document(ecoleId)
                .collection("Classes").document(classId)
                .collection("Matieres").document(subjectId)
                .collection("Cours").document(courseId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String courseUrl = document.getString("file");
                                if (courseUrl != null) {
                                    downloadFile(courseUrl);
                                } else {
                                    Toast.makeText(CourseViewActivity.this, "Course URL not found", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(CourseViewActivity.this, "No such course document", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CourseViewActivity.this, "Failed to fetch course content", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void downloadFile(String fileUrl) {
        StorageReference storageRef = storage.getReferenceFromUrl(fileUrl);
        File localFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "course.pdf");

        storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(CourseViewActivity.this, "Download completed", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(CourseViewActivity.this, "Download failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
