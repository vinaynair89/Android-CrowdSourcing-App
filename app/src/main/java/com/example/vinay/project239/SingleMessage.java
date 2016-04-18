package com.example.vinay.project239;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;


public class SingleMessage extends ActionBarActivity implements View.OnClickListener {

    Button map;
    TextView lati,longi;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_message);

        lati = (TextView) findViewById(R.id.lati);
        longi = (TextView) findViewById(R.id.longi);
        map = (Button) findViewById(R.id.map);
        extras = new Bundle();
        extras = getIntent().getExtras();
        Intent in=getIntent();
        String Name = in.getStringExtra("summary");
        String Description = in.getStringExtra("description");
        if( extras != null ) {
            String test = extras.getString("summary");
            TextView txtContact = (TextView) findViewById(R.id.title);
            txtContact.setText(test);

            String description = extras.getString("description");
            TextView descContact = (TextView) findViewById(R.id.mess);
            descContact.setText(description);

            String from = extras.getString("from");
            TextView fromval = (TextView) findViewById(R.id.from);
            fromval.setText(from);
            //fromval.setVisibility(View.INVISIBLE);

            String to = extras.getString("to");
            TextView toval = (TextView) findViewById(R.id.to);
            toval.setText(to);
            //toval.setVisibility(View.INVISIBLE);

        }
        map.setVisibility(View.VISIBLE);
        map.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_single_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.map){
            extras = getIntent().getExtras();
            if(extras!=null) {
                //Context context=getApplicationContext();
                float lati = Float.parseFloat(extras.getString("lati"));
                System.out.println(lati);
                float longi = Float.parseFloat(extras.getString("longi"));
                System.out.println(longi);
                /*String uri = String.format(Locale.ENGLISH, "geo:%f %f", lati, longi);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);*/
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/?q="+lati+","+longi));
                startActivity(intent);
            }
            else{
                System.out.println("Not Going");
            }
        }
    }
}
