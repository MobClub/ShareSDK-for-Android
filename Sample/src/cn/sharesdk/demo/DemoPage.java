/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package cn.sharesdk.demo;

import java.util.HashMap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.sharesdk.demo.widget.SlidingMenu;
import cn.sharesdk.framework.CustomPlatform;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.TitleLayout;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.mob.tools.utils.UIHandler;

/**
 * ShareSDK 官网地址 ： http://www.mob.com </br>
 *1、ShareSDK接口演示页面</br>
 *包括演示使用快捷分享完成图文分享、</br>
 *无页面直接分享、授权、关注和不同平台的分享等等功能。</br>
 *
 *2、如果要咨询客服，请加企业QQ 4006852216 </br>
 *3、咨询客服时，请把问题描述清楚，最好附带错误信息截图 </br>
 *4、一般问题，集成文档中都有，请先看看集成文档；减少客服压力，多谢合作  ^_^
 *5、由于客服压力巨大，每个月难免有那么几天，请见谅
 */
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
		pageView.findViewById(R.id.btnShareView).setVisibility(View.GONE);
		pageView.findViewById(R.id.btnFlSw).setOnClickListener(this);
		pageView.findViewById(R.id.btnGetToken).setOnClickListener(this);
		pageView.findViewById(R.id.btnFlTc).setOnClickListener(this);
		pageView.findViewById(R.id.btnGetInfor).setOnClickListener(this);
		pageView.findViewById(R.id.btnGetUserInfor).setOnClickListener(this);

		LinearLayout llList = (LinearLayout) pageView.findViewById(R.id.llList);
		for (int i = 0, count = llList.getChildCount(); i < count; i++) {
			llList.getChildAt(i).setVisibility(View.INVISIBLE);
		}

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
		llList.setLayoutAnimation(InLayoutAnim.getAnimationController());
		for (int i = 0, count = llList.getChildCount(); i < count; i++) {
			llList.getChildAt(i).setVisibility(View.VISIBLE);
		}

		LinearLayout line = (LinearLayout) View.inflate(getContext(),
				R.layout.demo_page_item, null);
		llList.addView(line);
		int lineCount = 0;

		for (Platform platform : platforms) {
			String name = platform.getName();
			if (DemoUtils.isUseClientToShare(name)) {
				continue;
			}

			if (platform instanceof CustomPlatform) {
				continue;
			}

			if (lineCount >= 2) {
				// 每行两个按钮
				line = (LinearLayout) View.inflate(getContext(),
						R.layout.demo_page_item, null);
				llList.addView(line);
				lineCount = 0;
			}

			// 处理左边按钮和右边按钮
			int res = lineCount == 0 ? R.id.btnLeft : R.id.btnRight;
			Button btn = (Button) line.findViewById(res);
			btn.setSingleLine();
			int platNameRes = com.mob.tools.utils.R.getStringRes(
					getContext(), "ssdk_" +name.toLowerCase());
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
		return LayoutInflater.from(getContext()).inflate(R.layout.page_demo, null);
	}

	// 使用快捷分享完成分享（请务必仔细阅读位于SDK解压目录下Docs文件夹中OnekeyShare类的JavaDoc）
	/**ShareSDK集成方法有两种</br>
	 * 1、第一种是引用方式，例如引用onekeyshare项目，onekeyshare项目再引用mainlibs库</br>
	 * 2、第二种是把onekeyshare和mainlibs集成到项目中，本例子就是用第二种方式</br>
	 * 请看“ShareSDK 使用说明文档”，SDK下载目录中 </br>
	 * 或者看网络集成文档 http://wiki.mob.com/Android_%E5%BF%AB%E9%80%9F%E9%9B%86%E6%88%90%E6%8C%87%E5%8D%97
	 * 3、混淆时，把sample或者本例子的混淆代码copy过去，在proguard-project.txt文件中
	 *
	 *
	 * 平台配置信息有三种方式：
	 * 1、在我们后台配置各个微博平台的key
	 * 2、在代码中配置各个微博平台的key，http://mob.com/androidDoc/cn/sharesdk/framework/ShareSDK.html
	 * 3、在配置文件中配置，本例子里面的assets/ShareSDK.conf,
	 */
	private void showShare(boolean silent, String platform, boolean captureView) {
		Context context = getContext();
		final OnekeyShare oks = new OnekeyShare();

		oks.setAddress("12345678901");
		oks.setTitle(CustomShareFieldsPage.getString("title", context.getString(R.string.evenote_title)));
		oks.setTitleUrl(CustomShareFieldsPage.getString("titleUrl", "http://mob.com"));
		oks.setMusicUrl("http://play.baidu.com/?__m=mboxCtrl.playSong&__a=7320512&__o=song/7320512||playBtn&fr=altg_new3||www.baidu.com#");
		String customText = CustomShareFieldsPage.getString( "text", null);
		if (customText != null) {
			oks.setText(customText);
		} else if (MainActivity.TEST_TEXT != null && MainActivity.TEST_TEXT.containsKey(0)) {
			oks.setText(MainActivity.TEST_TEXT.get(0));
		} else {
			oks.setText(context.getString(R.string.share_content));
		}

		if (captureView) {
			oks.setViewToShare(getPage());
		} else {
			oks.setImagePath(CustomShareFieldsPage.getString("imagePath", MainActivity.TEST_IMAGE));
			oks.setImageUrl(CustomShareFieldsPage.getString("imageUrl", MainActivity.TEST_IMAGE_URL));
		//	oks.setImageArray(new String[]{MainActivity.TEST_IMAGE, MainActivity.TEST_IMAGE_URL});
		}

		oks.setUrl(CustomShareFieldsPage.getString("url", "http://mob.com"));
		oks.setFilePath(CustomShareFieldsPage.getString("filePath", MainActivity.TEST_IMAGE));
		oks.setComment(CustomShareFieldsPage.getString("comment", context.getString(R.string.ssdk_oks_share)));
		oks.setSite(CustomShareFieldsPage.getString("site", context.getString(R.string.app_name)));
		oks.setSiteUrl(CustomShareFieldsPage.getString("siteUrl", "http://mob.com"));
		oks.setVenueName(CustomShareFieldsPage.getString("venueName", "ShareSDK"));
		oks.setVenueDescription(CustomShareFieldsPage.getString("venueDescription", "This is a beautiful place!"));
		oks.setSilent(silent);

		if (platform != null) {
			oks.setPlatform(platform);
		}

		oks.setLatitude(23.169f);
		oks.setLongitude(112.908f);

		// 令编辑页面显示为Dialog模式
		//oks.setDialogMode();

		// 在自动授权时可以禁用SSO方式
		//if(!CustomShareFieldsPage.getBoolean("enableSSO", true))
			oks.disableSSOWhenAuthorize();

		// 去除注释，则快捷分享的操作结果将通过OneKeyShareCallback回调
		//oks.setCallback(new OneKeyShareCallback());

		// 去自定义不同平台的字段内容
		//oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());

		// 去除注释，演示在九宫格设置自定义的图标
		Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		String label = getResources().getString(R.string.app_name);
		OnClickListener listener = new OnClickListener() {
			public void onClick(View v) {
				String text = "Customer Logo -- ShareSDK " + ShareSDK.getSDKVersionName();
				Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
			}
		};
		oks.setCustomerLogo(logo, label, listener);

		// 去除注释，则快捷分享九宫格中将隐藏新浪微博和腾讯微博
//		oks.addHiddenPlatform(SinaWeibo.NAME);
//		oks.addHiddenPlatform(TencentWeibo.NAME);

		// 为EditPage设置一个背景的View
		oks.show(context);
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
				// 图文分享
				showShare(false, null, false);
			}
			break;
			case R.id.btnShareAll: {
				// 直接分享
				showShare(true, null, false);
			}
			break;
			case R.id.btnShareView: {
				// 摇一摇截图分享
//				Shake2Share ss = new Shake2Share();
//				ss.setOnShakeListener(new OnShakeListener() {
//					public void onShake() {
//						showShare(false, "SinaWeibo", true);
//					}
//				});
//				ss.show(getContext(), null);
			}
			break;
			case R.id.btnFlSw: {
				// 关注新浪微博
				Platform plat = ShareSDK.getPlatform("SinaWeibo");
				plat.setPlatformActionListener(this);
				plat.followFriend(MainAdapter.SDK_SINAWEIBO_UID);
			}
			break;
			case R.id.btnFlTc: {
				// 关注腾讯微博
				Platform plat = ShareSDK.getPlatform("TencentWeibo");
				plat.setPlatformActionListener(this);
				plat.followFriend(MainAdapter.SDK_TENCENTWEIBO_UID);
			}
			break;
			case R.id.btnGetToken: {
				// 获取token
				GetTokenPage page = new GetTokenPage();
				page.show(getContext(), null);
			}
			break;
			case R.id.btnGetInfor: {
				// 获取自己的资料
				GetInforPage page = new GetInforPage();
				page.setType(0);
				page.show(getContext(), null);
			}
			break;
			case R.id.btnGetUserInfor: {
				// 获取指定帐号的资料
				GetInforPage page = new GetInforPage();
				page.setType(1);
				page.show(getContext(), null);
			}
			break;
			default: {
				// 分享到具体的平台
				Object tag = v.getTag();
				if (tag != null) {
					final String platformName = ((Platform) tag).getName();
					//QQ,QZone授权登录后发微博
					if("TencentWeibo".equals(platformName)){
						new AlertDialog.Builder(getContext())
								.setMessage(R.string.qq_share_way)
								.setPositiveButton(R.string.qq_share_from_qqlogin, new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										try
										{
											getContext().getPackageManager().getPackageInfo("com.qzone", 0);
											showShare(false, "QZone", false);
										} catch (PackageManager.NameNotFoundException e)
										{
											showShare(false, "QQ", false);
										}
									}
								})
								.setNegativeButton(R.string.qq_share_from_tlogin, new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										showShare(false, platformName, false);
									}
								})
								.setIcon(android.R.drawable.ic_dialog_alert)
								.show();
					}else{
						showShare(false, platformName, false);
					}

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

		Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
		return false;
	}

}
