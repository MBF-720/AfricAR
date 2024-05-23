
	 
	/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		enter_id
	 *	@date 		Thursday 25th of April 2024 10:03:48 AM
	 *	@title 		Page 1
	 *	@author 	
	 *	@keywords 	
	 *	@generator 	Export Kit v1.3.figma
	 *
	 */


	package pfe.africar.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import pfe.africar.R;

	public class grade_sheet_activity extends Activity {
	private View _bg__grade_sheet_ek2;
	private TextView grade_sheet_ek3;

	private ImageView ellipse_36_ek1;
	private TextView flen_el_fouleni_ek4;
	private ImageView rectangle_32_ek13;
	private TextView updates;
	private TextView stats;
	private TextView personnel;
	private TextView school;
	private ImageView vector_ek643;
	private ImageView vector_ek644;
	private ImageView vector_ek645;
	private ImageView vector_ek646;
		private ListView gradesListView;
		private Button addGradeButton;
		private List<String> gradesList;
		private ArrayAdapter<String> adapter;
		private FirebaseFirestore db;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.grade_sheet);

		_bg__grade_sheet_ek2 = (View) findViewById(R.id._bg__grade_sheet_ek2);
		grade_sheet_ek3 = (TextView) findViewById(R.id.grade_sheet_ek3);

		ellipse_36_ek1 = (ImageView) findViewById(R.id.ellipse_36_ek1);
		flen_el_fouleni_ek4 = (TextView) findViewById(R.id.flen_el_fouleni_ek4);

		rectangle_32_ek13 = (ImageView) findViewById(R.id.rectangle_32_ek13);
		updates = (TextView) findViewById(R.id.updates);
		stats = (TextView) findViewById(R.id.stats);
		personnel = (TextView) findViewById(R.id.personnel);
		school = (TextView) findViewById(R.id.school);
		vector_ek643 = (ImageView) findViewById(R.id.vector_ek643);
		vector_ek644 = (ImageView) findViewById(R.id.vector_ek644);
		vector_ek645 = (ImageView) findViewById(R.id.vector_ek645);
		vector_ek646 = (ImageView) findViewById(R.id.vector_ek646);
		gradesListView = findViewById(R.id.gradesListView);
		addGradeButton = findViewById(R.id.addGradeButton);

		db = FirebaseFirestore.getInstance();
		gradesList = new ArrayList<>();
		adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gradesList);
		gradesListView.setAdapter(adapter);

		loadGrades();
		addGradeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Intent to start AddGradeActivity
				Intent intent = new Intent(grade_sheet_activity.this, AddGradeActivity.class);
				startActivity(intent);
			}
		});
		//custom code goes here
		gradesListView.setOnItemClickListener((parent, view, position, id) -> {
			// Handle click event to modify or delete grade
			String selectedGrade = gradesList.get(position);
			Intent intent = new Intent(grade_sheet_activity.this, EditGradeActivity.class);
			intent.putExtra("gradeId", selectedGrade);
			startActivity(intent);
		});
	}


		private void loadGrades() {
			db.collection("grades").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
				@Override
				public void onComplete(@NonNull Task<QuerySnapshot> task) {
					if (task.isSuccessful()) {
						gradesList.clear();
						for (DocumentSnapshot document : task.getResult()) {
							String grade = document.getString("grade");
							gradesList.add(grade);
						}
						adapter.notifyDataSetChanged();
					} else {
						Toast.makeText(grade_sheet_activity.this, "Failed to load grades", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}



	}

	
	