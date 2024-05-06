
	 
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
	import com.google.android.gms.tasks.Task;
	import com.google.firebase.auth.AuthResult;
	import com.google.firebase.auth.FirebaseAuth;
	import com.google.firebase.auth.FirebaseUser;
	import com.google.firebase.auth.SignInMethodQueryResult;

	import pfe.africar.R;

	public class create_acount__inactive_state__activity extends Activity {

	
	private View _bg__create_acount__inactive_state__ek2;

	private ImageView uil_arrow_up_1_ek1;

	private TextView create_your_account;

	private View _bg__medium_1icon_ek1;
	private ImageView vector;

	private View _bg__medium_2icons_ek1;

	private ImageView vector_ek1;

	private ImageView vector_ek2;

	private View button;
	private TextView button_ek2;

	private View verifyEmailButton;

	private EditText email;
		private EditText password;


		private FirebaseAuth mAuth;
		private FirebaseUser user;




		// Méthode pour valider le mot de passe
		private boolean isValidPassword(String password) {
			// La regex pour vérifier si le mot de passe contient au moins 8 caractères, un chiffre et une majuscule
			String regex = "^(?=.*[0-9])(?=.*[A-Z]).{8,}$";
			// Vérifier si le mot de passe correspond à la regex
			return password.matches(regex);
		}

		// Méthode pour vérifier si l'email correspond à un format valide
		private boolean isValidEmail(String email) {
			// Utiliser une expression régulière pour vérifier le format de l'email
			String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
			return email.matches(emailPattern);
		}
		private void updateUI(FirebaseUser user) {
			if (user != null) {
				// L'utilisateur est connecté, vous pouvez mettre à jour l'interface utilisateur en conséquence
				// Par exemple, redirigez l'utilisateur vers une autre activité
				startActivity(new Intent(this, log_in_as__activity.class));
				finish(); // Fermer cette activité pour empêcher l'utilisateur de revenir en arrière
			} else {
				// L'utilisateur n'est pas connecté, vous pouvez afficher un message d'erreur ou lui permettre de se connecter à nouveau
				// Par exemple, affichez un message d'erreur
				Toast.makeText(this, "La création du compte a échoué.", Toast.LENGTH_SHORT).show();
			}
		}



		private void checkIfUserExists(String email) {
			FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email)
					.addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
						@Override
						public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
							if (task.isSuccessful()) {
								Toast.makeText(create_acount__inactive_state__activity.this, "Cet utilisateur existe déjà.", Toast.LENGTH_SHORT).show();

							}
						}
					});

		}



		private void createAccount(String email, String password) {


			// Créer un nouvel utilisateur avec l'e-mail et le mot de passe fournis
			mAuth.createUserWithEmailAndPassword(email, password)
					.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
						@Override
						public void onComplete(@NonNull Task<AuthResult> task) {
							if (task.isSuccessful()) {
								// Sign in success, update UI with the signed-in user's information
								Log.d(TAG, "createUserWithEmail:success");
								 user = mAuth.getCurrentUser();
								// Envoyer l'e-mail de vérification
								user.sendEmailVerification()
										.addOnCompleteListener(new OnCompleteListener<Void>() {
											@Override
											public void onComplete(@NonNull Task<Void> task) {
												if (task.isSuccessful()) {
													Log.d(TAG, "Email sent.");
													Toast.makeText(create_acount__inactive_state__activity.this, "Verification email sent.",
															Toast.LENGTH_SHORT).show();

													// Afficher un bouton pour permettre à l'utilisateur de vérifier son e-mail
													verifyEmailButton.setVisibility(View.VISIBLE);
												}
											}
										});




							} else {
								checkIfUserExists(email);
								// If sign in fails, display a message to the user.
								Log.w(TAG, "createUserWithEmail:failure", task.getException());
								Toast.makeText(create_acount__inactive_state__activity.this, "Authentication failed.",
										Toast.LENGTH_SHORT).show();
								updateUI(null);

							}
						}
					});;
		}
