/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.demo;

import java.util.HashMap;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import cn.sharesdk.framework.AbstractWeibo;
import cn.sharesdk.framework.AuthorizeAdapter;
import cn.sharesdk.framework.WeiboActionListener;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.weibo.TencentWeibo;

/**
 * 一个用于演示{@link AuthorizeAdapter}的例子。
 * <p>
 * 本demo将在授权页面底部显示一个“关注官方微博”的提示框，
 *用户可以在授权期间对这个提示进行控制，选择关注或者不关
 *注，如果用户最后确定关注此平台官方微博，会在授权结束以
 *后执行关注的方法。
 */
public class MyAdapter extends AuthorizeAdapter implements OnClickListener, WeiboActionListener {
	private CheckedTextView ctvFollow;
	private WeiboActionListener backListener;

	public void onCreate() {
		//注释掉下面两行代码，可以现实在授权页面时显示应用的logo
//		int count = getTitleLayout().getChildCount();
//		getTitleLayout().getChildAt(count - 1).setVisibility(View.GONE);

		String weiboName = getWeiboName();
		if (SinaWeibo.NAME.equals(weiboName)
				|| TencentWeibo.NAME.equals(weiboName)) {
			initUi(weiboName);
			interceptWeiboActionListener(weiboName);
		} else { // 使弹出动画失效，只能在onCreate中调用，否则无法起作用
			disablePopUpAnimation();
		}
	}

	private void initUi(String weiboName) {
		ctvFollow = new CheckedTextView(getActivity());
		ctvFollow.setBackgroundResource(R.drawable.auth_follow_bg);
		ctvFollow.setChecked(true);
		int dp_10 = cn.sharesdk.framework.res.R.dipToPx(getActivity(), 10);
		ctvFollow.setCompoundDrawablePadding(dp_10);
		ctvFollow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.auth_cb, 0, 0, 0);
		ctvFollow.setGravity(Gravity.CENTER_VERTICAL);
		ctvFollow.setPadding(dp_10, dp_10, dp_10, dp_10);
		ctvFollow.setText(R.string.sm_item_fl_weibo);
		if (weiboName.equals(TencentWeibo.NAME)) {
			ctvFollow.setText(R.string.sm_item_fl_tc);
		}
		ctvFollow.setTextColor(0xff909090);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		ctvFollow.setLayoutParams(lp);
		LinearLayout llBody = (LinearLayout) getBodyView().getChildAt(0);
		llBody.addView(ctvFollow);
		ctvFollow.setOnClickListener(this);

		ctvFollow.measure(0, 0);
		int height = ctvFollow.getMeasuredHeight();
		TranslateAnimation animShow = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0,
				Animation.ABSOLUTE, height,
				Animation.ABSOLUTE, 0);
		animShow.setDuration(1000);
		getWebBody().startAnimation(animShow);
		ctvFollow.startAnimation(animShow);
	}

	private void interceptWeiboActionListener(String weiboName) {
		AbstractWeibo weibo = AbstractWeibo.getWeibo(getActivity(), weiboName);
		// 备份此前设置的事件监听器
		backListener = weibo.getWeiboActionListener();
		// 设置新的监听器，实现事件拦截
		weibo.setWeiboActionListener(this);
	}

	public void onError(AbstractWeibo weibo, int action, Throwable t) {
		if (action == AbstractWeibo.ACTION_AUTHORIZING) { // 授权时即发生错误
			weibo.setWeiboActionListener(backListener);
			if (backListener != null) {
				backListener.onError(weibo, action, t);
			}
		}
		else { // 授权时即发生错误
			weibo.setWeiboActionListener(backListener);
			if (backListener != null) {
				backListener.onComplete(weibo, AbstractWeibo.ACTION_AUTHORIZING, null);
			}
		}
	}

	public void onComplete(AbstractWeibo weibo, int action,
			HashMap<String, Object> res) {
		if (action == AbstractWeibo.ACTION_FOLLOWING_USER) { // 当作授权以后不做任何事情
			weibo.setWeiboActionListener(backListener);
			if (backListener != null) {
				backListener.onComplete(weibo, AbstractWeibo.ACTION_AUTHORIZING, null);
			}
		}
		else if (ctvFollow.isChecked()) { // 授权成功，执行关注
			String account = MainAdapter.SDK_SINAWEIBO_UID;
			if (TencentWeibo.NAME.equals(weibo.getName())) {
				account = MainAdapter.SDK_TENCENTWEIBO_UID;
			}
			weibo.followFriend(account);
		}
		else { // 如果没有标记为“授权并关注”则直接返回
			weibo.setWeiboActionListener(backListener);
			if (backListener != null) {
				backListener.onComplete(weibo, AbstractWeibo.ACTION_AUTHORIZING, null);
			}
		}
	}

	public void onCancel(AbstractWeibo weibo, int action) {
		weibo.setWeiboActionListener(backListener);
		if (action == AbstractWeibo.ACTION_AUTHORIZING) { // 授权前取消
			if (backListener != null) {
				backListener.onCancel(weibo, action);
			}
		}
		else { // 当作授权以后不做任何事情
			if (backListener != null) {
				backListener.onComplete(weibo, AbstractWeibo.ACTION_AUTHORIZING, null);
			}

		}
	}

	public void onClick(View v) {
		CheckedTextView ctv = (CheckedTextView) v;
		ctv.setChecked(!ctv.isChecked());
	}

	public void onResize(int w, int h, int oldw, int oldh) {
		if (ctvFollow != null) {
			if (oldh - h > 100) {
				ctvFollow.setVisibility(View.GONE);
			}
			else {
				ctvFollow.setVisibility(View.VISIBLE);
			}
		}
	}

}
