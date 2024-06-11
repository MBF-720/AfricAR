package pfe.africar.activitys;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pfe.africar.R;

public class add_quiz_activity extends Activity {

	private TextView add_new_quiz;
	private TextView question;
	private EditText label_ek12;  // EditText for question
	private EditText label_ek13;  // EditText for correct answer
	private EditText label_ek14;  // EditText for false answer 1
	private EditText label_ek15;  // EditText for false answer 2
	private EditText label_ek16;  // EditText for false answer 3
	private TextView button_ek14;
	private TextView add_new_question;
	private EditText quizTitleInput;
	private int questionCounter = 0;
	private CollectionReference quizRef;
	private List<Map<String, Object>> questionsList = new ArrayList<>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_quiz);

		// Initialize views
		add_new_quiz = findViewById(R.id.add_new_quiz);
		question = findViewById(R.id.question);
		label_ek12 = findViewById(R.id.label_ek12);
		label_ek13 = findViewById(R.id.label_ek13);
		label_ek14 = findViewById(R.id.label_ek14);
		label_ek15 = findViewById(R.id.label_ek15);
		label_ek16 = findViewById(R.id.label_ek16);
		button_ek14 = findViewById(R.id.button_ek14);
		add_new_question = findViewById(R.id.add_new_question);
		quizTitleInput = findViewById(R.id.quiz_title_input);

		// Set click listeners
		button_ek14.setOnClickListener(v -> saveQuiz());
		add_new_question.setOnClickListener(v -> addNewQuestion());

		// Initialize Firestore reference
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		quizRef = db.collection("Ecoles")
				.document("Vgv1obkaHUASn7Z8rI7I")
				.collection("Classes")
				.document("CPY5KGWxBex1B5rHnFEb")
				.collection("Quizzes");
	}

	private void addNewQuestion() {
		String quizTitle = quizTitleInput.getText().toString().trim();
		if (quizTitle.isEmpty()) {
			Toast.makeText(this, "Please enter a quiz title first", Toast.LENGTH_SHORT).show();
			return;
		}

		String questionText = label_ek12.getText().toString().trim();
		String correctAnswerText = label_ek13.getText().toString().trim();
		String falseAnswer1Text = label_ek14.getText().toString().trim();
		String falseAnswer2Text = label_ek15.getText().toString().trim();
		String falseAnswer3Text = label_ek16.getText().toString().trim();

		if (questionText.isEmpty() || correctAnswerText.isEmpty() || falseAnswer1Text.isEmpty() || falseAnswer2Text.isEmpty() || falseAnswer3Text.isEmpty()) {
			Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
			return;
		}

		Map<String, Object> question = new HashMap<>();
		question.put("question", questionText);
		question.put("correct_answer", correctAnswerText);
		question.put("false_answer_1", falseAnswer1Text);
		question.put("false_answer_2", falseAnswer2Text);
		question.put("false_answer_3", falseAnswer3Text);

		questionsList.add(question);

		label_ek12.setText("");
		label_ek13.setText("");
		label_ek14.setText("");
		label_ek15.setText("");
		label_ek16.setText("");

		questionCounter++;
		Toast.makeText(this, "Question " + questionCounter + " added", Toast.LENGTH_SHORT).show();
	}

	private void saveQuiz() {
		String userEmail = null;
		String quizTitle = quizTitleInput.getText().toString().trim();
		if (quizTitle.isEmpty() || questionsList.isEmpty()) {
			Toast.makeText(this, "Please add at least one question and a quiz title", Toast.LENGTH_SHORT).show();
			return;
		}
       // Get the current user
		FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

		if (currentUser != null) {
			 userEmail = currentUser.getEmail();
			Log.d(TAG, "Current user email: " + userEmail);}else {
			Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();

		}

			Map<String, Object> quizData = new HashMap<>();
		quizData.put("title", quizTitle);
		quizData.put("profId",userEmail);

		quizRef.add(quizData).addOnSuccessListener(documentReference -> {
			WriteBatch batch = FirebaseFirestore.getInstance().batch();
			CollectionReference questionsRef = documentReference.collection("questions");

			for (Map<String, Object> question : questionsList) {
				batch.set(questionsRef.document(), question);
			}

			batch.commit().addOnSuccessListener(aVoid -> {
				Toast.makeText(add_quiz_activity.this, "Quiz saved successfully with " + questionCounter + " questions", Toast.LENGTH_SHORT).show();
				finish();
			}).addOnFailureListener(e -> Toast.makeText(add_quiz_activity.this, "Error saving quiz: " + e.getMessage(), Toast.LENGTH_SHORT).show());
		}).addOnFailureListener(e -> Toast.makeText(add_quiz_activity.this, "Error creating quiz: " + e.getMessage(), Toast.LENGTH_SHORT).show());
	}
}
