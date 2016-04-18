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
import android.widget.Spinner;
import android.widget.TextView;

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


public class Survey extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private static final String[]paths = {"Book","Headphones", "Network Provider", "Cell Phone Manufacturers", "Laptops", "Fashion Brands"};
    EditText ethobbit, etinferno, etsymbol, ethardy, etgf, etshure, etgrado, etklipsch, etbose, etsony;
    TextView tvhobbit, tvinferno, tvsymbol, tvhardy, tvgf, tvshure, tvgrado, tvklipsch, tvbose, tvsony;
    Spinner spin;
    Button bSubmit;
    ArrayList<NameValuePair> postParameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        tvhobbit= (TextView) findViewById(R.id.tvhobbit);
        tvinferno=(TextView) findViewById(R.id.tvinferno);
        tvsymbol=(TextView) findViewById(R.id.tvsymbol);
        tvhardy=(TextView) findViewById(R.id.tvhardy);
        tvgf=(TextView) findViewById(R.id.tvgf);
        tvshure=(TextView) findViewById(R.id.tvshure);
        tvgrado=(TextView) findViewById(R.id.tvgrado);
        tvklipsch=(TextView) findViewById(R.id.tvklipsch);
        tvbose=(TextView) findViewById(R.id.tvbose);
        tvsony=(TextView) findViewById(R.id.tvsony);;

        ethobbit = (EditText) findViewById(R.id.ethobbit);
        ethobbit.setVisibility(View.INVISIBLE);
        etinferno = (EditText) findViewById(R.id.etinferno);
        etinferno.setVisibility(View.INVISIBLE);
        etsymbol = (EditText) findViewById(R.id.etsymbol);
        etsymbol.setVisibility(View.INVISIBLE);
        ethardy = (EditText) findViewById(R.id.ethardy);
        ethardy.setVisibility(View.INVISIBLE);
        etgf = (EditText) findViewById(R.id.etgf);
        etgf.setVisibility(View.INVISIBLE);
        etshure = (EditText) findViewById(R.id.etshure);
        etshure.setVisibility(View.INVISIBLE);
        etgrado = (EditText) findViewById(R.id.etgrado);
        etgrado.setVisibility(View.INVISIBLE);
        etklipsch = (EditText) findViewById(R.id.etklipsch);
        etklipsch.setVisibility(View.INVISIBLE);
        etbose = (EditText) findViewById(R.id.etbose);
        etbose.setVisibility(View.INVISIBLE);
        etsony = (EditText) findViewById(R.id.etsony);
        etsony.setVisibility(View.INVISIBLE);
        bSubmit = (Button) findViewById(R.id.bSubmit);


        spin = (Spinner)findViewById(R.id.spinsur);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Survey.this,
                android.R.layout.simple_spinner_item, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        //spinner.setOnItemSelectedListener(this);


        bSubmit.setOnClickListener(this);
        spin.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

    }


    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.bSubmit) {
            System.out.println(spin.getItemAtPosition(0).toString());
            if (spin.getItemAtPosition(0).toString().equalsIgnoreCase("Book")) {
                System.out.println(ethobbit.getText().toString()+" "+tvhobbit.getText().toString());
                System.out.println(etinferno.getText().toString()+" "+tvinferno.getText().toString());
                System.out.println(etsymbol.getText().toString()+" "+tvsymbol.getText().toString());
                System.out.println(ethardy.getText().toString()+" "+tvhardy.getText().toString());
                System.out.println(etgf.getText().toString()+" "+tvgf.getText().toString());
            } else if (spin.getItemAtPosition(1).toString().equalsIgnoreCase("Headphones")) {
                System.out.println(etshure.getText().toString()+" "+tvshure.getText().toString());
                System.out.println(etgrado.getText().toString()+" "+tvgrado.getText().toString());
                System.out.println(etklipsch.getText().toString()+" "+tvklipsch.getText().toString());
                System.out.println(etbose.getText().toString()+" "+tvbose.getText().toString());
                System.out.println(etsony.getText().toString()+" "+tvsony.getText().toString());

            }
        }

        SendfeedbackJob job = new SendfeedbackJob();
        job.execute(ethobbit.getText().toString(), etinferno.getText().toString(), etsymbol.getText().toString(), ethardy.getText().toString(), etgf.getText().toString(), etshure.getText().toString(),
                etgrado.getText().toString(), etklipsch.getText().toString(), etbose.getText().toString(),etsony.getText().toString());

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(spin.getItemAtPosition(position).toString()=="Book")
        {
            tvhobbit.setVisibility(View.VISIBLE);
            tvinferno.setVisibility(View.VISIBLE);
            tvsymbol.setVisibility(View.VISIBLE);
            tvhardy.setVisibility(View.VISIBLE);
            tvgf.setVisibility(View.VISIBLE);
            ethobbit.setVisibility(View.VISIBLE);
            etinferno.setVisibility(View.VISIBLE);
            etsymbol.setVisibility(View.VISIBLE);
            ethardy.setVisibility(View.VISIBLE);
            etgf.setVisibility(View.VISIBLE);
            tvshure.setVisibility(View.GONE);
            tvgrado.setVisibility(View.GONE);
            tvklipsch.setVisibility(View.GONE);
            tvbose.setVisibility(View.GONE);
            tvsony.setVisibility(View.GONE);
            etshure.setVisibility(View.GONE);
            etgrado.setVisibility(View.GONE);
            etklipsch.setVisibility(View.GONE);
            etbose.setVisibility(View.GONE);
            etsony.setVisibility(View.GONE);
        }
        else if(spin.getItemAtPosition(position).toString()=="Headphones")
        {
            tvhobbit.setVisibility(View.GONE);
            tvinferno.setVisibility(View.GONE);
            tvsymbol.setVisibility(View.GONE);
            tvhardy.setVisibility(View.GONE);
            tvgf.setVisibility(View.GONE);
            ethobbit.setVisibility(View.GONE);
            etinferno.setVisibility(View.GONE);
            etsymbol.setVisibility(View.GONE);
            ethardy.setVisibility(View.GONE);
            etgf.setVisibility(View.GONE);
            tvshure.setVisibility(View.VISIBLE);
            tvgrado.setVisibility(View.VISIBLE);
            tvklipsch.setVisibility(View.VISIBLE);
            tvbose.setVisibility(View.VISIBLE);
            tvsony.setVisibility(View.VISIBLE);
            etshure.setVisibility(View.VISIBLE);
            etgrado.setVisibility(View.VISIBLE);
            etklipsch.setVisibility(View.VISIBLE);
            etbose.setVisibility(View.VISIBLE);
            etsony.setVisibility(View.VISIBLE);
        }/*
        else if(spin.getItemAtPosition(position).toString().equalsIgnorecase("Network Provider"))
        {
            editextObject.setVisibility(View.VISIBLE);
        }
        else if(spin.getItemAtPosition(position).toString().equalsIgnorecase("Cell Phone Manufacturers"))
        {
            editextObject.setVisibility(View.VISIBLE);
        }
        else if(spin.getItemAtPosition(position).toString().equalsIgnorecase("Laptops"))
        {
            editextObject.setVisibility(View.VISIBLE);
        }
        else if(spin.getItemAtPosition(position).toString().equalsIgnorecase("Fashion Brands")) {
            editextObject.setVisibility(View.VISIBLE);
        }*/

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private class SendfeedbackJob extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String[] params) {
            // do above Server call here
            final HttpClient httpclient = new DefaultHttpClient();
            final HttpPost httppost = new HttpPost("http://10.189.255.186:80/CMPE239/survey.php");
            Context c=getApplicationContext();
            Session s=new Session(c);
            String email = s.getusename();
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                if(spin.getItemAtPosition(0).toString().equalsIgnoreCase("Book")) {
                    nameValuePairs.add(new BasicNameValuePair("sess",email));
                    nameValuePairs.add(new BasicNameValuePair("category", spin.getItemAtPosition(0).toString()));
                    nameValuePairs.add(new BasicNameValuePair("input1", ethobbit.getText().toString()));
                    nameValuePairs.add(new BasicNameValuePair("input2", etinferno.getText().toString()));
                    nameValuePairs.add(new BasicNameValuePair("input3", etsymbol.getText().toString()));
                    nameValuePairs.add(new BasicNameValuePair("input4", ethardy.getText().toString()));
                    nameValuePairs.add(new BasicNameValuePair("input5", etgf.getText().toString()));
                }
                else if(spin.getItemAtPosition(1).toString().equalsIgnoreCase("Headphones")){
                    nameValuePairs.add(new BasicNameValuePair("sess",email));
                    nameValuePairs.add(new BasicNameValuePair("category", spin.getItemAtPosition(1).toString()));
                    nameValuePairs.add(new BasicNameValuePair("input1", etshure.getText().toString()));
                    nameValuePairs.add(new BasicNameValuePair("input2", etgrado.getText().toString()));
                    nameValuePairs.add(new BasicNameValuePair("input3", etklipsch.getText().toString()));
                    nameValuePairs.add(new BasicNameValuePair("input4", etbose.getText().toString()));
                    nameValuePairs.add(new BasicNameValuePair("input5", etsony.getText().toString()));
                }
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));


                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String response = httpclient.execute(httppost, responseHandler);
                System.out.println("Got response...");
                String reverseString = response;
                //Toast.makeText(TestAct.this, "response" + reverseString, Toast.LENGTH_LONG).show();
                System.out.println(reverseString);
                if(reverseString.equals("Success")) {

                    startActivity(new Intent(Survey.this, AllMessages.class));
                    //String responseBody = EntityUtils.toString(response.getEntity());
                }
                //error.setText(responseBody);
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
