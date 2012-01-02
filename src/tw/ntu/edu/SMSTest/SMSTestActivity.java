package tw.ntu.edu.SMSTest;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;

public class SMSTestActivity extends Activity {
	/** Called when the activity is first created. */

	private static final int DATE_PICKER_DIALOG = 0;
	private static final int TIME_PICKER_DIALOG = 1;

	private EditText dateEditText;
	private EditText timeEditText;
	private EditText phoneNumber;
	private EditText smsContent;
	private Button send;
	private Button reset;

	private int mYear;
	private int mMonth;
	private int mDay;
	private int mHour;
	private int mMin;
	private Boolean postFb;

	private static final Uri SMS_INBOX = Uri.parse("content://sms/inbox");

	ArrayAdapter<String> MessageArrayAdapter;
	ListView SMSlistView;
	ArrayList<String> SMS_arraylist;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final Calendar c = Calendar.getInstance();

		mMonth = c.get(Calendar.MONTH);
		mYear = c.get(Calendar.YEAR);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMin = c.get(Calendar.MINUTE);

		setContentView(R.layout.main);
		findviews();

	}

	private void findviews() {
		reset = (Button) findViewById(R.id.bt_reset);
		reset.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				backToDefault();
				
			}
		});

		phoneNumber = (EditText) findViewById(R.id.et_number);
		smsContent = (EditText) findViewById(R.id.et_title);
		send = (Button) findViewById(R.id.bt_send);
		send.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String strDestAddress = phoneNumber.getText().toString();
				String strMessage = smsContent.getText().toString();
				SmsManager smsManager = SmsManager.getDefault();

				// PendingIntent mPI = PendingIntent.getBroadcast(SMSTestActivity.this, 0, new Intent(), 0);

				smsManager.sendTextMessage(strDestAddress, null, strMessage, null, null);

			}
		});

		timeEditText = (EditText) findViewById(R.id.et_time);
		dateEditText = (EditText) findViewById(R.id.et_date);

		dateEditText.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				// return false;
				showDialog(DATE_PICKER_DIALOG);
				return true;
			}
		});
		timeEditText.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// return false;
				showDialog(TIME_PICKER_DIALOG);
				return true;
			}
		});
	}

	private void backToDefault() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		switch (id) {
		case DATE_PICKER_DIALOG:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
		case TIME_PICKER_DIALOG:
			return new TimePickerDialog(this, timeSetListener, mHour, mMin, false);
		}

		return null;
	}

	private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMin = minute;
			timeEditText.setText(hourOfDay + ":" + minute);

		}
	};

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear + 1;// Don't know why need to plus 1
			mDay = dayOfMonth;

			dateEditText.setText(mYear + "/" + mMonth + "/" + mDay);
		}
	};

}