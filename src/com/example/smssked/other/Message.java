package com.example.smssked.other;

public class Message {
	
	
	public String hour;
	public String minute;
	public String message_text;
	
	public String number;
	public int day;
	public int month;

	public Message(String h,String m,String sms, String n, int d, int mon){
		hour=h;
		minute=m;
		message_text=sms;
		number=n;
		day=d;
		month=mon;
	}

	public void print(){
		System.out.println(hour+" "+minute+" "+message_text+" "+number+" "+day+" "+month);
	}
	public String getDest() {
		
		return this.number;
	}

	public String getText() {
		return this.message_text;
	}

	public String getHour() {
		return this.hour;
	}

	public String getMin() {
		return this.minute;
	}

	public int getDay() {
		return this.day;
	}

	public int getMonth() {
		return this.month;
	}
	
	
}
