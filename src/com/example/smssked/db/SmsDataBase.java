package com.example.smssked.db;

import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SmsDataBase {

	SQLiteDatabase mDb;
    DbHelper mDbHelper;
    Context mContext;
    private static final String DB_NAME="tutorialdb";//nome del db
    private static final int DB_VERSION=1; //numero di versione del nostro db
    
    public SmsDataBase(Context ctx){
            mContext=ctx;
            mDbHelper=new DbHelper(ctx, DB_NAME, null, DB_VERSION);   
            //quando istanziamo questa classe, istanziamo anche l'helper (vedi sotto)     
    }
    
    public void open(){  //il database su cui agiamo è leggibile/scrivibile
            mDb=mDbHelper.getWritableDatabase();
            
    }
    
    public void close(){ //chiudiamo il database su cui agiamo
            mDb.close();
    }
    
    
    //i seguenti 2 metodi servono per la lettura/scrittura del db. aggiungete e modificate a discrezione
      
    public void insertProduct(String dest, String text, String hour, String min, int day, int month){ //metodo per inserire i dati
            ContentValues cv=new ContentValues();
            cv.put(ProductsMetaData.PRODUCT_DEST_KEY, dest);
            cv.put(ProductsMetaData.PRODUCT_TEXT_KEY, text);
            cv.put(ProductsMetaData.PRODUCT_HOUR_KEY, hour);
            cv.put(ProductsMetaData.PRODUCT_MIN_KEY, min);
            cv.put(ProductsMetaData.PRODUCT_DAY_KEY, day);
            cv.put(ProductsMetaData.PRODUCT_MONTH_KEY, month);
            mDb.insert(ProductsMetaData.PRODUCTS_TABLE, null, cv);
    }
    
    public Cursor fetchProducts(){ //metodo per fare la query di tutti i dati
            return mDb.query(ProductsMetaData.PRODUCTS_TABLE, null,null,null,null,null,null);               
    }
    
    public Cursor fetchsSMS() {
   
		Calendar cal = Calendar.getInstance(); 
        return mDb.query(ProductsMetaData.PRODUCTS_TABLE, new String[] { ProductsMetaData.ID,ProductsMetaData.PRODUCT_DEST_KEY,ProductsMetaData.PRODUCT_TEXT_KEY},
        		ProductsMetaData.PRODUCT_MONTH_KEY+"="+cal.get(Calendar.MONTH)+" AND "+ProductsMetaData.PRODUCT_DAY_KEY+"="+cal.get(Calendar.DAY_OF_MONTH)+" AND "
        				+ ProductsMetaData.PRODUCT_HOUR_KEY+"="+cal.get(Calendar.HOUR_OF_DAY)+" AND "
        				+ProductsMetaData.PRODUCT_MIN_KEY+"="+cal.get(Calendar.MINUTE),	null, null, null, null, null);
    }
    
    
    public void delete_byID(int id){
    	 //sqLiteDatabase.delete(ProductsMetaData.PRODUCTS_TABLE, ProductsMetaData.ID+"="+id, null);
    	}

    public Cursor showAllTables(SQLiteDatabase db){
        String mySql = " SELECT name FROM sqlite_master " + " WHERE type='table'"
                + "   AND name LIKE 'PR_%' ";
        return db.rawQuery(mySql, null);
    }
    
    public static class ProductsMetaData {  // i metadati della tabella, accessibili ovunque
			static final String PRODUCTS_TABLE = "messaggi";
            public static final String ID = "_id";
            public static final String PRODUCT_DEST_KEY = "destinatario";
			public static final String PRODUCT_TEXT_KEY = "testo";
			public static final String PRODUCT_MIN_KEY = "minuti";
			public static final String PRODUCT_HOUR_KEY = "ore";
            public static final String PRODUCT_DAY_KEY = "giorno";
            public static final String PRODUCT_MONTH_KEY = "mese";

    }

    private static final String PRODUCTS_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "  //codice sql di creazione della tabella
                    + ProductsMetaData.PRODUCTS_TABLE + " (" 
                    + ProductsMetaData.ID+ " integer primary key autoincrement, "
                    + ProductsMetaData.PRODUCT_DEST_KEY + " text not null, "
                    + ProductsMetaData.PRODUCT_TEXT_KEY + " text not null, "
                    + ProductsMetaData.PRODUCT_MIN_KEY + " text not null, "
                    + ProductsMetaData.PRODUCT_HOUR_KEY + " text not null, "
                    + ProductsMetaData.PRODUCT_DAY_KEY + " integer not null,"
    				+ ProductsMetaData.PRODUCT_MONTH_KEY + " integer not null);";

    private class DbHelper extends SQLiteOpenHelper { //classe che ci aiuta nella creazione del db

            public DbHelper(Context context, String name, CursorFactory factory,int version) {
                    super(context, name, factory, version);
            }

            @Override
            public void onCreate(SQLiteDatabase _db) { //solo quando il db viene creato, creiamo la tabella
                    _db.execSQL(PRODUCTS_TABLE_CREATE);
            }

            @Override
            public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
                    //qui mettiamo eventuali modifiche al db, se nella nostra nuova versione della app, il db cambia numero di versione

            }

    }
            

}