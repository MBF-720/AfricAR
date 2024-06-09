
	 
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
	import android.content.Intent;
	import android.os.Bundle;
	import android.util.Log;
	import android.view.View;
	import android.widget.ImageView;
	import android.widget.TextView;

	import androidx.annotation.NonNull;

	import com.google.android.gms.tasks.OnFailureListener;
	import com.google.android.gms.tasks.OnSuccessListener;
	import com.google.firebase.auth.FirebaseAuth;
	import com.google.firebase.auth.FirebaseUser;
	import com.google.firebase.firestore.DocumentReference;
	import com.google.firebase.firestore.DocumentSnapshot;
	import com.google.firebase.firestore.FirebaseFirestore;

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
		FirebaseFirestore db;




	@Override
		public void onStart() {
			super.onStart();
			// Check if user is signed in (non-null) and update UI accordingly.
			FirebaseUser currentUser = mAuth.getCurrentUser();
			if(currentUser != null){
				updateUI(currentUser);
			}
		}
		private void updateUI(FirebaseUser user) {
			if (user != null) {
				String userId = user.getUid();

				DocumentReference userDocRef = db.collection("Users").document(userId);

				userDocRef.get()
						.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
							@Override
							public void onSuccess(DocumentSnapshot documentSnapshot) {
								if (documentSnapshot.exists()) {
									String statue = documentSnapshot.getString("statue");
									if (statue != null) {
										switch (statue) {
											case "Eleve":

												startActivity(new Intent(onboarding_screen_activity.this, acceille_activity.class));
												break;
											case "Professeur":
												startActivity(new Intent(onboarding_screen_activity.this, add_cours_activity.class));
												break;
											default:
												Log.d(TAG, "Unknown statue: " + statue);
												break;
										}
									} else {
										Log.d(TAG, "Statue not found in user metadata.");
									}
								} else {
									Log.d(TAG, "User metadata not found.");
									// Handle missing user metadata
								}
							}
						})
						.addOnFailureListener(new OnFailureListener() {
							@Override
							public void onFailure(@NonNull Exception e) {
								Log.e(TAG, "Error getting user metadata: ", e);
								// Handle error and provide feedback to the user
							}
						});
			} else {
				Log.d(TAG, "Current user is null.");
				// Handle unauthenticated user
			}
		}








		@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.onboarding_screen);

		


		happy_student_bro_1 = (ImageView) findViewById(R.id.happy_student_bro_1);
		let_s_you_in = (TextView) findViewById(R.id.let_s_you_in);


		loginButton = (View) findViewById(R.id.google_sign_in_button);
		button_ek1 = (TextView) findViewById(R.id.button_ek1);
		don_t_have_an_account__sign_up = (TextView) findViewById(R.id.don_t_have_an_account__sign_up);

			mAuth = FirebaseAuth.getInstance();

			// Initialize Firebase Firestore
			db = FirebaseFirestore.getInstance();









		don_t_have_an_account__sign_up.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent=new Intent(onboarding_screen_activity.this, create_acount__inactive_state__activity.class);
			startActivity(intent);
		}



	});
//todo app is so slow here
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(onboarding_screen_activity.this, log_in_inactive_state__activity.class);
				startActivity(intent);
			}




		});














		}

}
	
	