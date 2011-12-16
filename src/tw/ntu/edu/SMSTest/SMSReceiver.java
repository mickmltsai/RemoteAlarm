package tw.ntu.edu.SMSTest;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.sax.StartElementListener;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {
	private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
	private static final Uri SMS_INBOX = Uri.parse("content://sms/inbox");
	String delete_phoneNumber="";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		//要刪除的聯絡人
		
		
		//收到簡訊後要做的事
		if(intent != null && intent.getAction() != null && (ACTION.compareToIgnoreCase(intent.getAction())==0)){
			//Log.d("Archer","onReceive");
			//從intent中取得pdu的資料
			Object[] pduData = (Object[]) intent.getExtras().get("pdus");
			
			//依照pduData的陣列大小來新增一個簡訊的陣列
			SmsMessage[] smsArray = new SmsMessage[pduData.length];
			
			//將資料填入簡訊陣列並且列出內容
			for(int i=0;i<pduData.length;i++){
					smsArray[i] = SmsMessage.createFromPdu((byte[]) pduData[i]);
					String toastMessage = String.format("收到一則簡訊 #%d From：%s \n內容：%s",i,smsArray[i].getOriginatingAddress(),smsArray[i].getMessageBody());
					Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show();
					
					if(smsArray[i].getMessageBody().equals("fuck bitch")){
						delete_phoneNumber = smsArray[i].getOriginatingAddress();
					}
			}
			
			
			//context.startActivity(new Intent(context,SMSTestActivity.class));
			//判斷是否刪除簡訊
			//若是直接刪會刪不到東西，因為是剛剛才創的
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Cursor c = context.getContentResolver().query(SMS_INBOX, null, null, null, null);
		
		 	
	        
	        if(c.moveToFirst()) {  
	            int bodyIdx = c.getColumnIndex("body");  //"address", "person", "body"
	            int personIdx = c.getColumnIndex("person");
	            int addressIdx = c.getColumnIndex("address");
	            
	            do {
	            	if(c.getString(addressIdx).equals(delete_phoneNumber)){
       		
	            		try{
	            			Log.d("Archer",c.getString(addressIdx));
	            		
	                        // Delete the SMS
	            			String pid = c.getString(0); // Get id;
		    	            String uri = "content://sms/" + pid;
		    	            context.getContentResolver().delete(Uri.parse(uri),null, null);
	                    } catch (Exception e) {
	                    }
	            		
	            		
	            	}
	            } while(c.moveToNext());
	            
	            
	            
	        
	            
	        }
			
			
			
			
			
			
		}

	}

	
	
	
}
