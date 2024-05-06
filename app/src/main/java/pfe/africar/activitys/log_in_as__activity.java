
	 



	package pfe.africar.activitys;

	import android.app.Activity;
	import android.content.Intent;
	import android.graphics.Color;
	import android.os.Bundle;
	import android.view.View;
	import android.widget.TextView;
	import android.widget.Toast;

	import pfe.africar.R;

	public class log_in_as__activity extends Activity {

	

	private View student;


	private View teacher;



	private View button_ek19;

	private String statu;
	private TextView teachertext;
		private TextView studentText;




	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_in_as_);

		

		student = (View) findViewById(R.id._bg__component_12_ek9);
		teacher = (View) findViewById(R.id._bg__component_13_ek3);

		teachertext = (TextView) findViewById(R.id.teacher);
		studentText = (TextView) findViewById(R.id.studentText);

		button_ek19 = (View) findViewById(R.id._bg__component_2_ek19);



		statu="";
		student.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				 statu = "Eleve";
				studentText.setTextColor(Color.parseColor("#207E5A"));
				teachertext.setTextColor(Color.parseColor("#5A6D67"));



			}
		});

		teacher.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				 statu = "Professeur";
				teachertext.setTextColor(Color.parseColor("#207E5A"));
				studentText.setTextColor(Color.parseColor("#5A6D67"));




			}
		});

	button_ek19.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if(!statu.isEmpty()){

//todo if stat= eleve => idclasse

			Intent intent = new Intent(log_in_as__activity.this, enter_id_activity.class);
			intent.putExtra("statu", statu);
			startActivity(intent);}

			else {
				Toast.makeText(log_in_as__activity.this,"choose your statu",Toast.LENGTH_LONG).show();
			}




		}
	});





		

	
	}
}
	
	