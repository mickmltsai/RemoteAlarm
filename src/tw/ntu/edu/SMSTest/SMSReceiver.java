package tw.ntu.edu.SMSTest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;


import com.facebook.android.Example;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {
	private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
	private static final Uri SMS_INBOX = Uri.parse("content://sms/inbox");
	String delete_phoneNumber = "";

	@Override
	public void onReceive(Context context, Intent intent) {
		
		if (intent != null && intent.getAction() != null && (ACTION.compareToIgnoreCase(intent.getAction()) == 0)) {
			
			Object[] pduData = (Object[]) intent.getExtras().get("pdus");
			SmsMessage[] smsArray = new SmsMessage[pduData.length];
			
			boolean isFacebook = false;
			String[] time = new String[1];
			String[] date = new String[1];

			for (int i = 0; i < pduData.length; i++) {
				smsArray[i] = SmsMessage.createFromPdu((byte[]) pduData[i]);
				//?��? text @2012/1/4@15:16@T
				String msg = smsArray[i].getMessageBody();
				String realMsg="";
				
				
				String[] msgParse = msg.split("@");
				for(int j=0;j<msgParse.length-3;j++)
					realMsg=realMsg+msgParse[j];
				
				if(msgParse.length >= 4){
					if(!msgParse[msgParse.length-1].matches("[TF]") ||
							!msgParse[msgParse.length-2].matches("[0-9]{1,2}:[0-9]{1,2}") ||
					   !msgParse[msgParse.length-3].matches("[0-9]{4}/[0-9]{1,2}/[0-9]{1,2}")){
						continue;
					}
					if(msgParse[msgParse.length-1].equals("T")){
						isFacebook = true;
					}
					time = msgParse[msgParse.length-2].split(":"); 
					date = msgParse[msgParse.length-3].split("/");
					
					Toast.makeText(context, date[0] + date[1] + date[2], Toast.LENGTH_LONG).show();
					Toast.makeText(context, time[0] + time[1], Toast.LENGTH_LONG).show();
					Toast.makeText(context, ""+isFacebook, Toast.LENGTH_LONG).show();
				}
				
				if(isFacebook){
					Bundle b = new Bundle();
					String frontMsg = "";
					for(int j=0; j<msgParse.length-3; j++){
						frontMsg += msgParse[j] + "@";
					}
					frontMsg = frontMsg.substring(0, frontMsg.length()-1);
					b.putString("msg", frontMsg);
	
					Intent it = new Intent(context, Example.class);
					it.putExtras(b);
					it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(it);
				}

				///////////LATA work
				Calendar calendar = Calendar.getInstance();
				
				calendar.set(Calendar.YEAR,Integer.parseInt(date[0]) );
				calendar.set(Calendar.MONTH,Integer.parseInt(date[1])-1 );
				calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(date[2]) );
		    	calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(time[0]));
		    	calendar.set(Calendar.MINUTE,Integer.parseInt(time[1]));
		    	//�?��??�毫�?��置为0  
	            calendar.set(Calendar.SECOND, 0);  
	            calendar.set(Calendar.MILLISECOND, 0);
	            Toast.makeText(context,""+calendar.getTime(), Toast.LENGTH_LONG).show(); 
	            Intent alarmIntent = new Intent(context,AlarmReceiver.class);  
	            Bundle alarmBundle=new Bundle();
	            alarmBundle.putString("realMsg",realMsg);
	            alarmBundle.putString("date", msgParse[msgParse.length-3]);
	            alarmBundle.putString("time", msgParse[msgParse.length-2]);
                alarmIntent.putExtras(alarmBundle);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context,msg.length(), alarmIntent, 0);  
		        //?��??��?管�??? 
		        AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);  
		        //设置?��?  
		        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);  
//		        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 10*1000, pendingIntent);  
		        

		        ///////////LATA work

				
				if (smsArray[i].getMessageBody().equals("fuck bitch")) {
					delete_phoneNumber = smsArray[i].getOriginatingAddress();
				}
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Cursor c = context.getContentResolver().query(SMS_INBOX, null, null, null, null);

			if (c.moveToFirst()) {
				int bodyIdx = c.getColumnIndex("body"); // "address", "person",
														// "body"
				int personIdx = c.getColumnIndex("person");
				int addressIdx = c.getColumnIndex("address");

				do {
					if (c.getString(addressIdx).equals(delete_phoneNumber)) {

						try {
							Log.d("Archer", c.getString(addressIdx));

							// Delete the SMS
							String pid = c.getString(0); // Get id;
							String uri = "content://sms/" + pid;
							context.getContentResolver().delete(Uri.parse(uri), null, null);
						} catch (Exception e) {
						}

					}
				} while (c.moveToNext());

			}

		}

	}

}
