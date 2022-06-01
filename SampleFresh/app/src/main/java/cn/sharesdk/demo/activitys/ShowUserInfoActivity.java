package cn.sharesdk.demo.activitys;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.sharesdk.demo.R;
import cn.sharesdk.demo.entity.BaseEntity;
import cn.sharesdk.demo.manager.BasePresenter;
import cn.sharesdk.demo.manager.ui.ShowUserInfoPresenter;
import cn.sharesdk.demo.ui.BaseActivity;


public class ShowUserInfoActivity extends BaseActivity implements View.OnClickListener {
	private String userMsg;
	private ClipboardManager clipBoard;
	private android.text.ClipboardManager preClipBoard;

	@Override
	public int getLayoutId() {
		return R.layout.show_userinfo_msg;
	}

	@Override
	public void initView() {
		TextView showTxt = this.findViewById(R.id.showUserInfo);
		TextView copyTxt = this.findViewById(R.id.userCopy);
		copyTxt.setOnClickListener(this);
		showTxt.setText(userMsg);
		if (TextUtils.isEmpty(userMsg)) {
			copyTxt.setBackgroundResource(R.drawable.copy_btn_unclick);
		}
	}

	@Override
	public void initData() {
		Intent intent = getIntent();
		if (intent != null) {
			userMsg = intent.getStringExtra("userInfo");
		}
		clipBoard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		preClipBoard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
	}

	@Override
	protected BasePresenter createPresenter() {
		return new ShowUserInfoPresenter();
	}

	@Override
	public void showLoad() {
		
	}

	@Override
	public void cancelLoad() {
		
	}

	@Override
	public void refreshResult(BaseEntity baseEntity) {
		
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.userCopy) {
			if (!TextUtils.isEmpty(userMsg)) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					clipBoard.setPrimaryClip(ClipData.newPlainText(null, userMsg));
				} else {
					preClipBoard.setText(userMsg);
				}
				Toast.makeText(this, getString(R.string.copy_pasted_on), Toast.LENGTH_SHORT).show();
			}
		}
	}
}
