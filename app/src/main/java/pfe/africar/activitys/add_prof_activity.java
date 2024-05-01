
	 
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

		@Override
	public void onCreate(Bundle savedInstanceState) {

			super.onCreate(savedInstanceState);
			setContentView(R.layout.add_prof);


			fullName = (EditText) findViewById(R.id.label_ek39);//

			schoolID = (EditText) findViewById(R.id.label_ek40);//

			email = (EditText) findViewById(R.id.label_ek41);//

			phone = (EditText) findViewById(R.id.label_ek42);//

			field = (EditText) findViewById(R.id.label_ek43);//
			button = (View) findViewById(R.id._bg__component_1_ek37);//

			x_1_ek18 = (ImageView) findViewById(R.id.x_1_ek18);//exit

			//nav bar

			vector_ek728 = (ImageView) findViewById(R.id.vector_ek728);
			vector_ek729 = (ImageView) findViewById(R.id.vector_ek729);
			vector_ek730 = (ImageView) findViewById(R.id.vector_ek730);
			vector_ek731 = (ImageView) findViewById(R.id.vector_ek731);


			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					FirebaseFirestore db = FirebaseFirestore.getInstance();


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

						// Vérifier si tous les champs sont remplis
						if (schoolIDText.isEmpty() || emailText.isEmpty() || phoneText.isEmpty() || fieldText.isEmpty()) {
							Toast.makeText(add_prof_activity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
							return;
						}

						// Créer un nouvel objet Professeur avec les informations récupérées

						Professeur newProfesseur = new Professeur(schoolIDText, nom, prenom, emailText, phoneText, fieldText);

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

