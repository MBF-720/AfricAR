
	 
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
	import android.util.Log;
	import android.view.KeyEvent;
	import android.view.View;
	import android.view.inputmethod.InputMethodManager;
	import android.widget.EditText;
	import android.widget.ImageView;
	import android.widget.TextView;
	import android.widget.Toast;

	import androidx.annotation.NonNull;

	import com.google.android.gms.tasks.OnCompleteListener;
	import com.google.android.gms.tasks.Task;
	import com.google.firebase.firestore.DocumentReference;
	import com.google.firebase.firestore.DocumentSnapshot;
	import com.google.firebase.firestore.FirebaseFirestore;

	import pfe.africar.R;

	public class enter_id_activity extends Activity {

	
	private View _bg__enter_id_ek2;
	private ImageView uil_arrow_up_1;
	private TextView enter_your_scholar_id;
	private View _bg__frame_96_ek1;
	private View _bg__medium_ek1;
	private View continuebtn;
	private EditText label;
	private View _bg__component_2_ek1;
	private TextView button;
	private String statu;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.enter_id);

		
		continuebtn = (View) findViewById(R.id._bg__enter_id_ek2);

		enter_your_scholar_id = (TextView) findViewById(R.id.enter_your_scholar_id);

		label = (EditText) findViewById(R.id.label);

		//recuperer le statu
		 statu = getIntent().getStringExtra("statu");
		Toast.makeText(enter_id_activity.this, statu, Toast.LENGTH_SHORT).show();




		label.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
						(keyCode == KeyEvent.KEYCODE_ENTER)) {
					// Masquer le clavier
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(label.getWindowToken(), 0);
					return true;
				}
				return false;
			}
		});


// Ajouter un écouteur de clic au bouton "continuebtn"
		continuebtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Récupérer le texte entré dans le champ "label"
				String labelText = label.getText().toString().trim();

				// Vérifier si le champ "label" n'est pas vide
				if (!labelText.isEmpty()) {
					FirebaseFirestore db = FirebaseFirestore.getInstance();
					DocumentReference docRef = db.collection("Ecoles").document(labelText );
					docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
						@Override
						public void onComplete(@NonNull Task<DocumentSnapshot> task) {
							if (task.isSuccessful()) {
								DocumentSnapshot document = task.getResult();
								if (document.exists()) {

									if(statu.equals("Eleve")){

										try {
											Intent intent = new Intent(enter_id_activity.this, enter_id_classe_activity.class);
											intent.putExtra("statu", statu);
											intent.putExtra("school_id", labelText);
											startActivity(intent);
										} catch (Exception e) {
											Log.e(TAG, "Error starting enter_id_classe_activity", e);
											Toast.makeText(enter_id_activity.this, "Error starting enter_id_classe_activity: " + e.getMessage(), Toast.LENGTH_SHORT).show();
										}

									}else{

									Log.d(TAG, "DocumentSnapshot data: " + document.getData());
										Toast.makeText(enter_id_activity.this, "stat= prof", Toast.LENGTH_SHORT).show();

									//-------------------- Le champ n'est pas vide, vous pouvez continue-------------------------------------------------------------
									Intent intent2 = new Intent(enter_id_activity.this, fill_profile_activity.class);
									intent2.putExtra("statu", statu);
									intent2.putExtra("school_id", labelText);
									startActivity(intent2);}

								} else {
									Log.d(TAG, "No such document");
									Toast.makeText(enter_id_activity.this, "Veuillez verifier votre ID", Toast.LENGTH_SHORT).show();


								}
							} else {
								Log.d(TAG, "get failed with ", task.getException());
							}
						}
					});



				} else {
					// Le champ est vide, afficher un message d'erreur à l'utilisateur
					Toast.makeText(enter_id_activity.this, "Veuillez entrer une valeur pour le champ", Toast.LENGTH_SHORT).show();
				}
			}
		});

















	
	}


}
	
	