package pfe.africar.classes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class PieChartView extends View {

    private Paint paint;
    private Paint textPaint;
    private float percentage = 0;  // Initialisation du pourcentage à afficher
    private int highlightColor = Color.parseColor("#2F9A6F");  // Couleur du segment mis en évidence

    public PieChartView(Context context) {
        super(context);
        init();
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);  // Largeur de la bordure du cercle

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(50);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();
        float radius = Math.min(width, height) / 4 - 20;  // Réduire la taille du cercle
        float cx = width / 2;
        float cy = height / 4;  // Positionner le cercle en haut

        // Dessiner le cercle de fond
        paint.setColor(Color.LTGRAY);
        canvas.drawCircle(cx, cy, radius, paint);

        // Dessiner le segment mis en évidence
        RectF oval = new RectF(cx - radius, cy - radius, cx + radius, cy + radius);
        paint.setColor(highlightColor);
        canvas.drawArc(oval, -90, percentage * 3.6f, false, paint);

        // Dessiner le texte au centre
        canvas.drawText(String.format("%d%%", (int) percentage), cx, cy - ((textPaint.descent() + textPaint.ascent()) / 2), textPaint);
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
        invalidate();  // Redessiner la vue

    }
}
