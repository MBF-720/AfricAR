package pfe.africar.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import pfe.africar.R;

public class add_quiz_activity extends Activity {

	private View _bg__add_quiz_ek2;
	private TextView add_new_quiz;
	private TextView question;
	private EditText label_ek12;  // EditText for question
	private EditText label_ek13;  // EditText for correct answer
	private EditText label_ek14;  // EditText for false answer 1
	private EditText label_ek15;  // EditText for false answer 2
	private EditText label_ek16;  // EditText for false answer 3
	private TextView button_ek14;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_quiz);

		// Initialize views
		_bg__add_quiz_ek2 = findViewById(R.id._bg__add_quiz_ek2);
		add_new_quiz = findViewById(R.id.add_new_quiz);
		question = findViewById(R.id.question);
		label_ek12 = findViewById(R.id.label_ek12);
		label_ek13 = findViewById(R.id.label_ek13);
		label_ek14 = findViewById(R.id.label_ek14);
		label_ek15 = findViewById(R.id.label_ek15);
		label_ek16 = findViewById(R.id.label_ek16);
		button_ek14 = findViewById(R.id.button_ek14);

		// Set click listener on the button
		button_ek14.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addQuizToDatabase();
			}
		});
	}

	private void addQuizToDatabase() {
		String questionText = label_ek12.getText().toString().trim();
		String correctAnswerText = label_ek13.getText().toString().trim();
		String falseAnswer1Text = label_ek14.getText().toString().trim();
		String falseAnswer2Text = label_ek15.getText().toString().trim();
		String falseAnswer3Text = label_ek16.getText().toString().trim();

		if (questionText.isEmpty() || correctAnswerText.isEmpty() || falseAnswer1Text.isEmpty() || falseAnswer2Text.isEmpty() || falseAnswer3Text.isEmpty()) {
			Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
			return;
		}

		// Create a map to store the quiz data
		Map<String, Object> quiz = new HashMap<>();
		quiz.put("question", questionText);
		quiz.put("correct_answer", correctAnswerText);
		quiz.put("false_answer_1", falseAnswer1Text);
		quiz.put("false_answer_2", falseAnswer2Text);
		quiz.put("false_answer_3", falseAnswer3Text);

		// Get Firestore instance
		FirebaseFirestore db = FirebaseFirestore.getInstance();

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

		// Add a new document to the questions collection
		questionsRef.add(quiz)
				.addOnSuccessListener(documentReference -> {
					Toast.makeText(this, "Quiz added successfully", Toast.LENGTH_SHORT).show();
					// Clear the fields
					label_ek12.setText("");
					label_ek13.setText("");
					label_ek14.setText("");
					label_ek15.setText("");
					label_ek16.setText("");
				})
				.addOnFailureListener(e -> Toast.makeText(this, "Error adding quiz: " + e.getMessage(), Toast.LENGTH_SHORT).show());
	}
}


	
	