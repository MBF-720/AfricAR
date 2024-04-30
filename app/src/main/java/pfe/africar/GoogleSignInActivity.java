package pfe.africar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import pfe.africar.activitys.onboarding_screen_activity;

public class GoogleSignInActivity extends AppCompatActivity {



    private static final String TAG = "GoogleSignInActivity";
    private static final int REQ_ONE_TAP = 2;
    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    ProgressDialog progressDialog;
    private AuthResult GoogleAuthProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("google sign in...");
        progressDialog.show();
        setContentView(R.layout.activity_google_sign_in);

        mAuth = FirebaseAuth.getInstance();
        mUser =mAuth.getCurrentUser();

        // BeginSignInRequest initialization...
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_ONE_TAP) {
            if (resultCode == RESULT_OK && data != null) {
                try {
                    SignInClient oneTapClient = Identity.getSignInClient(this);
                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                    String idToken = credential.getGoogleIdToken();
                    if (idToken != null) {
                        signInWithGoogle(idToken);
                    }
                } catch (ApiException e) {
                    Log.e(TAG, "Error: " + e.getStatusCode());
                }
            } else {

                Log.e(TAG, "Sign-in canceled or failed");
                progressDialog.dismiss();
                finish();
            }
        }
    }

    private void signInWithGoogle(String idToken) {
        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential();
        mAuth.signInWithCredential(firebaseCredential)
                .addOnCompleteListener(this, new OnCompleteListener <AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task <AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                            progressDialog.dismiss();
                            finish();
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        // Your UI update logic here
        Intent intent=new Intent(GoogleSignInActivity.this, onboarding_screen_activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}




