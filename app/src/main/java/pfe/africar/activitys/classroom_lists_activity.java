
	 
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
	import android.util.Log;
	import android.view.View;
	import android.widget.AdapterView;
	import android.widget.ArrayAdapter;
	import android.widget.ImageView;
	import android.widget.ListView;

	import com.google.android.gms.tasks.OnCompleteListener;
	import com.google.android.gms.tasks.Task;
	import com.google.firebase.firestore.DocumentSnapshot;
	import com.google.firebase.firestore.FirebaseFirestore;
	import com.google.firebase.firestore.QuerySnapshot;

	import java.util.ArrayList;
	import java.util.List;

	import pfe.africar.R;

	public class classroom_lists_activity extends Activity {

	

	private View _bg__frame_141_ek1;

	private ImageView x_1_ek14;
	private ImageView rectangle_32_ek14;

	private ImageView vector_ek648;
	private ImageView vector_ek649;
	private ImageView vector_ek650;
	private ImageView vector_ek651;
	private  ListView listeView;

	private String ecoleId="Vgv1obkaHUASn7Z8rI7I";

	private List<String> nomClassesList;


		FirebaseFirestore db;

		public Task<List<String>> getNomClasses(String ecoleId) {
			List<String> nomClassesList = new ArrayList<>();
			FirebaseFirestore db = FirebaseFirestore.getInstance();
			Task<QuerySnapshot> queryTask = db.collection("Ecoles").document(ecoleId).collection("Classes")
					.get();

			return queryTask.continueWith(task -> {
				if (task.isSuccessful()) {
					for (DocumentSnapshot document : task.getResult()) {
						String nomClasse = document.getString("nom");
						nomClassesList.add(nomClasse);
					}
					return nomClassesList;
				} else {
					// Gérer les erreurs
					return null;
				}
			});
		}

		private String getClassId(String className) {
			String classId = null;
			db.collection("Ecoles").document(ecoleId).collection("Classes")
					.whereEqualTo("nom", className)
					.get()
					.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
						@Override
						public void onComplete(Task<QuerySnapshot> task) {
							if (task.isSuccessful()) {
								for (DocumentSnapshot document : task.getResult()) {
									String classId = document.getId();
									break;
								}
							} else {
								Log.d("ClassroomListsActivity", "Error getting class ID: ", task.getException());
							}
						}
					});
			return classId;
		}



	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.classroom_lists);

		
		_bg__frame_141_ek1 = (View) findViewById(R.id._bg__frame_141_ek1);

		x_1_ek14 = (ImageView) findViewById(R.id.x_1_ek14);
		rectangle_32_ek14 = (ImageView) findViewById(R.id.rectangle_32_ek14);

		vector_ek648 = (ImageView) findViewById(R.id.vector_ek648);
		vector_ek649 = (ImageView) findViewById(R.id.vector_ek649);
		vector_ek650 = (ImageView) findViewById(R.id.vector_ek650);
		vector_ek651 = (ImageView) findViewById(R.id.vector_ek651);

		listeView =(ListView) findViewById(R.id.ListView);

		 db = FirebaseFirestore.getInstance();



		/*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
		String userId = user.getUid();


		db.collection("Users").document(userId).get()
				.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
					@Override
					public void onComplete(Task<DocumentSnapshot> task) {
						if (task.isSuccessful()) {
							DocumentSnapshot document = task.getResult();
							 ecoleId = document.getString("idEcole");
						} else {
							Toast.makeText(classroom_lists_activity.this, "cant get schoolID", Toast.LENGTH_SHORT).show();

						}}});

*/
						getNomClasses("Vgv1obkaHUASn7Z8rI7I").addOnCompleteListener(task -> {
							if (task.isSuccessful()) {
								nomClassesList = task.getResult();
								ArrayAdapter<String> adapter = new ArrayAdapter<>(classroom_lists_activity.this, android.R.layout.simple_list_item_1, nomClassesList);
								listeView.setAdapter(adapter);

							} else {

							}
						});



		listeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String className = nomClassesList.get(position);
				String classId = getClassId(className); //  this method to get the class ID from the class name
				Intent intent = new Intent(classroom_lists_activity.this, list_eleve__activity.class);
				intent.putExtra("classId", classId);
				startActivity(intent);
			}
		});




	}}

	
	