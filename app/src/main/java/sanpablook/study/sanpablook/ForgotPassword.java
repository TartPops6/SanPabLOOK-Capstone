package sanpablook.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.study.sanpablook.R;

public class ForgotPassword extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();
        emailField = findViewById(R.id.txtforgotPassEmail);

        findViewById(R.id.sendEmailBtn).setOnClickListener(v -> forgotPassword());
    }

    private void forgotPassword() {
        String email = String.valueOf(emailField.getText());

        if (email.isEmpty()) {
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Error occurred: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}