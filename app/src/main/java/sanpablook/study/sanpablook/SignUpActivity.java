package sanpablook.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.study.sanpablook.R;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {


    //Var
    TextView signInRedirect;
    EditText editTextEmail, editText_SignUpPassword, editTextFirstName, editTextLastName;
    FirebaseAuth mAuth;
    Button signBtn, datePickerButton;

    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    TextView textView;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), BottomNavBar.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Objects
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editText_SignUpPassword = (EditText) findViewById(R.id.editTextSign_Password);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        datePickerButton = (Button) findViewById(R.id.datePickerButton);

        signBtn = (Button) findViewById(R.id.signBtn);
        signInRedirect = findViewById(R.id.signInRedirect);
        signInRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = String.valueOf(editTextFirstName.getText());
                String lastName = String.valueOf(editTextLastName.getText());
                String email = String.valueOf(editTextEmail.getText());
                String password = String.valueOf(editText_SignUpPassword.getText());
                String dateOfBirth = datePickerButton.getText().toString();

                if(TextUtils.isEmpty(firstName)){
                    Toast.makeText(SignUpActivity.this, "Please enter first name", Toast.LENGTH_SHORT).show();
                    editTextFirstName.setError("Please enter first name");
                    editTextFirstName.requestFocus();
                } else if (TextUtils.isEmpty(lastName)){
                    Toast.makeText(SignUpActivity.this, "Please enter last name", Toast.LENGTH_SHORT).show();
                    editTextLastName.setError("Please enter last name");
                    editTextLastName.requestFocus();
                } else if (TextUtils.isEmpty(email)){
                    Toast.makeText(SignUpActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    editTextEmail.setError("Please enter email");
                    editTextEmail.requestFocus();
                } else if(TextUtils.isEmpty(password)){
                    Toast.makeText(SignUpActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    editText_SignUpPassword.setError("Please enter password");
                    editText_SignUpPassword.requestFocus();
                } else if (password.length() <= 6 || !password.matches(".*\\d.*")){
                    Toast.makeText(SignUpActivity.this, "Password should be more than 6 characters and contain at least 1 number", Toast.LENGTH_SHORT).show();
                    editText_SignUpPassword.setError("Password should be more than 6 characters and contain at least 1 number");
                    editText_SignUpPassword.requestFocus();
                } else {
                    dateOfBirth = datePickerButton.getText().toString();
                    registerUser(firstName, lastName, dateOfBirth, email, password);
                }
            }

            private void registerUser(String firstName, String lastName, String dateOfBirth, String email, String password) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                                //Email Verification
                                FirebaseUser user = auth.getCurrentUser();
                                user.sendEmailVerification().addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(SignUpActivity.this, "Check your email for verification", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);

                                //Clears the activity stack
                                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();  //Close registration
                            } else {
                                Exception exception = task.getException();
                                Toast.makeText(SignUpActivity.this, "Registration Failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


        //Date of Birth Picker
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
        return;
    }

    private String getTodaysDate()
    {
        Calendar cal= Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month= month + 1;
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(dayOfMonth, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = makeDateString(dayOfMonth, month, year);
                dateButton.setText(date);
            }

        };

        Calendar cal= Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, dayOfMonth);

    }
    private String makeDateString(int dayOfMonth, int month, int year)
    {
        return getMonthFormat(month) + " " + dayOfMonth + " " + year;

    }
    private String getMonthFormat(int month) {
        if (month == 1)
            return "January";
        if (month == 2)
            return "February";
        if (month == 3)
            return "March";
        if (month == 4)
            return "April";
        if (month == 5)
            return "May";
        if (month == 6)
            return "June";
        if (month == 7)
            return "July";
        if (month == 8)
            return "August";
        if (month == 9)
            return "September";
        if (month == 10)
            return "October";
        if (month == 11)
            return "November";
        if (month == 12)
            return "December";

        return "October";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
};





