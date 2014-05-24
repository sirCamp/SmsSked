package com.example.smssked;

import com.example.smssked.model.ItemListActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MenuListActivity extends ListActivity {

  private ItemListActivity menu;
  private ArrayAdapter<String> adapter;
	
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
   
    menu = new ItemListActivity();
    adapter = new ArrayAdapter<String>(this,R.layout.rowlayout, R.id.label, menu.getItems());
    setListAdapter(adapter);
  }

  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {
    String item = (String) getListAdapter().getItem(position);
    Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
  }
} 