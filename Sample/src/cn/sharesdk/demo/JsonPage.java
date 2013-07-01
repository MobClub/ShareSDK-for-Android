/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.demo;

import cn.sharesdk.framework.TitleLayout;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/** Json数据显示页面 */
public class JsonPage extends Activity implements OnClickListener {
	public static String bigData;
	private TitleLayout llTitle;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String title = getIntent().getStringExtra("title");

		setContentView(R.layout.page_show_user_info);
		llTitle = (TitleLayout) findViewById(R.id.llTitle);
		llTitle.getBtnBack().setOnClickListener(this);
		if (TextUtils.isEmpty(title)) {
			llTitle.getTvTitle().setText(R.string.app_name);
		}
		else {
			llTitle.getTvTitle().setText(title);
		}

		final TextView tvJson = (TextView) findViewById(R.id.tvJson);
		if (bigData == null) {
			tvJson.setText(getIntent().getStringExtra("data"));
		}
		else {
			tvJson.postDelayed(new Runnable() {
				public void run() {
					tvJson.setText(bigData);
					bigData = null;
				}
			}, 333);
		}
	}

	public void onClick(View v) {
		if (v.equals(llTitle.getBtnBack())) {
			finish();
		}
	}

}
