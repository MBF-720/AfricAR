
	 
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
	import android.os.Bundle;
	import android.widget.ArrayAdapter;
	import android.widget.ListView;
	import android.widget.TextView;
	import android.widget.Toast;

	import com.google.firebase.firestore.DocumentSnapshot;
	import com.google.firebase.firestore.FirebaseFirestore;

	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.Map;

	import pfe.africar.R;


	public class list_eleve__activity extends Activity {

	private TextView updateTimetable,addStudent;
		private ListView listeView;
		private String ecoleId="Vgv1obkaHUASn7Z8rI7I";




		public void getListeEleves(String ecoleId, String classeId,Map<String, String> elevesMap) {

			FirebaseFirestore db = FirebaseFirestore.getInstance();

			db.collection("Ecoles").document(ecoleId)
					.collection("Eleves").whereEqualTo("idClasse", classeId)
					.get()
					.addOnCompleteListener(task -> {
						if (task.isSuccessful()) {        Toast.makeText(getApplicationContext(), "find id DB", Toast.LENGTH_SHORT).show();

							for (DocumentSnapshot document : task.getResult()) {
								String nom = document.getString("nom");
								String prenom = document.getString("prenom");
								String id = document.getId();
								String name = String.format("%s %s", nom, prenom);
								elevesMap.put(name, id);
							}

						} else {

							Toast.makeText(getApplicationContext(), "can't finde list ", Toast.LENGTH_SHORT).show();
						}
					});}


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_eleve_);

		

		addStudent = (TextView) findViewById(R.id.add_new_student);

		updateTimetable = (TextView) findViewById(R.id.update_timetable);
		listeView =(ListView) findViewById(R.id.ListView);

		Map<String, String> mapEleves = new HashMap<>();//todo alech to9ad empty



		String classeId = getIntent().getStringExtra("classId");

		getListeEleves("Vgv1obkaHUASn7Z8rI7I",  classeId,mapEleves);


		if(mapEleves.isEmpty()){
			Toast.makeText(getApplicationContext(), "map empty ", Toast.LENGTH_SHORT).show();

		}


		// Créez une liste de noms d'élèves à partir de la map
		ArrayList<String> elevesList = new ArrayList<>(mapEleves.keySet());

		// Créez un adaptateur pour lier la liste des noms d'élèves à la vue de la liste
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, elevesList);

		// Définissez l'adaptateur de votre ListView
		listeView.setAdapter(adapter);

	}
}
	
	