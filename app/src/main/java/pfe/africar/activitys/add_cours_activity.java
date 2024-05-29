
	 
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

	public class add_cours_activity extends Activity {

	
	private View _bg__add_cours_ek2;
	private TextView add_new_course;
	private View _bg__frame_113_ek1;
	private View _bg__frame_112_ek1;
	private View _bg__frame_110_ek1;
	private TextView course_details__;
	private View _bg__frame_108_ek1;
	private View _bg__medium_ek3;
	private View _bg__frame_7_ek25;
	private EditText label_ek9;
	private View _bg__medium_1icon_ek7;
	private View _bg__material_symbols_upload_ek1;
	private ImageView vector_ek597;
	private View _bg__frame_7_ek27;
	private TextView upload_file;
	private View _bg__frame_111_ek1;
	private TextView course_schedule__;
	private View _bg__frame_109_ek1;
	private View _bg__medium_ek5;
	private View _bg__frame_7_ek29;
	private TextView label_ek10;
	private View _bg__medium_ek7;
	private View _bg__frame_7_ek31;
	private TextView label_ek11;
	private View _bg__component_1_ek19;
	private TextView button_ek13;
	private ImageView x_1_ek6;
	private ImageView rectangle_32_ek7;
	private TextView academics_ek5;
	private TextView classroom_ek1;
	private TextView discover_ek8;
	private TextView profile_ek9;
	private View _bg__group_52_ek17;
	private ImageView vector_ek598;
	private ImageView vector_ek599;
	private ImageView vector_ek600;
	private ImageView vector_ek601;
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
			label_ek10.setText(sdf.format(selectedDate.getTime()));
		}

		@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_cours);

		
		_bg__add_cours_ek2 = (View) findViewById(R.id._bg__add_cours_ek2);
		add_new_course = (TextView) findViewById(R.id.add_new_course);

		course_details__ = (TextView) findViewById(R.id.course_details__);
		_bg__medium_ek3 = (View) findViewById(R.id._bg__medium_ek3);
		label_ek9 = (EditText) findViewById(R.id.label_ek9);
		vector_ek597 = (ImageView) findViewById(R.id.vector_ek597);
		upload_file = (TextView) findViewById(R.id.upload_file);
			upload_file.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					uploadFile(v);
				}
			});
		course_schedule__ = (TextView) findViewById(R.id.course_schedule__);
		_bg__medium_ek5 = (View) findViewById(R.id._bg__medium_ek5);
		label_ek10 = (TextView) findViewById(R.id.label_ek10);
			label_ek10.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					showDatePicker();
				}
			});
		_bg__medium_ek7 = (View) findViewById(R.id._bg__medium_ek7);
		label_ek11 = (TextView) findViewById(R.id.label_ek11);
		_bg__component_1_ek19 = (View) findViewById(R.id._bg__component_1_ek19);
		button_ek13 = (TextView) findViewById(R.id.button_ek13);
			button_ek13.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					submitForm();
				}
			});


				x_1_ek6 = (ImageView) findViewById(R.id.x_1_ek6);
				rectangle_32_ek7 = (ImageView) findViewById(R.id.rectangle_32_ek7);
				academics_ek5 = (TextView) findViewById(R.id.academics_ek5);
				classroom_ek1 = (TextView) findViewById(R.id.classroom_ek1);
				discover_ek8 = (TextView) findViewById(R.id.discover_ek8);
				profile_ek9 = (TextView) findViewById(R.id.profile_ek9);
				_bg__group_52_ek17 = (View) findViewById(R.id._bg__group_52_ek17);
				vector_ek598 = (ImageView) findViewById(R.id.vector_ek598);
				vector_ek599 = (ImageView) findViewById(R.id.vector_ek599);
				vector_ek600 = (ImageView) findViewById(R.id.vector_ek600);
				vector_ek601 = (ImageView) findViewById(R.id.vector_ek601);


				//custom code goes here

			};

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
			String title = label_ek9.getText().toString();
			String date =label_ek10.getText().toString();
			String time = label_ek11.getText().toString();
			String file= upload_file.getText().toString();
			if (title.isEmpty() || file.isEmpty() || date.isEmpty() || time.isEmpty()) {
				Toast.makeText(this, "Please fill all fields correctly.", Toast.LENGTH_LONG).show();
				return;
			}

			Map<String, Object> data = new HashMap<>();
			data.put("title", title);
			data.put("file",file);
			data.put("date", date);
			data.put("time", time);
			String schoolId = "actualité_de_l'école";  //
			db.collection("Ecoles").document("Vgv1obkaHUASn7Z8rI7I").collection("Classes").document("CPY5KGWxBex1B5rHnFEb").collection("Matieres").document("BAe7Ylf1Zbn9SOxsyWfy").collection("Cours").document("wMEKttamiaJKtSBda6R0")
					.set(data)
					.addOnSuccessListener(documentReference -> {
						Toast.makeText(add_cours_activity.this, "cours added successfully!", Toast.LENGTH_SHORT).show();
						// Optionally clear the form here or navigate away
						Intent intent = new Intent(add_cours_activity.this, prof_cour_list_activity.class);
						startActivity(intent);
					})
					.addOnFailureListener(e -> Toast.makeText(add_cours_activity.this, "Error adding announcement.", Toast.LENGTH_SHORT).show());
		}
	}