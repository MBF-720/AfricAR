package pfe.africar.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import android.util.Log;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import pfe.africar.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// Importez les classes nécessaires


// Dans votre méthode de déconnexion ou lors d'un événement de clic sur un bouton
        FirebaseAuth.getInstance().signOut();
        Log.d(TAG, "Utilisateur déconnecté de Firebase");

       }}
