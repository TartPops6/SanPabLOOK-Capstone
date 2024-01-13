package sanpablook.study.sanpablook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.app.TimePickerDialog;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.view.ViewGroup;
import android.view.Gravity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.study.sanpablook.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DineReservationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageButton btnReturn;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    Button timeButton, phoneNumber, btnConfirm, btnMessagePlace;
    int hour, minute;

    private String establishmentName, establishmentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dine_reservation);

        // Get the image path from the intent
        String imagePath = getIntent().getStringExtra("imagePath");

        // Get the ImageView
        ImageView imgEstabProfile = findViewById(R.id.imgEstabProfile);

        // Get the Firestore Storage instance
        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Create a storage reference
        StorageReference storageRef = storage.getReference();

        // Create a reference to the image file
        StorageReference imageRef = storageRef.child(imagePath);

        // Load the image using Glide
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(DineReservationActivity.this)
                        .load(uri)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                Log.e("TAG", "Load failed", e);
                                // Logs the root cause
                                for (Throwable t : e.getRootCauses()) {
                                    Log.e("TAG", "Caused by", t);
                                }
                                // Logs all individual causes
                                e.logRootCauses("TAG");
                                return false; // Allows the error placeholder to be set.
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false; // Allows the loaded resource to be set on the ImageView.
                            }
                        })
                        .into(imgEstabProfile);
            }
        });

        // Get the document ID from the intent
        String documentId = getIntent().getStringExtra("documentId");

        // Text Views
        TextView txtHotelBooking = findViewById(R.id.txtBookingType);
        TextView txtHotelReservation = findViewById(R.id.txtBookingPlaceName);
        TextView txtHotelRating = findViewById(R.id.txtEstablishmentRating);
        TextView txtHotelAccredited = findViewById(R.id.txtBookingAccredited);

        // Get the Firestore instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Fetch the data from Firestore
        db.collection("RegisteredEstablishment").document(documentId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            // Set the data to the TextViews

                            establishmentID = documentSnapshot.getString("establishmentID");
                            establishmentName = documentSnapshot.getString("place");
                            txtHotelBooking.setText(documentSnapshot.getString("bookingType"));
                            txtHotelReservation.setText(documentSnapshot.getString("place"));
                            txtHotelRating.setText(documentSnapshot.getString("rating"));
                            txtHotelAccredited.setText(documentSnapshot.getString("accredited"));
                        } else {
                            Toast.makeText(DineReservationActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DineReservationActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                });

        //date picker
        initDatePicker();
        dateButton = findViewById(R.id.editTxtDate);
        dateButton.setText("");

        //time picker
        timeButton = findViewById(R.id.editTxtTime);
        timeButton.setText("");

        //guest picker
        Spinner guestSpinner = findViewById(R.id.editTxtGuest);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.guests, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        guestSpinner.setAdapter(adapter);
        guestSpinner.setSelection(0);
        guestSpinner.setOnItemSelectedListener(this);

        //add phone number button
        phoneNumber = findViewById(R.id.btnPhoneNumber);
        phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        //back button
        btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //confirm button
        btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Firestore
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                //Current user's ID
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = auth.getCurrentUser();
                String userID = currentUser.getUid();

                //Take data from editTxtDate, editTxtTime, and editTxtGuest
                String date = ((Button) findViewById(R.id.editTxtDate)).getText().toString();
                String time = ((Button) findViewById(R.id.editTxtTime)).getText().toString();
                String guest = ((Spinner) findViewById(R.id.editTxtGuest)).getSelectedItem().toString();
                final String[] bookingID = new String[1];
                do {
                    bookingID[0] = BookingIDGenerator.generateBookingID();
                } while (db.collection("BookingPending").document(bookingID[0]).get().isSuccessful());

                //Get user info from Firestore
                DocumentReference documentReference = db.collection("users").document(userID);
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String phoneNumber = documentSnapshot.getString("phoneNumber");
                            String fullName = documentSnapshot.getString("firstName") + " " + documentSnapshot.getString("lastName");

                            // Check if date, time, and guest count are selected
                            if (date.isEmpty() || time.isEmpty() || guest.equals("0")) {
                                Toast.makeText(DineReservationActivity.this, "Plase fulfill the missing field", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            //Document BookingPending collection for this user
                            Map<String, Object> booking = new HashMap<>();

                            booking.put("userID", userID);
                            booking.put("place", establishmentName);
                            booking.put("bookingID", bookingID[0]);
                            booking.put("fullName", fullName);
                            booking.put("date", date);
                            booking.put("time", time);
                            booking.put("guest", guest);
                            booking.put("status", "Pending");
                            booking.put("establishmentID", establishmentID);
                            booking.put("phoneNumber", phoneNumber);

                            //Save to Firestore
                            db.collection("BookingPending")
                                    .add(booking)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(DineReservationActivity.this, "Booking Successful", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(DineReservationActivity.this, "Booking Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                });
            }
        });


    btnMessagePlace = findViewById(R.id.btnMessagePlace);
        btnMessagePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DineReservationActivity.this, DineMessageActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_dine_reserve_phone_number);
        LinearLayout btnSetPhoneNumber = dialog.findViewById(R.id.buttonsSetPhoneNumber);
        final EditText editTextPhone = dialog.findViewById(R.id.editTextPhone);
        Button cancelButton = dialog.findViewById(R.id.cancelButton);
        final Button saveButton = dialog.findViewById(R.id.saveButton);

        editTextPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (validateMobile(editTextPhone.getText().toString())) {
                    saveButton.setEnabled(true);
                }
                else {
                    saveButton.setEnabled(false);
                    editTextPhone.setError("Invalid phone number");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DineReservationActivity.this, "Save is clicked", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    public void popTimePicker (View view){
         TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
             @Override
             public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                 hour = selectedHour;
                 minute = selectedMinute;
                 timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
             }
         };
         int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;
         TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, false);
         timePickerDialog.setTitle("Select Time of Reservation");
         timePickerDialog.show();
     }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
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

        return "January";
    }

    public void openDatePicker(View view){
        datePickerDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
//        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //OLD MESSAGE BUTTON
//    public void btnMessage(View v) {
//        String number = "09283395502";
//        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
//    }

    boolean validateMobile(String input) {
        Pattern p = Pattern.compile("[0][9][0-9]{9}");
        Matcher m = p.matcher(input);
        return m.matches();
    }
}

