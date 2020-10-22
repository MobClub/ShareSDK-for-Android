package cn.sharesdk.demo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import cn.sharesdk.demo.R;
import cn.sharesdk.demo.eventbus.MessageWrap;

public class LoadingActivity extends Activity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading_layout);
		Log.d("WWW", "LoadingActivity onCreate");
	}

	@Override
	protected void onStart() {
		super.onStart();
		EventBus.getDefault().register(this);
		Log.d("WWW", "LoadingActivity onStart");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d("WWW", "LoadingActivity onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d("WWW", "LoadingActivity onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d("WWW", "LoadingActivity onDestroy");
	}

}
