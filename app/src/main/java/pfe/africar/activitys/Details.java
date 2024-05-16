package pfe.africar.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import pfe.africar.R;

class DetailActivity extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvTitle = findViewById(R.id.tvTitle);
        tvContent = findViewById(R.id.tvContent);

        // Récupérer les données de l'intent
        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("title");
            tvTitle.setText(title);
            // Définir plus de détails si disponible
            tvContent.setText("Détails pour " + title);
        }
    }
}
