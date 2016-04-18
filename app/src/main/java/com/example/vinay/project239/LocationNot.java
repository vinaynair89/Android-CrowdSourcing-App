package com.example.vinay.project239;

import android.os.Handler;

/**
 * Created by Vinay on 11/26/2015.
 */
public class LocationNot implements Runnable {

    public void running(){


    }
    public static void main(String ards[]){


    }

    @Override
    public void run() {

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while(true) {
                        System.out.println("Hello");
                        sleep(5000);
                        //handler.post(this);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();

    }
}
