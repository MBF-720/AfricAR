
	 
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

	import com.google.android.gms.tasks.OnFailureListener;
	import com.google.android.gms.tasks.OnSuccessListener;
	import com.google.firebase.auth.FirebaseAuth;
	import com.google.firebase.auth.FirebaseUser;
	import com.google.firebase.firestore.DocumentReference;
	import com.google.firebase.firestore.FirebaseFirestore;

	import pfe.africar.R;
	import pfe.africar.classes.Professeur;

	public class add_prof_activity extends Activity {


		private EditText fullName;
		private EditText schoolID;
		private EditText email;
		private EditText phone;
		private EditText field;
		private View button;
		private ImageView x_1_ek18;
		private ImageView vector_ek728;
		private ImageView vector_ek729;
		private ImageView vector_ek730;
		private ImageView vector_ek731;
		private FirebaseAuth mAuth;
		private FirebaseUser user;
		private FirebaseFirestore db;


		/*private void createAccount(String email, String password) {


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
													Toast.makeText(add_prof_activity.this, "Verification email sent.",
															Toast.LENGTH_SHORT).show();


												}
											}
										});




							} else {
								checkIfUserExists(email);
								// If sign in fails, display a message to the user.
								Log.w(TAG, "createUserWithEmail:failure", task.getException());
								Toast.makeText(add_prof_activity.this, "Authentication failed.",
										Toast.LENGTH_SHORT).show();


							}
						}
					});;
		}

		private void checkIfUserExists(String email) {
			FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email)
					.addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
						@Override
						public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
							if (task.isSuccessful()) {
								Toast.makeText(add_prof_activity.this, "Cet utilisateur existe déjà.", Toast.LENGTH_SHORT).show();

							}
						}
					});

		}

		private void updateUserMetadata(String idEcole, String statue) {
			FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
			if (user != null) {
				DocumentReference userDocRef = db.collection("Users").document(user.getUid());
				Map<String, Object> userMetadata = new HashMap<>();
				userMetadata.put("idEcole", idEcole);
				userMetadata.put("statue", statue);

				userDocRef.set(userMetadata, SetOptions.merge())
						.addOnSuccessListener(new OnSuccessListener<Void>() {
							@Override
							public void onSuccess(Void aVoid) {
								Log.d(TAG, "User metadata updated successfully.");
								// Handle success and provide feedback to the user
							}
						})
						.addOnFailureListener(new OnFailureListener() {
							@Override
							public void onFailure(@NonNull Exception e) {
								Log.e(TAG, "Error updating user metadata: ", e);
								// Handle error and provide feedback to the user
							}
						});
			}
		}*/ // creation des users impossible



		@Override
	public void onCreate(Bundle savedInstanceState) {

			super.onCreate(savedInstanceState);
			setContentView(R.layout.add_prof);


			fullName = (EditText) findViewById(R.id.label_ek39);//

			schoolID = (EditText) findViewById(R.id.label_ek40);//

			email = (EditText) findViewById(R.id.label_ek41);//
			//EditText password = (EditText) findViewById(R.id.password);


			phone = (EditText) findViewById(R.id.label_ek42);//

			field = (EditText) findViewById(R.id.label_ek43);//
			button = (View) findViewById(R.id._bg__component_1_ek37);//

			x_1_ek18 = (ImageView) findViewById(R.id.x_1_ek18);//exit

			//nav bar

			vector_ek728 = (ImageView) findViewById(R.id.vector_ek728);
			vector_ek729 = (ImageView) findViewById(R.id.vector_ek729);
			vector_ek730 = (ImageView) findViewById(R.id.vector_ek730);
			vector_ek731 = (ImageView) findViewById(R.id.vector_ek731);

			x_1_ek18.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onBackPressed();
				}
			});


			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					 db = FirebaseFirestore.getInstance();


					// Récupérer le nom complet saisi par l'utilisateur

					String fullNameText = fullName.getText().toString();

					String[] parts = fullNameText.split("_");

					if (parts.length >= 2) {
						String nom = parts[0];
						String prenom = parts[1];

						// Si le nom contient plus de deux parties, les considérer comme faisant partie du prénom
						if (parts.length > 2) {
							for (int i = 2; i < parts.length; i++) {
								prenom += " " + parts[i];
							}
						}

						// Récupérer les autres informations saisies par l'utilisateur
						String schoolIDText = schoolID.getText().toString();
						String emailText = email.getText().toString();
						String phoneText = phone.getText().toString();
						String fieldText = field.getText().toString();
						//String passwordText =password.getText().toString();


						// Vérifier si tous les champs sont remplis
						if (schoolIDText.isEmpty() || emailText.isEmpty() || phoneText.isEmpty() || fieldText.isEmpty()) {
							Toast.makeText(add_prof_activity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
							return;
						}

						// Créer un nouvel objet Professeur avec les informations récupérées

						Professeur newProfesseur = new Professeur(schoolIDText, nom, prenom, emailText, phoneText);
						newProfesseur.setMatiere(fieldText);

						//createAccount(emailText,passwordText);



						// Ajouter le professeur à la base de données Firestore

						db.collection("Ecoles").document(String.valueOf(schoolID)).collection("Professeurs")
								.add(newProfesseur)
								.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
									@Override
									public void onSuccess(DocumentReference documentReference) {

										Log.d(TAG, "DocumentSnapshot ajouté avec ID: " + documentReference.getId());
										Toast.makeText(add_prof_activity.this, "Professeur ajouté avec succès !", Toast.LENGTH_SHORT).show();
									}
								})
								.addOnFailureListener(new OnFailureListener() {
									@Override
									public void onFailure(@NonNull Exception e) {
										Log.w(TAG, "Erreur lors de l'ajout du document", e);
										Toast.makeText(add_prof_activity.this, "Erreur lors de l'ajout du professeur !", Toast.LENGTH_SHORT).show();


									}
								});
					} else {
						Toast.makeText(add_prof_activity.this, "Veuillez saisir le nom et le prénom séparés par '_' (nom_prenom)", Toast.LENGTH_SHORT).show();
					}
                          // <!-- TODO: nav bar , test .
				}
			});







		}}

