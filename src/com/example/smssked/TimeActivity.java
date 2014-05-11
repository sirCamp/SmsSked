package com.example.smssked;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;


import android.widget.Toast;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.GradientDrawable;

import com.example.smssked.db.SmsDataBase;
import com.example.smssked.other.*;

public class TimeActivity extends Activity{

String TAG = "[SMSSked]";
String VOICE= "L'Activity TimeAcitivity ";

public Button smsButton;
public Button showButton;
public Message message;



@SuppressLint("NewApi")
@SuppressWarnings("deprecation")
public void changeBackground(){
	
	LinearLayout layout = (LinearLayout)findViewById(R.id.time_root);
	getWindow().setFormat(PixelFormat.RGBA_8888);
	getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
    
    if (android.os.Build.VERSION.SDK_INT > 15) { 
    	layout.setBackground(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#E0FFFF"),Color.parseColor("#4181C0")}));
    }
    else{
    	layout.setBackgroundColor(getResources().getColor(Color.BLUE));
    }
    //layout.setBackground(new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{Color.RED,Color.parseColor("#f2bf26")}));
}


public Message createMessage(Bundle b,TimePicker t, DatePicker d){
	
   try{
	   Message m=new Message(t.getCurrentHour().toString(), 
			   t.getCurrentMinute().toString(), b.getString("text"),
			   b.getString("dest"),d.getDayOfMonth(),d.getMonth());
	   return m;

   }
   catch(NullPointerException e){
	   Log.d(TAG,VOICE+"ha riscontrato un errore nella creazione del Messaggio");
	   return null;
   }
   
}

public void insertElement(Message m){
	
	SmsDataBase db=new SmsDataBase(getApplicationContext());
	db.open();
		String dest=m.getDest();
		String text=m.getText();
		String hour=m.getHour();
		String min=m.getMin();
		int day=m.getDay();
		int month=m.getMonth();
	db.insertProduct(dest, text, hour, min, day, month);
	db.close();

	Log.d(TAG,VOICE+"ha inserito un messaggio: dest: "+dest+ " sms: "
			+text+" hour: "+hour+" min: "+min+" day: "+day+" month: "+month);
	
}


public void showMessage(){
	try{
		Intent print_intent = new Intent(getApplicationContext(), PrintActivity.class);
		startActivity(print_intent);
	}
	catch(ActivityNotFoundException e){
		Log.d(TAG,VOICE+" ha trovato un errore, manca un'activity!");
	}
}


public void setMessage(View view){
	TimePicker time = (TimePicker)findViewById(R.id.timePicker);
	DatePicker date = (DatePicker)findViewById(R.id.datePicker);
    Message m=this.createMessage(this.getIntent().getExtras(),time,date);
    if(m!=null){
    	this.message=m;
    	insertElement(m);
    	this.showMessage();
    }
    else{
    	Toast.makeText(this,"[SmsSked]: Ci sono dei problemi con la crezione del messaggio", Toast.LENGTH_LONG).show();
    }
       
}




protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setta il layout alla creazione
		setContentView(R.layout.activity_time);
		this.changeBackground();
		TimePicker hour;
		hour = (TimePicker)this.findViewById(R.id.timePicker);
		hour.setIs24HourView(true);

				
		
		smsButton = (Button)this.findViewById(R.id.set_sms_button);
				
}

}
