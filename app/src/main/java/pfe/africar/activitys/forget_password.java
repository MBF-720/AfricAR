
	 
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


	import static android.content.ContentValues.TAG;

	import android.app.Activity;
	import android.os.Bundle;
	import android.util.Log;
	import android.view.View;
	import android.widget.EditText;
	import android.widget.ImageView;
	import android.widget.Toast;

	import androidx.annotation.NonNull;

	import com.google.android.gms.tasks.OnCompleteListener;
	import com.google.android.gms.tasks.Task;
	import com.google.firebase.auth.FirebaseAuth;

	import pfe.africar.R;



	public class forget_password extends Activity {

		private ImageView backBtn;
		private EditText email;
		private View resetBtn;




	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgot_psw);

		backBtn=(ImageView)findViewById(R.id.uil_arrow_up_1_ek3);
		email=(EditText) findViewById(R.id.email);
		resetBtn=(View) findViewById(R.id._bg__component_1_ek3);

		backBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		resetBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String emailText = email.getText().toString();

				FirebaseAuth auth = FirebaseAuth.getInstance();


				auth.sendPasswordResetEmail(emailText)
						.addOnCompleteListener(new OnCompleteListener<Void>() {
							@Override
							public void onComplete(@NonNull Task<Void> task) {
								if (task.isSuccessful()) {
									Log.d(TAG, "Email sent.");
									Toast.makeText(forget_password.this, "Email sent", Toast.LENGTH_SHORT).show();

								}
							}
						});




			}
		});



		



	
		

	}
}
	
	