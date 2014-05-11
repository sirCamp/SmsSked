package com.example.smssked.service;

import com.example.smssked.receiver.DeliverReceiver;
import com.example.smssked.receiver.SentReceiver;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.util.Log;


public class SMSSend extends Thread {

	String TAG = "[SMSSked]";
	String VOICE= "Thread SMSSend ha ricevuto una spedizione da eseguire";
	String VOICE_END="Thread SMSSend ha terminato la spedizione";
	
	String SENT = "SMS_SENT";
	String DELIVERED = "SMS_DELIVERED";
    
	private BroadcastReceiver deliver;
    private BroadcastReceiver sent;
    
    public String dest;
    public String text;
    public Service context;
    
    
    public SMSSend(Service context2,String d, String t){
    	dest=d;
    	text=t;
    	context=context2;
    }
    
    public void run(){
    	
    	Log.d(TAG,VOICE);
        this.deliver=new DeliverReceiver();
        this.sent=new SentReceiver();
                
    	PendingIntent sentPI = PendingIntent.getBroadcast(this.context, 0,
    	new Intent(SENT), 0);

    	PendingIntent deliveredPI = PendingIntent.getBroadcast(this.context, 0,
    	new Intent(DELIVERED), 0);
        
    	this.context.registerReceiver(this.deliver, new IntentFilter(DELIVERED));
    	this.context.registerReceiver(this.sent, new IntentFilter(SENT));
        
    	SmsManager sms = SmsManager.getDefault();
    	sms.sendTextMessage(this.dest, null,this.text, sentPI, deliveredPI);
    	
       /* this.context.unregisterReceiver(this.deliver);
        this.context.unregisterReceiver(this.sent);*/
        
        Log.d(TAG,VOICE_END);
    	
    }
    
    
}
/*
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
    	super.onStartCommand(intent, flags, startId);
        
        this.deliver=new DeliverReceiver();
        this.sent=new SentReceiver();
        
		Bundle b=intent.getExtras();		
        
    	PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
    	new Intent(SENT), 0);

    	PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
    	new Intent(DELIVERED), 0);
        
        this.registerReceiver(this.sent, new IntentFilter(DELIVERED));
        this.registerReceiver(this.sent, new IntentFilter(SENT));
        
    	SmsManager sms = SmsManager.getDefault();
    	sms.sendTextMessage(b.getString("dest"), null,b.getString("text"), sentPI, deliveredPI);
    	return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Do not forget to unregister the receiver!!!
        this.unregisterReceiver(this.deliver);
        this.unregisterReceiver(this.sent);
    }

    private void showSuccessfulBroadcast() {
        Toast.makeText(this, "Broadcast Successful!!!", Toast.LENGTH_LONG)
                .show();
    }
}


*/














/*extends Activity {
    //...
	public String to;
	public String text;
	BroadcastReceiver receiver; 
	BroadcastReceiver sent; 
	
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.receiver =new DeliverReceiver(this);
		this.sent =new SentReceiver(this);
		Bundle b=this.getIntent().getExtras();
		this.text=b.getString("text");
		this.to=b.getString("dest");
		sendSMS (b.getString("dest"),b.getString("text"));
		
	}
	*//*
	
	private void sendSMS(String phoneNumber, String message)
	{

	String SENT = "SMS_SENT";
	String DELIVERED = "SMS_DELIVERED";*//*

	PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
	new Intent(SENT), 0);

	PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
	new Intent(DELIVERED), 0);

	
	registerReceiver(receiver,new IntentFilter(SENT));
	registerReceiver(sent,new IntentFilter(DELIVERED));

	SmsManager sms = SmsManager.getDefault();
	sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI); 
	} 

	@Override
	protected void onStop()
	{
	    unregisterReceiver(receiver);
	    unregisterReceiver(sent);
	    super.onStop();
	}
}*/