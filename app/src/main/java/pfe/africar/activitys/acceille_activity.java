
	 
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


import android.telecom.Call;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import pfe.africar.R;

	public class acceille_activity extends Activity {

	
	private View _bg__acceille_ek2;
	private View _bg__search_bar_ek1;
	private View rectangle_25;
	private TextView search_courses__quizzes__grades___;
	private View _bg__tabler_search_ek1;
	private ImageView vector_ek17;
	private View _bg__mingcute_settings_6_fill_ek1;
	private View _bg__group_ek1;
	private ImageView vector_ek18;
	private ImageView vector_ek19;
	private View _bg__frame_53_ek1;
	private View _bg__frame_10_ek7;
	private View _bg__frame_8_ek9;
	private View _bg__frame_1_ek3;
	private TextView assignments;
	private TextView history_essay_due_friday__april_5th__make_sure_to_include_citations_;
	private TextView _10_40_am;
	private View _bg__frame_9_ek7;
	private View ellipse_25;
	private View ellipse_26;
	private View ellipse_27;
	private View _bg__frame_11_ek7;
	private View _bg__frame_8_ek11;
	private View _bg__frame_1_ek5;
	private TextView activities;
	private TextView history_essay_due_friday__april_5th__make_sure_to_include_citations__ek1;
	private TextView _10_40_am_ek1;
	private View _bg__frame_9_ek9;
	private View ellipse_25_ek1;
	private View ellipse_26_ek1;
	private View ellipse_27_ek1;
	private View _bg__frame_12_ek7;
	private View _bg__frame_8_ek13;
	private View _bg__frame_1_ek7;
	private TextView events;
	private TextView history_essay_due_friday__april_5th__make_sure_to_include_citations__ek2;
	private TextView _10_40_am_ek2;
	private View _bg__frame_9_ek11;
	private View ellipse_25_ek2;
	private View ellipse_26_ek2;
	private View ellipse_27_ek2;
	private View _bg__frame_13_ek7;
	private View _bg__frame_8_ek15;
	private View _bg__frame_1_ek9;
	private TextView opportunities;
	private TextView history_essay_due_friday__april_5th__make_sure_to_include_citations__ek3;
	private TextView _10_40_am_ek3;
	private View _bg__frame_9_ek13;
	private View ellipse_25_ek3;
	private View ellipse_26_ek3;
	private View ellipse_27_ek3;
	private ImageView rectangle_32;
	private TextView good_morning__;
	private TextView foulen_ben_foulen;
	private View _bg__mingcute_notification_fill_ek1;
	private View _bg__group_ek3;
	private ImageView vector_ek20;
	private ImageView vector_ek21;
	private View ellipse_24;
	private View _bg__healthicons_i_exam_multiple_choice_ek1;
	private View _bg__component_2_ek9;
	private ImageView vector_ek22;
	private TextView quizzes;
	private TextView courses;
	private TextView profile;
	private TextView discover;
	private View _bg__group_52_ek1;
	private ImageView vector_ek23;
	private ImageView vector_ek24;
	private ImageView vector_ek25;
	private ImageView vector_ek26;
		public class Acceil extends AppCompatActivity {
			private ListView listViewActualites;
			private ArrayAdapter<String> adapter;
			private List<String> titles = new ArrayList<>();
			private FirebaseFirestore db = FirebaseFirestore.getInstance();

			@Override
			public void onCreate(Bundle savedInstanceState) {

				super.onCreate(savedInstanceState);
				setContentView(R.layout.acceille);
				listViewActualites = findViewById(R.id.listViewActualites);
				adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles);
				listViewActualites.setAdapter(adapter);

				loadActualites();
				listViewActualites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						String selectedTitle = adapter.getItem(position);
						// Intent pour ouvrir une nouvelle activité qui montre les détails de l'actualité
						// Passez le titre ou un identifiant unique si nécessaire
						Intent intent = new Intent(acceille_activity.this,DetailActivity.class);
						intent.putExtra("title", selectedTitle);
						startActivity(intent);
					}
				});
				_bg__acceille_ek2 = (View) findViewById(R.id._bg__acceille_ek2);
				_bg__search_bar_ek1 = (View) findViewById(R.id._bg__search_bar_ek1);
				rectangle_25 = (View) findViewById(R.id.rectangle_25);
				search_courses__quizzes__grades___ = (TextView) findViewById(R.id.search_courses__quizzes__grades___);
				vector_ek17 = (ImageView) findViewById(R.id.vector_ek17);
				vector_ek18 = (ImageView) findViewById(R.id.vector_ek18);
				vector_ek19 = (ImageView) findViewById(R.id.vector_ek19);
				rectangle_32 = (ImageView) findViewById(R.id.rectangle_32);
				good_morning__ = (TextView) findViewById(R.id.good_morning__);
				foulen_ben_foulen = (TextView) findViewById(R.id.foulen_ben_foulen);
				vector_ek20 = (ImageView) findViewById(R.id.vector_ek20);
				vector_ek21 = (ImageView) findViewById(R.id.vector_ek21);
				ellipse_24 = (View) findViewById(R.id.ellipse_24);
				_bg__healthicons_i_exam_multiple_choice_ek1 = (View) findViewById(R.id._bg__healthicons_i_exam_multiple_choice_ek1);
				vector_ek22 = (ImageView) findViewById(R.id.vector_ek22);
				quizzes = (TextView) findViewById(R.id.quizzes);
				courses = (TextView) findViewById(R.id.courses);
				profile = (TextView) findViewById(R.id.profile);
				discover = (TextView) findViewById(R.id.discover);
				_bg__group_52_ek1 = (View) findViewById(R.id._bg__group_52_ek1);
				vector_ek23 = (ImageView) findViewById(R.id.vector_ek23);
				vector_ek24 = (ImageView) findViewById(R.id.vector_ek24);
				vector_ek25 = (ImageView) findViewById(R.id.vector_ek25);
				vector_ek26 = (ImageView) findViewById(R.id.vector_ek26);


				//custom code goes here

			}
			private void loadActualites() {
				db.collection("ecoles").document("actualité_de_l'école").collection("actualités")
						.get()
						.addOnCompleteListener(task -> {
							if (task.isSuccessful()) {
								for (QueryDocumentSnapshot document : task.getResult()) {
									titles.add(document.getString("title"));  // Assurez-vous que 'title' est correct
								}
								adapter.notifyDataSetChanged();
							} else {
								Toast.makeText(this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
							}
						});
		}


		}}
	
	