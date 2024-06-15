package pfe.africar.helpers;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import pfe.africar.R;

import pfe.africar.activitys.acceille_activity;
import pfe.africar.activitys.eleve_profile;

public class EleveNavBar {

    public static void setupBottomNavigation(Context context, BottomNavigationView bottomNavigationView) {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.navigation_discover) {
                    Intent intent = new Intent(context, acceille_activity.class);
                    context.startActivity(intent);
                    return true;
                } else if (id == R.id.navigation_courses) {
                    Intent intent = new Intent(context, .class);
                    context.startActivity(intent);
                    return true;
                } else if (id == R.id.navigation_quizzes) {
                    Intent intent = new Intent(context, .class);
                    context.startActivity(intent);
                    return true;
                } else if (id == R.id.navigation_profile) {
                    Intent intent = new Intent(context, eleve_profile.class);
                    context.startActivity(intent);
                    return true;
                } else {
                    Toast.makeText(context, "Unknown menu item selected", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        });
    }
}
