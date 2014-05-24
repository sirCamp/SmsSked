
package com.example.smssked.service;

import java.util.Calendar;

import com.example.smssked.service.SMSSend;
import com.example.smssked.db.SmsDataBase;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


public class SkedService extends Service{
	 private  static  String TAG = "[SkedSms]: " ;
	 private static String VOICE="Controllo SMS da inviare";
	 private static String VOICE_END="SkedService e' stato terminato";
	 public String name;
	 private Intent myIntent;
	 public PendingIntent pendingIntent;
	 private AlarmManager alarmManager;
	 private Calendar calendar;
	 public static final long DELAY_INTERVAL_SERVICE = 1000;
 
	 
	 public SkedService(){
		 name="SkedService";
	 }
	 
	 
	 @Override 
	 public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
	 }
    
	 @Override
	 public void onCreate() { 
			   super.onCreate();
			   myIntent = new Intent(this, SkedService.class);
			   pendingIntent = PendingIntent.getService(this, 0, myIntent, 0);
			   alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
			   calendar = Calendar.getInstance();
			   calendar.setTimeInMillis(System.currentTimeMillis());
			   calendar.add(Calendar.SECOND, 10);
			   alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), DELAY_INTERVAL_SERVICE*30, pendingIntent);
			}
    
    @Override
    public void onStart(Intent intent, int startId) {
  	  
    	final Service context=this;
    	Thread t=new Thread(){
    		public void run(){
  				Log.d(TAG,VOICE);
  			  	SmsDataBase db=new SmsDataBase(getApplicationContext());
  			  	db.open();
  			  	Cursor c=db.fetchsSMS();
  			  	while (c.moveToNext()) {
  			  			String id = c.getString( c.getColumnIndex(SmsDataBase.ProductsMetaData.ID));
  			  			String dest = c.getString( c.getColumnIndex(SmsDataBase.ProductsMetaData.PRODUCT_DEST_KEY));
  			  			String text =c.getString( c.getColumnIndex(SmsDataBase.ProductsMetaData.PRODUCT_TEXT_KEY));
  			    
  			  			Log.d("ID", "id = " + id);
  			  			Log.d("DEST", "dest = " + dest);
  			  			Log.d("TEXT", "text = " + text);
		  			

  			  		SMSSend t= new SMSSend(context,dest,text);
  			  		t.start();
  			    
  			  	}           
  			  c.close();
  			  db.close();

  		  }
  	  };
  	  
  	  t.start();
  
    }
     
    
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.d (TAG, VOICE_END );
 
        Toast.makeText(this, "Il servizio e' stato disabilitato", Toast.LENGTH_LONG).show();
        alarmManager.cancel(pendingIntent);
    }
}
