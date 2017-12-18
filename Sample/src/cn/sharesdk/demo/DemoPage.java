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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.UIHandler;

import java.util.HashMap;

import cn.sharesdk.demo.widget.SlidingMenu;
import cn.sharesdk.framework.CustomPlatform;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.TitleLayout;
import cn.sharesdk.onekeyshare.OnekeyShare;

//#if def{lang} == cn
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
//#elif def{lang} == en
/** page to show how to use onekeyshare, how to get accse token, how to get user info etc. */
//#endif
public class DemoPage extends SlidingMenuPage implements
		OnClickListener, PlatformActionListener {
	private TitleLayout llTitle;
	private String platformName;

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
				//#if def{lang} == cn
				// 每行两个按钮
				//#elif def{lang} == en
				// place tow buttons in each line
				//#endif
				line = (LinearLayout) View.inflate(getContext(), 
						R.layout.demo_page_item, null);
				llList.addView(line);
				lineCount = 0;
			}
			
			//#if def{lang} == cn
			// 处理左边按钮和右边按钮
			//#elif def{lang} == en
			// initiate buttons
			//#endif
			int res = lineCount == 0 ? R.id.btnLeft : R.id.btnRight;
			Button btn = (Button) line.findViewById(res);
			btn.setSingleLine();
			int platNameRes = ResHelper.getStringRes(getContext(), "ssdk_" + name.toLowerCase());
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
	
	//#if def{lang} == cn
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
	//#elif def{lang} == en
	// sharing by onekeyshare
	//#endif
	private void showShare(boolean silent, String platform, boolean captureView) {
		showShare(silent,platform,captureView,null);
	}
	private void showShare(boolean silent, String platform, boolean captureView,String filePath) {
		Context context = getContext();
		final OnekeyShare oks = new OnekeyShare();

		oks.setAddress("12345678901");
		if(!TextUtils.isEmpty(filePath)){
			oks.setFilePath(filePath);
		} else{
			oks.setFilePath(CustomShareFieldsPage.getString("filePath", MainActivity.testVideo));
		}
		oks.setTitle(CustomShareFieldsPage.getString("title", context.getString(R.string.evenote_title)));
		oks.setTitleUrl(CustomShareFieldsPage.getString("titleUrl", "http://mob.com"));
		oks.setUrl(CustomShareFieldsPage.getString("url", "http://mob.com"));
		oks.setMusicUrl("http://play.baidu.com/?__m=mboxCtrl.playSong&__a=7320512&__o=song/7320512||playBtn&fr=altg_new3||www.baidu.com#");
		String customText = CustomShareFieldsPage.getString( "text", null);
		if (customText != null) {
			oks.setText(customText);
		} else if (MainActivity.testText != null && MainActivity.testText.containsKey(0)) {
			oks.setText(MainActivity.testText.get(0));
		} else {
			oks.setText(context.getString(R.string.share_content));
		}

		if (captureView) {
			oks.setViewToShare(getPage());
		} else {
			oks.setImagePath(CustomShareFieldsPage.getString("imagePath", MainActivity.testImage));
			oks.setImageUrl(CustomShareFieldsPage.getString("imageUrl", MainActivity.testImageUrl));
			//	oks.setImageArray(new String[]{MainActivity.TEST_IMAGE, MainActivity.TEST_IMAGE_URL});
		}

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
		//#if def{sdk.debugable} == true
//		final String[] AVATARS = {
//				"http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
//				"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
//				"http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
//				"http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
//				"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
//				"http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg",
//		};
//		oks.setImageArray(AVATARS);
		//#endif

		//#if def{lang} == cn
		// 令编辑页面显示为Dialog模式
		//#elif def{lang} == en
		// display editpage in dialog mode
		//#endif
		//oks.setDialogMode();

		//#if def{lang} == cn
		// 在自动授权时可以禁用SSO方式
		//#elif def{lang} == en
		// disable sso in authorizing
		//#endif
		//if(!CustomShareFieldsPage.getBoolean("enableSSO", true))
		oks.disableSSOWhenAuthorize();

		//#if def{lang} == cn
		// 去除注释，则快捷分享的操作结果将通过OneKeyShareCallback回调
		//#elif def{lang} == en
		// remove comments, use OneKeyShareCallback as the share action callback
		//#endif
		//oks.setCallback(new OneKeyShareCallback());

		//内部测试人员，需要在Sample demo中添加对话框的回调提示,方便测试看结果
		oks.setCallback(this);

		//#if def{lang} == cn
		// 去自定义不同平台的字段内容
		//#elif def{lang} == en
		// Custom fields content of different platforms
		//#endif
		oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());

		//#if def{lang} == cn
		// 去除注释，演示在九宫格设置自定义的图标
		//#elif def{lang} == en
		// remove comments, shows how to add custom logos in platform gridview
		//#endif
		Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		String label = getResources().getString(R.string.app_name);
		OnClickListener listener = new OnClickListener() {
			public void onClick(View v) {
				String text = "Customer Logo -- ShareSDK " + ShareSDK.getSDKVersionName();
				Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
			}
		};
		oks.setCustomerLogo(logo, label, listener);

		//#if def{lang} == cn
		// 去除注释，则快捷分享九宫格中将隐藏新浪微博和腾讯微博
		//#elif def{lang} == en
		// remove comments, hide logos of sinaweibo and tencentweibo in platform gridview
		//#endif
