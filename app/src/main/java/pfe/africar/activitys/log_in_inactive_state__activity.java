
	 


	package pfe.africar.activitys;

	import static android.content.ContentValues.TAG;

	import android.annotation.SuppressLint;
	import android.app.Activity;
	import android.content.Context;
	import android.content.Intent;
	import android.os.Bundle;
	import android.text.InputType;
	import android.util.Log;
	import android.view.KeyEvent;
	import android.view.View;
	import android.view.inputmethod.EditorInfo;
	import android.view.inputmethod.InputMethodManager;
	import android.widget.EditText;
	import android.widget.ImageView;
	import android.widget.TextView;
	import android.widget.Toast;

	import androidx.annotation.NonNull;

	import com.google.android.gms.tasks.OnCompleteListener;
	import com.google.android.gms.tasks.OnFailureListener;
	import com.google.android.gms.tasks.OnSuccessListener;
	import com.google.android.gms.tasks.Task;
	import com.google.firebase.auth.AuthResult;
	import com.google.firebase.auth.FirebaseAuth;
	import com.google.firebase.auth.FirebaseUser;
	import com.google.firebase.firestore.DocumentReference;
	import com.google.firebase.firestore.DocumentSnapshot;
	import com.google.firebase.firestore.FirebaseFirestore;

	import pfe.africar.R;



	public class log_in_inactive_state__activity extends Activity {


		private ImageView backbtn;

		private EditText email;
		private EditText password;
		private ImageView vector_ek6;
		private TextView forgot_password;
		private View button;


		private FirebaseAuth mAuth;
		FirebaseFirestore db;

		private void login(String email, String password){


			mAuth.signInWithEmailAndPassword(email, password)
					.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
						@Override
						public void onComplete(@NonNull Task<AuthResult> task) {
							if (task.isSuccessful()) {
								// Sign in success, update UI with the signed-in user's information
								Log.d(TAG, "signInWithEmail:success");
								FirebaseUser user = mAuth.getCurrentUser();
								Toast.makeText(log_in_inactive_state__activity.this, "Welcome",Toast.LENGTH_SHORT).show();

										updateUI(user);
							} else {
								// If sign in fails, display a message to the user.
								Log.w(TAG, "signInWithEmail:failure", task.getException());

								updateUI(null);
							}
						}
					});
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
												Toast.makeText(log_in_inactive_state__activity.this, "Welcome student!", Toast.LENGTH_SHORT).show();

												startActivity(new Intent(log_in_inactive_state__activity.this, acceille_activity.class));
												break;
											case "Professeur":
												startActivity(new Intent(log_in_inactive_state__activity.this, add_cours_activity.class));
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


	@SuppressLint("MissingInflatedId")
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_in_inactive_state_);

		

		backbtn = (ImageView) findViewById(R.id.uil_arrow_up_1_ek2);

		email = (EditText) findViewById(R.id.label_ek3);//


		password = (EditText) findViewById(R.id.label_ek4);//
		vector_ek6 = (ImageView) findViewById(R.id.vector_ek6);//

		forgot_password = (TextView) findViewById(R.id.forgot_password_);//
		button = (View) findViewById(R.id._bg__component_2_ek5);//

		mAuth = FirebaseAuth.getInstance();

		// Initialize Firebase Firestore
		 db = FirebaseFirestore.getInstance();
//pwd visibility
		vector_ek6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Récupérer le type d'entrée actuel du champ de mot de passe
				int inputType = password.getInputType();

				// Vérifier si le mot de passe est actuellement visible
				boolean isPasswordVisible = (inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

				// Basculer la visibilité du mot de passe
				if (isPasswordVisible) {
					// Si le mot de passe est actuellement visible, le rendre invisible
					password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					// Mettre à jour l'icône pour indiquer un mot de passe masqué

				} else {
					// Si le mot de passe est actuellement masqué, le rendre visible
					password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					// Mettre à jour l'icône pour indiquer un mot de passe visible
				}

				// Déplacer le curseur à la fin du texte
				password.setSelection(password.getText().length());
			}
		});
//pswd enter=> clavier disparu
		password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE || (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					// Hide the keyboard
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
					return true;
				}
				return false;
			}
		});


		backbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String emailtext = email.getText().toString().trim();
				String passwordtext = password.getText().toString().trim();

				if (!emailtext.isEmpty() && !passwordtext.isEmpty()) {
					login(emailtext, passwordtext);
				} else {
					Toast.makeText(log_in_inactive_state__activity.this, "Please enter your email and password", Toast.LENGTH_SHORT).show();
				}
			}
		});

		forgot_password.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(log_in_inactive_state__activity.this, forget_password.class));


			}
		});


	

	}
}
	
	