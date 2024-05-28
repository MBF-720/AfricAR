
package pfe.africar.activitys;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import pfe.africar.R;

public class add_emploi_de_temps_activity extends Activity {

	private static final int FILE_SELECT_CODE = 0;

	private FirebaseFirestore db;
	private StorageReference storageRef;
	private Uri fileUri;
	private String classId = "CPY5KGWxBex1B5rHnFEb";
	private String EcoleId = "Vgv1obkaHUASn7Z8rI7I";
	private TextView label_ek25;
	private TextView button_ek21;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_emploi_de_temps);

		db = FirebaseFirestore.getInstance();
		storageRef = FirebaseStorage.getInstance().getReference();

		label_ek25 = findViewById(R.id.label_ek25);
		button_ek21 = findViewById(R.id.button_ek21);

		label_ek25.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				uploadFile();



			}
		});




		button_ek21.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (fileUri != null) {
					saveTimetableToFirestore(fileUri);
					  		String fileName = getFileName(fileUri);
					     label_ek25.setText(fileName);            


				} else {
					Toast.makeText(add_emploi_de_temps_activity.this, "Please select a file first.", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void uploadFile() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		try {
			startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK) {
			fileUri = data.getData();
			Log.d("File Uri", "File Uri: " + fileUri.toString());
		}
	}

	private void saveTimetableToFirestore(Uri fileUri) {
		StorageReference fileRef = storageRef.child("timetables/" + fileUri.getLastPathSegment());
		fileRef.putFile(fileUri)
				.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
					@Override
					public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
						fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
							@Override
							public void onSuccess(Uri uri) {
								String fileUrl = uri.toString();
								Map<String, Object> timetable = new HashMap<>();
								timetable.put("timetable", fileUrl);

								db.collection("Ecoles").document(EcoleId).collection("Classes").document(classId)
										.update(timetable)
										.addOnSuccessListener(new OnSuccessListener<Void>() {
											@Override
											public void onSuccess(Void aVoid) {
												Toast.makeText(add_emploi_de_temps_activity.this, "Timetable uploaded successfully.", Toast.LENGTH_SHORT).show();
											}
										})
										.addOnFailureListener(new OnFailureListener() {
											@Override
											public void onFailure(@NonNull Exception e) {
												Toast.makeText(add_emploi_de_temps_activity.this, "Failed to upload timetable.", Toast.LENGTH_SHORT).show();
												Log.d("Firestore Error", e.toString());
											}
										});
							}
						});
					}
				})
				.addOnFailureListener(new OnFailureListener() {
					@Override
					public void onFailure(@NonNull Exception e) {
						Toast.makeText(add_emploi_de_temps_activity.this, "Failed to upload file to storage.", Toast.LENGTH_SHORT).show();
						Log.d("Storage Error", e.toString());
					}
				});
	}


	@SuppressLint("Range")
	private String getFileName(Uri uri) {
		String result = null;
		if (uri.getScheme().equals("content")) {
			Cursor cursor = getContentResolver().query(uri, null, null, null, null);
			try {
				if (cursor != null && cursor.moveToFirst()) {
					result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
				}
			} finally {
				cursor.close();
			}
		}
		if (result == null) {
			result = uri.getLastPathSegment();
		}
		return result;
	}






}

	
	