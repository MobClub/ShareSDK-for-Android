/*
 * Offical Website:http://www.mob.com
 * Support QQ: 4006852216
 * Offical Wechat Account:ShareSDK   (We will inform you our updated news at the first time by Wechat, if we release a new version. If you get any problem, you can also contact us with Wechat, we will reply you within 24 hours.)
 *
 * Copyright (c) 2013 mob.com. All rights reserved.
 */

package cn.sharesdk.onekeyshare.theme.classic;

import android.content.res.Configuration;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

import cn.sharesdk.onekeyshare.PlatformListFakeActivity;

import static cn.sharesdk.framework.utils.R.getBitmapRes;
import static cn.sharesdk.framework.utils.R.getStringRes;

public class PlatformListPage extends PlatformListFakeActivity implements View.OnClickListener {
	// page container
	private FrameLayout flPage;
	// gridview of platform list
	private PlatformGridView grid;
	// cancel button
	private Button btnCancel;
	// sliding up animation
	private Animation animShow;
	// sliding down animation
	private Animation animHide;
	private boolean finishing;


	public void onCreate() {
		super.onCreate();

		finishing = false;
		initPageView();
		initAnim();
		activity.setContentView(flPage);

		// set the data for platform gridview
		grid.setData(shareParamsMap, silent);
		grid.setHiddenPlatforms(hiddenPlatforms);
		grid.setCustomerLogos(customerLogos);
		grid.setParent(this);
		btnCancel.setOnClickListener(this);

		// display gridviews
		flPage.clearAnimation();
		flPage.startAnimation(animShow);
	}

	private void initPageView() {
		flPage = new FrameLayout(getContext());
		flPage.setOnClickListener(this);

		// container of the platform gridview
		LinearLayout llPage = new LinearLayout(getContext()) {
			public boolean onTouchEvent(MotionEvent event) {
				return true;
			}
		};
		llPage.setOrientation(LinearLayout.VERTICAL);
		int resId = getBitmapRes(getContext(), "share_vp_back");
		if (resId > 0) {
			llPage.setBackgroundResource(resId);
		}
		FrameLayout.LayoutParams lpLl = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
		lpLl.gravity = Gravity.BOTTOM;
		llPage.setLayoutParams(lpLl);
		flPage.addView(llPage);

		// gridview
		grid = new PlatformGridView(getContext());
		grid.setEditPageBackground(getBackgroundView());
		LinearLayout.LayoutParams lpWg = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		grid.setLayoutParams(lpWg);
		llPage.addView(grid);

		// cancel button
		btnCancel = new Button(getContext());
		btnCancel.setTextColor(0xffffffff);
		btnCancel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
		resId = getStringRes(getContext(), "cancel");
		if (resId > 0) {
			btnCancel.setText(resId);
		}
		btnCancel.setPadding(0, 0, 0, cn.sharesdk.framework.utils.R.dipToPx(getContext(), 5));
		resId = getBitmapRes(getContext(), "btn_cancel_back");
		if (resId > 0) {
			btnCancel.setBackgroundResource(resId);
		}
		LinearLayout.LayoutParams lpBtn = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, cn.sharesdk.framework.utils.R.dipToPx(getContext(), 45));
		int dp_10 = cn.sharesdk.framework.utils.R.dipToPx(getContext(), 10);
		lpBtn.setMargins(dp_10, dp_10, dp_10, dp_10);
		btnCancel.setLayoutParams(lpBtn);
		llPage.addView(btnCancel);
	}

	private void initAnim() {
		animShow = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 1,
				Animation.RELATIVE_TO_SELF, 0);
		animShow.setDuration(300);

		animHide = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 1);
		animHide.setDuration(300);
	}

	public void onConfigurationChanged(Configuration newConfig) {
		if (grid != null) {
			grid.onConfigurationChanged();
		}
	}

	public boolean onFinish() {
		if (finishing) {
			return super.onFinish();
		}

		if (animHide == null) {
			finishing = true;
			return false;
		}

		finishing = true;
		animHide.setAnimationListener(new Animation.AnimationListener() {
			public void onAnimationStart(Animation animation) {

			}

			public void onAnimationRepeat(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				flPage.setVisibility(View.GONE);
				finish();
			}
		});
		flPage.clearAnimation();
		flPage.startAnimation(animHide);
		//中断finish操作
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v.equals(flPage) || v.equals(btnCancel)) {
			setCanceled(true);
			finish();
		}
	}

	public void onPlatformIconClick(View v, ArrayList<Object> platforms) {
		onShareButtonClick(v, platforms);
	}
}
