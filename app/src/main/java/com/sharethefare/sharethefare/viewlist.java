package com.sharethefare.sharethefare;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class viewlist extends AppCompatActivity {
    private session session;
    DBH myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewlist);
        final TextView vieww = findViewById(R.id.view_list);
        final Button logout = findViewById(R.id.logoutx);
        session = new session(this);
        if(!session.loggedin()){
            logout();
        }
        myDb = new DBH(this);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            // show message
            showMessage("Error","Nothing found");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("Name :"+ res.getString(0)+"\n");
            buffer.append("Mobile :"+ res.getString(1)+"\n");
            buffer.append("Pickup :"+ res.getString(2)+"\n");
            buffer.append("Destination :"+ res.getString(3)+"\n");
            buffer.append("Date :"+ res.getString(4)+"\n");
            buffer.append("Time :"+ res.getString(5)+"\n\n\n");
        }

        // Show all data
        //showMessage("Data",buffer.toString());
        vieww.setText(buffer.toString());

    }
    private void logout(){
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(viewlist.this,start.class));
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
