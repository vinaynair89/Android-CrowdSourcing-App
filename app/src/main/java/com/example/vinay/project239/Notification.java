package com.example.vinay.project239;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Notification extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText etTitle, etMessage,etFrom, etTo;
    Button bnPost;
    SeekBar seek;
    AllMessages a;
    EditText title;
    GPSTracker gps;
    Spinner spinner;
    ArrayList<NameValuePair> postParameters;
    private static final String[]paths = {"Book", "Music, Movie & Book", "Electronics", "Home, Furniture & Patio"};
    String dept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        etTitle = (EditText) findViewById(R.id.etTitle);
        etMessage = (EditText) findViewById(R.id.etMessage);
        etFrom = (EditText) findViewById(R.id.etFrom);
        etTo = (EditText) findViewById(R.id.etTo);
        seek = (SeekBar) findViewById(R.id.seek1);
        bnPost =(Button) findViewById(R.id.bnPost);
        spinner = (Spinner)findViewById(R.id.spindep);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Notification.this,
                android.R.layout.simple_spinner_item, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //spinner.setOnItemSelectedListener(this);



        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        bnPost.setOnClickListener(this);
    }


    public void onStart(){
        super.onStart();
        EditText etFrom = (EditText) findViewById(R.id.etFrom);
        EditText etTo = (EditText) findViewById(R.id.etTo);
        etFrom.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    DateDialog dialog = new DateDialog();
                    dialog.setView(v);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft,"DatePicker");
                }
            }
        });
    }

    public void setTitle(EditText etitle){
        title = etitle;
    }

    public String getTitl(){
        return title.getText().toString();
    }

    @Override
    public void onClick(View v) {



        if(v.getId()==R.id.bnPost){
            /*setTitle(etTitle);
            System.out.println(etTitle.getText().toString());
            a.addInList(etTitle.getText().toString());*/
            System.out.println(etTitle.getText().toString());
            System.out.println(etMessage.getText().toString());
            System.out.println(seek.getProgress());

            /*gps = new GPSTracker(Notification.this);

            if(gps.canGetLocation()){
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();

                System.out.println(latitude + " "+ longitude );
            }*/

            //Intent intent = new Intent(this,AllMessages.class);
            //intent.putExtra("title",etTitle.getText().toString());
            SendfeedbackJob job = new SendfeedbackJob();
            job.execute(etTitle.getText().toString(), etMessage.getText().toString(),seek.getContext().toString() );
            //startActivity(intent);*/
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        dept=spinner.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class SendfeedbackJob extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String[] params) {
            // do above Server call here
            final HttpClient httpclient = new DefaultHttpClient();
            final HttpPost httppost = new HttpPost("http://10.189.255.186:80/CMPE239/notification.php");
            String title=etTitle.getText().toString();
            String message=etMessage.getText().toString();
            String strDate = etFrom.getText().toString();
            /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date date = null;
            try {
                date = dateFormat.parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println(date);*/

            String toDate=etTo.getText().toString();
           /* SimpleDateFormat ndateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date ndate = null;
            try {
                ndate = ndateFormat.parse(toDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println(date);*/
            int s = seek.getProgress();
            String slid = s+"";
            gps = new GPSTracker(Notification.this);
            double latitude=20.0;
            double longitude=20.0;

            if(gps.canGetLocation()){
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();

                System.out.println(latitude + " "+ longitude );
            }

            String lat=latitude+"";
            String longi=longitude+"";

            try {
                postParameters = new ArrayList<NameValuePair>(2);
                postParameters.add(new BasicNameValuePair("title", title));
                postParameters.add(new BasicNameValuePair("message", message));
                postParameters.add(new BasicNameValuePair("sliderval", slid));
                postParameters.add(new BasicNameValuePair("dept", dept));
                postParameters.add(new BasicNameValuePair("fromdate", strDate));
                postParameters.add(new BasicNameValuePair("todate", toDate));
                postParameters.add(new BasicNameValuePair("lat",lat));
                postParameters.add(new BasicNameValuePair("longi",longi));

                httppost.setEntity(new UrlEncodedFormEntity(postParameters));
                System.out.println("Here 1");
                HttpResponse response = httpclient.execute(httppost);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                //final String resp = httpclient.execute(httppost, responseHandler);

                System.out.println("Here 2");
                Log.d("myapp", "response " + response.getEntity());

                String responseBody = EntityUtils.toString(response.getEntity());
                System.out.println(responseBody);
                if(responseBody.equals("Success")) {
                    startActivity(new Intent(Notification.this, AllMessages.class));

                    //new Thread(new Task()).start();

                }
                else
                {
                    System.out.println("Errrrrrrrror");
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
