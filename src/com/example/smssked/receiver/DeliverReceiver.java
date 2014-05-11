package com.example.smssked.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

public class DeliverReceiver extends BroadcastReceiver{
	
	public Activity activity;
	
	public DeliverReceiver(){
	}
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		switch (getResultCode())
		{
			case Activity.RESULT_OK:
				Toast.makeText(arg0,"[SMSSked]: sms  inviato!", 
				Toast.LENGTH_SHORT).show();
			break;
			case Activity.RESULT_CANCELED:
				Toast.makeText(arg0,"[SMSSked]: sms non inviato!", 
				Toast.LENGTH_SHORT).show();
			break; 
			case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
				Toast.makeText(arg0,"[SMSSked]: sms  inviato, problema comunicazione!",
				Toast.LENGTH_SHORT).show();
			break;
			case SmsManager.RESULT_ERROR_NO_SERVICE:
				Toast.makeText(arg0,"[SMSSked]: sms  inviato, manca il servizio", 
				Toast.LENGTH_SHORT).show();
			break;
			case SmsManager.RESULT_ERROR_NULL_PDU:
				Toast.makeText(arg0,"[SMSSked]: sms  inviato, PDU Nullo!", 
				Toast.LENGTH_SHORT).show();
			break;
			case SmsManager.RESULT_ERROR_RADIO_OFF:
				Toast.makeText(arg0,"[SMSSked]: sms  inviato, Non c'è rete!", 
				Toast.LENGTH_SHORT).show();
			break;
			
		}
	}
}


