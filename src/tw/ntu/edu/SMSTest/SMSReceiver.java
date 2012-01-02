package tw.ntu.edu.SMSTest;

import java.util.ArrayList;

import com.facebook.android.Example;

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
	String delete_phoneNumber = "";

	@Override
	public void onReceive(Context context, Intent intent) {
		
		if (intent != null && intent.getAction() != null && (ACTION.compareToIgnoreCase(intent.getAction()) == 0)) {
			
			Object[] pduData = (Object[]) intent.getExtras().get("pdus");
			SmsMessage[] smsArray = new SmsMessage[pduData.length];
			
			Intent it = new Intent(context, Example.class);
			it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(it);

			for (int i = 0; i < pduData.length; i++) {
				smsArray[i] = SmsMessage.createFromPdu((byte[]) pduData[i]);
				//格式 text @2012/1/4@15:16@T
				String msg = smsArray[i].getMessageBody();
				String[] msgParse = msg.split("@");
				boolean isFacebook = false;
				if(msgParse[msgParse.length-1].equals("T")){
					isFacebook = true;
				}
				String time[] = msgParse[msgParse.length-2].split(":"); 
				String date[] = msgParse[msgParse.length-3].split("/"); 
				
				Toast.makeText(context, date[0] + date[1] + date[2], Toast.LENGTH_LONG).show();
				Toast.makeText(context, time[0] + time[1], Toast.LENGTH_LONG).show();
				Toast.makeText(context, ""+isFacebook, Toast.LENGTH_LONG).show();
				
//				context.starActivity();

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
