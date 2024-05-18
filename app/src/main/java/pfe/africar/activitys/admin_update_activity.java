

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

	import com.google.firebase.firestore.FirebaseFirestore;
	import com.google.firebase.firestore.QueryDocumentSnapshot;

	import java.util.ArrayList;
	import java.util.List;

	import pfe.africar.R;

	public class admin_update_activity extends Activity {

			private FirebaseFirestore db;
			private ListView listViewActualites;
			private List<String> titlesList;
			private ArrayAdapter<String> adapter;

	private View _bg__admin_update_ek2;
	private View _bg__frame_133_ek1;
	private View _bg__frame_10_ek11;
	private View _bg__frame_8_ek25;
	private View _bg__frame_1_ek21;
	private TextView assignments_ek3;
	private TextView history_essay_due_friday__april_5th__make_sure_to_include_citations__ek8;
	private TextView _10_40_am_ek8;
	private View _bg__frame_9_ek23;
	private View ellipse_25_ek8;
	private View ellipse_26_ek8;
	private View ellipse_27_ek8;
	private View _bg__frame_129_ek1;
	private View _bg__frame_8_ek27;
	private View _bg__frame_1_ek23;
	private TextView reminders_ek1;
	private TextView history_essay_due_friday__april_5th__make_sure_to_include_citations__ek9;
	private TextView _10_40_am_ek9;
	private View _bg__frame_9_ek25;
	private View ellipse_25_ek9;
	private View ellipse_26_ek9;
	private View ellipse_27_ek9;
	private View _bg__frame_130_ek1;
	private View _bg__frame_8_ek29;
	private View _bg__frame_1_ek25;
	private TextView notices_ek1;
	private TextView history_essay_due_friday__april_5th__make_sure_to_include_citations__ek10;
	private TextView _10_40_am_ek10;
	private View _bg__frame_9_ek27;
	private View ellipse_25_ek10;
	private View ellipse_26_ek10;
	private View ellipse_27_ek10;
	private View _bg__frame_131_ek1;
	private View _bg__frame_8_ek31;
	private View _bg__frame_1_ek27;
	private TextView events_ek3;
	private TextView history_essay_due_friday__april_5th__make_sure_to_include_citations__ek11;
	private TextView _10_40_am_ek11;
	private View _bg__frame_9_ek29;
	private View ellipse_25_ek11;
	private View ellipse_26_ek11;
	private View ellipse_27_ek11;
	private View _bg__frame_132_ek7;
	private TextView add_new_announcement_ek1;
	private View _bg__frame_128_ek13;
	private ImageView vector_ek759;
	private ImageView rectangle_32_ek25;
	private View _bg__mingcute_notification_fill_ek11;
	private View _bg__group_ek181;
	private ImageView vector_ek760;
	private ImageView vector_ek761;
	private View ellipse_24_ek5;
	private TextView welcome_to;
	private TextView fallujah;
	private View _bg__component_2_ek23;
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
		rectangle_32_ek25 = (ImageView) findViewById(R.id.rectangle_32_ek25);

		welcome_to = (TextView) findViewById(R.id.welcome_to);
		fallujah = (TextView) findViewById(R.id.fallujah);

		vector_ek762 = (ImageView) findViewById(R.id.vector_ek762);
		updates_ek12 = (TextView) findViewById(R.id.updates_ek12);
		personnel_ek14 = (TextView) findViewById(R.id.personnel_ek14);
		school_ek12 = (TextView) findViewById(R.id.school_ek12);
		stats_ek12 = (TextView) findViewById(R.id.stats_ek12);
		vector_ek763 = (ImageView) findViewById(R.id.vector_ek763);
		vector_ek764 = (ImageView) findViewById(R.id.vector_ek764);
		vector_ek765 = (ImageView) findViewById(R.id.vector_ek765);
		vector_ek766 = (ImageView) findViewById(R.id.vector_ek766);


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

				Toast.makeText(admin_update_activity.this, "Selected ID: " + selectedId, Toast.LENGTH_SHORT).show();

				startActivity(intent);
			}
		});	//custom code goes here

	}






	}

	
	