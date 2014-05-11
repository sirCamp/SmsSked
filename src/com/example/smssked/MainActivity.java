package com.example.smssked;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.smssked.service.SkedService;
import com.google.ads.AdRequest;
import com.google.ads.AdView;

import android.net.Uri;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.GradientDrawable;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	static final int PICK_CONTACT_REQUEST = 0;
    private String phone_number;
    private boolean correct=true;
    public Intent mServiceIntent; 

    
    @SuppressWarnings("deprecation")
	public void changeBackground(){
    	
    	LinearLayout layout = (LinearLayout)findViewById(R.id.root);
    	getWindow().setFormat(PixelFormat.RGBA_8888);
    	getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
        //layout.setBackgroundColor(getResources().getColor(Color.BLACK));
        
        layout.setBackground(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#E0FFFF"),Color.parseColor("#4181C0")}));
        //layout.setBackground(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#E0FFFF"),Color.parseColor("#4181C0")}));
        //layout.setBackground(new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{Color.RED,Color.parseColor("#f2bf26")}));
    }
    
    public void showMessage(View view){
    	
    	Intent print_intent = new Intent(getApplicationContext(), PrintActivity.class);
        startActivity(print_intent);
    }
    
    public void setSmsObject(View view){
 
  
           	   EditText textMessage = (EditText)findViewById(R.id.textMessagge);
               EditText phoneNumber = (EditText)findViewById(R.id.phoneNumber);
               Bundle bundle = new Bundle();
               
               if(textMessage.getText().toString().equals("")){
               	
               	Toast.makeText(this, "Iseririre un testo valido", Toast.LENGTH_LONG).show(); 
               	correct=false;
               	
               }
               
               if(textMessage.getText().toString().equals("")){
            	   Toast.makeText(this,"Inserire un numero di telefono valido", Toast.LENGTH_LONG).show();
            	   correct=false;
               }
               
               if(correct){
            	   	bundle.putString("text", textMessage.getText().toString());
               		bundle.putString("dest", phoneNumber.getText().toString());
               		try{
               			Intent setDate_intent = new Intent(getApplicationContext(), TimeActivity.class);
               			setDate_intent.putExtras(bundle);
               			startActivity(setDate_intent);
               		}
               		catch(ActivityNotFoundException e){
               		/*	AlertDialog.Builder builder=new AlertDialog.Builder(context);
               			builder.setTitle("Avviso");
               			builder.setMessage("Attenzione!Questo è un avviso");
               			builder.setCancelable(true);
               			AlertDialog alert=builder.create();
               		*/
               			Toast.makeText(this,e.getMessage()+" L'APPLICAZIONE VERRA TERMINATA", Toast.LENGTH_LONG).show();
               			System.exit(0);
               		}
               }
               else{
            	   correct=true;
               }
              
           }
     
    //Metodo che chiama la rubrica per selezione numero
    public void doLaunchContactPicker(View view) {  
    	
    	Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
        
    }
   
public String estraiNumeri(String s) {
        
        String number="";
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(s);
        
        while (m.find()) {
             //Converte in Intero, VALUTARE!!
            //System.out.println(Integer.parseInt(m.group()));
            System.out.println(m.group());
            number=number+m.group();
        }

        return number;
    }
    
   //Metodo che attende la risposta dall'activity della rubrica 
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Controllo del risultato
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri contactUri = data.getData();
                String[] projection = {Phone.NUMBER};
                Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                int column = cursor.getColumnIndex(Phone.NUMBER);
                String number = cursor.getString(column);
                this.phone_number=this.estraiNumeri(number);
                
                final EditText phoneArea = (EditText)findViewById(R.id.phoneNumber);
                phoneArea.setText(phone_number);
                
                //Toast.makeText(this, "Contect LIST  =  "+number, Toast.LENGTH_LONG).show(); 
  
            }
        }
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		//setta il layout alla creazione
		setContentView(R.layout.activity_main);
		this.changeBackground();
		/*Intent mServiceIntent = new Intent(this, SkedService.class);
		this.startService(mServiceIntent);*/
		AdView adview = (AdView)findViewById(R.id.adView);
		AdRequest re = new AdRequest();
		adview.loadAd(re);

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
		
		
		
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.action_settings:
			Toast.makeText(this, "Wait",Toast.LENGTH_LONG).show();
			return true;
		
			
		case R.id.exit_service:
			if(mServiceIntent!=null){
			       AlertDialog.Builder builder=new AlertDialog.Builder(this);
			        builder.setTitle("ATTENZIONE:");
			        builder.setMessage("ATTENZIONE:\n disattivando il servizio non saranno inviati messaggi!!" );
			        builder.setCancelable(false);
			        builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
			                public void onClick(DialogInterface dialog, int id){
			                        dialog.dismiss();
			                        }
			                });
			        builder.show();
			        
				this.stopService(mServiceIntent);
				mServiceIntent=null;
			}
			else{
				Toast.makeText(this, "Abilita il servizio prima di disattivarlo", Toast.LENGTH_LONG).show();
			}
			return true;
			
		case R.id.exit:
			finish();
			return true;
			
		case R.id.start_service:
			if(mServiceIntent==null){
				this.mServiceIntent = new Intent(this, SkedService.class);
				this.startService(mServiceIntent);
				Toast.makeText(this, "[SkedSMS]: Il servizio è stato abilitato", Toast.LENGTH_LONG).show();
			}
			else{
				Toast.makeText(this, "[SkedSMS]: Il servizio è già abilitato", Toast.LENGTH_LONG).show();
			}
			
			return true;
			
		}
		return super.onOptionsItemSelected(item);
	}
}
