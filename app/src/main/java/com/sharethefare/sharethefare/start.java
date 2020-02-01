package com.sharethefare.sharethefare;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class start extends AppCompatActivity {

    private Vibrator vib;
    DBH myDb;

    public static EditText email_login;
    EditText pass_login;

    Button login;
    Button register;

    private session session;

    public void init(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg = new Intent(start.this, registerr.class);
                startActivity(reg);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        session = new session(this);

        email_login = (EditText)findViewById(R.id.email_login);
        pass_login = (EditText)findViewById(R.id.pass_login);
        login = (Button)findViewById(R.id.login_button);
        register = (Button)findViewById(R.id.register);

        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        myDb = new DBH(this);

        init();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent log = new Intent(start.this,after_login.class);
                if(!validateEmail(email_login.getText().toString().trim())){
                    vib.vibrate(120);
                    email_login.setError("Invalid Email");
                    email_login.requestFocus();
                }
                else {
                    if(!validatePassword(pass_login.getText().toString())) {
                        vib.vibrate(120);
                        pass_login.setError("Invalid Password");
                        pass_login.requestFocus();
                    }
                    else{
                        boolean isInserted = myDb.check(
                                email_login.getText().toString(), pass_login.getText().toString() );
                        if(isInserted == true) {
                            session.setLoggedin(true);
                            startActivity(log);
                        }
                        else
                        {
                            Toast.makeText(start.this, "Invalid Credentials!",
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                }
            }
        });

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
