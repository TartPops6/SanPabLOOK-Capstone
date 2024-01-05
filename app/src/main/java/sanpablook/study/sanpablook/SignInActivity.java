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

    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.signUpRedirect).setOnClickListener(v ->
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class)));

        findViewById(R.id.loginBtn).setOnClickListener(v -> {
            String email = String.valueOf(((EditText) findViewById(R.id.editTextEmail)).getText());
            String password = String.valueOf(((EditText) findViewById(R.id.editTextPassword)).getText());

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignInActivity.this, "Email and Password are required", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignInActivity.this, testMain.class);
                            intent.putExtra("BottomNavBar", "BottomNavBar");
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        //Text Forgot Password
        forgotPassword = findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });
    }
}