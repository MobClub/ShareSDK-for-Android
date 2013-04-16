/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 * 
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import m.framework.ui.SlidingMenu;
import cn.sharesdk.douban.Douban;
import cn.sharesdk.evernote.Evernote;
import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.framework.AbstractWeibo;
import cn.sharesdk.framework.TitleLayout;
import cn.sharesdk.framework.WeiboActionListener;
import cn.sharesdk.netease.microblog.NetEaseMicroBlog;
import cn.sharesdk.onekeyshare.ShareAllGird;
import cn.sharesdk.onekeyshare.SharePage;
import cn.sharesdk.renren.Renren;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.twitter.Twitter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

/** Share SDK接口演示页面，包括演示使用快捷分享完成图文分享、
 *无页面直接分享、授权、关注和不同平台的分享等等功能。
 */
public class DemoPage implements Callback, 
		OnClickListener, WeiboActionListener {
	private SlidingMenu menu;
	private View pageView;
	private TitleLayout llTitle;
	private Handler handler;
	
	public DemoPage(SlidingMenu menu) {
		handler = new Handler(this);
		this.menu = menu;
		pageView = LayoutInflater.from(menu.getContext())
				.inflate(R.layout.page_demo, null);
		
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
		pageView.findViewById(R.id.btnShareSw).setOnClickListener(this);
		pageView.findViewById(R.id.btnShareTc).setOnClickListener(this);
		pageView.findViewById(R.id.btnShareFb).setOnClickListener(this);
		pageView.findViewById(R.id.btnShareTw).setOnClickListener(this);
		pageView.findViewById(R.id.btnShareRr).setOnClickListener(this);
		pageView.findViewById(R.id.btnShareQz).setOnClickListener(this);
		pageView.findViewById(R.id.btnShareDb).setOnClickListener(this);
		pageView.findViewById(R.id.btnShareEn).setOnClickListener(this);
		pageView.findViewById(R.id.btnShareNemb).setOnClickListener(this);
	}
	
	/** 获取页面View */
	public View getPage() {
		return pageView;
	}
	
	// 使用快捷分享完成图文分享
	private Thread newLaunchThread(final boolean silent) {
		return new Thread(){
			public void run() {
				final String image = getImagePath();
				handler.post(new Runnable() {
					public void run() {
						Intent i = new Intent(menu.getContext(), ShareAllGird.class);
						i.putExtra("notif_icon", R.drawable.ic_launcher);
						i.putExtra("notif_title", menu.getContext().getString(R.string.app_name));
						
						i.putExtra("address", "10086");
						i.putExtra("title", menu.getContext().getString(R.string.share));
						i.putExtra("text", menu.getContext().getString(R.string.share_content));
						i.putExtra("image", image);
						i.putExtra("image_url", "http://sharesdk.cn/Public/Frontend/images/logo.png");
						i.putExtra("site", menu.getContext().getString(R.string.app_name));
						i.putExtra("siteUrl", "http://sharesdk.cn");
						
						i.putExtra("silent", silent);
						menu.getContext().startActivity(i);
					}
				});
			}
		};
	}
	
	// 使用快捷分享完成直接分享
	private Thread newShareThread(final String platform) {
		return new Thread(){
			public void run() {
				final String image = getImagePath();
				handler.post(new Runnable() {
					public void run() {
						Intent i = new Intent(menu.getContext(), SharePage.class);
						i.putExtra("notif_icon", R.drawable.ic_launcher);
						i.putExtra("notif_title", menu.getContext().getString(R.string.app_name));
						
						i.putExtra("address", "13800123456");
						i.putExtra("title", menu.getContext().getString(R.string.share));
						i.putExtra("text", menu.getContext().getString(R.string.share_content));
						i.putExtra("image", image);
						i.putExtra("image_url", "http://sharesdk.cn/Public/Frontend/images/logo.png");
						i.putExtra("site", menu.getContext().getString(R.string.app_name));
						i.putExtra("siteUrl", "http://sharesdk.cn");
						
						i.putExtra("platform", platform);
						menu.getContext().startActivity(i);
					}
				});
			}
		};
	}
	
	private String getImagePath() {
		try {
			Activity act = (Activity) menu.getContext();
			String path;
			if (Environment.getExternalStorageDirectory().exists()) {
				path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/pic.jpg";
			}
			else {
				path = act.getApplication().getFilesDir().getAbsolutePath() + "/pic.jpg";
			}
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
				Bitmap pic = BitmapFactory.decodeResource(act.getResources(), R.drawable.pic);
				FileOutputStream fos = new FileOutputStream(file);
				pic.compress(CompressFormat.JPEG, 100, fos);
				fos.flush();
				fos.close();
			}
			return path;
		} catch(Throwable t) {
			t.printStackTrace();
		}
		return null;
	}
	
	/** 操作演示的代码集中于此方面 */
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
				newLaunchThread(false).start();
			}
			break;
			case R.id.btnShareAll: {
				// 直接分享
				newLaunchThread(true).start();
			}
			break;
			case R.id.btnFlSw: {
				// 关注新浪微博
				AbstractWeibo weibo = AbstractWeibo.getWeibo(
						menu.getContext(), SinaWeibo.NAME);
				weibo.setWeiboActionListener(this);
				weibo.followFriend(MainAdapter.SDK_SINAWEIBO_UID);
			}
			break;
			case R.id.btnFlTc: {
				// 关注腾讯微博
				AbstractWeibo weibo = AbstractWeibo.getWeibo(
						menu.getContext(), TencentWeibo.NAME);
				weibo.setWeiboActionListener(this);
				weibo.followFriend(MainAdapter.SDK_TENCENTWEIBO_UID);
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
			case R.id.btnShareSw: {
				// 分享到新浪微博
				newShareThread(SinaWeibo.NAME).start();
			}
			break;
			case R.id.btnShareTc: {
				// 分享到腾讯微博
				newShareThread(TencentWeibo.NAME).start();
			}
			break;
			case R.id.btnShareFb: {
				// 分享到facebook
				newShareThread(Facebook.NAME).start();
			}
			break;
			case R.id.btnShareTw: {
				// 分享到twitter
				newShareThread(Twitter.NAME).start();
			}
			break;
			case R.id.btnShareRr: {
				// 分享到人人网
				newShareThread(Renren.NAME).start();
			}
			break;
			case R.id.btnShareQz: {
				// 分享到qq空间
				newShareThread(QZone.NAME).start();
			}
			break;
			case R.id.btnShareDb: {
				// 分享到豆瓣
				newShareThread(Douban.NAME).start();
			}
			break;
			case R.id.btnShareEn: {
				// 分享到印象笔记
				newShareThread(Evernote.NAME).start();
			}
			break;
			case R.id.btnShareNemb: {
				// 分享到网易微博
				newShareThread(NetEaseMicroBlog.NAME).start();
			}
			break;
		}
	}
	
	public void onComplete(AbstractWeibo weibo, int action,
			HashMap<String, Object> res) {
		Message msg = new Message();
		msg.arg1 = 1;
		msg.arg2 = action;
		msg.obj = weibo;
		handler.sendMessage(msg);
	}
	
	public void onCancel(AbstractWeibo weibo, int action) {
		Message msg = new Message();
		msg.arg1 = 3;
		msg.arg2 = action;
		msg.obj = weibo;
		handler.sendMessage(msg);
	}
	
	public void onError(AbstractWeibo weibo, int action, Throwable t) {
		Message msg = new Message();
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = weibo;
		handler.sendMessage(msg);
	}
	
	/** 处理操作结果 */
	public boolean handleMessage(Message msg) {
		AbstractWeibo weibo = (AbstractWeibo) msg.obj;
		String text = AbstractWeibo.actionToString(msg.arg2);
		switch (msg.arg1) {
			case 1: { // 成功
				text = weibo.getName() + " completed at " + text;
			}
			break;
			case 2: { // 失败
				text = weibo.getName() + " caught error at " + text;
			}
			break;
			case 3: { // 取消
				text = weibo.getName() + " canceled at " + text;
			}
			break;
		}
		
		Toast.makeText(menu.getContext(), text, Toast.LENGTH_SHORT).show();
		return false;
	}
	
}
