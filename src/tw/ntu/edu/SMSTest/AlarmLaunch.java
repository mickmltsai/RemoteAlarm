package tw.ntu.edu.SMSTest;


import android.app.Activity;
import android.app.Service;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;



public class AlarmLaunch extends Activity{
	TextView textView1;
	String realMsg,date,time;
	Button button1;
	MediaPlayer mp;
	Vibrator myVibrator;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Bundle bundle = this.getIntent().getExtras();  
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm);
		realMsg=bundle.getString("realMsg");
		date=bundle.getString("date");
		time=bundle.getString("time");
		textView1=(TextView)findViewById(R.id.textView1);
		button1=(Button)findViewById(R.id.button1);
		textView1.setText("約會通知：\n"+realMsg);
		mp = MediaPlayer.create(this, R.raw.ring);

		// mp.prepare();
		mp.setVolume((float) (1), (float) (1));
		mp.setLooping(true);
		mp.start();
		myVibrator = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);
		myVibrator.vibrate(new long[] { 1000, 1000 }, 0);
		 setListeners();
		
		
		
	}
	private void setListeners() {
    	button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	@Override
	public void onPause() {
		super.onPause();
		mp.release();
			myVibrator.cancel();
	}

	 @Override
	 public void onBackPressed() {

	 return;
	 }
	@Override
	public void onAttachedToWindow()

	{ // TODO Auto-generated method stub

		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);

		super.onAttachedToWindow();

	}
	
}
