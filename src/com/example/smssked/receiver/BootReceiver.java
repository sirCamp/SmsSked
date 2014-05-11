package com.example.smssked.receiver;

import com.example.smssked.service.SkedService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
 
public class BootReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
        	if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
        		Intent serviceIntent = new Intent(context, SkedService.class);
            	context.startService(serviceIntent);
        	}
               
        }
}