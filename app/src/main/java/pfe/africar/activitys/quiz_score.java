package pfe.africar.activitys;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import pfe.africar.R;
import pfe.africar.helpers.EleveNavBar;

public class quiz_score extends AppCompatActivity {

    private TextView scoreTextView;
    private TextView questionCountTextView;
    private TextView averageScoreTextView;

    private int score ; // Example score
    private int questionCount ; // Example number of questions

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_score);

        scoreTextView = findViewById(R.id.score_text_view);
        questionCountTextView = findViewById(R.id.question_count_text_view);
        averageScoreTextView = findViewById(R.id.average);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        EleveNavBar.setupBottomNavigation(this, bottomNavigationView);

         score = getIntent().getIntExtra("score", 0);
         questionCount = getIntent().getIntExtra("questions", 0);




        // Convertir les valeurs de chaîne de caractères en entiers
        if ( questionCount != 0) {
            try {

                // Mettre à jour les TextView et calculer le score moyen
                updateScore(score);
                updateQuestionCount(questionCount);
                updateAverageScore(score, questionCount);
            } catch (NumberFormatException e) {
                // Gérer les erreurs de conversion
                scoreTextView.setText("Score: N/A");
                questionCountTextView.setText("Questions: N/A");
                averageScoreTextView.setText("Average Score: N/A");
            }
        } else {
            // Gérer les valeurs nulles
            scoreTextView.setText("Score: N/A");
            questionCountTextView.setText("Questions: N/A");
            averageScoreTextView.setText("Average Score: N/A");
        }
    }

    private void updateScore(int score) {
        scoreTextView.setText("Score: " + score);
    }

    private void updateQuestionCount(int questionCount) {
        questionCountTextView.setText("Questions: " + questionCount);
    }

    private void updateAverageScore(int score, int questionCount) {
        if (questionCount == 0) {
            averageScoreTextView.setText("Average Score: N/A");
        } else {
            int averageScore = (int) Math.round((double) score * 100 / questionCount);
            averageScoreTextView.setText("Average Score: " + averageScore + "%");
        }
    }
}
