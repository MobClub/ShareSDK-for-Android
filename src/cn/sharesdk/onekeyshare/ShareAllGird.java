/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 * 
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.onekeyshare;

import cn.sharesdk.framework.AbstractWeibo;
import cn.sharesdk.onekeyshare.res.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * 快捷分享入口，也是平台宫格列表的UI外壳
 * <p>
 * 下面是启动快捷分享的一个例子，例子里面包含的字段是分享所必需的字段：<br>
 * <table border="1"><tr><td>
 * Intent i = new Intent(v.getContext(), ShareAllGird.class);<br>
 * // 分享内容的文本<br>
 * i.putExtra("text", "share at: " + System.currentTimeMillis());<br>
 * // 分享内容的本地图片路径（为null或文件不存在表示不分享图片 <br>
 * i.putExtra("image", Environment.getExternalStorageDirectory () + "/ssdk.png");<br>
 * startActivity(i);
 * </td></tr></table>
 * <p>
 * 不过请注意，这样子进入分享页面以后，部分平台可能无法很好地工作，比方说
 *QQ空间不使用官方推荐的“add_share”接口，而是用需要申请更多权限的
 *“add_topic”接口，以及印象笔记无法设置笔记标题等等。
 * <p>
 * 下面例子则展示当前ShareAllGird支持的完整参数列表：<br>
 * <table border="1"><tr><td>
 * Intent i = new Intent(v.getContext(), ShareAllGird.class);<br>
 * // 分享时Notification的图标<br>
 * i.putExtra("notif_icon", R.drawable.ic_launcher);<br>
 * // 分享时Notification的标题<br>
 * i.putExtra("notif_title", getString(R.string.app_name));<br>
 * <br>
 * // 分享内容的标题（仅部分平台需要此字段）<br>
 * i.putExtra("title", "Share SDK Demo");<br>
 * // 分享内容的文本<br>
 * i.putExtra("text", "share at: " + System.currentTimeMillis());<br>
 * // 分享内容的本地图片路径（为null或文件不存在表示不分享图片）<br>
 * i.putExtra("image", Environment.getExternalStorageDirectory () + "/ssdk.png");<br>
 * // 分享内容的网络图片地址（仅部分平台需要此字段）<br>
 * i.putExtra("image_url", "http://sharesdk.cn/Public/Frontend/images/logo.png");<br>
 * // 分享的来源网站名称（仅部分平台需要此字段）<br>
 * i.putExtra("site", "Share SDK");<br>
 * // 分享的来源网站对应的网站地址url（仅部分平台需要此字段）<br>
 * i.putExtra("siteUrl", "http://sharesdk.cn");<br>
 * <br>
 * // 设置是否跳转内容编辑页面，true表示不跳转，不传递表示false（跳转）<br>
 * i.putExtra("silent", false);<br>
 * startActivity(i);
 * </td></tr></table>
 * <p>
 * 示例代码中的notif_icon和notif_title用于分享时在状态栏上的提示；image_url、
 *site和siteUrl是QQ空间“add_share”接口的参数；title是QQ空间和印象笔记平台
 *的更新标题；silent用于告知ShareAllGird是否使用“直接分享”，还是点击平台
 *图标以后进入{@link SharePage}编辑后才分享。
 */
public class ShareAllGird extends Activity implements OnClickListener {
	private FrameLayout flPage; // 页面
	private WeiboGridView grid; // 宫格列表
	private Button btnCancel; // 取消按钮
	private Animation animShow; // 滑上来的动画
	private Animation animHide; // 滑下去的动画
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initPageView();
		initAnim();
		setContentView(flPage);
		
		// 设置宫格列表数据
		grid.setData(getIntent());
		btnCancel.setOnClickListener(this);
		
		// 显示列表
		AbstractWeibo.postStatisticsEvent(this, null, 1001);
		flPage.clearAnimation();
		flPage.startAnimation(animShow);
	}
	
	private void initPageView() {
		flPage = new FrameLayout(this);
		
		// 宫格列表的容器，为了“下对齐”，在外部包含了一个FrameLayout
		LinearLayout llPage = new LinearLayout(this);
		llPage.setOrientation(LinearLayout.VERTICAL);
		llPage.setBackgroundDrawable(R.getDrawable(this, "share_vp_back"));
		FrameLayout.LayoutParams lpLl = new FrameLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		lpLl.gravity = Gravity.BOTTOM;
		llPage.setLayoutParams(lpLl);
		flPage.addView(llPage);
		
		// 宫格列表
		grid = new WeiboGridView(this);
		LinearLayout.LayoutParams lpWg = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		int dp_10 = R.dipToPx(this, 10);
		lpWg.setMargins(0, dp_10, 0, 0);
		grid.setLayoutParams(lpWg);
		llPage.addView(grid);
		
		// 取消按钮
		btnCancel = new Button(this);
		btnCancel.setTextColor(0xffffffff);
		btnCancel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
		btnCancel.setText(R.getString(this, "cancel"));
		btnCancel.setPadding(0, 0, 0, R.dipToPx(this, 5));
		btnCancel.setBackgroundDrawable(R.getDrawable(this, "btn_cancel_back"));
		LinearLayout.LayoutParams lpBtn = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, R.dipToPx(this, 45));
		lpBtn.setMargins(dp_10, 0, dp_10, dp_10 * 2);
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
	
	public void onClick(View v) {
		if (v.equals(btnCancel)) {
			finish();
		}
	}
	
	public void finish() {
		animHide.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) {
				
			}
			
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			public void onAnimationEnd(Animation animation) {
				ShareAllGird.super.finish();
			}
		});
		flPage.clearAnimation();
		flPage.startAnimation(animHide);
	}
	
}