//		oks.addHiddenPlatform(SinaWeibo.NAME);
//		oks.addHiddenPlatform(TencentWeibo.NAME);

		//#if def{lang} == cn
		// 为EditPage设置一个背景的View
		//#elif def{lang} == en
		// set a view to be the background of EditPage
		//#endif
		oks.show(context);
	}
	
	public void onClick(View v) {
		if (v.equals(llTitle.getBtnBack())) {
			if (isMenuShown()) {
				hideMenu();
			} else {
				showMenu();
			}
			return;
		}
		
		switch (v.getId()) {
			case R.id.btnShareAllGui: {
				//#if def{lang} == cn
				// 图文分享
				//#elif def{lang} == en
				// photo-text sharing
				//#endif
				showShare(false, null, false);
			} break;
			case R.id.btnShareAll: {
				//#if def{lang} == cn
				// 直接分享
				//#elif def{lang} == en
				// share directly
				//#endif
				showShare(true, null, false);
			} break;
			case R.id.btnShareView: {
				//#if def{lang} == cn
				// 摇一摇截图分享
				//#elif def{lang} == en
				// shake to share the current page view
				//#endif
//				Shake2Share ss = new Shake2Share();
//				ss.setOnShakeListener(new OnShakeListener() {
//					public void onShake() {
//						showShare(false, "SinaWeibo", true);
//					} 
//				});
//				ss.show(getContext(), null);
			} break;
			case R.id.btnFlSw: {
				//#if def{lang} == cn
				// 关注新浪微博
				//#elif def{lang} == en
				// follow our official sina weibo
				//#endif
				Platform plat = ShareSDK.getPlatform("SinaWeibo");
				plat.setPlatformActionListener(this);
				plat.followFriend(MainAdapter.SDK_SINAWEIBO_UID);
			} break;
			case R.id.btnFlTc: {
				//#if def{lang} == cn
				// 关注腾讯微博
				//#elif def{lang} == en
				// follow our official tencent weibo
				//#endif
				Platform plat = ShareSDK.getPlatform("TencentWeibo");
				plat.setPlatformActionListener(this);
				plat.followFriend(MainAdapter.SDK_TENCENTWEIBO_UID);
			} break;
			case R.id.btnGetToken: {
				//#if def{lang} == cn
				// 获取token
				//#elif def{lang} == en
				// request token
				//#endif
				GetTokenPage page = new GetTokenPage();
				page.show(getContext(), null);
			} break;
			case R.id.btnGetInfor: {
				//#if def{lang} == cn
				// 获取自己的资料
				//#elif def{lang} == en
				// request user info of the authorizing account
				//#endif
				GetInforPage page = new GetInforPage();
				page.setType(0);
				page.show(getContext(), null);
			} break;
			case R.id.btnGetUserInfor: {
				//#if def{lang} == cn
				// 获取指定帐号的资料
				//#elif def{lang} == en
				// request user info of other account
				//#endif
				GetInforPage page = new GetInforPage();
				page.setType(1);
				page.show(getContext(), null);
			} break;
			default: {
				//#if def{lang} == cn
				// 分享到具体的平台
				//#elif def{lang} == en
				// share to platforms
				//#endif
				Object tag = v.getTag();
				if (tag != null) {
					final String platformName = ((Platform) tag).getName();
					//QQ,QZone授权登录后发微博
					if("TencentWeibo".equals(platformName)){
						new AlertDialog.Builder(getContext())
								.setMessage(R.string.qq_share_way)
								.setPositiveButton(R.string.qq_share_from_qqlogin, new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										try {
											getContext().getPackageManager().getPackageInfo("com.qzone", 0);
											showShare(false, "QZone", false);
										} catch (PackageManager.NameNotFoundException e) {
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
					} else {
						showShare(false, platformName, false);
					}
				}
			} break;
		}
	}
//	private MainActivity getActivity(){
//
//		Activity activity = (Activity) getContext();
//		if(activity instanceof MainActivity){
//			((MainActivity) getContext()).setMediaSelected(meidaSouces);
//			return (MainActivity) activity;
//		}
//		return null;
//	}
//	private void startImageIntent(){
//		getActivity().startSelectImages();
//	}
//	private void startVideoIntent(String platformName){
//		getActivity().startSelectVideo(platformName);
//	}
//	private void showDialog(final String platformName){
//		new AlertDialog.Builder(getContext())
//				.setMessage(R.string.share_selected)
//				.setPositiveButton(R.string.image_share, new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//							startImageIntent();
//					}
//				})
//				.setNegativeButton(R.string.video_share, new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						startVideoIntent(platformName);
//					}
//				})
//				.setIcon(android.R.drawable.ic_dialog_alert)
//				.show();
//	}
//	MainActivity.iSelectMeidaSouces meidaSouces = new MainActivity.iSelectMeidaSouces(){
//		@Override
//		public void onResult(int type, Uri uri) {
//			String path = uri.toString();
//			int objectType = type;
//			if(!TextUtils.isEmpty(platformName)){
//				showShare(false, platformName, false,path);
//			}
//		}
//
//		@Override
//		public void onResult(int type, String filePath) {
//			String path = filePath;
//			int objectType = type;
//			if(!TextUtils.isEmpty(platformName)){
//				showShare(false, platformName, false,filePath);
//			}
//		}
//	};
	
	public void onComplete(Platform plat, int action, HashMap<String, Object> res) {
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
		boolean isCallBackMsg=false;
		switch (msg.arg1) {
			case 1: {
				//#if def{lang} == cn
				// 成功
				//#elif def{lang} == en
				// success
				//#endif
				text = plat.getName() + " completed at " + text;
				isCallBackMsg=true;
			} break;
			case 2: {
				//#if def{lang} == cn
				// 失败
				//#elif def{lang} == en
				// failed
				//#endif
				text = plat.getName() + " caught error at " + text;
				isCallBackMsg=true;
			} break;
			case 3: {
				//#if def{lang} == cn
				// 取消
				//#elif def{lang} == en
				// canceled
				//#endif
				text = plat.getName() + " canceled at " + text;
				isCallBackMsg=true;
			} break;
		}
		
		Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
		//#if def{lang} == cn
		//添加对话框提示，方便内部测试观察回调结果
		//#elif def{lang} == en
		//use dialog for tip,for test
		//#endif
		if(isCallBackMsg){
			new AlertDialog.Builder(getContext()).setMessage(text).create().show();
		}
		return false;
	}

}
