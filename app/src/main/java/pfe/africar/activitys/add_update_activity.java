
	 
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
import android.os.Bundle;


import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import pfe.africar.R;

	public class add_update_activity extends Activity {

	
	private View _bg__add_update_ek2;
	private TextView add_new_announcement;
	private View _bg__frame_138_ek1;
	private View _bg__frame_137_ek1;
	private View _bg__frame_136_ek1;
	private View _bg__medium_ek83;
	private View _bg__frame_7_ek107;
	private TextView label_ek49;
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
	private TextView label_ek50;
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
	private TextView label_ek53;
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

		private Calendar selectedDate = Calendar.getInstance();
		TextView birthDate;



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
			birthDate.setText(sdf.format(selectedDate.getTime()));
		}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_update);

		
		_bg__add_update_ek2 = (View) findViewById(R.id._bg__add_update_ek2);
		add_new_announcement = (TextView) findViewById(R.id.add_new_announcement);

		_bg__medium_ek83 = (View) findViewById(R.id._bg__medium_ek83);

		label_ek49 = (TextView) findViewById(R.id.label_ek49);


		_bg__medium_ek85 = (View) findViewById(R.id._bg__medium_ek85);

		label_ek50 = (TextView) findViewById(R.id.label_ek50);
		_bg__medium_ek87 = (View) findViewById(R.id._bg__medium_ek87);

		label_ek51 = (TextView) findViewById(R.id.label_ek51);

		schedule__ = (TextView) findViewById(R.id.schedule__);

		_bg__medium_ek89 = (View) findViewById(R.id._bg__medium_ek89);

		label_ek52 = (TextView) findViewById(R.id.label_ek52);
		_bg__medium_ek91 = (View) findViewById(R.id._bg__medium_ek91);

		label_ek53 = (TextView) findViewById(R.id.label_ek53);
		_bg__component_1_ek41 = (View) findViewById(R.id._bg__component_1_ek41);
		button_ek26 = (TextView) findViewById(R.id.button_ek26);
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
	
		
		//custom code goes here
	
	}
}
	
	