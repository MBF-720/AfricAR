
	 
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
	import android.widget.ImageView;
	import android.widget.TextView;

	import com.google.firebase.auth.FirebaseAuth;
	import com.google.firebase.auth.FirebaseUser;

	import pfe.africar.R;

	public class onboarding_screen_activity extends Activity {

	FirebaseAuth mAuth;
	FirebaseUser mUser;
	private View loginButton;
	private View _bg__frame_1_ek1;
	private View _bg__frame_5_ek1;
	private View _bg__frame_2_ek1;
	private ImageView happy_student_bro_1;
	private TextView let_s_you_in;
	private View _bg__frame_3_ek1;
	private ImageView facebook_login_1;
	private ImageView google_login;
	private ImageView apple_login;
	private View _bg__frame_4_ek1;
	private ImageView line_1;
	private TextView or;
	private ImageView line_2;
	private View _bg__component_1_ek1;
	private TextView button_ek1;
	private TextView don_t_have_an_account__sign_up;


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.onboarding_screen);

		


		happy_student_bro_1 = (ImageView) findViewById(R.id.happy_student_bro_1);
		let_s_you_in = (TextView) findViewById(R.id.let_s_you_in);


		loginButton = (View) findViewById(R.id.google_sign_in_button);
		button_ek1 = (TextView) findViewById(R.id.button_ek1);
		don_t_have_an_account__sign_up = (TextView) findViewById(R.id.don_t_have_an_account__sign_up);
	
		
		//custom code goes here
		don_t_have_an_account__sign_up.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent=new Intent(onboarding_screen_activity.this, log_in_as__activity.class);
			startActivity(intent);
		}



	});

		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(onboarding_screen_activity.this, fill_profile_activity.class);
				startActivity(intent);
			}



		});
	}

}
	
	