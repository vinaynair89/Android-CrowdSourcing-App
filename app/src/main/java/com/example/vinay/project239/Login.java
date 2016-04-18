package com.example.vinay.project239;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class Login extends ActionBarActivity implements View.OnClickListener {
    TextView content;
    Button bLogin;
    EditText etUsername, etPassword;
    TextView etRegLink;
    LocationManager lm;
    GPSTracker gps;
    LocationListener localis;
    String provider,currloca="";
    ArrayList<NameValuePair> postParameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etRegLink = (TextView) findViewById(R.id.etRegLink);

        bLogin = (Button) findViewById(R.id.bLogin);



        bLogin.setOnClickListener(this);
        etRegLink.setOnClickListener(this);
    }





    @Override
    public void onClick(View v) {


        System.out.println(etUsername.getText().toString());

        System.out.println(etPassword.getText().toString());

        if(v.getId()==R.id.bLogin){



            SendfeedbackJob job = new SendfeedbackJob();
            job.execute(etUsername.getText().toString(), etPassword.getText().toString());



        }

        else if (v.getId()==R.id.etRegLink){
            startActivity(new Intent(this, Register.class));
        }
    }
/*
    class Task implements Runnable{

        public String getLati() {
            gps = new GPSTracker(Login.this);
            double latitude = 0.0;
            double longitude = 0.0;
            if (gps.canGetLocation()) {
                latitude = gps.getLatitude();
               longitude = gps.getLongitude();

                System.out.println(latitude + " " + longitude);

            }
            //lati = 20.49999999 + "";
            //longi = 20.3999995 + "";

            return lati;
        }

        public String getLongi(){
            gps = new GPSTracker(Login.this);
            double latitude = 0.0;
            double longitude = 0.0;
            if (gps.canGetLocation()) {
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();

                //System.out.println(latitude + " " + longitude);

            }


            return longi;
        }

            public void run() {
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }

                while(true) {
                    String lati, longi;
                    lati = 37.348541 + "";
                    longi = -121.88627 + "";
                    if (lati != "0.0" && longi != "0.0") {
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost("http://10.189.255.186:80/CMPE239/thread.php");
                        // lati = 20.49999999 + "";
                        // longi = 20.3999995 + "";

                        // final int value = i;
                    /*Context c=getApplicationContext();
                    Session s=new Session(c);
                    String email = s.getusename();

                        try {
                            System.out.println("Hello");
                            String lati,longi;
                            lati = 37.348541 + "";
                            longi = -121.88627 + "";
                            System.out.println(lati + "------" + longi);
                            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                            nameValuePairs.add(new BasicNameValuePair("sess", etUsername.getText().toString()));
                            nameValuePairs.add(new BasicNameValuePair("lati", "37.348541"));
                            nameValuePairs.add(new BasicNameValuePair("longi", "-121.88627"));

                            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));


                            ResponseHandler<String> responseHandler = new BasicResponseHandler();
                            String response = httpclient.execute(httppost, responseHandler);

                            String reverseString = response;
                            //Toast.makeText(TestAct.this, "response" + reverseString, Toast.LENGTH_LONG).show();
                            System.out.println(reverseString);
                            Thread.sleep(6000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ClientProtocolException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //bar.setProgress(value);

                    }
                }
            }


    }
*/
    private class SendfeedbackJob extends AsyncTask<String, Void, String> implements Runnable{

        @Override
        protected String doInBackground(String[] params) {
            // do above Server call here

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://10.189.255.186:80/CMPE239/login.php");


            try {
                etUsername.getText().toString();

                System.out.println(etPassword.getText().toString());

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("email", etUsername.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("pwd", etPassword.getText().toString()));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));


                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String response = httpclient.execute(httppost, responseHandler);

                String reverseString = response;
                //Toast.makeText(TestAct.this, "response" + reverseString, Toast.LENGTH_LONG).show();
                System.out.println("-----------------------"+reverseString);

                Context c=getApplicationContext();
                Session s=new Session(c);
                s.setusename(etUsername.getText().toString());
                //new Thread(new Task()).start();
                if(reverseString.equals("Success")) {
                    startActivity(new Intent(Login.this, AllMessages.class));

                    //new Thread(new Task()).start();

                }
                else if(reverseString.equals("Login failed")){
                    return "Login failed";
                }

            } catch (ClientProtocolException e) {
                // Toast.makeText(TestAct.this, "CPE response " + e.toString(), Toast.LENGTH_LONG).show();
                System.out.println(e.toString());
// TODO Auto-generated catch block
            } catch (IOException e) {
                //Toast.makeText(TestAct.this, "IOE response " + e.toString(), Toast.LENGTH_LONG).show();
                System.out.println(e.toString());
// TODO Auto-generated catch block
            }



            return "some message";
        }


        @Override
        protected void onPostExecute(String message) {

            if(message.equalsIgnoreCase("Login failed")){
                Toast.makeText(Login.this, message, Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void run() {

        }
    }





}
