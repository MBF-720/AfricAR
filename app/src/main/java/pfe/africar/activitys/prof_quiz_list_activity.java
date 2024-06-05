

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
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import pfe.africar.R;

	public class prof_quiz_list_activity extends Activity {


	private View _bg__prof_quiz_list_ek2;
	private TextView quizzes_ek8;
	private View _bg__frame_107_ek3;
	private View _bg__frame_81_ek5;
	private View _bg__frame_77_ek5;
	private View _bg__frame_76_ek17;
	private View _bg__frame_75_ek17;
	private TextView physics_fundamentals_ek8;
	private TextView _10_04_2024_ek8;
	private View _bg__flowbite_edit_solid_ek9;
	private View _bg__group_ek27;
	private ImageView vector_ek96;
	private ImageView vector_ek97;
	private View _bg__frame_78_ek5;
	private View _bg__frame_76_ek19;
	private View _bg__frame_75_ek19;
	private TextView physics_fundamentals_ek9;
	private TextView _10_04_2024_ek9;
	private View _bg__flowbite_edit_solid_ek11;
	private View _bg__group_ek29;
	private ImageView vector_ek98;
	private ImageView vector_ek99;
	private View _bg__frame_79_ek5;
	private View _bg__frame_76_ek21;
	private View _bg__frame_75_ek21;
	private TextView physics_fundamentals_ek10;
	private TextView _10_04_2024_ek10;
	private View _bg__flowbite_edit_solid_ek13;
	private View _bg__group_ek31;
	private ImageView vector_ek100;
	private ImageView vector_ek101;
	private View _bg__frame_80_ek5;
	private View _bg__frame_76_ek23;
	private View _bg__frame_75_ek23;
	private TextView physics_fundamentals_ek11;
	private TextView _10_04_2024_ek11;
	private View _bg__flowbite_edit_solid_ek15;
	private View _bg__group_ek33;
	private ImageView vector_ek102;
	private ImageView vector_ek103;
	private View _bg__component_1_ek17;
	private TextView button_ek12;
	private ImageView x_1_ek4;
	private View _bg__healthicons_i_exam_multiple_choice_ek13;
	private ImageView rectangle_32_ek5;
	private TextView quizzes_ek9;
	private TextView courses_ek12;
	private TextView discover_ek6;
	private TextView profile_ek7;
	private View _bg__group_52_ek13;
	private ImageView vector_ek104;
	private ImageView vector_ek105;
	private ImageView vector_ek106;
	private ImageView vector_ek107;

		private FirebaseFirestore db;
		private ListView quizListView;
		private ArrayList<String> quizList;
		private ArrayAdapter<String> quizAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.prof_quiz_list);


		_bg__prof_quiz_list_ek2 = (View) findViewById(R.id._bg__prof_quiz_list_ek2);
		quizzes_ek8 = (TextView) findViewById(R.id.quizzes_ek8);
		_bg__component_1_ek17 = (View) findViewById(R.id._bg__component_1_ek17);
		button_ek12 = (TextView) findViewById(R.id.button_ek12);
		x_1_ek4 = (ImageView) findViewById(R.id.x_1_ek4);
		_bg__healthicons_i_exam_multiple_choice_ek13 = (View) findViewById(R.id._bg__healthicons_i_exam_multiple_choice_ek13);
		rectangle_32_ek5 = (ImageView) findViewById(R.id.rectangle_32_ek5);
		quizzes_ek9 = (TextView) findViewById(R.id.quizzes_ek9);
		courses_ek12 = (TextView) findViewById(R.id.courses_ek12);
		discover_ek6 = (TextView) findViewById(R.id.discover_ek6);
		profile_ek7 = (TextView) findViewById(R.id.profile_ek7);
		vector_ek104 = (ImageView) findViewById(R.id.vector_ek104);
		vector_ek105 = (ImageView) findViewById(R.id.vector_ek105);
		vector_ek106 = (ImageView) findViewById(R.id.vector_ek106);
		vector_ek107 = (ImageView) findViewById(R.id.vector_ek107);

		db = FirebaseFirestore.getInstance();
		quizListView = findViewById(R.id.quiz_list_view);
		quizList = new ArrayList<>();
		quizAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, quizList);
		quizListView.setAdapter(quizAdapter);

		// Chargement des quizzes depuis Firestore
		loadQuizzes();
		//custom code goes here
		button_ek12.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(prof_quiz_list_activity.this, add_quiz_activity.class));
			}});
	};

		private void loadQuizzes() {
			// Define the path to the specific collection
			CollectionReference questionsRef = db.collection("ecoles")
					.document("Vgv1obkaHUASn7Z8rI7I")
					.collection("classes")
					.document("CPY5KGWxBex1B5rHnFEb")
					.collection("matieres")
					.document("BAe7Ylf1Zbn9SOxsyWfy")
					.collection("cours")
					.document("wMEKttamiaJKtSBda6R0")
					.collection("quizzes")
					.document("TOJkfNBu8l7fnLGmdDbY")
					.collection("questions");

			questionsRef.get().addOnCompleteListener(task -> {
				if (task.isSuccessful()) {
					quizList.clear(); // Clear the list before adding new items
					for (QueryDocumentSnapshot document : task.getResult()) {
						String quizTitle = document.getString("question");
						quizList.add(quizTitle);
					}
					quizAdapter.notifyDataSetChanged();
				} else {
					Toast.makeText(this, "Error loading quizzes: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
				}
			});
		}
	}



	
	