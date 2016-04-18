package com.example.vinay.project239;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class TestAct extends ActionBarActivity implements View.OnClickListener {
    String username,password;
    Button login;
    TextView error;
    ArrayList<NameValuePair> postParameters;
   // LocationNot l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
       // Button login;
        username = "vinay";
        password = "vinay";
        //error = (TextView) findViewById(R.id.error);
        //final HttpClient httpclient = new DefaultHttpClient();
        //final HttpPost httppost = new HttpPost("http://localhost:8000/index.php");


        //l.run();



        login = (Button) findViewById(R.id.newlog);




    /*postParameters.add(new BasicNameValuePair("debug_data","1"));
    postParameters.add(new BasicNameValuePair("client_api_ver","1.5.1.422"));
    postParameters.add(new BasicNameValuePair("timestamp","1314195661"));
    postParameters.add(new BasicNameValuePair("set_locale","en_US"));
    postParameters.add(new BasicNameValuePair("device_os_type","Android 2.2"));
    postParameters.add(new BasicNameValuePair("device_sync_type","3"));
    postParameters.add(new BasicNameValuePair("device_identificator","255634997"));
    postParameters.add(new BasicNameValuePair("device_identification_string",deviceId));*/
        login.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.newlog) {

            SendfeedbackJob job = new SendfeedbackJob();
            job.execute(username, password);
        }
    }
    public void postData(String toPost) {

    }

    private class SendfeedbackJob extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String[] params) {
            // do above Server call here
            /*final HttpClient httpclient = new DefaultHttpClient();
            final HttpPost httppost = new HttpPost("http://10.189.200.121:80/index2.php");

            try {
                postParameters = new ArrayList<NameValuePair>(2);
                postParameters.add(new BasicNameValuePair("username_hash", username));
                postParameters.add(new BasicNameValuePair("password_hash", password));
                httppost.setEntity(new UrlEncodedFormEntity(postParameters));
                System.out.println("Here 1");
                HttpResponse response = httpclient.execute(httppost);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                //final String resp = httpclient.execute(httppost, responseHandler);

                System.out.println("Here 2");
                Log.d("myapp", "response " + response.getEntity());

                String responseBody = EntityUtils.toString(response.getEntity());
                System.out.println(responseBody);

                //error.setText(responseBody);
            } catch (Exception e) {
                System.out.println(e);
            }*/
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.1.149/cmpe239/test.php");

//This is the data to send
            String email = "yes"; //any data to send

            try {
// Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("emailid", email));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

// Execute HTTP Post Request

                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String response = httpclient.execute(httppost, responseHandler);

//This is the response from a php application
                String reverseString = response;
                //Toast.makeText(TestAct.this, "response" + reverseString, Toast.LENGTH_LONG).show();
                System.out.println(reverseString);

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
            //process message
        }
    }



}