private void veriferEmail(){
	FirebaseAuth.getInstance().getCurrentUser().reload().addOnCompleteListener(new OnCompleteListener<Void>() {
		@Override
		public void onComplete(@NonNull Task<Void> task) {
			if (task.isSuccessful()) {
				 user = FirebaseAuth.getInstance().getCurrentUser();
				if (user != null && user.isEmailVerified()) {
					// L'e-mail est vérifié
					Toast.makeText(create_acount__inactive_state__activity.this, "e-mail verified", Toast.LENGTH_SHORT).show();
					updateUI(user);
				} else {
					// L'e-mail n'est toujours pas vérifié
					Toast.makeText(create_acount__inactive_state__activity.this, "L'e-mail n'est toujours pas vérifié.", Toast.LENGTH_SHORT).show();
				}
			} else {
				// Une erreur s'est produite lors de la recharge de l'utilisateur
				Log.e(TAG, "Erreur lors du rechargement de l'utilisateur: ", task.getException());
			}
		}
	});
}
		@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_acount__inactive_state_);

		
		_bg__create_acount__inactive_state__ek2 = (View) findViewById(R.id._bg__create_acount__inactive_state__ek2);

		uil_arrow_up_1_ek1 = (ImageView) findViewById(R.id.uil_arrow_up_1_ek1);
		create_your_account = (TextView) findViewById(R.id.create_your_account);

		_bg__medium_1icon_ek1 = (View) findViewById(R.id._bg__medium_1icon_ek1);
		vector = (ImageView) findViewById(R.id.vector);
		email = (EditText) findViewById(R.id.email);
		_bg__medium_2icons_ek1 = (View) findViewById(R.id._bg__medium_2icons_ek1);

		vector_ek1 = (ImageView) findViewById(R.id.vector_ek1);
		password = (EditText) findViewById(R.id.password);
		vector_ek2 = (ImageView) findViewById(R.id.vector_ek2);

		button = (View) findViewById(R.id.signup_btn);
		button_ek2 = (TextView) findViewById(R.id.button_ek2);

		verifyEmailButton =(View) findViewById(R.id.verifyEmailButton);


		// back to login
			uil_arrow_up_1_ek1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// Créer une Intent pour démarrer MainActivity
					Intent intent = new Intent(create_acount__inactive_state__activity.this, onboarding_screen_activity.class);
					startActivity(intent);
					finish(); // Optionnel : cela ferme l'activité actuelle si vous ne voulez pas y revenir
				}
			});

		//when you click on enter keyboard dissapere
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

		// when you clik pswd shows
			vector_ek2.setOnClickListener(new View.OnClickListener() {
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

			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// Récupérer le texte des EditText pour l'email et le mot de passe
					String mail = email.getText().toString().trim();
					String pswd= password.getText().toString().trim();

					// Vérifier si l'email et le mot de passe sont remplis
					if (mail.isEmpty() || pswd.isEmpty()) {
						// Afficher un message d'erreur si l'email ou le mot de passe est vide
						Toast.makeText(create_acount__inactive_state__activity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
						return; // Sortir de la méthode si un champ est vide
					}

					// Vérifier si l'email correspond à un format valide
					if (!isValidEmail(mail)) {
						// Afficher un message d'erreur si l'email n'est pas valide
						email.setError("Veuillez saisir une adresse e-mail valide");
						email.requestFocus();
						return; // Sortir de la méthode si l'email n'est pas valide
					}

					// Vérifier si le mot de passe est valide
					if (!isValidPassword(pswd)) {
						// Afficher un message d'erreur si le mot de passe n'est pas valide
						password.setError("Le mot de passe doit contenir au moins 8 caractères, un chiffre et une majuscule");
						password.requestFocus();
						return;
					}

                      // authentification ajout d'un nv util

					// Initialize Firebase Auth
					mAuth = FirebaseAuth.getInstance();


					createAccount(mail, pswd);

					verifyEmailButton.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							veriferEmail();

						}
					});
























				}
			});






















		}

}
	
	