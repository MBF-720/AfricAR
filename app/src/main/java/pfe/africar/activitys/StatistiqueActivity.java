package pfe.africar.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import pfe.africar.R;
import pfe.africar.classes.BarChartView;
import pfe.africar.classes.PieChartView;

public class StatistiqueActivity extends AppCompatActivity {

    private TextView titleTextView;
    private PieChartView pieChartView;
    private Button class1Button;
    private Button class2Button;
    private Button class3Button;

    private BarChartView barChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistique);

        titleTextView = findViewById(R.id.titleTextView);
        pieChartView = findViewById(R.id.pieChartView);
        class1Button = findViewById(R.id.class1Button);
        class2Button = findViewById(R.id.class2Button);
        class3Button = findViewById(R.id.class3Button);
        barChartView = findViewById(R.id.barChartView);


        class1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pieChartView.setPercentage(80);
                // Data for the bar chart
                List<String> subjects = Arrays.asList("Math", "Physique", "Science", "Technologie");
                List<Float> firstSemester = Arrays.asList(14f, 12f, 11f, 10f);
                List<Float> secondSemester = Arrays.asList(15f, 10f, 16f, 13f);

                barChartView.setData(subjects, firstSemester, secondSemester);
            }
        });

        class2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pieChartView.setPercentage(70);
                // Data for the bar chart
                List<String> subjects = Arrays.asList("Math", "Physique", "Science", "Technologie");
                List<Float> firstSemester = Arrays.asList(15f, 13f, 12f, 17f);
                List<Float> secondSemester = Arrays.asList(17f, 14f, 14f, 19f);

                barChartView.setData(subjects, firstSemester, secondSemester);
            }
        });

        class3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pieChartView.setPercentage(60);
                // Data for the bar chart
                List<String> subjects = Arrays.asList("Math", "Physique", "Science", "Technologie");
                List<Float> firstSemester = Arrays.asList(10f, 13f, 12f, 11f);
                List<Float> secondSemester = Arrays.asList(16f, 10f, 11f, 9f);

                barChartView.setData(subjects, firstSemester, secondSemester);
            }
        });




    }
}
