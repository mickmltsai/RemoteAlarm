package tw.ntu.edu.SMSTest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AfterSendViewActivity extends Activity {
	private Button exit;
	private Button sendAgain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aftersendview);

		findViews();
	}

	private void findViews() {
		exit = (Button) findViewById(R.id.bt_exit);
		exit.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		sendAgain = (Button) findViewById(R.id.bt_backSend);
		sendAgain.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(AfterSendViewActivity.this, SMSTestActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
}
