package com.example.vinay.project239;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;


public class Register extends ActionBarActivity implements View.OnClickListener, OnItemSelectedListener {


    Button bRegister;
    ArrayList<NameValuePair> postParameters;
    EditText etName, etDOB, etEmail, etPassword;
    RadioGroup  gender_group;
    String selection = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = (EditText)findViewById(R.id.etName);
        etDOB  = (EditText) findViewById(R.id.etDOB);
        gender_group = (RadioGroup) findViewById(R.id.gender_group);
        etEmail = (EditText) findViewById(R.id.etEmail);
        //etInterest = (EditText) findViewById(R.id.etInterest);
        //phone = (EditText) findViewById(R.id.phone);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(gender_group.getCheckedRadioButtonId()!=-1){
            int id= gender_group.getCheckedRadioButtonId();
            View radioButton = gender_group.findViewById(id);
            int radioId = gender_group.indexOfChild(radioButton);
            RadioButton btn = (RadioButton) gender_group.getChildAt(radioId);
            selection = (String) btn.getText();
        }
        if(v.getId()==R.id.bRegister){
            System.out.println(etName.getText().toString());
            System.out.println(etDOB.getText().toString());
            System.out.println(selection);
            System.out.println(etEmail.getText().toString());
            //System.out.println(phone.getText().toString());
            System.out.println(etPassword.getText().toString());

        }
        SendfeedbackJob job = new SendfeedbackJob();
        job.execute(etName.getText().toString(), etDOB.getText().toString(), selection, etEmail.getText().toString(), etPassword.getText().toString());

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class SendfeedbackJob extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String[] params) {
            // do above Server call here
            final HttpClient httpclient = new DefaultHttpClient();
            final HttpPost httppost = new HttpPost("http://10.189.255.186:80/CMPE239/registration.php");

            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                /*nameValuePairs.add(new BasicNameValuePair("name", etName.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("dob", etDOB.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("email", etUsername.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("pwd", etPassword.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("email", etUsername.getText().toString()));*/
               // nameValuePairs.add(new BasicNameValuePair("pwd", etPassword.getText().toString()));
                nameValuePairs = new ArrayList<NameValuePair>(10);
                nameValuePairs.add(new BasicNameValuePair("fullname", etName.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("dob", etDOB.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("gender", selection));
                nameValuePairs.add(new BasicNameValuePair("email", etEmail.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("password", etPassword.getText().toString()));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));


                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String response = httpclient.execute(httppost, responseHandler);


                //final String resp = httpclient.execute(httppost, responseHandler);
                String reverseString = response;
                //Toast.makeText(TestAct.this, "response" + reverseString, Toast.LENGTH_LONG).show();
                System.out.println(reverseString);
                Context c=getApplicationContext();
                Session s=new Session(c);
                s.setusename( etEmail.getText().toString());


                startActivity(new Intent(Register.this, Survey.class));

            } catch (Exception e) {
                System.out.println(e);
            }

            return "some message";
        }

        @Override
        protected void onPostExecute(String message) {
            //process message
        }
    }
}
