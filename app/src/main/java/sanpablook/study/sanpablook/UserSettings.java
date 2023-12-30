package sanpablook.study.sanpablook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.study.sanpablook.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserSettings extends AppCompatActivity {

    //buttons and clickable
    ImageButton btnBackUS;
    TextView txtEditUsername, txtEditPassword, txtEditBio, txtEditPhoneNumber, txtEditEmail;
    Button btnLogout, btnDeleteAccount;

    //profilepic
    FloatingActionButton fabEditProfile;
    ShapeableImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        //find view by id
        btnBackUS = findViewById(R.id.buttonBackUS);
        txtEditUsername = findViewById(R.id.editTheUsername);
        txtEditPassword = findViewById(R.id.editThePassword);
        txtEditBio = findViewById(R.id.editTheBio);
        txtEditPhoneNumber = findViewById(R.id.editThePhoneNumber);
        txtEditEmail = findViewById(R.id.editTheEmail);
        btnLogout = findViewById(R.id.buttonLogout);
        btnDeleteAccount = findViewById(R.id.buttonDeleteAccount);

        //profilepic
        fabEditProfile = findViewById(R.id.fabEditProfilePicture);
        profilePicture = findViewById(R.id.profilePicture);

        fabEditProfile.setOnClickListener(view1 -> {
            ImagePicker.Companion.with(this)
                    .crop()                 // Crop image(Optional), Check Customization for more option
                    .compress(1024)         // Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)   // Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });

        //onclick
        btnBackUS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        txtEditUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEditUsername(view);
            }
        });
        txtEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEditPassword(view);
            }
        });
        txtEditBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEditBio(view);
            }
        });
        txtEditPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEditPhoneNumber(view);
            }
        });
        txtEditEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEditEmail(view);
            }
        });
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDeleteAccount(view);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(UserSettings.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data.getData();
        profilePicture.setImageURI(uri);
    }

    private void showDialogEditUsername (View view) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_username);

        Button btnSave = dialog.findViewById(R.id.buttonSave);
        Button btnCancel = dialog.findViewById(R.id.buttonCancel);
        EditText editTextUsername = dialog.findViewById(R.id.editTextUsername);

        btnSave.setEnabled(false);
        btnSave.setAlpha(0.5f);

        editTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not needed for this case
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btnSave.setEnabled(true);
                btnSave.setAlpha(1.0f);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Not needed for this case
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserSettings.this , "Your username has been updated", Toast.LENGTH_SHORT);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    //for edit password
    private EditText editTextNew;
    private EditText editTextRetype;
    private Button btnSave;

    private void showDialogEditPassword (View view) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_password);

        btnSave = dialog.findViewById(R.id.buttonSave);
        Button btnCancel = dialog.findViewById(R.id.buttonCancel);
        editTextNew = dialog.findViewById(R.id.editTextNew);
        editTextRetype = dialog.findViewById(R.id.editTextRetype);
        btnSave.setEnabled(false);
        btnSave.setAlpha(0.5f);  // initial color is opaque

        editTextNew.addTextChangedListener(passwordTextWatcher);
        editTextRetype.addTextChangedListener(passwordTextWatcher);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserSettings.this , "Your password has been updated", Toast.LENGTH_SHORT);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private TextWatcher passwordTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Not needed for this case
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Check if the text in both EditTexts match
            boolean passwordsMatch = editTextNew.getText().toString().equals(editTextRetype.getText().toString());

            // Enable or disable the Save button accordingly
            btnSave.setEnabled(passwordsMatch);
            btnSave.setAlpha(passwordsMatch ? 1.0f : 0.5f);
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // Not needed for this case
        }
    };

    private void showDialogEditBio (View view) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_bio);

        Button btnSave = dialog.findViewById(R.id.buttonSave);
        Button btnCancel = dialog.findViewById(R.id.buttonCancel);
        EditText editTextBio = dialog.findViewById(R.id.editTextBio);

        btnSave.setEnabled(false);
        btnSave.setAlpha(0.5f);

        editTextBio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not needed for this case
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btnSave.setEnabled(true);
                btnSave.setAlpha(1.0f);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Not needed for this case
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserSettings.this , "Your bio has been updated", Toast.LENGTH_SHORT);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void showDialogEditPhoneNumber (View view) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_phone_number);

        final Button btnSave = dialog.findViewById(R.id.buttonSave);
        Button btnCancel = dialog.findViewById(R.id.buttonCancel);
        final EditText editTextPhoneNumber = dialog.findViewById(R.id.editTextPhoneNumber);

        btnSave.setEnabled(false);
        btnSave.setAlpha(0.5f);

        editTextPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // Not needed for this case
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (validateMobile(editTextPhoneNumber.getText().toString())) {
                    btnSave.setEnabled(true);
                    btnSave.setAlpha(1.0f);
                }
                else {
                    btnSave.setEnabled(false);
                    editTextPhoneNumber.setError("Invalid phone number");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Not needed for this case
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserSettings.this , "Your phone number has been updated", Toast.LENGTH_SHORT);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void showDialogEditEmail (View view) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_email);

        Button btnSave = dialog.findViewById(R.id.buttonSave);
        Button btnCancel = dialog.findViewById(R.id.buttonCancel);
        EditText editTextEmail = dialog.findViewById(R.id.editTextEmail);

        btnSave.setEnabled(false);
        btnSave.setAlpha(0.5f);

        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not needed for this case
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btnSave.setEnabled(true);
                btnSave.setAlpha(1.0f);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Not needed for this case
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserSettings.this , "Your email has been updated", Toast.LENGTH_SHORT);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void showDialogDeleteAccount (View view) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delete_account);

        Button btnConfirm = dialog.findViewById(R.id.buttonConfirm);
        Button btnCancel = dialog.findViewById(R.id.buttonCancel);
        EditText txtDeleteAccount = dialog.findViewById(R.id.editTextDeleteAccount);

        btnConfirm.setEnabled(false);
        btnConfirm.setAlpha(0.5f);  // initial color is opaque

        txtDeleteAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not needed for this case
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // if text is "Delete account"
                boolean isDeleteAccount = charSequence.toString().equals("Delete account");

                // Enable or disable button
                btnConfirm.setEnabled(isDeleteAccount);
                btnConfirm.setAlpha(isDeleteAccount ? 1.0f : 0.5f);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Not needed for this case
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserSettings.this , "Your account has been deleted", Toast.LENGTH_SHORT);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    boolean validateMobile(String input) {
        Pattern p = Pattern.compile("[0][9][0-9]{9}");
        Matcher m = p.matcher(input);
        return m.matches();
    }
}