package com.sharethefare.sharethefare;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class after_login extends AppCompatActivity{

    private Vibrator vib;
    DBH myDb;
    private session session;

    EditText destin,date,time;
    Button dest,date_button,time_button;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String ax,ay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logined);

        date_button = findViewById(R.id.date_button);
        time_button = findViewById(R.id.time_button);

         date = (EditText)findViewById(R.id.date_text);
         time = (EditText)findViewById(R.id.time_text);
        final Button add = (Button) findViewById(R.id.add_button);
        final Button view = findViewById(R.id.view_button);
        final String emailz=start.email_login.getText().toString();

        final Button logout = findViewById(R.id.logout);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        session = new session(this);
        if(!session.loggedin()){
               logout();
        }
        myDb = new DBH(this);

        EditText pickup = findViewById(R.id.pickup_text);
        Button pick = findViewById(R.id.pickup_button);

        destin = findViewById(R.id.destination_text);
        dest = findViewById(R.id.destination_button);

        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg = new Intent(after_login.this, MapsActivity.class);
                startActivity(reg);
            }
        });
        pickup.setText(MapsActivity.strr);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        date_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                getdate();
            }
        });

        time_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                gettime();
            }
        });

        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                        boolean isInserted = myDb.insertDate(ax,ay, emailz );
                        if(isInserted == true) {
                            Toast.makeText(after_login.this, "Sorry! Request failed.",
                                    Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(after_login.this,
                                    "Your date and time of travel has been registered.",
                                    Toast.LENGTH_LONG).show();

                        }
                    }
        });

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vi = new Intent(after_login.this, viewlist.class);
                startActivity(vi);
            }
        });

        dest.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gantavya = new Intent(after_login.this,MapsActivity2.class);
                startActivity(gantavya);
            }
        });
        destin.setText(MapsActivity2.des_loc);
    }

    private void logout(){
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(after_login.this,start.class));
    }

    private void getdate(){
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            ax=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
    }

    private void gettime(){
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        time.setText(hourOfDay + ":" + minute);
                        ay=hourOfDay + ":" + minute;
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
