package tw.ntu.edu.SMSTest;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class SMSTestActivity extends Activity {
	/** Called when the activity is first created. */

	private final String tag = getClass().getName();// For Log usage

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
	private TextView textCount;
	private int textLeftNumber;
	private CheckBox postFbBox;
	private Boolean postFB;

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

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		setContentView(R.layout.main);
		findviews();
		backToDefault();

	}

	private void findviews() {
		postFbBox = (CheckBox) findViewById(R.id.checkPoFb);
		textCount = (TextView) findViewById(R.id.count);
		reset = (Button) findViewById(R.id.bt_reset);
		reset.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				backToDefault();

			}
		});

		phoneNumber = (EditText) findViewById(R.id.et_number);
		smsContent = (EditText) findViewById(R.id.et_title);
		smsContent.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				textLeftNumber = 51 - smsContent.length();
				textCount.setText(String.valueOf(textLeftNumber));
				if (textLeftNumber <= 0) {
					Toast.makeText(SMSTestActivity.this, "字數已滿！", Toast.LENGTH_SHORT).show();
				}
				Log.e(tag, "" + textLeftNumber);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		// smsContent.set
		send = (Button) findViewById(R.id.bt_send);
		send.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				String strDestAddress = phoneNumber.getText().toString();
				String strMessage = smsContent.getText().toString();
				postFB = postFbBox.isChecked();

				SmsManager smsManager = SmsManager.getDefault();

				// PendingIntent mPI = PendingIntent.getBroadcast(SMSTestActivity.this, 0, new Intent(), 0);
				try {

					if (phoneNumber.getText().toString().equals("")) {
						Toast.makeText(SMSTestActivity.this, "請輸入號碼！", Toast.LENGTH_SHORT).show();
					} else if (dateEditText.getText().toString().equals("")) {
						Toast.makeText(SMSTestActivity.this, "請設定日期！", Toast.LENGTH_SHORT).show();
					} else if (timeEditText.getText().toString().equals("")) {
						Toast.makeText(SMSTestActivity.this, "請設定時間！", Toast.LENGTH_SHORT).show();
					} else {

						if (postFB) {
							strMessage = strMessage + "@" + dateEditText.getText().toString() + "@" + timeEditText.getText().toString() + "@" + "T";
						} else {
							strMessage = strMessage + "@" + dateEditText.getText().toString() + "@" + timeEditText.getText().toString() + "@" + "F";
						}

						smsManager.sendTextMessage(strDestAddress, null, strMessage, null, null);

						Intent intent = new Intent();
						intent.setClass(SMSTestActivity.this, AfterSendViewActivity.class);
						startActivity(intent);
						finish();

					}

				} catch (Exception e) {
					if (e.toString().equals("java.lang.IllegalArgumentException: Invalid destinationAddress")) {
						Log.e(tag, e.toString());
					} else if (e.toString().equals("java.lang.IllegalArgumentException: Invalid message body")) {
						Log.e(tag, e.toString());
					} else {
						Log.e(tag, e.toString());
					}

				}

			}
		});

		timeEditText = (EditText) findViewById(R.id.et_time);
		timeEditText.setFocusableInTouchMode(false);
		dateEditText = (EditText) findViewById(R.id.et_date);
		dateEditText.setFocusableInTouchMode(false);

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
		phoneNumber.setText("");
		smsContent.setText("");
		timeEditText.setText("");
		dateEditText.setText("");
		textCount.setText("51");
		textLeftNumber = Integer.valueOf(textCount.getText().toString());
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