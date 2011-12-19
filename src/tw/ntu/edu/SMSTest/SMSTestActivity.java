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

	ArrayAdapter<String> MessageArrayAdapter;
	ListView SMSlistView;
	ArrayList<String> SMS_arraylist;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);


	}

}