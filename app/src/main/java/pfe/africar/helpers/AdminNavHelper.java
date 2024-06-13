package pfe.africar.helpers;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import pfe.africar.R;
import pfe.africar.activitys.AddGradeActivity;
import pfe.africar.activitys.admin_update_activity;

import pfe.africar.activitys.StatistiqueActivity;
import pfe.africar.activitys.classroom_lists_activity;

public class AdminNavHelper{

    public static void setupBottomNavigation(Activity activity, BottomNavigationView bottomNavigationView) {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Intent intent = null;
                if (id == R.id.menu_updates) {
                    intent = new Intent(activity, admin_update_activity.class);
                } else if (id == R.id.menu_personnel) {
                    intent = new Intent(activity, AddGradeActivity.class);
                } else if (id == R.id.menu_school) {
                    intent = new Intent(activity, classroom_lists_activity.class);
                } else if (id == R.id.menu_stats) {
                    intent = new Intent(activity, StatistiqueActivity.class);
                }

                if (intent != null) {
                    activity.startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
}
