package com.example.smssked;

import java.util.Vector;

import com.example.smssked.cursor.AlternateRowCursorAdapter;
import com.example.smssked.db.*;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;


public class PrintActivity extends Activity {

	Vector<String> contextMenu = new Vector<String>();
	Activity context=this;
	String selectedItem;
	Cursor c;
    int[] to = new int[]{R.id.smsid,R.id.day,R.id.hour,R.id.dest,R.id.text};

	

	public void changeBackground(){
    	
    	ListView layout = (ListView)findViewById(R.id.productsLv);
        
        layout.setBackground(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#E0FFFF"),Color.parseColor("#4181C0")}));
        //layout.setBackground(new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{Color.RED,Color.parseColor("#f2bf26")}));
    }
		
	
	public AlternateRowCursorAdapter reloadList(){
		
		 SmsDataBase db=new SmsDataBase(getApplicationContext());
		    db.open();  //apriamo il db
		    
			    Cursor c=db.fetchProducts();
	       String[] from= new String[]{SmsDataBase.ProductsMetaData.ID,SmsDataBase.ProductsMetaData.PRODUCT_DAY_KEY, SmsDataBase.ProductsMetaData.PRODUCT_MONTH_KEY,
	        		SmsDataBase.ProductsMetaData.PRODUCT_HOUR_KEY, SmsDataBase.ProductsMetaData.PRODUCT_MIN_KEY, 
	        		SmsDataBase.ProductsMetaData.PRODUCT_DEST_KEY,SmsDataBase.ProductsMetaData.PRODUCT_TEXT_KEY};//questi colonne 
	      
	       AlternateRowCursorAdapter adapterOff = new AlternateRowCursorAdapter(this, R.layout.product, c, from, to);
	       return adapterOff;
		
	}
	
	protected void onCreate(Bundle savedInstanceState) {
			
		
		super.onCreate(savedInstanceState);
		//setta il layout alla creazione
		setContentView(R.layout.activity_print);
		changeBackground();
		selectedItem="";
		
		ListView productsLv=(ListView)findViewById(R.id.productsLv);
	
		
       String[] from= new String[]{SmsDataBase.ProductsMetaData.ID,SmsDataBase.ProductsMetaData.PRODUCT_DAY_KEY, SmsDataBase.ProductsMetaData.PRODUCT_MONTH_KEY,
        		SmsDataBase.ProductsMetaData.PRODUCT_HOUR_KEY, SmsDataBase.ProductsMetaData.PRODUCT_MIN_KEY, 
        		SmsDataBase.ProductsMetaData.PRODUCT_DEST_KEY,SmsDataBase.ProductsMetaData.PRODUCT_TEXT_KEY};//questi colonne 
      

	
		 SmsDataBase db=new SmsDataBase(getApplicationContext());
		    db.open();  //apriamo il db
		    
			    c=db.fetchProducts(); // query
			    AlternateRowCursorAdapter adapterOff = new AlternateRowCursorAdapter(this, R.layout.product, c, from, to);
			    productsLv.setAdapter(adapterOff);
			    
			    productsLv.setLongClickable(false);
			    productsLv.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

		            @Override 
		            public void onCreateContextMenu(ContextMenu menu, View v,
		                    ContextMenuInfo menuInfo) { //here u set u rute
		                MenuInflater inflater = getMenuInflater();
		                inflater.inflate(R.menu.sms_menu, menu);

		            }
		        });
			    
			    
				productsLv.setOnItemClickListener(new OnItemClickListener() {
		            @Override
		            public void onItemClick(AdapterView<?> parent, View view, int position,
		                    long id) {
		                
		               TextView c = (TextView) view.findViewById(R.id.smsid);
		               selectedItem = c.getText().toString();
	                   openContextMenu(view);
		               
            
	                
		            }

	
		        });
			    

		    db.close();
	    


	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    @SuppressWarnings("unused")
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item 
	            .getMenuInfo(); 
	    switch (item.getItemId()) {
			  case R.id.first:
				  Log.d("[SmsSked]: ","Elimina: "+selectedItem);
			    deleteItem(selectedItem);
			    selectedItem="";
			    return true;
			  case R.id.second:
				  	Log.d("[SmsSked]: ","Modifica: "+selectedItem);
			        deleteItem(selectedItem);
			        selectedItem="";
			    return true;
			  case R.id.third:
			        System.out.println("Esci");
			        selectedItem="";
			    return true;
		
			  default:
				  selectedItem="";
				  return super.onContextItemSelected(item);
	  }
	}
	
	
	public void allTables(){
		Vector<String> todoItems= new Vector<String>();
		SQLiteDatabase db=openOrCreateDatabase("tutorialdb", MODE_PRIVATE, null);
	    Cursor ti = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'",null);
         if (ti.moveToFirst())
         {
         do{
            todoItems.add(c.getString(0));

            }while (ti.moveToNext());
         }
         if (todoItems.size() >= 0)
         {
             for (int i=0; i<todoItems.size(); i++)
             {
                 Log.d("TODOItems(" + i + ")", todoItems.get(i) + "");

             }

         }
		

	}
	
	public void colInfo(){
		SQLiteDatabase db=openOrCreateDatabase("tutorialdb", MODE_PRIVATE, null);
		Cursor ti = db.rawQuery("PRAGMA table_info(messaggi)", null);
		if ( ti.moveToFirst() ) {
		    do {
		        System.out.println("col: " + ti.getString(1));
		    } while (ti.moveToNext());
		}
	}
	

	public void deleteItem(String id){
    	SQLiteDatabase db=openOrCreateDatabase("tutorialdb", MODE_PRIVATE, null);
    	db.delete("messaggi", "_id" + "=" + id, null);
    	db.close();
    	
    	SmsDataBase dbs=new SmsDataBase(getApplicationContext());
		dbs.open();
    	c=dbs.fetchProducts();
        String[] from= new String[]{SmsDataBase.ProductsMetaData.ID,SmsDataBase.ProductsMetaData.PRODUCT_DAY_KEY, SmsDataBase.ProductsMetaData.PRODUCT_MONTH_KEY,
        		SmsDataBase.ProductsMetaData.PRODUCT_HOUR_KEY, SmsDataBase.ProductsMetaData.PRODUCT_MIN_KEY, 
        		SmsDataBase.ProductsMetaData.PRODUCT_DEST_KEY,SmsDataBase.ProductsMetaData.PRODUCT_TEXT_KEY};
		ListView productsLv=(ListView)findViewById(R.id.productsLv);
    	productsLv.setAdapter(new AlternateRowCursorAdapter(this, R.layout.product, c, from, to));
    	dbs.close();
    }
	//PRODUCTS_TABLE
	//public void deleteSMS(int i){}

}
