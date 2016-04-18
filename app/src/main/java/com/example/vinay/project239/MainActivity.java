package com.example.vinay.project239;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {


    Button bnLogin,bnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //new Thread(new Task()).start();
        bnLogin = (Button) findViewById(R.id.bnLogin);
        bnRegister = (Button) findViewById(R.id.bnRegister);

        bnLogin.setOnClickListener(this);
        bnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.bnLogin){
            startActivity(new Intent(this, Notification.class));
        }
        else if(v.getId()==R.id.bnRegister){
            startActivity(new Intent(this, Register.class));
        }
    }


}
