package com.example.smssked.cursor;

import com.example.smssked.R;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.TextView;

@SuppressLint("UseValueOf")
public class AlternateRowCursorAdapter extends SimpleCursorAdapter implements Filterable{  
	  
	//private int[] colors = new int[] { Color.parseColor("#F0F0F0"), Color.parseColor("#D2E4FC") };
	private int[] colors = new int[] { Color.parseColor("#F0F0F0"), Color.parseColor("#D2E4FC") };
	
	@SuppressWarnings("unused")
	private Cursor c;
	private int layout;
	@SuppressWarnings("unused")
	private Context context;

	@SuppressWarnings("deprecation")
	public AlternateRowCursorAdapter(Context context, int layout, Cursor c,  String[] from, int[] to) {  
		super(context, layout, c, from, to); 
			
        this.context = context;
        this.layout = layout;
        this.c = c;
	}  


	@Override  
	public  View getView(int position, View convertView, ViewGroup parent) {  
		View view = super.getView(position, convertView, parent);  
		int colorPos = position % colors.length;  
		view.setBackgroundColor(colors[colorPos]);  
		return view;  
	} 


	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {

	    final LayoutInflater inflater = LayoutInflater.from(context);
	    View v = inflater.inflate(layout, parent, false);
	    return v;
	}

	@Override
	public void bindView(View v, Context context, Cursor c) {
    
	/*	System.out.println("1): "+ c.getString(1));
		System.out.println("2): "+ c.getString(2));
		System.out.println("3): "+ c.getString(3));
		System.out.println("4): "+ c.getString(4));
		System.out.println("5): "+ c.getString(5));
		System.out.println("6): "+ c.getString(6));
		
	*/	
		
		Integer month = new Integer( Integer.parseInt(c.getString(6))+1);
	    String day_s= c.getString(5)+"/"+ month.toString();
	    String hour_s=c.getString(4)+":"+c.getString(3);
	    String dest_s="";
	    
	    TextView day = (TextView) v.findViewById(R.id.day);
	    TextView hour = (TextView) v.findViewById(R.id.hour);
	    TextView dest = (TextView) v.findViewById(R.id.dest);
	    TextView text = (TextView) v.findViewById(R.id.text);
	    TextView id =(TextView) v.findViewById(R.id.smsid);
	    
	    
	    ContentResolver localContentResolver = this.mContext.getContentResolver();
	    Cursor contactLookupCursor =  
	       localContentResolver.query(
	                Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, 
	                Uri.encode(c.getString(1))), 
	                new String[] {PhoneLookup.DISPLAY_NAME, PhoneLookup._ID}, 
	                null, 
	                null, 
	                null);
	    try {
	    while(contactLookupCursor.moveToNext()){
	        String contactName = contactLookupCursor.getString(contactLookupCursor.getColumnIndexOrThrow(PhoneLookup.DISPLAY_NAME));
	        String contactId = contactLookupCursor.getString(contactLookupCursor.getColumnIndexOrThrow(PhoneLookup._ID));
	        dest_s=contactName;
	        Log.d("RUBRICA", "contactMatch id: " + contactId);
	        }
	    } finally {
	    contactLookupCursor.close();
	    }
	    
	    day.setText(day_s);
	    hour.setText(hour_s);
	    if(dest_s.equals(""))
	    {
	    	dest.setText(c.getString(1));
	    }
	    else{
	    	dest.setText(dest_s);
	    }
	    text.setText(c.getString(2));
	    id.setText(c.getString(0));
	    
	    dest_s="";
	    
	}


} ;