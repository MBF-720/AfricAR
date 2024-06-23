
	 
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

	public class classroom_lists_activity extends Activity {

	

	private View creatClas;


	private  ListView listeView;
	private TextView listeProf;



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

									Intent intent = new Intent(classroom_lists_activity.this, list_eleve__activity.class);
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

		private void addNewClass(String className) {
			Map<String, Object> classData = new HashMap<>();
			classData.put("nom", className);


			db.collection("Ecoles").document(ecoleId).collection("Classes")
					.add(classData)
					.addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
						@Override
						public void onComplete(Task<DocumentReference> addTask) {
							if (addTask.isSuccessful()) {
								Toast.makeText(getApplicationContext(), "Class added successfully", Toast.LENGTH_SHORT).show();
								// Update the list
								getNomClasses(ecoleId).addOnCompleteListener(new OnCompleteListener<List<String>>() {
									@Override
									public void onComplete(Task<List<String>> getTask) {
										if (getTask.isSuccessful()) {
											nomClassesList = getTask.getResult();
											ArrayAdapter<String> adapter = new ArrayAdapter<>(classroom_lists_activity.this, android.R.layout.simple_list_item_1, nomClassesList);
											listeView.setAdapter(adapter);
										} else {
											Toast.makeText(getApplicationContext(), "Failed to update class list", Toast.LENGTH_SHORT).show();
										}
									}
								});
							} else {
								Toast.makeText(getApplicationContext(), "Failed to add class", Toast.LENGTH_SHORT).show();
							}
						}
					});
		}

		private void showAddClassDialog() {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			LayoutInflater inflater = getLayoutInflater();
			View dialogView = inflater.inflate(R.layout.dialog_add_class, null);
			builder.setView(dialogView);

			final EditText editTextClassName = dialogView.findViewById(R.id.editTextClassName);

			builder.setTitle("Add New Class")
					.setPositiveButton("Add", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							String className = editTextClassName.getText().toString();
							if (!className.isEmpty()) {
								addNewClass(className);
							} else {
								Toast.makeText(getApplicationContext(), "Class name cannot be empty", Toast.LENGTH_SHORT).show();
							}
						}
					})
					.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});

			AlertDialog alertDialog = builder.create();
			alertDialog.show();
		}

		private BottomNavigationView bottomNavigationView;


		@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.classroom_lists);

		
		creatClas = (View) findViewById(R.id._bg__frame_141_ek1);



			listeProf = (TextView) findViewById(R.id.listeProf);

		listeView =(ListView) findViewById(R.id.ListView);


			bottomNavigationView = findViewById(R.id.bottom_navigation);
			AdminNavHelper.setupBottomNavigation(this, bottomNavigationView);

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
				getClassId(className); //  this method to get the class ID from the class name

			}
		});





			creatClas.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					showAddClassDialog();
				}
			});

			listeProf.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(classroom_lists_activity.this, list_des_prof_activity.class);
					startActivity(intent);
				}

			});


// Ajoutez ce code dans la méthode onCreate après avoir initialisé listeView

			listeView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
					String className = nomClassesList.get(position);

					// Afficher la boîte de dialogue de confirmation pour supprimer la classe
					showDeleteClassDialog(className);

					return true; // Indiquer que l'événement a été consommé
				}
			});








	}
		private void deleteClass(String className) {
			db.collection("Ecoles").document(ecoleId).collection("Classes")
					.whereEqualTo("nom", className)
					.get()
					.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
						@Override
						public void onComplete(Task<QuerySnapshot> task) {
							if (task.isSuccessful() && !task.getResult().isEmpty()) {
								for (DocumentSnapshot document : task.getResult()) {
									String classId = document.getId();

									// Supprimer le document de la collection
									db.collection("Ecoles").document(ecoleId).collection("Classes")
											.document(classId)
											.delete()
											.addOnCompleteListener(new OnCompleteListener<Void>() {
												@Override
												public void onComplete(Task<Void> deleteTask) {
													if (deleteTask.isSuccessful()) {
														Toast.makeText(getApplicationContext(), "Class deleted successfully", Toast.LENGTH_SHORT).show();
														// Mettre à jour la liste des classes
														getNomClasses(ecoleId).addOnCompleteListener(new OnCompleteListener<List<String>>() {
															@Override
															public void onComplete(Task<List<String>> getTask) {
																if (getTask.isSuccessful()) {
																	nomClassesList = getTask.getResult();
																	ArrayAdapter<String> adapter = new ArrayAdapter<>(classroom_lists_activity.this, android.R.layout.simple_list_item_1, nomClassesList);
																	listeView.setAdapter(adapter);
																} else {
																	Toast.makeText(getApplicationContext(), "Failed to update class list", Toast.LENGTH_SHORT).show();
																}
															}
														});
													} else {
														Toast.makeText(getApplicationContext(), "Failed to delete class", Toast.LENGTH_SHORT).show();
													}
												}
											});
								}
							} else {
								Toast.makeText(getApplicationContext(), "Class not found", Toast.LENGTH_SHORT).show();
							}
						}
					});
		}
		private void showDeleteClassDialog(String className) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Delete Class")
					.setMessage("Are you sure you want to delete the class \"" + className + "\"?")
					.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// Supprimer la classe de Firestore
							deleteClass(className);
						}
					})
					.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});

			AlertDialog alertDialog = builder.create();
			alertDialog.show();
		}




	}

	
	