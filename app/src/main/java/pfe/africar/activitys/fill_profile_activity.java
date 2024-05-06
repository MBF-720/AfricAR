
	 
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
	import android.app.AlertDialog;
	import android.app.DatePickerDialog;
	import android.content.Context;
	import android.content.DialogInterface;
	import android.content.Intent;
	import android.net.Uri;
	import android.os.Bundle;
	import android.provider.MediaStore;
	import android.text.TextUtils;
	import android.util.Log;
	import android.view.KeyEvent;
	import android.view.View;
	import android.view.inputmethod.InputMethodManager;
	import android.widget.DatePicker;
	import android.widget.EditText;
	import android.widget.ImageView;
	import android.widget.TextView;
	import android.widget.Toast;

	import androidx.annotation.NonNull;

	import com.canhub.cropper.CropImageView;
	import com.google.android.gms.tasks.OnCompleteListener;
	import com.google.android.gms.tasks.OnFailureListener;
	import com.google.android.gms.tasks.OnSuccessListener;
	import com.google.android.gms.tasks.Task;
	import com.google.firebase.auth.FirebaseAuth;
	import com.google.firebase.auth.FirebaseUser;
	import com.google.firebase.auth.UserInfo;
	import com.google.firebase.auth.UserProfileChangeRequest;
	import com.google.firebase.firestore.DocumentReference;
	import com.google.firebase.firestore.FieldValue;
	import com.google.firebase.firestore.FirebaseFirestore;

	import java.text.SimpleDateFormat;
	import java.util.Calendar;
	import java.util.Locale;

	import pfe.africar.R;
	import pfe.africar.classes.Eleve;
	import pfe.africar.classes.Professeur;

	public class fill_profile_activity extends Activity {

	


	private ImageView imageView;

		private static final int PICK_IMAGE_REQUEST = 1;
		private CropImageView cropImageView;

		View _bg__fill_profile_ek2;
		ImageView backbtn;
		EditText firstName, lastName, phone ;
		View _bg__medium_ek101, continuebtn;
		TextView button_ek27,gender, birthDate;

		String emailText,uid;

		Uri selectedImageUri;

		private Calendar selectedDate = Calendar.getInstance();

		private void openGallery() {
			Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, PICK_IMAGE_REQUEST);
		}

		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);


			if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
				// Get the URI of the selected image
				 selectedImageUri = data.getData();


				// Perform actions with the selected image URI, such as displaying it or uploading it
				// For example, you can display the image in an ImageView:
				imageView.setImageURI(selectedImageUri);
			}
		}

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

		private void showGenderOptionsDialog() {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Select Gender");
			String[] genderOptions = {"Female", "Male"};

			builder.setItems(genderOptions, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// The 'which' argument contains the index position of the selected item
					String selectedGender = genderOptions[which];
					// Update the gender field with the selected option
					gender.setText(selectedGender);
				}
			});

			builder.show();
		}

		private void updateUser(String name){
			FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

			UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
					.setDisplayName(name)
					.setPhotoUri(selectedImageUri)
					.build();

			user.updateProfile(profileUpdates)
					.addOnCompleteListener(new OnCompleteListener<Void>() {
						@Override
						public void onComplete(@NonNull Task<Void> task) {
							if (task.isSuccessful()) {
								Log.d(TAG, "User profile updated.");

							}
						}
					});
		}





		@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.fill_profile);

		
		_bg__fill_profile_ek2 = (View) findViewById(R.id._bg__fill_profile_ek2);

		backbtn = (ImageView) findViewById(R.id.backbtn);

		imageView = (ImageView) findViewById(R.id.image_view);

		firstName = (EditText) findViewById(R.id.label_ek54);

		lastName = (EditText) findViewById(R.id.label_ek55);

		birthDate = (TextView) findViewById(R.id.label_ek56);
		phone = (EditText) findViewById(R.id.label_ek57);
		_bg__medium_ek101 = (View) findViewById(R.id._bg__medium_ek101);
		gender = (TextView) findViewById(R.id.label_ek58);
		continuebtn = (View) findViewById(R.id._bg__component_1_ek43);
		button_ek27 = (TextView) findViewById(R.id.button_ek27);

		firstName.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
						(keyCode == KeyEvent.KEYCODE_ENTER)) {
					lastName.requestFocus();
					return true;
				}
				return false;
			}
		});

		lastName.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
						(keyCode == KeyEvent.KEYCODE_ENTER)) {
					birthDate.requestFocus();
					return true;
				}
				return false;
			}
		});

		birthDate.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
						(keyCode == KeyEvent.KEYCODE_ENTER)) {
					phone.requestFocus();
					return true;
				}
				return false;
			}
		});

		phone.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
						(keyCode == KeyEvent.KEYCODE_ENTER)) {
					gender.requestFocus();
					return true;
				}
				return false;
			}
		});

		gender.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
						(keyCode == KeyEvent.KEYCODE_ENTER)) {
					// Masquer le clavier
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(gender.getWindowToken(), 0);
					return true;
				}
				return false;

			}
		});




             // click sur image to open gallery
			imageView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					openGallery();
				}
			});


           // Ajout d'un écouteur de clic au champ de texte "birthDate"
			birthDate.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					showDatePicker();
				}
			});

			gender.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					showGenderOptionsDialog();
				}
			});






			//recuperation de statu et id ecole et id classe

			String schoolId = getIntent().getStringExtra("school_id");
			String statu = getIntent().getStringExtra("statu");
			String classeId = getIntent().getStringExtra("classe_id");

			FirebaseFirestore db = FirebaseFirestore.getInstance();


			continuebtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					Toast.makeText(fill_profile_activity.this, "buttn cliked", Toast.LENGTH_SHORT).show();

					// recuperation des valeur
					String firstNameValue = firstName.getText().toString().trim();
					String lastNameValue = lastName.getText().toString().trim();
					String birthDateValue = birthDate.getText().toString().trim();
					String phoneValue = phone.getText().toString().trim();
					String genderValue = gender.getText().toString().trim();

					// Check if any of the fields are empty
					if (firstNameValue.isEmpty() || lastNameValue.isEmpty() || birthDateValue.isEmpty() || phoneValue.isEmpty() || genderValue.isEmpty()) {
						// Display an error message indicating that all fields are required
						Toast.makeText(fill_profile_activity.this, "All fields are required", Toast.LENGTH_SHORT).show();
						return;
					}

					// Check if the phone number consists of 8 digits
					if (phoneValue.length() != 8 || !TextUtils.isDigitsOnly(phoneValue)) {
						// Display an error message indicating that the phone number should be 8 digits long
						Toast.makeText(fill_profile_activity.this, "Phone number should be 8 digits long", Toast.LENGTH_SHORT).show();
						return;
					}

					if(statu.equals("Professeur")){

						FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
						if (user != null) {
							for (UserInfo profile : user.getProviderData()) {
								// Id of the provider (ex: google.com)
								String providerId = profile.getProviderId();
								// UID specific to the provider
								uid = profile.getUid();
								emailText = profile.getEmail();

							}
						}

						//creation d'objet prof
						Professeur newProfesseur = new Professeur(schoolId, firstNameValue, lastNameValue, emailText, phoneValue);
						newProfesseur.setUid(uid);


						//ajout du prof dans firestore

						db.collection("Ecoles").document(String.valueOf(schoolId)).collection("Professeurs")
								.add(newProfesseur)
								.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
									@Override
									public void onSuccess(DocumentReference documentReference) {

										Log.d(TAG, "DocumentSnapshot ajouté avec ID: " + documentReference.getId());
										Toast.makeText(fill_profile_activity.this, "Professeur ajouté avec succès !", Toast.LENGTH_SHORT).show();
									}
								})
								.addOnFailureListener(new OnFailureListener() {
									@Override
									public void onFailure(@NonNull Exception e) {
										Log.w(TAG, "Erreur lors de l'ajout du document", e);
										Toast.makeText(fill_profile_activity.this, "Erreur lors de l'ajout du professeur !", Toast.LENGTH_SHORT).show();


									}
								});
						updateUser( firstNameValue);

					}



					if(statu.equals("Eleve")){

						FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
						if (user != null) {
							for (UserInfo profile : user.getProviderData()) {
								// Id of the provider (ex: google.com)
								String providerId = profile.getProviderId();
								// UID specific to the provider
								uid = profile.getUid();
								emailText = profile.getEmail();

							}
						}

						//creation d'objet Eleve
						Eleve nouvelEleve = new Eleve(schoolId, firstNameValue, lastNameValue, emailText, phoneValue);
						nouvelEleve.setUid(uid);
						nouvelEleve.setIdClasse(classeId);


						//ajout du prof dans firestore dans document Eleve et son referance a la liste des eleve de son classe

						db.collection("Ecoles").document(String.valueOf(schoolId)).collection("Eleves")
								.add(nouvelEleve)
								.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
									@Override
									public void onSuccess(DocumentReference documentReference) {

										Log.d(TAG, "DocumentSnapshot ajouté avec ID: " + documentReference.getId());
										Toast.makeText(fill_profile_activity.this, "Eleve ajouté avec succès !", Toast.LENGTH_SHORT).show();

										// Add the student's ID to the listEleves field in the class document
										db.collection("Ecoles").document(String.valueOf(schoolId)).collection("Classes").document(classeId)
												.update("listeEleves", FieldValue.arrayUnion(documentReference.getId()))
												.addOnSuccessListener(new OnSuccessListener<Void>() {
													@Override
													public void onSuccess(Void aVoid) {
														Log.d(TAG, "Student ID added to class document");
														Toast.makeText(fill_profile_activity.this, "Eleve ajouté avec succès !", Toast.LENGTH_SHORT).show();
													}
												})
												.addOnFailureListener(new OnFailureListener() {
													@Override
													public void onFailure(@NonNull Exception e) {
														Log.w(TAG, "Error adding student ID to class document", e);
														Toast.makeText(fill_profile_activity.this, "Erreur lors de l'ajout du professeur !", Toast.LENGTH_SHORT).show();
													}
												});

									}
								})
								.addOnFailureListener(new OnFailureListener() {
									@Override
									public void onFailure(@NonNull Exception e) {
										Log.w(TAG, "Erreur lors de l'ajout du document", e);
										Toast.makeText(fill_profile_activity.this, "Erreur lors de l'ajout du professeur !", Toast.LENGTH_SHORT).show();


									}
								});
						updateUser( firstNameValue);


						//ajouter la referance de l'eleve dans champ listeEleves dans classes





					}


				}
			});
















			}
		}
	
