package ba.sum.fsre.buyaticket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Check if the user is already logged in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is already logged in, redirect to MainActivity
            redirectToMainActivity();
        } else {
            // User not logged in, show login screen
            setContentView(R.layout.activity_login);

            // Initialize UI elements
            emailEditText = findViewById(R.id.editTextEmailAddress);
            passwordEditText = findViewById(R.id.editTextConfirmPassword);
            loginButton = findViewById(R.id.buttonLogin);

            loginButton.setOnClickListener(v -> loginUser());
        }
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Unesite Vašu email adresu ili lozinku", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("LoginActivity", "login successful");
                Toast.makeText(LoginActivity.this, "Prijava uspješna", Toast.LENGTH_SHORT).show();
                redirectToMainActivity();
            } else {
                Log.d("LoginActivity", "login unsuccessful");
                Toast.makeText(LoginActivity.this, "Graška prilikom prijave: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void redirectToMainActivity() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the activity stack
        startActivity(i);
        finish(); // Ensure LoginActivity is closed and removed from the back stack
    }
}