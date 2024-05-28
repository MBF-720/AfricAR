package pfe.africar.classes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.List;

public class BarChartView extends View {

    private List<String> subjects;
    private List<Float> firstSemesterScores;
    private List<Float> secondSemesterScores;

    private Paint barPaint;
    private Paint textPaint;
    private Paint axisPaint;

    public BarChartView(Context context) {
        super(context);
        init();
    }

    public BarChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        barPaint = new Paint();
        barPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(36f);
        textPaint.setTextAlign(Paint.Align.CENTER);

        axisPaint = new Paint();
        axisPaint.setColor(Color.BLACK);
        axisPaint.setStrokeWidth(4f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (subjects == null || firstSemesterScores == null || secondSemesterScores == null) {
            return;
        }

        float width = getWidth();
        float height = getHeight();
        float barWidth = width / (subjects.size() * 3 + 1); // Adjusted for initial space
        float maxScore = 20;  // Assuming the maximum score is 20

        // Draw y-axis labels
        for (int i = 0; i <= maxScore; i += 5) {
            float y = height - 100 - (i / maxScore) * (height - 200);
            canvas.drawText(String.valueOf(i), 60, y + 10, textPaint); // Adjusted for better alignment
        }

        // Draw axes
        canvas.drawLine(100, height - 100, width - 100, height - 100, axisPaint);
        canvas.drawLine(100, height - 100, 100, 100, axisPaint);

        // Draw bars
        for (int i = 0; i < subjects.size(); i++) {
            float x = 110 + i * barWidth * 3; // Adjusted for small initial space (10px)

            // First semester bar
            barPaint.setColor(Color.parseColor("#C4CFCC")); // Light green
            float firstSemesterHeight = (firstSemesterScores.get(i) / maxScore) * (height - 200);
            canvas.drawRect(x, height - 100 - firstSemesterHeight, x + barWidth, height - 100, barPaint);

            // Second semester bar
            barPaint.setColor(Color.parseColor("#207E5A")); // Dark green
            float secondSemesterHeight = (secondSemesterScores.get(i) / maxScore) * (height - 200);
            canvas.drawRect(x + barWidth * 1.5f, height - 100 - secondSemesterHeight, x + barWidth * 2.5f, height - 100, barPaint);

            // Draw subject labels
            canvas.drawText(subjects.get(i), x + barWidth * 1.25f, height - 60, textPaint);
        }
    }

    public void setData(List<String> subjects, List<Float> firstSemesterScores, List<Float> secondSemesterScores) {
        this.subjects = subjects;
        this.firstSemesterScores = firstSemesterScores;
        this.secondSemesterScores = secondSemesterScores;
        invalidate();  // Redraw the view
    }
}
