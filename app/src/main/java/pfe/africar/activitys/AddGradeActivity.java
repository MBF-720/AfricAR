

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
    import android.app.AlertDialog;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.EditText;
    import android.widget.ListView;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.Task;
    import com.google.android.material.bottomnavigation.BottomNavigationView;
    import com.google.firebase.firestore.DocumentReference;
    import com.google.firebase.firestore.DocumentSnapshot;
    import com.google.firebase.firestore.FirebaseFirestore;
    import com.google.firebase.firestore.QuerySnapshot;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    import pfe.africar.R;
    import pfe.africar.helpers.AdminNavHelper;

    public class AddGradeActivity extends Activity {





        private  ListView listeView;
        private TextView listeProf,newClasse;

private View  background;

        private String ecoleId="Vgv1obkaHUASn7Z8rI7I";

        private List<String> nomClassesList;


        FirebaseFirestore db;

        public Task<List<String>> getNomClasses(String ecoleId) {
            List<String> nomClassesList = new ArrayList<>();
            db = FirebaseFirestore.getInstance();
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

        private void getClassId(String className) {

            db.collection("Ecoles").document(ecoleId).collection("Classes")
                    .whereEqualTo("nom", className)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    String classId = document.getId();

                                    Intent intent = new Intent(AddGradeActivity.this, EditGradeActivity.class);
                                    intent.putExtra("classId", classId);
                                    intent.putExtra("className", className);

                                    startActivity(intent);
                                    break;
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "cant get cls id ", Toast.LENGTH_SHORT).show();

                                Log.d("ClassroomListsActivity", "Error getting class ID: ", task.getException());
                            }
                        }
                    });

        }



        private BottomNavigationView bottomNavigationView;

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.classroom_lists);





            bottomNavigationView = findViewById(R.id.bottom_navigation);
            AdminNavHelper.setupBottomNavigation(this, bottomNavigationView);


 background=(View) findViewById(R.id._bg__frame_141_ek1);
background.setVisibility(View.INVISIBLE);


listeProf = (TextView) findViewById(R.id.listeProf);
            listeProf.setVisibility(View.INVISIBLE);

             newClasse = (TextView) findViewById(R.id.create_new_category);
           newClasse.setVisibility(View.INVISIBLE);

            listeView =(ListView) findViewById(R.id.ListView);

            db = FirebaseFirestore.getInstance();

            bottomNavigationView = findViewById(R.id.bottom_navigation);
            AdminNavHelper.setupBottomNavigation(this, bottomNavigationView);

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
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AddGradeActivity.this, android.R.layout.simple_list_item_1, nomClassesList);
                    listeView.setAdapter(adapter);

                } else {

                }
            });



            listeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String className = nomClassesList.get(position);
                    getClassId(className); //  this method to get the class ID from the class name

                }
            });


















        }}
