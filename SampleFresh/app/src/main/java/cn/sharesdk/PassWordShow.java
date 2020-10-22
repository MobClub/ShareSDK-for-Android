package cn.sharesdk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import cn.sharesdk.demo.R;
import cn.sharesdk.demo.ShareMobLinkActivity;

public class PassWordShow extends Activity implements View.OnClickListener {

	private Button checkDetails;
	private Button skip;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_show);
		initView();
		Log.e("QQQ", " PassWordShow onCreate");
	}

	private void initView() {
		skip = findViewById(R.id.skip_button);
		skip.setOnClickListener(this);
		checkDetails = findViewById(R.id.check_details);
		checkDetails.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.skip_button: {
				finish();
				break;
			}
			case R.id.check_details: {
				Intent intent = new Intent();
				intent.setClass(this, ShareMobLinkActivity.class);
				startActivity(intent);
				finish();
				break;
			}
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.e("QQQ", " PassWordShow onStart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.e("QQQ", " PassWordShow onResume");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.e("QQQ", " PassWordShow onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e("QQQ", " PassWordShow onDestroy");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.e("QQQ", " PassWordShow onPause");
	}

}
