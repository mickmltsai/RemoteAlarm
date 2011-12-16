package tw.ntu.edu.SMSTest;

import java.util.ArrayList;


import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SMSTestActivity extends Activity {
    /** Called when the activity is first created. */
    
	private static final Uri SMS_INBOX = Uri.parse("content://sms/inbox");
	
	ArrayAdapter<String> MessageArrayAdapter ;
	ListView SMSlistView;
	ArrayList<String> SMS_arraylist;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        findAllView();
        
        ReadSMS();
        
        
        
       
        
    }
    
    
	private void findAllView(){
		SMSlistView = (ListView) findViewById(R.id.smsMessageList);
	}


	//setAdapt
	private void setAdapter(ArrayList<String> SMS_arraylist){
		MessageArrayAdapter = new ArrayAdapter<String>(this, R.layout.listitem, R.id.Item, SMS_arraylist);
		SMSlistView.setAdapter(MessageArrayAdapter);
	}

	private void ReadSMS(){
		 Cursor c = getContentResolver().query(SMS_INBOX, null, null, null, null);
		
		 	SMS_arraylist = new ArrayList<String>();
	        
	        if(c.moveToFirst()) {  
	            int bodyIdx = c.getColumnIndex("body");  //"address", "person", "body"
	            int personIdx = c.getColumnIndex("person");
	            int addressIdx = c.getColumnIndex("address");
	            
	            do {
	        
	            		String body = "body >>"+c.getString(bodyIdx)+"\naddress >>"+c.getString(addressIdx);
		                SMS_arraylist.add(body);
	                
	            } while(c.moveToNext());
	            
	            
	            
	            setAdapter(SMS_arraylist);
	            
	        }
	}



}