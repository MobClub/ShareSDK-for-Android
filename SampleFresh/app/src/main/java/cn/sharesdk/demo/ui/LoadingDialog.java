package cn.sharesdk.demo.ui;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.NonNull;
import cn.sharesdk.demo.R;
import cn.sharesdk.demo.eventbus.MessageWrap;

public class LoadingDialog extends Dialog {

	public LoadingDialog(@NonNull Context context, int style) {
		super(context, style);
		setContentView(R.layout.loading_layout);
		Window window = getWindow();

		WindowManager.LayoutParams params = window.getAttributes();

		params.gravity = Gravity.CENTER;

		window.setAttributes(params);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void getMessage(MessageWrap msg) {
		if (msg.getMessage() == 1) {
			dismiss();
			Log.d("ShareSDK", " 我收到了关闭弹窗的消息 ");
		}
	}

}
