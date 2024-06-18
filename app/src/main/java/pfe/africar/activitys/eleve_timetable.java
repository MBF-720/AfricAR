package pfe.africar.activitys;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import pfe.africar.R;
import pfe.africar.helpers.EleveNavBar;

public class eleve_timetable extends AppCompatActivity {

    private WebView webView;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private String timetableUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleve_timetable);

        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        EleveNavBar.setupBottomNavigation(this, bottomNavigationView);

        Button downloadButton = findViewById(R.id.download_button);
        downloadButton.setOnClickListener(v -> {
            if (timetableUrl != null) {
                downloadFile(timetableUrl);
            } else {
                Toast.makeText(this, "Timetable URL not available", Toast.LENGTH_SHORT).show();
            }
        });

        fetchTimetable();
    }

    private void fetchTimetable() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = currentUser.getUid();
        db.collection("Users").document(uid).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        String ecoleID = documentSnapshot.getString("idEcole");
                        if (ecoleID != null) {
                            fetchClassTimetable(currentUser.getEmail(), ecoleID);
                        } else {
                            Toast.makeText(eleve_timetable.this, "Ecole ID not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(eleve_timetable.this, "Failed to fetch ecole ID", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fetchClassTimetable(String email, String ecoleId) {
        db.collection("Ecoles").document(ecoleId).collection("Eleves")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            String classId = document.getString("idClasse");
                            if (classId != null) {
                                loadTimetable(classId, ecoleId);
                            } else {
                                Toast.makeText(eleve_timetable.this, "Class ID not found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(eleve_timetable.this, "Error fetching user details: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadTimetable(String classId, String ecoleId) {
        db.collection("Ecoles").document(ecoleId).collection("Classes").document(classId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        String timetablePath = documentSnapshot.getString("timetable");
                        if (timetablePath != null) {
                            loadTimetableFromStorage(timetablePath);
                        } else {
                            Toast.makeText(eleve_timetable.this, "Timetable path not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(eleve_timetable.this, "Failed to fetch timetable", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadTimetableFromStorage(String timetablePath) {
        StorageReference storageRef = storage.getReferenceFromUrl(timetablePath);
        storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
            timetableUrl = uri.toString();
            webView.loadUrl(timetableUrl);
        }).addOnFailureListener(e -> {
            Toast.makeText(eleve_timetable.this, "Failed to fetch timetable URL", Toast.LENGTH_SHORT).show();
        });
    }

    private void downloadFile(String url) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle("Timetable");
        request.setDescription("Downloading timetable");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "timetable.pdf");

        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        if (downloadManager != null) {
            downloadManager.enqueue(request);
            Toast.makeText(this, "Download started", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to get DownloadManager", Toast.LENGTH_SHORT).show();
        }
    }
}
