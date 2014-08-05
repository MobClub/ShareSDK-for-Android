/*
 * Offical Website:http://www.ShareSDK.cn
 * Support QQ: 4006852216
 * Offical Wechat Account:ShareSDK   (We will inform you our updated news at the first time by Wechat, if we release a new version. If you get any problem, you can also contact us with Wechat, we will reply you within 24 hours.)
 *
 * Copyright (c) 2013 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.demo;

import java.util.HashMap;
import m.framework.ui.widget.slidingmenu.SlidingMenu;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.sharesdk.framework.CustomPlatform;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.TitleLayout;
import cn.sharesdk.framework.utils.UIHandler;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.Shake2Share;
import cn.sharesdk.onekeyshare.Shake2Share.OnShakeListener;
import cn.sharesdk.onekeyshare.ShareCore;

/** page to show how to use onekeyshare, how to get accse token, how to get user info etc. */
public class DemoPage extends SlidingMenuPage implements
		OnClickListener, PlatformActionListener {
	private TitleLayout llTitle;

	public DemoPage(final SlidingMenu menu) {
		super(menu);
		View pageView = getPage();

		llTitle = (TitleLayout) pageView.findViewById(R.id.llTitle);
		llTitle.getBtnBack().setOnClickListener(this);
		llTitle.getTvTitle().setText(R.string.sm_item_demo);

		pageView.findViewById(R.id.btnShareAllGui).setOnClickListener(this);
		pageView.findViewById(R.id.btnShareAll).setOnClickListener(this);
		pageView.findViewById(R.id.btnShareView).setOnClickListener(this);
		pageView.findViewById(R.id.btnFlSw).setOnClickListener(this);
		pageView.findViewById(R.id.btnGetToken).setOnClickListener(this);
		pageView.findViewById(R.id.btnFlTc).setOnClickListener(this);
		pageView.findViewById(R.id.btnGetInfor).setOnClickListener(this);
		pageView.findViewById(R.id.btnGetUserInfor).setOnClickListener(this);

		new Thread() {
			public void run() {
				Platform[] list = ShareSDK.getPlatformList();
				if (list != null) {
					Message msg = new Message();
					msg.obj = list;
					UIHandler.sendMessage(msg, new Callback() {
						public boolean handleMessage(Message msg) {
							afterPlatformsGot((Platform[]) msg.obj);
							return false;
						}
					});
				}
			}
		}.start();
	}

	private void afterPlatformsGot(Platform[] platforms) {
		View pageView = getPage();
		LinearLayout llList = (LinearLayout) pageView.findViewById(R.id.llList);
		LinearLayout line = (LinearLayout) View.inflate(getContext(),
				R.layout.demo_page_item, null);
		llList.addView(line);
		int lineCount = 0;

		for (Platform platform : platforms) {
			String name = platform.getName();
			if (ShareCore.isUseClientToShare(name)) {
				continue;
			}

			if (platform instanceof CustomPlatform) {
				continue;
			}

			if (lineCount >= 2) {
				// place tow buttons in each line
				line = (LinearLayout) View.inflate(getContext(),
						R.layout.demo_page_item, null);
				llList.addView(line);
				lineCount = 0;
			}

			// initiate buttons
			int res = lineCount == 0 ? R.id.btnLeft : R.id.btnRight;
			Button btn = (Button) line.findViewById(res);
			btn.setSingleLine();
			int platNameRes = cn.sharesdk.framework.utils.R.getStringRes(
					getContext(), name);
			if (platNameRes > 0) {
				String platName = getContext().getString(platNameRes);
				String text = getContext().getString(R.string.share_to_format, platName);
				btn.setText(text);
			}
			btn.setTag(platform);
			btn.setVisibility(View.VISIBLE);
			btn.setOnClickListener(this);
			lineCount++;
		}
	}

	protected View initPage() {
		return LayoutInflater.from(getContext())
				.inflate(R.layout.page_demo, null);
	}

	// sharing by onekeyshare
	private void showShare(boolean silent, String platform, boolean captureView) {
		final OnekeyShare oks = new OnekeyShare();
		oks.setNotification(R.drawable.ic_launcher, getContext().getString(R.string.app_name));
		oks.setAddress("12345678901");
		oks.setTitle(getContext().getString(R.string.evenote_title));
		oks.setTitleUrl("http://sharesdk.cn");
		if (MainActivity.TEST_TEXT != null && MainActivity.TEST_TEXT.containsKey(0)) {
			oks.setText(MainActivity.TEST_TEXT.get(0));
		} else {
			oks.setText(getContext().getString(R.string.share_content));
		}
		if (captureView) {
			oks.setViewToShare(getPage());
		} else {
			oks.setImagePath(MainActivity.TEST_IMAGE);
			oks.setImageUrl(MainActivity.TEST_IMAGE_URL);
		}
		oks.setUrl("http://www.sharesdk.cn");
		oks.setFilePath(MainActivity.TEST_IMAGE);
		oks.setComment(getContext().getString(R.string.share));
		oks.setSite(getContext().getString(R.string.app_name));
		oks.setSiteUrl("http://sharesdk.cn");
		oks.setVenueName("ShareSDK");
		oks.setVenueDescription("This is a beautiful place!");
		oks.setLatitude(23.056081f);
		oks.setLongitude(113.385708f);
		oks.setSilent(silent);
		if (platform != null) {
			oks.setPlatform(platform);
		}

		// display editpage in dialog mode
		oks.setDialogMode();

		// disable sso in authorizing
		oks.disableSSOWhenAuthorize();

		// remove comments, use OneKeyShareCallback as the share action callback
//		oks.setCallback(new OneKeyShareCallback());
		oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());

		// remove comments, shows how to add custom logos in platform gridview
//		Bitmap logo = BitmapFactory.decodeResource(menu.getResources(), R.drawable.ic_launcher);
//		String label = menu.getResources().getString(R.string.app_name);
//		OnClickListener listener = new OnClickListener() {
//			public void onClick(View v) {
//				String text = "Customer Logo -- ShareSDK " + ShareSDK.getSDKVersionName();
//				Toast.makeText(menu.getContext(), text, Toast.LENGTH_SHORT).show();
//				oks.finish();
//			}
//		};
//		oks.setCustomerLogo(logo, label, listener);

		// remove comments, hide logos of sinaweibo and tencentweibo in platform gridview
//		oks.addHiddenPlatform(SinaWeibo.NAME);
//		oks.addHiddenPlatform(TencentWeibo.NAME);

		// set a view to be the background of EditPage
		oks.setEditPageBackground(getPage());

		oks.show(getContext());
	}

	public void onClick(View v) {
		if (v.equals(llTitle.getBtnBack())) {
			if (isMenuShown()) {
				hideMenu();
			}
			else {
				showMenu();
			}
			return;
		}

		switch (v.getId()) {
			case R.id.btnShareAllGui: {
				// photo-text sharing
				showShare(false, null, false);
			}
			break;
			case R.id.btnShareAll: {
				// share directly
				showShare(true, null, false);
			}
			break;
			case R.id.btnShareView: {
				// shake to share the current page view
				Shake2Share ss = new Shake2Share();
				ss.setOnShakeListener(new OnShakeListener() {
					public void onShake() {
						showShare(false, "SinaWeibo", true);
					}
				});
				ss.show(getContext(), null);
			}
			break;
			case R.id.btnFlSw: {
				// follow our official sina weibo
				Platform plat = ShareSDK.getPlatform("SinaWeibo");
				plat.setPlatformActionListener(this);
				plat.followFriend(MainAdapter.SDK_SINAWEIBO_UID);
			}
			break;
			case R.id.btnFlTc: {
				// follow our official tencent weibo
				Platform plat = ShareSDK.getPlatform("TencentWeibo");
				plat.setPlatformActionListener(this);
				plat.followFriend(MainAdapter.SDK_TENCENTWEIBO_UID);
			}
			break;
			case R.id.btnGetToken: {
				// request token
				GetTokenPage page = new GetTokenPage();
				page.show(getContext(), null);
			}
			break;
			case R.id.btnGetInfor: {
				// request user info of the authorizing account
				GetInforPage page = new GetInforPage();
				page.setType(0);
				page.show(getContext(), null);
			}
			break;
			case R.id.btnGetUserInfor: {
				// request user info of other account
				GetInforPage page = new GetInforPage();
				page.setType(1);
				page.show(getContext(), null);
			}
			break;
			default: {
				// share to platforms
				Object tag = v.getTag();
				if (tag != null) {
					showShare(false, ((Platform) tag).getName(), false);
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
		UIHandler.sendMessage(msg, this);
	}

	public void onCancel(Platform palt, int action) {
		Message msg = new Message();
		msg.arg1 = 3;
		msg.arg2 = action;
		msg.obj = palt;
		UIHandler.sendMessage(msg, this);
	}

	public void onError(Platform palt, int action, Throwable t) {
		t.printStackTrace();

		Message msg = new Message();
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = palt;
		UIHandler.sendMessage(msg, this);
	}

	public boolean handleMessage(Message msg) {
		Platform plat = (Platform) msg.obj;
		String text = MainActivity.actionToString(msg.arg2);
		switch (msg.arg1) {
			case 1: {
				// success
				text = plat.getName() + " completed at " + text;
			}
			break;
			case 2: {
				// failed
				text = plat.getName() + " caught error at " + text;
			}
			break;
			case 3: {
				// canceled
				text = plat.getName() + " canceled at " + text;
			}
			break;
		}

		Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
		return false;
	}

}
