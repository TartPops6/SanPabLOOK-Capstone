package sanpablook.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.view.ViewGroup;
import android.view.Gravity;

import com.study.sanpablook.R;

import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HotelReservationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageButton btnReturn;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    Button timeButton, phoneNumber, btnConfirm;
    int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dine_palmeras_reservation);

        //date picker
        initDatePicker();
        dateButton = findViewById(R.id.editTxtDate);
        dateButton.setText(getTodaysDate());

        //time picker
        timeButton = findViewById(R.id.editTxtTime);

        //guest picker
        Spinner guestSpinner = findViewById(R.id.editTxtGuest);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.guests, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        guestSpinner.setAdapter(adapter);
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
                Toast.makeText(HotelReservationActivity.this, "You confirmed your reservation.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(HotelReservationActivity.this, "Save is clicked", Toast.LENGTH_SHORT).show();
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

    public void btnMessage(View v) {
        String number = "09283395502";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
    }

    boolean validateMobile(String input) {
        Pattern p = Pattern.compile("[0][9][0-9]{9}");
        Matcher m = p.matcher(input);
        return m.matches();
    }
}