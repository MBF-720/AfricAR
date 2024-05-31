
package pfe.africar.activitys;

	import android.app.Activity;
	import android.content.Intent;
	import android.os.Bundle;
	import android.view.View;
	import android.widget.ArrayAdapter;
	import android.widget.ImageView;
	import android.widget.ListView;
	import android.widget.TextView;

	import com.google.firebase.database.DataSnapshot;
	import com.google.firebase.database.DatabaseError;
	import com.google.firebase.database.DatabaseReference;
	import com.google.firebase.database.FirebaseDatabase;
	import com.google.firebase.database.ValueEventListener;

	import java.util.ArrayList;
	import java.util.List;

	import pfe.africar.R;

	public class prof_cour_list_activity extends Activity {

	
	private View _bg__prof_cour_list_ek2;
	private TextView courses_ek8;
	private View _bg__component_1_ek15;
	private TextView button_ek11;
	private View _bg__component_11_ek5;
	private TextView courses_ek9;
	private ImageView x_1_ek3;
	private View _bg__healthicons_i_exam_multiple_choice_ek11;
	private ImageView rectangle_32_ek4;
	private TextView quizzes_ek7;
	private TextView courses_ek11;
	private TextView profile_ek6;
	private TextView discover_ek5;
	private View _bg__group_52_ek11;
	private ImageView vector_ek92;
	private ImageView vector_ek93;
	private ImageView vector_ek94;
	private ImageView vector_ek95;
		private ListView listView;
		private List<String> courseList;
		private ArrayAdapter<String> adapter;
		private DatabaseReference databaseReference;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.prof_cour_list);

		listView = findViewById(R.id.course_list_view);
		courseList = new ArrayList<>();
		adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseList);
		listView.setAdapter(adapter);

		databaseReference = FirebaseDatabase.getInstance().getReference("Cours");
		databaseReference.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				courseList.clear();
				for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
					String courseName = snapshot.child("wMEKttamiaJKtSBda6R0").getValue(String.class);//todo chnya hetha
					courseList.add(courseName);
				}
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});
		_bg__prof_cour_list_ek2 = (View) findViewById(R.id._bg__prof_cour_list_ek2);
		courses_ek8 = (TextView) findViewById(R.id.courses_ek8);

		_bg__component_1_ek15 = (View) findViewById(R.id._bg__component_1_ek15);
		button_ek11 = (TextView) findViewById(R.id.button_ek11);
		button_ek11.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(prof_cour_list_activity.this, add_cours_activity.class);
				startActivity(intent);
			}
		});
		_bg__component_11_ek5 = (View) findViewById(R.id._bg__component_11_ek5);
		courses_ek9 = (TextView) findViewById(R.id.courses_ek9);
		x_1_ek3 = (ImageView) findViewById(R.id.x_1_ek3);
		_bg__healthicons_i_exam_multiple_choice_ek11 = (View) findViewById(R.id._bg__healthicons_i_exam_multiple_choice_ek11);
		rectangle_32_ek4 = (ImageView) findViewById(R.id.rectangle_32_ek4);
		quizzes_ek7 = (TextView) findViewById(R.id.quizzes_ek7);
		courses_ek11 = (TextView) findViewById(R.id.courses_ek11);
		profile_ek6 = (TextView) findViewById(R.id.profile_ek6);
		discover_ek5 = (TextView) findViewById(R.id.discover_ek5);
		_bg__group_52_ek11 = (View) findViewById(R.id._bg__group_52_ek11);
		vector_ek92 = (ImageView) findViewById(R.id.vector_ek92);
		vector_ek93 = (ImageView) findViewById(R.id.vector_ek93);
		vector_ek94 = (ImageView) findViewById(R.id.vector_ek94);
		vector_ek95 = (ImageView) findViewById(R.id.vector_ek95);
	
		
		//custom code goes here
	
	}
}
	
	