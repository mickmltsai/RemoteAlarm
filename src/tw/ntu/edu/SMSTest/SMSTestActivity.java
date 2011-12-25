package tw.ntu.edu.SMSTest;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;

public class SMSTestActivity extends Activity {
	/** Called when the activity is first created. */

	private EditText dateEditText;
	private EditText timeEditText;
	private DatePickerDialog date;
	private Builder time;
	private int hour;
	private int min;
	private static final int TIME_PICKER_DIALOG = 0;
	private static final int DATE_PICKER_DIALOG = 1;

	private int mYear;
	private int mMonth;
	private int mDay;

	private static final Uri SMS_INBOX = Uri.parse("content://sms/inbox");

	ArrayAdapter<String> MessageArrayAdapter;
	ListView SMSlistView;
	ArrayList<String> SMS_arraylist;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		setContentView(R.layout.main);
		findviews();

	}

	private void findviews() {
		timeEditText = (EditText) findViewById(R.id.et_time);
		dateEditText = (EditText) findViewById(R.id.et_date);
		timeEditText.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				showDialog(TIME_PICKER_DIALOG);

				// return false;
				return true;
			}
		});

		dateEditText.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				showDialog(DATE_PICKER_DIALOG);

				// return false;
				return true;
			}
		});

	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		switch (id) {
		case TIME_PICKER_DIALOG:
			return new TimePickerDialog(this, timeSetListener, hour, min, false);

		case DATE_PICKER_DIALOG:
			// return new TimePickerDialog(this, timeSetListener, hour, min, false);
			DatePickerDialog dpd= new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);  
            return dpd;  
		}

		return null;
	}

	// @Override
	// protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
	// switch (id) {
	// case TIME_PICKER_DIALOG:
	// Log.d("DEBUG", "get current time!");
	// TimePickerDialog timePicker = (TimePickerDialog) dialog;
	// timePicker.updateTime(Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE));
	// break;

	// case DATE_PICKER_DIALOG:
	// Log.d("DEBUG", "get current time!");
	// // TimePickerDialog timePicker = (TimePickerDialog) dialog;
	// // timePicker.updateTime(Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE));
	// DatePickerDialog datePicker = (DatePickerDialog) dialog;
	// datePicker.updateDate(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
	//
	// break;
	// }
	// }

	TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker v, int h, int m) {
			// 取得user所選擇的時間
			hour = h;
			min = m;
			// 更新time text
			// updateTimeText();
		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_PICKER_DIALOG:
			DatePickerDialog dpd = new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
			return dpd;
		}
		return null;
	}

	// DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
	//
	// @Override
	// public void onDateSet(DatePicker view, int y, int m, int d) {
	// year = y;
	// monthOfYear = m;
	// dayOfMonth = d;
	// }
	// };

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
		}
	};

}