package com.sharethefare.sharethefare;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class registerr extends AppCompatActivity{

    private Vibrator vib;
    DBH myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText name = (EditText)findViewById(R.id.names);
        final EditText mobile = (EditText)findViewById(R.id.mobiles);
        final EditText email = (EditText)findViewById(R.id.emails);
        final EditText password = (EditText)findViewById(R.id.pwds);
        final Button register = (Button) findViewById(R.id.register);

        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        myDb = new DBH(this);

        register.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!validateName(name.getText().toString().trim())){
                    vib.vibrate(120);
                    name.setError("Invalid Name");
                    name.requestFocus();
                }
                else{
                    if(!validateMobile(mobile.getText().toString().trim())){
                        vib.vibrate(120);
                        mobile.setError("Invalid Mobile Number");
                        mobile.requestFocus();
                    }
                    else{
                        if(!validateEmail(email.getText().toString())) {
                            vib.vibrate(120);
                            email.setError("Invalid Email");
                            email.requestFocus();
                        }
                        else{
                            if (!validatePassword(password.getText().toString())) {
                                vib.vibrate(120);
                                password.setError("Invalid Password");
                                password.requestFocus();
                            }
                            else{
                                boolean isInserted = myDb.insertData(name.getText().toString(),
                                        mobile.getText().toString(),
                                        email.getText().toString(), password.getText().toString() );
                                if(isInserted == true) {
                                    Toast.makeText(registerr.this,
                                            "You have registered successfully!",
                                            Toast.LENGTH_LONG).show();
                                    Intent inte = new Intent(registerr.this, start.class);
                                    startActivity(inte);
                                }
                                else
                                {
                                    Toast.makeText(registerr.this,
                                            "You have not registered successfully!",
                                            Toast.LENGTH_LONG).show();

                                }
                            }
                        }
                    }
                }
            }
        });


    }
    protected boolean validateName(String name) {
        if(name!=null && name.length()>1) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean validateMobile(String mobile) {
        if(mobile!=null && mobile.length()==10 && !mobile.contains(" ")) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean validateEmail(String email) {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    protected boolean validatePassword(String password) {
        if(password!=null && password.length()>1) {
            return true;
        } else {
            return false;
        }
    }
}





