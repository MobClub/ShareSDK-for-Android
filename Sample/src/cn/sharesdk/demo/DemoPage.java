/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.demo;

import java.util.HashMap;
import m.framework.ui.widget.slidingmenu.SlidingMenu;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.TitleLayout;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Share SDK接口演示页面，包括演示使用快捷分享完成图文分享、
 *无页面直接分享、授权、关注和不同平台的分享等等功能。
 */
public class DemoPage extends SlidingMenuPage implements
		OnClickListener, PlatformActionListener {
	private TitleLayout llTitle;

	public DemoPage(SlidingMenu menu) {
		super(menu);
		View pageView = getPage();

		llTitle = (TitleLayout) pageView.findViewById(R.id.llTitle);
		llTitle.getBtnBack().setOnClickListener(this);
		llTitle.getTvTitle().setText(R.string.sm_item_demo);

		pageView.findViewById(R.id.btnShareAllGui).setOnClickListener(this);
		pageView.findViewById(R.id.btnShareAll).setOnClickListener(this);
		pageView.findViewById(R.id.btnFlSw).setOnClickListener(this);
		pageView.findViewById(R.id.btnFlTc).setOnClickListener(this);
		pageView.findViewById(R.id.btnGetToken).setOnClickListener(this);
		pageView.findViewById(R.id.btnVisitWc).setOnClickListener(this);
		pageView.findViewById(R.id.btnGetInfor).setOnClickListener(this);
		pageView.findViewById(R.id.btnGetUserInfor).setOnClickListener(this);

		LinearLayout llList = (LinearLayout) pageView.findViewById(R.id.llList);
		Platform[] platforms = ShareSDK.getPlatformList(menu.getContext());
		LinearLayout line = (LinearLayout) View.inflate(menu.getContext(),
				R.layout.demo_page_item, null);
		llList.addView(line);
		int lineCount = 0;

		for (Platform platform : platforms) {
			if (lineCount >= 2) {
				// 每行两个按钮
				line = (LinearLayout) View.inflate(menu.getContext(),
						R.layout.demo_page_item, null);
				llList.addView(line);
				lineCount = 0;
			}

			String name = platform.getName();
			if ("Wechat".equals(name) || "WechatMoments".equals(name)
					|| "ShortMessage".equals(name) || "Email".equals(name)
					|| "GooglePlus".equals(name) || "QQ".equals(name)
					|| "Pinterest".equals(name)) {
				continue;
			}

			// 处理左边按钮和右边按钮
			int res = lineCount == 0 ? R.id.btnLeft : R.id.btnRight;
			Button btn = (Button) line.findViewById(res);
			int platNameRes = cn.sharesdk.framework.utils.R.getStringRes(
					menu.getContext(), name);
			String platName = menu.getContext().getString(platNameRes);
			String text = menu.getContext().getString(R.string.share_to_format, platName);
			btn.setText(text);
			btn.setTag(platform);
			btn.setVisibility(View.VISIBLE);
			btn.setOnClickListener(this);
			lineCount++;
		}
	}

	protected View initPage() {
		return LayoutInflater.from(menu.getContext())
				.inflate(R.layout.page_demo, null);
	}

	// 使用快捷分享完成分享（请务必仔细阅读位于SDK解压目录下Docs文件夹中OnekeyShare类的JavaDoc）
	private void showShare(boolean silent, String platform) {
		final OnekeyShare oks = new OnekeyShare();
		oks.setNotification(R.drawable.ic_launcher, menu.getContext().getString(R.string.app_name));
		oks.setAddress("12345678901");
		oks.setTitle(menu.getContext().getString(R.string.share));
		oks.setTitleUrl("http://sharesdk.cn");
		oks.setText(menu.getContext().getString(R.string.share_content));
		oks.setImagePath(MainActivity.TEST_IMAGE);
		oks.setImageUrl("http://img.appgo.cn/imgs/sharesdk/content/2013/07/25/1374723172663.jpg");
		oks.setUrl("http://www.sharesdk.cn");
		oks.setFilePath(MainActivity.TEST_IMAGE);
		oks.setComment(menu.getContext().getString(R.string.share));
		oks.setSite(menu.getContext().getString(R.string.app_name));
		oks.setSiteUrl("http://sharesdk.cn");
		oks.setVenueName("Southeast in China");
		oks.setVenueDescription("This is a beautiful place!");
		oks.setLatitude(23.122619f);
		oks.setLongitude(113.372338f);
		oks.setSilent(silent);
		if (platform != null) {
			oks.setPlatform(platform);
		}

		// 去除注释，可令编辑页面显示为Dialog模式
//		oks.setDialogMode();

		// 去除注释，则快捷分享的操作结果将通过OneKeyShareCallback回调
//		oks.setCallback(new OneKeyShareCallback());
		oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());

		// 在九宫格设置自定义的图标
		Bitmap logo = BitmapFactory.decodeResource(menu.getResources(), R.drawable.ic_launcher);
		String label = menu.getResources().getString(R.string.app_name);
		OnClickListener listener = new OnClickListener() {
			public void onClick(View v) {
				String text = "Customer Logo -- Share SDK " + ShareSDK.getSDKVersionName();
				Toast.makeText(menu.getContext(), text, Toast.LENGTH_SHORT).show();
				oks.finish();
			}
		};
		oks.setCustomerLogo(logo, label, listener);

		oks.show(menu.getContext());
	}

	/** 操作演示的代码集中于此方法 */
	public void onClick(View v) {
		if (v.equals(llTitle.getBtnBack())) {
			if (menu.isMenuShown()) {
				menu.hideMenu();
			}
			else {
				menu.showMenu();
			}
			return;
		}

		switch (v.getId()) {
			case R.id.btnShareAllGui: {
				// 图文分享
				showShare(false, null);
			}
			break;
			case R.id.btnShareAll: {
				// 直接分享
				showShare(true, null);
			}
			break;
			case R.id.btnFlSw: {
				// 关注新浪微博
				Platform plat = ShareSDK.getPlatform(menu.getContext(), SinaWeibo.NAME);
				plat.setPlatformActionListener(this);
				plat.followFriend(MainAdapter.SDK_SINAWEIBO_UID);
			}
			break;
			case R.id.btnFlTc: {
				// 关注腾讯微博
				Platform plat = ShareSDK.getPlatform(menu.getContext(), TencentWeibo.NAME);
				plat.setPlatformActionListener(this);
				plat.followFriend(MainAdapter.SDK_TENCENTWEIBO_UID);
			}
			break;
			case R.id.btnGetToken: {
				// 获取token
				Intent i = new Intent(menu.getContext(), GetTokenPage.class);
				menu.getContext().startActivity(i);
			}
			break;
			case R.id.btnVisitWc: {
				// 关注官方微信
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(MainAdapter.WECHAT_ADDR));
				menu.getContext().startActivity(i);
			}
			break;
			case R.id.btnGetInfor: {
				// 获取自己的资料
				Intent i = new Intent(menu.getContext(), GetInforPage.class);
				i.putExtra("type", 0);
				menu.getContext().startActivity(i);
			}
			break;
			case R.id.btnGetUserInfor: {
				// 获取指定帐号的资料
				Intent i = new Intent(menu.getContext(), GetInforPage.class);
				i.putExtra("type", 1);
				menu.getContext().startActivity(i);
			}
			break;
			default: {
				// 分享到具体的平台
				Object tag = v.getTag();
				if (tag != null) {
					showShare(false, ((Platform) tag).getName());
				}
			}
			break;
		}
	}

	public void onComplete(Platform plat, int action,
			HashMap<String, Object> res) {

		Message msg = new Message();
		msg.arg1 = 1;
		msg.arg2 = action;
		msg.obj = plat;
		handler.sendMessage(msg);
	}

	public void onCancel(Platform palt, int action) {
		Message msg = new Message();
		msg.arg1 = 3;
		msg.arg2 = action;
		msg.obj = palt;
		handler.sendMessage(msg);
	}

	public void onError(Platform palt, int action, Throwable t) {
		t.printStackTrace();

		Message msg = new Message();
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = palt;
		handler.sendMessage(msg);
	}

	/** 处理操作结果 */
	public boolean handleMessage(Message msg) {
		Platform plat = (Platform) msg.obj;
		String text = MainActivity.actionToString(msg.arg2);
		switch (msg.arg1) {
			case 1: {
				// 成功
				text = plat.getName() + " completed at " + text;
			}
			break;
			case 2: {
				// 失败
				text = plat.getName() + " caught error at " + text;
			}
			break;
			case 3: {
				// 取消
				text = plat.getName() + " canceled at " + text;
			}
			break;
		}

		Toast.makeText(menu.getContext(), text, Toast.LENGTH_SHORT).show();
		return false;
	}

}
