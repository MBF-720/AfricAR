
package pfe.africar.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pfe.africar.R;
import pfe.africar.classes.GradeAdapter;
import pfe.africar.classes.GradeInfo;

public class grade_sheet_activity extends Activity {
	private ListView gradesListView;

	private TextView studentName;
	private Button addGradeButton;
	private List<GradeInfo> gradesList;
	private GradeAdapter adapter;
	private FirebaseFirestore db;
private double overallAverage;
	private TextView moyenne;


//	private String selectedEleveId = "UNfQ0AtYQugZ8eXrENVe";

	private String selectedEleveId ;
	private String ecoleId="Vgv1obkaHUASn7Z8rI7I";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grade_sheet);

		gradesListView = findViewById(R.id.gradesListView);
		addGradeButton = findViewById(R.id.addGradeButton);
		studentName = findViewById(R.id.studentName);
		moyenne = findViewById(R.id.moyenne);

		db = FirebaseFirestore.getInstance();
		gradesList = new ArrayList<>();
		adapter = new GradeAdapter(this, gradesList);
		gradesListView.setAdapter(adapter);

		selectedEleveId=getIntent().getStringExtra("studentId");
		getStudentName(ecoleId, selectedEleveId);

		loadGrades();



		addGradeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(grade_sheet_activity.this, AjouterMatiere.class);
				intent.putExtra("eleveId", selectedEleveId);
				startActivity(intent);
			}
		});

		gradesListView.setOnItemClickListener((parent, view, position, id) -> {
			GradeInfo selectedGradeInfo = gradesList.get(position);
			Intent intent = new Intent(grade_sheet_activity.this, ModifyNote.class);
			intent.putExtra("eleveId", selectedEleveId);
			intent.putExtra("ecoleId", ecoleId);
			intent.putExtra("matiereId", selectedGradeInfo.getSubjectName());
			startActivity(intent);
		});

		calculateOverallAverage();



	}

	private void loadGrades() {
		db.collection("Ecoles").document(ecoleId).collection("Eleves").document(selectedEleveId).collection("notes")
				.get()
				.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
					@Override
					public void onComplete(@NonNull Task<QuerySnapshot> task) {
						if (task.isSuccessful()) {
							gradesList.clear();
							for (DocumentSnapshot document : task.getResult()) {
								String matiereId = document.getId();
								double averageGrade = document.getDouble("moyenne");
								gradesList.add(new GradeInfo(matiereId, averageGrade));
							}
							adapter.notifyDataSetChanged();
						} else {
							Toast.makeText(grade_sheet_activity.this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
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
							studentName.setText(nom + " " + prenom);
						} else {
							Toast.makeText(grade_sheet_activity.this, "Nom or prenom field is empty.", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(grade_sheet_activity.this, "No such document.", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(grade_sheet_activity.this, "Failed to get document: " + task.getException(), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void calculateOverallAverage() {
		db.collection("Ecoles").document(ecoleId).collection("Eleves").document(selectedEleveId).collection("notes")
				.get()
				.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
					@Override
					public void onComplete(@NonNull Task<QuerySnapshot> task) {
						if (task.isSuccessful()) {
							double totalWeightedSum = 0.0;
							double totalCoefficientSum = 0.0;

							for (DocumentSnapshot document : task.getResult()) {
								Double moyenne = document.getDouble("moyenne");
								Double matiereCoeff = document.getDouble("matiereCoeff");

								if (moyenne != null && matiereCoeff != null) {
									totalWeightedSum += moyenne * matiereCoeff;
									totalCoefficientSum += matiereCoeff;
								}
							}

							  overallAverage = 0.0;
							if (totalCoefficientSum != 0) {
								overallAverage = totalWeightedSum / totalCoefficientSum;
								String overallAverageStr = String.format("%.2f", overallAverage);
								moyenne.setText(overallAverageStr);

								// Update the student's overall average in the database
								updateStudentMoyenne(overallAverage, selectedEleveId);
							}

							// You can now use overallAverage as needed, for example:
							Toast.makeText(grade_sheet_activity.this, "Overall Average: " + overallAverage, Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(grade_sheet_activity.this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
						}
					}
				});
	}

	private void updateStudentMoyenne(double newMoyenne, String eleveId) {
		DocumentReference studentDocRef = db.collection("Ecoles").document(ecoleId).collection("Eleves").document(eleveId);

		// Create a map to hold the new value for the 'moyenne' field
		Map<String, Object> updates = new HashMap<>();
		updates.put("moyenne", newMoyenne);

		// Update the document
		studentDocRef.update(updates)
				.addOnCompleteListener(new OnCompleteListener<Void>() {
					@Override
					public void onComplete(@NonNull Task<Void> task) {
						if (task.isSuccessful()) {
							// Update successful
							Toast.makeText(grade_sheet_activity.this, "Moyenne updated successfully", Toast.LENGTH_SHORT).show();
						} else {
							// Update failed
							Toast.makeText(grade_sheet_activity.this, "Failed to update moyenne: " + task.getException(), Toast.LENGTH_SHORT).show();
						}
					}
				});
	}




	@Override
	protected void onResume() {
		super.onResume();
		loadGrades(); // Reload grades when the activity resumes
	}
}
