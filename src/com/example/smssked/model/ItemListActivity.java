package com.example.smssked.model;

public class ItemListActivity {
	
	private String[] items = new String[] { "Vedi messaggi impostati", "Setta messaggio","Vedi mail impostate", "Setta mail" };
	
	public ItemListActivity(){
		
	}
	
	public String[] getItems(){
		return items;
	}
	
	public void setItems(String[] i){
		items = i;
	}
}
