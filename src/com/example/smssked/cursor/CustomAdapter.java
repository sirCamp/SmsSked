package com.example.smssked.cursor;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

class CustomAdapter extends CursorAdapter {
	
	
    // CursorAdapter will handle all the moveToFirst(), getCount() logic for you :)

    @SuppressWarnings("deprecation")
	public CustomAdapter(Context context, Cursor c) {
        super(context, c);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        @SuppressWarnings("unused")
		String id = cursor.getString(0);
        String name = cursor.getString(1);
        // Get all the values
        // Use it however you need to
        TextView textView = (TextView) view;
        textView.setText(name);
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate your view here.
        TextView view = new TextView(context);
        return view;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {  
    View view = super.getView(position, convertView, parent);  
    if (position % 2 == 1) {
        view.setBackgroundColor(Color.BLUE);  
    } else {
        view.setBackgroundColor(Color.CYAN);  
    }

    return view;  
    }
}