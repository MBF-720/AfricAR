

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

	import android.annotation.SuppressLint;
	import android.app.Activity;
	import android.content.Intent;
	import android.os.Bundle;
	import android.view.View;
	import android.widget.AdapterView;
	import android.widget.ArrayAdapter;
	import android.widget.ImageView;
	import android.widget.ListView;
	import android.widget.TextView;
	import android.widget.Toast;

	import com.google.android.material.bottomnavigation.BottomNavigationView;
	import com.google.firebase.firestore.FirebaseFirestore;
	import com.google.firebase.firestore.QueryDocumentSnapshot;

	import java.util.ArrayList;
	import java.util.List;

	import pfe.africar.R;
	import pfe.africar.helpers.AdminNavHelper;

	public class admin_update_activity extends Activity {

			private FirebaseFirestore db;
			private ListView listViewActualites;
			private List<String> titlesList;
			private ArrayAdapter<String> adapter;


	private View _bg__frame_132_ek7;
	private TextView add_new_announcement_ek1;

	private ImageView rectangle_32_ek25;

	private ImageView vector_ek31;

	private TextView welcome_to;
	private TextView fallujah;

	private ImageView vector_ek762;
	private TextView updates_ek12;
	private TextView personnel_ek14;
	private TextView school_ek12;
	private TextView stats_ek12;
	private ImageView vector_ek763;
	private ImageView vector_ek764;
	private ImageView vector_ek765;
	private ImageView vector_ek766;

		private List<String> idsList;



		public class Actualite {
			private String title;
			private String description;

			public Actualite() {}  // Firestore nécessite un constructeur vide

			public Actualite(String title, String description) {
				this.title = title;
				this.description = description;
			}

			public String getTitle() { return title; }
			public String getDescription() { return description; }
		}


		private void loadActualites() {
			db.collection("Ecoles").document("Vgv1obkaHUASn7Z8rI7I").collection("Actualités").get().addOnCompleteListener(task -> {
				if (task.isSuccessful()) {
					titlesList.clear();
					idsList.clear();
					for (QueryDocumentSnapshot document : task.getResult()) {
						String title = document.getString("title"); // Assurez-vous que le champ s'appelle "title" dans Firestore
						if (title != null) {
							titlesList.add(title);
							idsList.add(document.getId()); // Ajoute l'ID du document à la liste des IDs

						} else {
							Toast.makeText(this, "Document without title field", Toast.LENGTH_SHORT).show();
						}
					}
					adapter.notifyDataSetChanged();
					if (titlesList.isEmpty()) {
						Toast.makeText(this, "No news found", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(this, "Failed to load news", Toast.LENGTH_SHORT).show();
				}
			}).addOnFailureListener(e -> {
				Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
			});
		}
		private BottomNavigationView bottomNavigationView;

	@SuppressLint("MissingInflatedId")
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_update);
		db = FirebaseFirestore.getInstance();


		_bg__frame_132_ek7 = (View) findViewById(R.id._bg__frame_132_ek7);
		add_new_announcement_ek1 = (TextView) findViewById(R.id.add_new_announcement_ek1);
		add_new_announcement_ek1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(admin_update_activity.this, "Selected "  , Toast.LENGTH_SHORT).show();

				Intent intent = new Intent(admin_update_activity.this, add_update_activity.class);

				startActivity(intent);
			}
		});

		welcome_to = (TextView) findViewById(R.id.welcome_to);
		fallujah = (TextView) findViewById(R.id.fallujah);

		vector_ek762 = (ImageView) findViewById(R.id.vector_ek762);

		vector_ek31 = findViewById(R.id.vector_ek31);

		bottomNavigationView = findViewById(R.id.bottom_navigation);
		AdminNavHelper.setupBottomNavigation(this, bottomNavigationView);

		// Initialiser ListView et adapter
		listViewActualites = findViewById(R.id.listViewActualites);
		titlesList = new ArrayList<>();
		idsList = new ArrayList<>();
		// Charger les actualités depuis Firestore
		loadActualites();
		adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titlesList);
		listViewActualites.setAdapter(adapter);

	if(titlesList.isEmpty()){
		Toast.makeText(admin_update_activity.this, "title empty", Toast.LENGTH_SHORT).show();

	}

		// Gérer le clic sur un élément de la ListView
		listViewActualites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String selectedTitle = adapter.getItem(position);
				Toast.makeText(admin_update_activity.this, "Selected: " + selectedTitle, Toast.LENGTH_SHORT).show();
				String selectedId = idsList.get(position);
				// Intent pour ouvrir une nouvelle activité qui montre les détails de l'actualité
				Intent intent = new Intent(admin_update_activity.this, DetailActivity.class);
				intent.putExtra("selectedId", selectedId);
				intent.putExtra("selectedTitle", selectedTitle);


				startActivity(intent);
			}
		});



		vector_ek31.setOnClickListener(v -> {
			Intent intent = new Intent(admin_update_activity.this, Reclamations.class); // Replace NewActivity with the activity you want to start
			startActivity(intent);
		});

	}






	}

	
	