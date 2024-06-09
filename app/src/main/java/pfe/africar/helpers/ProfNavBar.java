package pfe.africar.helpers;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import pfe.africar.R;
import pfe.africar.activitys.prof_actualites;
import pfe.africar.activitys.prof_classes_activity;
import pfe.africar.activitys.prof_profile;
import pfe.africar.activitys.prof_quiz_list_activity;

public class ProfNavBar {

    public static void setupBottomNavigation(final Activity activity, BottomNavigationView bottomNavigationView) {
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_discover) {
                    activity.startActivity(new Intent(activity, prof_actualites.class));
                    return true;
                } else if (itemId == R.id.navigation_courses) {
                    activity.startActivity(new Intent(activity, prof_classes_activity.class));
                    return true;
                } else if (itemId == R.id.navigation_quizzes) {
                    activity.startActivity(new Intent(activity, prof_quiz_list_activity.class));
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    activity.startActivity(new Intent(activity, prof_profile.class));
                    return true;
                } else {
                    return false;
                }
            }
        });
    }
}
