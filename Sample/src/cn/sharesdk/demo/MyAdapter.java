//#if def{lang} == cn
/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 * 
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */
//#elif def{lang} == en
/*
 * Offical Website:http://www.mob.com
 * Support QQ: 4006852216
 * Offical Wechat Account:ShareSDK   (We will inform you our updated news at the first time by Wechat, if we release a new version. 
 * If you get any problem, you can also contact us with Wechat, we will reply you within 24 hours.)
 * 
 * Copyright (c) 2013 mob.com. All rights reserved.
 */
//#endif

package cn.sharesdk.demo;

import java.util.HashMap;

import com.mob.tools.utils.ResHelper;

import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import cn.sharesdk.framework.authorize.AuthorizeAdapter;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

//#if def{lang} == cn
/** 
 * 一个用于演示{@link AuthorizeAdapter}的例子。
 * <p>
 * 本demo将在授权页面底部显示一个“关注官方微博”的提示框，
 *用户可以在授权期间对这个提示进行控制，选择关注或者不关
 *注，如果用户最后确定关注此平台官方微博，会在授权结束以
 *后执行关注的方法。
 */
//#elif def{lang} == en
/** 
 * a example of {@link AuthorizeAdapter}
 * <p>
 * the demo will display a "follow our official weibo" checkbox 
 * at the bottom of the auth-page. User can make their decision
 * during authorizing, if he follows our weibo, the following
 * operation will be triggered after authorizing.
 */
