package sanpablook.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.study.sanpablook.R;

public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailField, passwordField;

    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        emailField = findViewById(R.id.editTextEmail);
        passwordField = findViewById(R.id.editTextPassword);
        forgotPassword = findViewById(R.id.forgotPassword);

        if (mAuth.getCurrentUser() != null) {
            redirectToMain();
            return;
        }

        findViewById(R.id.forgotPassword).setOnClickListener(v -> startActivity(new Intent(this, ForgotPassword.class)));
        findViewById(R.id.signUpRedirect).setOnClickListener(v -> startActivity(new Intent(this, SignUpActivity.class)));
        findViewById(R.id.loginBtn).setOnClickListener(v -> login());
    }

    private void login() {
        String email = String.valueOf(emailField.getText());
        String password = String.valueOf(passwordField.getText());

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and Password are required", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                        redirectToMain();
                    } else {
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void redirectToMain() {
        startActivity(new Intent(this, BottomNavBar.class));
        finish();
    }
}