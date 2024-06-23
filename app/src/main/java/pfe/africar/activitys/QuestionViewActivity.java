package pfe.africar.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pfe.africar.R;

public class QuestionViewActivity extends AppCompatActivity {

    private TextView questionText;
    private RadioGroup radioGroup;
    private RadioButton option1, option2, option3, option4;
    private Button nextButton;
    private FirebaseFirestore db;
    private String ecoleId, classId, quizId;
    private List<DocumentSnapshot> questions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private DocumentSnapshot currentQuestion;
    private int score = 0; // Variable to store the score

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_view);

        questionText = findViewById(R.id.question_text);
        radioGroup = findViewById(R.id.radio_group);
        option1 = findViewById(R.id.option_1);
        option2 = findViewById(R.id.option_2);
        option3 = findViewById(R.id.option_3);
        option4 = findViewById(R.id.option_4);
        nextButton = findViewById(R.id.next_button);

        db = FirebaseFirestore.getInstance();

        ecoleId = getIntent().getStringExtra("ecoleId");
        classId = getIntent().getStringExtra("classId");
        quizId = getIntent().getStringExtra("quizId");

        loadQuestions();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioGroup.getCheckedRadioButtonId() != -1) {
                    checkAnswer();
                    if (currentQuestionIndex < questions.size() -1) {
                        currentQuestionIndex++;
                        displayQuestion();
                    } else {
                        showScore();


                    }
                } else {
                    Toast.makeText(QuestionViewActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadQuestions() {
        db.collection("Ecoles").document(ecoleId)
                .collection("Classes").document(classId)
                .collection("Quizzes").document(quizId)
                .collection("questions")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                questions.add(document);
                            }
                            displayQuestion();
                        } else {
                            Toast.makeText(QuestionViewActivity.this, "Failed to load questions", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void displayQuestion() {
        if (questions.isEmpty()) return;

        currentQuestion = questions.get(currentQuestionIndex);
        questionText.setText(currentQuestion.getString("question"));
        List<String> answers = new ArrayList<>();
        answers.add(currentQuestion.getString("correct_answer"));
        answers.add(currentQuestion.getString("false_answer_1"));
        answers.add(currentQuestion.getString("false_answer_2"));
        answers.add(currentQuestion.getString("false_answer_3"));
        Collections.shuffle(answers);

        option1.setText(answers.get(0));
        option2.setText(answers.get(1));
        option3.setText(answers.get(2));
        option4.setText(answers.get(3));

        radioGroup.clearCheck();
    }

    private void checkAnswer() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        String selectedAnswer = selectedRadioButton.getText().toString();

        if (selectedAnswer.equals(currentQuestion.getString("correct_answer"))) {
            score++;
        }
    }

    private void showScore() {
        Intent intent = new Intent(QuestionViewActivity.this, quiz_score.class);
        intent.putExtra("score", score);
        intent.putExtra("questions", (questions.size()));
        startActivity(intent);

    }

}
