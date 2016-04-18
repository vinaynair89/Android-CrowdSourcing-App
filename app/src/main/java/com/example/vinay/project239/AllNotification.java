package com.example.vinay.project239;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Vinay on 12/5/2015.
 */
public class AllNotification extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String,String>> data;
    private static LayoutInflater inflater=null;
    //public Resources res;

    public AllNotification(Activity a, ArrayList<HashMap<String,String>> d) {

        /********** Take passed values **********/
        activity = a;
        data=d;
        //res = resLocal
        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {

        if(data.size()<=0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/


    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
       // ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.activity_single_message, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            //holder = new ViewHolder();
            TextView title = (TextView) vi.findViewById(R.id.title);
            TextView mess=(TextView)vi.findViewById(R.id.mess);
            TextView from=(TextView)vi.findViewById(R.id.from);
            TextView to=(TextView)vi.findViewById(R.id.to);

            /************  Set holder with LayoutInflater ************/
           // vi.setTag(holder);
        }

        return vi;
    }


}
