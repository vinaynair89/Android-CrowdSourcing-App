package com.example.vinay.project239;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class AllMessages extends ListActivity implements View.OnClickListener {

    public ArrayList<String> arrList;
    public ArrayAdapter<String> arrAdap;
    Button newPost, refresh;

    Notification n;
    GPSTracker gps;
    String lati,longi;

    private ProgressDialog pDialog;

    // URL to get contacts JSON
    private static String url = "http://10.189.255.186:80/CMPE239/thread.php";
    //private static String url = "http://10.0.0.22:80/CMPE239/thread.php";
    //private static final String TAG_CONTACTS = "contacts";
    //private static final String TAG_ID = "id";
    private static final String TAG_TITLE = "summary";
    private static final String TAG_MESS = "description";
    private static final String TAG_FROM = "from";
    private static final String TAG_TO = "to";
    private static final String TAG_LATI = "lati";
    private static final String TAG_LONGI = "longi";
    /*private static final String TAG_ADDRESS = "address";
    private static final String TAG_GENDER = "gender";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_PHONE_MOBILE = "mobile";
    private static final String TAG_PHONE_HOME = "home";
    private static final String TAG_PHONE_OFFICE = "office";*/


    Button bnPost;
    JSONArray contacts = new JSONArray();
    ArrayList<HashMap<String, String>> contactList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_messages, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_messages);

        contactList=new ArrayList<HashMap<String,String>>();
        Intent in = getIntent();
        newPost = (Button) findViewById(R.id.newPost);
        refresh = (Button) findViewById(R.id.refresh);
        ListView lv = getListView();
        //((TextView) getListView().findViewById(R.id.lati)).setVisibility(View.INVISIBLE);
        //((TextView) getListView().findViewById(R.id.longi)).setVisibility(View.INVISIBLE);
        // Listview on item click listener
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem

                String title = ((TextView) view.findViewById(R.id.title))
                        .getText().toString();
                String mess = ((TextView) view.findViewById(R.id.mess))
                        .getText().toString();
                String from = ((TextView) view.findViewById(R.id.from))
                        .getText().toString();

                String to = ((TextView) view.findViewById(R.id.to))
                        .getText().toString();
                String lati = ((TextView) view.findViewById(R.id.lati))
                        .getText().toString();


                String longi = ((TextView) view.findViewById(R.id.longi))
                        .getText().toString();

                /*String description = ((TextView) view.findViewById(R.id.mobile))
                        .getText().toString();*/

                Bundle extras = new Bundle();
                extras.putString(TAG_TITLE, title);
                extras.putString(TAG_MESS, mess);
                // Starting single contact activity
                Intent in = new Intent(getApplicationContext(), SingleMessage.class);
                in.putExtra(TAG_TITLE, title);
                in.putExtra(TAG_MESS, mess);
                in.putExtra(TAG_FROM, from);
                in.putExtra(TAG_TO, to);
                in.putExtra(TAG_LATI, lati);
                in.putExtra(TAG_LONGI, longi);
                startActivity(in);

            }
        });


        new GetContacts(AllMessages.this).execute();
        refresh.setOnClickListener(this);
        newPost.setOnClickListener(this);

        // Calling async task to get json



    }

    public void addInList(String item){
        arrAdap.add(item);
    }




    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.etPost:
                startActivity(new Intent(this, Notification.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.newPost){

            startActivity(new Intent(AllMessages.this,Notification.class));
        }
        else if(v.getId()==R.id.refresh){
            startActivity(new Intent(AllMessages.this,AllMessages.class));
        }
    }





    private class GetContacts extends AsyncTask<Void, Void, Void> {
        Activity activity;

        public GetContacts(Activity activity){
            this.activity=activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(AllMessages.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        public String convertinputStreamToString(InputStream ists)
                throws IOException {
            if (ists != null) {
                StringBuilder sb = new StringBuilder();
                String line;

                try {
                    BufferedReader r1 = new BufferedReader(new InputStreamReader(
                            ists, "UTF-8"));
                    while ((line = r1.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                } finally {
                    ists.close();
                }
                return sb.toString();
            } else {
                return "";
            }
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            gps = new GPSTracker(AllMessages.this);
            String latitude = "";
            String longitude = "";
            if (gps.canGetLocation()) {
                latitude = gps.getLatitude()+"";
                longitude = gps.getLongitude()+"";

                //System.out.println(latitude + " " + longitude);

            }
            //new Thread(new Task()).start();
           // lati = 20.49999999 + "";
            //longi = 20.3999995 + "";
            //latitude=37.348541+"";
            //longitude=-121.88627+"";
            ServiceHandler sh = new ServiceHandler();
            Context c1=getApplicationContext();
            Session s=new Session(c1);
            String newsession=s.getusename();
            // Making a request to url and getting response
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("sess", newsession));
            nameValuePairs.add(new BasicNameValuePair("lati", latitude));
            nameValuePairs.add(new BasicNameValuePair("longi", longitude));
            String js= sh.makeServiceCall(url, ServiceHandler.POST, nameValuePairs);
            //String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            //System.out.println(jsonStr);

            //System.out.println(parsedString);
            System.out.println(js);
            String result;
           // result = getJSONUrl()
           // Log.d("Response: ", "> " + jsonStr);

            if (js != null) {
                try {
                   // JSONObject jsonObj = new JSONObject(js);
                    //String arr[]=js.split(" ");
                    String jsonstr=js.replace("][",",");
                    // Getting JSON Array node
                  // for(int j=0;j<arr.length;j++) {
                    //    contacts = new JSONArray(arr[j]);
                    System.out.println(jsonstr);

                    try{
                        contacts = new JSONArray(jsonstr.toString());
                    }
                    catch(Exception e){
                        System.out.println("Error"+e);
                    }
                        System.out.println("Length of the array:" + contacts.length());
                        // looping through All Contacts
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);

                        /*String id = c.getString(TAG_ID);
                        String name = c.getString(TAG_NAME);
                        String email = c.getString(TAG_EMAIL);
                        String address = c.getString(TAG_ADDRESS);
                        String gender = c.getString(TAG_GENDER);*/

                            String title = c.getString(TAG_TITLE);
                            String mess = c.getString(TAG_MESS);
                            String from = c.getString(TAG_FROM);
                            String to = c.getString(TAG_TO);
                            String lati = c.getString(TAG_LATI);
                            String longi = c.getString(TAG_LONGI);

                            // Phone node is JSON Object
                        /*JSONObject phone = c.getJSONObject(TAG_PHONE);
                        String mobile = phone.getString(TAG_PHONE_MOBILE);
                        String home = phone.getString(TAG_PHONE_HOME);
                        String office = phone.getString(TAG_PHONE_OFFICE);*/

                            // tmp hashmap for single contact
                            HashMap<String, String> contact = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            //contact.put(TAG_ID, id);
                            contact.put(TAG_TITLE, title);
                            contact.put(TAG_MESS, mess);
                            contact.put(TAG_FROM, from);
                            contact.put(TAG_TO, to);
                            contact.put(TAG_LATI, lati);
                            contact.put(TAG_LONGI, longi);
                            //contact.put(TAG_PHONE_MOBILE, mobile);*/

                            // adding contact to contact list
                            contactList.add(contact);
                        }
                   // }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    AllMessages.this, contactList,
                    //R.layout.activity_single_message, new String[] { TAG_TITLE, TAG_MESS}, new int[] { R.id.title,
                   // R.id.mess});
                    R.layout.activity_single_message, new String[] { TAG_TITLE, TAG_MESS, TAG_FROM, TAG_TO, TAG_LATI, TAG_LONGI}, new int[] { R.id.title, R.id.mess, R.id.from, R.id.to, R.id.lati, R.id.longi });
            setListAdapter(adapter);

            //AllNotification an=new AllNotification(activity,contactList);

        }

    }


}
