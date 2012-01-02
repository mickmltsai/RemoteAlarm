package tw.ntu.edu.SMSTest;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
	String realMsg,date,time;
	@Override
	public void onReceive(Context arg0, Intent data) {
        Bundle bundle = data.getExtras();  
        realMsg=bundle.getString("realMsg");
        date=bundle.getString("date");
        time=bundle.getString("time");
		Toast.makeText(arg0, realMsg, Toast.LENGTH_LONG).show();
		Intent alaramIntent = new Intent(arg0, AlarmLaunch.class); 
        alaramIntent.putExtras(bundle);
        alaramIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
        arg0.startActivity(alaramIntent); 
        
        

	}


}