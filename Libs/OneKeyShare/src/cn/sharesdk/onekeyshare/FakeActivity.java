/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.onekeyshare;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import cn.sharesdk.framework.AbstractAuthorizePage;
import cn.sharesdk.framework.AuthorizeActivity;
import cn.sharesdk.framework.AuthorizeAdapter;
import cn.sharesdk.framework.RegisterView;

/**
 * 为了不多注册两个Activity，快捷分享复用了SDK内置的
 *{@link AuthorizeActivity}，后续的页面，可以通过继
 *承本类，实现GUI功能
 */
public abstract class FakeActivity extends AbstractAuthorizePage {
	private AuthorizeActivity act;
	private Intent intent;

	public FakeActivity(Context context) {
		super(context);
	}

	protected RegisterView getBodyView(Context context) {
		// do nothing
		return null;
	}

	public void onCreate(AuthorizeActivity act, AuthorizeAdapter adapter) {
		this.act = act;
		onCreate(act);
	}

	public Intent getIntent() {
		return act.getIntent();
	}

	protected abstract void onCreate(Context context);

	public void setContentView(int layoutResID) {
		act.setContentView(layoutResID);
	}

	public void setContentView(View view) {
		act.setContentView(view);
	}

	public void setContentView(View view, LayoutParams params) {
		act.setContentView(view, params);
	}

	public Context getContext() {
		return act;
	}

	public void onDestroy() {
		finish();
	}

	public void finish() {
		act.finish();
	}

	public Resources getResources() {
		return act.getResources();
	}

	public void show(Intent intent) {
		this.intent = intent;
		super.show(getClass().getSimpleName(), null);
	}

	protected Intent getIntent(String name) {
		Intent i = super.getIntent(name);
		i.putExtras(intent);
		i.putExtra("name", name);
		intent = null;
		return i;
	}

	public void setResult(int resultCode) {
		act.setResult(resultCode);
	}

	public void setResult(int resultCode, Intent data) {
		act.setResult(resultCode, data);
	}

}
