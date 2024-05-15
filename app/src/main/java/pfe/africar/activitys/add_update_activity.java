
	 
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
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import pfe.africar.R;

	public class add_update_activity extends Activity {

	
	private View _bg__add_update_ek2;
	private TextView add_new_announcement;
	private View _bg__frame_138_ek1;
	private View _bg__frame_137_ek1;
	private View _bg__frame_136_ek1;
	private View _bg__medium_ek83;
	private View _bg__frame_7_ek107;
	private EditText label_ek49;
	private View _bg__frame_135_ek1;
	private TextView category;
	private View _bg__frame_134_ek1;
	private View _bg__frame_1_ek19;
	private TextView assignments_ek2;
	private View _bg__frame_2_ek3;
	private TextView events_ek2;
	private View _bg__frame_3_ek3;
	private TextView activities_ek2;
	private View _bg__frame_4_ek3;
	private TextView reminders;
	private View _bg__frame_5_ek3;
	private TextView opportunities_ek2;
	private View _bg__frame_6_ek9;
	private TextView notices;
	private View _bg__medium_ek85;
	private View _bg__frame_7_ek109;
	private EditText label_ek50;
	private View _bg__medium_ek87;
	private View _bg__frame_7_ek111;
	private TextView label_ek51;
	private View _bg__frame_111_ek3;
	private TextView schedule__;
	private View _bg__frame_109_ek3;
	private View _bg__medium_ek89;
	private View _bg__frame_7_ek113;
	private TextView label_ek52;
	private View _bg__medium_ek91;
	private View _bg__frame_7_ek115;
	private EditText label_ek53;
	private View _bg__component_1_ek41;
	private TextView button_ek26;
	private ImageView x_1_ek23;
	private ImageView rectangle_32_ek24;
	private TextView updates_ek11;
	private TextView stats_ek11;
	private TextView personnel_ek13;
	private TextView school_ek11;
	private ImageView vector_ek755;
	private ImageView vector_ek756;
	private ImageView vector_ek757;
	private ImageView vector_ek758;
		private FirebaseFirestore db;
		private Calendar selectedDate = Calendar.getInstance();
		TextView birthDate;
		private static final int FILE_SELECT_CODE = 0;


		private void showDatePicker() {
			// Créer un DatePickerDialog avec la date actuelle
			DatePickerDialog datePickerDialog = new DatePickerDialog(
					this,
					new DatePickerDialog.OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
							// Mettre à jour la date sélectionnée lorsque l'utilisateur choisit une nouvelle date
							selectedDate.set(year, monthOfYear, dayOfMonth);
							// Mettre à jour le champ de texte avec la date sélectionnée
							updateDateInView();
						}
					},
					// Définir l'année, le mois et le jour actuels comme date par défaut dans le sélecteur de date
					selectedDate.get(Calendar.YEAR),
					selectedDate.get(Calendar.MONTH),
					selectedDate.get(Calendar.DAY_OF_MONTH)
			);

			// Afficher le sélecteur de date
			datePickerDialog.show();
		}

		private void updateDateInView() {
			// Mettre à jour le champ de texte "birthDate" avec la date sélectionnée
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
			label_ek52.setText(sdf.format(selectedDate.getTime()));
		}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_update);

		
		_bg__add_update_ek2 = (View) findViewById(R.id._bg__add_update_ek2);
		add_new_announcement = (TextView) findViewById(R.id.add_new_announcement);

		_bg__medium_ek83 = (View) findViewById(R.id._bg__medium_ek83);

		label_ek49 = (EditText) findViewById(R.id.label_ek49);


		_bg__medium_ek85 = (View) findViewById(R.id._bg__medium_ek85);

		label_ek50 = (EditText) findViewById(R.id.label_ek50);
		_bg__medium_ek87 = (View) findViewById(R.id._bg__medium_ek87);

		label_ek51 = (TextView) findViewById(R.id.label_ek51);
		label_ek51.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				uploadFile(v);
			}
		});


		schedule__ = (TextView) findViewById(R.id.schedule__);

		_bg__medium_ek89 = (View) findViewById(R.id._bg__medium_ek89);

		label_ek52 = (TextView) findViewById(R.id.label_ek52);
		label_ek52.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDatePicker();
			}
		});


		_bg__medium_ek91 = (View) findViewById(R.id._bg__medium_ek91);

		label_ek53 = (EditText) findViewById(R.id.label_ek53);
		_bg__component_1_ek41 = (View) findViewById(R.id._bg__component_1_ek41);
		button_ek26 = (TextView) findViewById(R.id.button_ek26);
		button_ek26.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				submitForm();
			}
		});

		x_1_ek23 = (ImageView) findViewById(R.id.x_1_ek23);
		rectangle_32_ek24 = (ImageView) findViewById(R.id.rectangle_32_ek24);
		updates_ek11 = (TextView) findViewById(R.id.updates_ek11);
		stats_ek11 = (TextView) findViewById(R.id.stats_ek11);
		personnel_ek13 = (TextView) findViewById(R.id.personnel_ek13);
		school_ek11 = (TextView) findViewById(R.id.school_ek11);
		vector_ek755 = (ImageView) findViewById(R.id.vector_ek755);
		vector_ek756 = (ImageView) findViewById(R.id.vector_ek756);
		vector_ek757 = (ImageView) findViewById(R.id.vector_ek757);
		vector_ek758 = (ImageView) findViewById(R.id.vector_ek758);
		 db = FirebaseFirestore.getInstance();
		
		//custom code goes here
	
	}

		public void uploadFile(View view) {
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("*/*");  // Utilisez "image/*" pour les images uniquement
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
				Uri uri = data.getData();
				Log.d("File Uri", "File Uri: " + uri.toString());
				// Use the Uri to load the file
				// Vous pouvez ici écrire le code pour gérer le fichier, par exemple l'afficher ou le charger vers un serveur
			}
		}


		public void submitForm() {
			String title = label_ek49.getText().toString();
			String description =label_ek50.getText().toString();
			String date =label_ek52.getText().toString();
			String time = label_ek53.getText().toString();

			if (title.isEmpty() || description.isEmpty() || date.isEmpty() || time.isEmpty()) {
				Toast.makeText(this, "Please fill all fields correctly.", Toast.LENGTH_LONG).show();
				return;
			}

			Map<String, Object> data = new HashMap<>();
			data.put("title", title);
			data.put("description", description);
			data.put("date", date);
			data.put("time", time);
			String schoolId = "actualité_de_l'école";  //
			db.collection("Ecoles").document("Vgv1obkaHUASn7Z8rI7I").collection("Actualités")
					.add(data)
					.addOnSuccessListener(documentReference -> {
						Toast.makeText(add_update_activity.this, "Announcement added successfully!", Toast.LENGTH_SHORT).show();
						// Optionally clear the form here or navigate away
					})
					.addOnFailureListener(e -> Toast.makeText(add_update_activity.this, "Error adding announcement.", Toast.LENGTH_SHORT).show());
		}
		}
//todo tzid el acceil
// todo upload file to db and set text by filename

	
	