//#endif
public class MyAdapter extends AuthorizeAdapter implements OnClickListener, 
		PlatformActionListener {
	private CheckedTextView ctvFollow;
	private PlatformActionListener backListener;
	private boolean stopFinish;
	
	public void onCreate() {
		//#if def{lang} == cn
		// 隐藏标题栏右部的ShareSDK Logo
		//#elif def{lang} == en
		// hide the logo of ShareSDK at the right of the title bar
		//#endif
		//hideShareSDKLogo();
		
//		TitleLayout llTitle = getTitleLayout();
//		llTitle.getTvTitle().setText("xxxx");
		
		String platName = getPlatformName();
		if ("SinaWeibo".equals(platName)
				|| "TencentWeibo".equals(platName)) {
			initUi(platName);
			interceptPlatformActionListener(platName);
			return;
		} 
		
		//#if def{lang} == cn
		// 使弹出动画失效，只能在onCreate中调用，否则无法起作用
		//#elif def{lang} == en
		// disable the pop-up animation
		//#endif
		if ("KaiXin".equals(platName)) {
			disablePopUpAnimation();
		}
		
		//#if def{lang} == cn
		// 下面的代码演示如何设置自定义的授权页面打开动画
		//#elif def{lang} == en
		// codes to show how to customize the start up animation of the auth-page
		//#endif
		if ("Douban".equals(platName)) {
			stopFinish = true;
			disablePopUpAnimation();
			View rv = (View) getBodyView().getParent();
			TranslateAnimation ta = new TranslateAnimation(
					Animation.RELATIVE_TO_SELF, -1, 
					Animation.RELATIVE_TO_SELF, 0, 
					Animation.RELATIVE_TO_SELF, 0, 
					Animation.RELATIVE_TO_SELF, 0);
			ta.setDuration(500);
			rv.setAnimation(ta);
		}
	}
	
	private void initUi(String platName) {
		ctvFollow = new CheckedTextView(getActivity());
		try {
			ctvFollow.setBackgroundResource(R.drawable.auth_follow_bg);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		ctvFollow.setChecked(true);
		int dp10 = ResHelper.dipToPx(getActivity(), 10);
		ctvFollow.setCompoundDrawablePadding(dp10);
		ctvFollow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.auth_cb, 0, 0, 0);
		ctvFollow.setGravity(Gravity.CENTER_VERTICAL);
		ctvFollow.setPadding(dp10, dp10, dp10, dp10);
		ctvFollow.setText(R.string.sm_item_fl_weibo);
		if (platName.equals("TencentWeibo")) {
			ctvFollow.setText(R.string.sm_item_fl_tc);
		}
		ctvFollow.setTextColor(0xff909090);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
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
	
	private void interceptPlatformActionListener(String platName) {
		Platform plat = ShareSDK.getPlatform(platName);
		//#if def{lang} == cn
		// 备份此前设置的事件监听器
		//#elif def{lang} == en
		// backup action callback
		//#endif
		backListener = plat.getPlatformActionListener();
		//#if def{lang} == cn
		// 设置新的监听器，实现事件拦截
		//#elif def{lang} == en
		// set a new callback to intercept the authorization event
		//#endif
		plat.setPlatformActionListener(this);
	}

	public void onError(Platform plat, int action, Throwable t) {
		if (action == Platform.ACTION_AUTHORIZING) {
			//#if def{lang} == cn
			// 授权时即发生错误
			//#elif def{lang} == en
			// failed when authorizing
			//#endif
			plat.setPlatformActionListener(backListener);
			if (backListener != null) {
				backListener.onError(plat, action, t);
			}
		} else {
			//#if def{lang} == cn
			// 关注时发生错误
			//#elif def{lang} == en
			// failed when following
			//#endif
			plat.setPlatformActionListener(backListener);
			if (backListener != null) {
				backListener.onComplete(plat, Platform.ACTION_AUTHORIZING, null);
			}
		}
	}
	
	public void onComplete(Platform plat, int action,
			HashMap<String, Object> res) {
		if (action == Platform.ACTION_FOLLOWING_USER) {
			//#if def{lang} == cn
			// 当作授权以后不做任何事情
			//#elif def{lang} == en
			// callback as authorizing
			//#endif
			plat.setPlatformActionListener(backListener);
			if (backListener != null) {
				backListener.onComplete(plat, Platform.ACTION_AUTHORIZING, null);
			}
		} else if (ctvFollow.isChecked()) {
			//#if def{lang} == cn
			// 授权成功，执行关注
			//#elif def{lang} == en
			// following the official weibo after authorizing
			//#endif
			String account = MainAdapter.SDK_SINAWEIBO_UID;
			if ("TencentWeibo".equals(plat.getName())) {
				account = MainAdapter.SDK_TENCENTWEIBO_UID;
			}
			plat.followFriend(account);
		} else {
			//#if def{lang} == cn
			// 如果没有标记为“授权并关注”则直接返回
			//#elif def{lang} == en
			// return to previous page if user didn't check "follow our official weibo"
			//#endif
			plat.setPlatformActionListener(backListener);
			if (backListener != null) {
				//#if def{lang} == cn
				// 关注成功也只是当作授权成功返回
				//#elif def{lang} == en
				// callback as authorizing
				//#endif
				backListener.onComplete(plat, Platform.ACTION_AUTHORIZING, null);
			}
		}
	}
	
	public void onCancel(Platform plat, int action) {
		plat.setPlatformActionListener(backListener);
		if (action == Platform.ACTION_AUTHORIZING) {
			//#if def{lang} == cn
			// 授权前取消
			//#elif def{lang} == en
			// canceled at authorizing
			//#endif
			if (backListener != null) {
				backListener.onCancel(plat, action);
			}
		} else {
			//#if def{lang} == cn
			// 当作授权以后不做任何事情
			//#elif def{lang} == en
			// dead code
			//#endif
			if (backListener != null) {
				backListener.onComplete(plat, Platform.ACTION_AUTHORIZING, null);
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
			} else {
				ctvFollow.setVisibility(View.VISIBLE);
			}
		}
	}
	
	public boolean onFinish() {
		//#if def{lang} == cn
		// 下面的代码演示如何设置自定义的授权页面退出动画
		//#elif def{lang} == en
		// codes to show how to customize the close animation of the auth-page
		//#endif
		if ("Douban".equals(getPlatformName())) {
			final View rv = (View) getBodyView().getParent();
			rv.clearAnimation();
			
			TranslateAnimation ta = new TranslateAnimation(
					Animation.RELATIVE_TO_SELF, 0, 
					Animation.RELATIVE_TO_SELF, 1, 
					Animation.RELATIVE_TO_SELF, 0, 
					Animation.RELATIVE_TO_SELF, 0);
			ta.setDuration(500);
			ta.setAnimationListener(new AnimationListener() {
				public void onAnimationStart(Animation animation) {
					
				}
				
				public void onAnimationRepeat(Animation animation) {
					
				}
				
				public void onAnimationEnd(Animation animation) {
					stopFinish = false;
					getActivity().finish();
				}
			});
			rv.setAnimation(ta);
		}
		return stopFinish;
	}
	
}
