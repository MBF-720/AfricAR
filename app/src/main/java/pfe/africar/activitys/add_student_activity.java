
	 
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
	import android.widget.TextView;
	import android.widget.Toast;

	import androidx.annotation.NonNull;

	import com.google.android.gms.tasks.OnFailureListener;
	import com.google.android.gms.tasks.OnSuccessListener;
	import com.google.firebase.firestore.DocumentReference;
	import com.google.firebase.firestore.FirebaseFirestore;

	import pfe.africar.R;
	import pfe.africar.classes.Eleve;

	public class add_student_activity extends Activity {


		private EditText fullName;
		private EditText scolarID;
		private EditText email;
		private EditText phone;
		private EditText classroom;
		private View button;
		private TextView button_ek23;
		private ImageView x_1_ek17;
		private ImageView vector_ek724;
		private ImageView vector_ek725;
		private ImageView vector_ek726;
		private ImageView vector_ek727;


		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.add_student);

			// Récupérer les références des éléments de l'interface utilisateur
			fullName = findViewById(R.id.label_ek34);
			scolarID = findViewById(R.id.label_ek35);
			email = findViewById(R.id.label_ek36);
			phone = findViewById(R.id.label_ek37);
			classroom = findViewById(R.id.label_ek38);
			button = findViewById(R.id._bg__component_1_ek35);
			button_ek23 = findViewById(R.id.button_ek23);
			x_1_ek17 = findViewById(R.id.x_1_ek17);
			vector_ek724 = findViewById(R.id.vector_ek724);
			vector_ek725 = findViewById(R.id.vector_ek725);
			vector_ek726 = findViewById(R.id.vector_ek726);
			vector_ek727 = findViewById(R.id.vector_ek727);

			// Ajouter un écouteur de clics au bouton
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// Récupérer les valeurs saisies dans les champs EditText
					String fullNameText = fullName.getText().toString();
					String scolarIDText = scolarID.getText().toString();
					String emailText = email.getText().toString();
					String phoneText = phone.getText().toString();
					String classroomText = classroom.getText().toString();

					// Diviser le nom complet en nom et prénom
					String[] parts = fullNameText.split("_");

                     // Vérifier que le nom complet a été divisé en nom et prénom
					if (parts.length >= 2) {
						String nom = parts[0];
						String prenom = parts[1];


						// Créer une instance de Eleve avec les informations saisies
						Eleve nouvelEleve = new Eleve(scolarIDText, nom, prenom, emailText, phoneText);

						// Ajouter le nouvel élève à la base de données Firestore
						FirebaseFirestore db = FirebaseFirestore.getInstance();
						db.collection("Ecoles").document(String.valueOf(scolarID)).collection("Eleves")
								.add(nouvelEleve)
								.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
									@Override
									public void onSuccess(DocumentReference documentReference) {
										Log.d(TAG, "DocumentSnapshot ajouté avec ID: " + documentReference.getId());
										// Mettez ici le code pour gérer le succès de l'ajout de l'élève à la base de données
									}
								})
								.addOnFailureListener(new OnFailureListener() {
									@Override
									public void onFailure(@NonNull Exception e) {
										Log.w(TAG, "Erreur lors de l'ajout du document", e);
										// Mettez ici le code pour gérer l'échec de l'ajout de l'élève à la base de données
									}
								});



					} else {
						// Gérer le cas où le nom complet ne peut pas être divisé en nom et prénom <(8, 14) <!-- TODO: else prenom verifier limplementation .
						Toast.makeText(getApplicationContext(), "Veuillez ajouter un séparateur '_' entre le nom et le prénom", Toast.LENGTH_SHORT).show();



					}
				}
			});
		}}


	