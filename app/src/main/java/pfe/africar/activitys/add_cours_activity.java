package pfe.africar.activitys;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import pfe.africar.R;

public class add_cours_activity extends Activity {

	private View _bg__add_cours_ek2;
	private TextView add_new_course;
	private EditText label_ek9;
	private TextView upload_file;
	private TextView label_ek10;
	private TextView label_ek11;
	private TextView button_ek13;
	private FirebaseFirestore db;
	private StorageReference storageRef;
	private Calendar selectedDate = Calendar.getInstance();
	private static final int FILE_SELECT_CODE = 0;
	private Uri fileUri;

	private String ecoleId;
	private String classeId;
	private String matiereId;
	private String courId;
	private String userId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_cours);

		db = FirebaseFirestore.getInstance();
		storageRef = FirebaseStorage.getInstance().getReference();

		// Retrieve IDs from intent extras
		ecoleId = getIntent().getStringExtra("ecoleId");
		classeId = getIntent().getStringExtra("classeId");
		matiereId = getIntent().getStringExtra("matiereId");

		if (ecoleId == null || classeId == null || matiereId == null) {
			Toast.makeText(this, "Missing parameters", Toast.LENGTH_SHORT).show();
			Toast.makeText(this, "ecole+"+ecoleId, Toast.LENGTH_SHORT).show();
			Toast.makeText(this, "classe"+classeId, Toast.LENGTH_SHORT).show();
			Toast.makeText(this, "matiere"+matiereId, Toast.LENGTH_SHORT).show();
			finish();
			return;
		}

		// Get current user ID
		FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
		if (currentUser != null) {
			userId = currentUser.getEmail();
		} else {
			Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}

		// Initialize UI elements
		add_new_course = findViewById(R.id.add_new_course);
		label_ek9 = findViewById(R.id.label_ek9);
		upload_file = findViewById(R.id.upload_file);
		label_ek10 = findViewById(R.id.label_ek10);
		label_ek11 = findViewById(R.id.label_ek11);
		button_ek13 = findViewById(R.id.button_ek13);

		// Set single line for EditText programmatically
		label_ek9.setSingleLine(true);
		label_ek10.setSingleLine(true);
		label_ek11.setSingleLine(true);

		// Set listeners
		upload_file.setOnClickListener(this::uploadFile);
		label_ek10.setOnClickListener(v -> showDatePicker());
		button_ek13.setOnClickListener(v -> submitForm());
	}

	private void showDatePicker() {
		DatePickerDialog datePickerDialog = new DatePickerDialog(
				this,
				(view, year, monthOfYear, dayOfMonth) -> {
					selectedDate.set(year, monthOfYear, dayOfMonth);
					updateDateInView();
				},
				selectedDate.get(Calendar.YEAR),
				selectedDate.get(Calendar.MONTH),
				selectedDate.get(Calendar.DAY_OF_MONTH)
		);

		datePickerDialog.show();
	}

	private void updateDateInView() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		label_ek10.setText(sdf.format(selectedDate.getTime()));
	}

	public void uploadFile(View view) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);

		try {
			startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK) {
			// Get the Uri of the selected file
			fileUri = data.getData();
			Log.d("File Uri", "File Uri: " + fileUri.toString());
		}
	}

	public void submitForm() {
		String title = label_ek9.getText().toString();
		String date = label_ek10.getText().toString();
		String time = label_ek11.getText().toString();

		if (title.isEmpty() || fileUri == null || date.isEmpty() || time.isEmpty()) {
			Toast.makeText(this, "Please fill all fields correctly.", Toast.LENGTH_LONG).show();
			return;
		}

		// Upload file to Firebase Storage
		StorageReference fileRef = storageRef.child("cours/" + fileUri.getLastPathSegment());
		fileRef.putFile(fileUri)
				.addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
					String fileUrl = uri.toString();
					saveCourseToFirestore(title, date, time, fileUrl);
				}))
				.addOnFailureListener(e -> Toast.makeText(add_cours_activity.this, "Error uploading file.", Toast.LENGTH_SHORT).show());
	}

	private void saveCourseToFirestore(String title, String date, String time, String fileUrl) {
		Map<String, Object> data = new HashMap<>();
		data.put("title", title);
		data.put("file", fileUrl);
		data.put("date", date);
		data.put("time", time);
		data.put("userId", userId);

		// Generate a new courId if not provided
		if (courId == null) {
			courId = db.collection("Ecoles").document(ecoleId)
					.collection("Classes").document(classeId)
					.collection("Matieres").document(matiereId)
					.collection("Cours").document().getId();
		}

		db.collection("Ecoles").document(ecoleId)
				.collection("Classes").document(classeId)
				.collection("Matieres").document(matiereId)
				.collection("Cours").document(courId)
				.set(data)
				.addOnSuccessListener(documentReference -> {
					Toast.makeText(add_cours_activity.this, "Course added successfully!", Toast.LENGTH_SHORT).show();

					finish(); // Optional: Finish current activity
				})
				.addOnFailureListener(e -> Toast.makeText(add_cours_activity.this, "Error adding course.", Toast.LENGTH_SHORT).show());
	}
}
