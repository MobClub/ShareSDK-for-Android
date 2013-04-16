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
import cn.sharesdk.framework.AbstractWeibo;
import cn.sharesdk.framework.TitleLayout;
import cn.sharesdk.framework.WeiboActionListener;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckedTextView;
import android.widget.Toast;

/** 微信api的演示页面，展示了“微信好友”和“微信朋友圈”的接口 */
public class WechatPage implements Callback, 
		OnClickListener, WeiboActionListener {
	private SlidingMenu menu;
	private View pageView;
	private TitleLayout llTitle;
	private Handler handler;
	private CheckedTextView ctvStWm;
	private String picPath;
	
	public WechatPage(SlidingMenu menu) {
		handler = new Handler(this);
		this.menu = menu;
		pageView = LayoutInflater.from(menu.getContext())
				.inflate(R.layout.page_wechate, null);
		
		llTitle = (TitleLayout) pageView.findViewById(R.id.llTitle);
		llTitle.getBtnBack().setOnClickListener(this);
		llTitle.getTvTitle().setText(R.string.sm_item_wechat);
		
		ctvStWm = (CheckedTextView) pageView.findViewById(R.id.ctvStWm);
		ctvStWm.setOnClickListener(this);
		pageView.findViewById(R.id.btnUpdate).setOnClickListener(this);
		pageView.findViewById(R.id.btnUpload).setOnClickListener(this);
		pageView.findViewById(R.id.btnMusic).setOnClickListener(this);
		pageView.findViewById(R.id.btnVideo).setOnClickListener(this);
		pageView.findViewById(R.id.btnWebpage).setOnClickListener(this);
		pageView.findViewById(R.id.btnApp).setOnClickListener(this);
		
		new Thread(){
			public void run() {
				picPath = getPath();
			}
		}.start();
	}
	
	/** 获取页面的View实例 */
	public View getPage() {
		return pageView;
	}
	
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
		
		if (v.equals(ctvStWm)) {
			ctvStWm.setChecked(!ctvStWm.isChecked());
			return;
		}
		
		String name = ctvStWm.isChecked() ? WechatMoments.NAME : Wechat.NAME;
		AbstractWeibo weibo = AbstractWeibo.getWeibo(menu.getContext(), name);
		weibo.setWeiboActionListener(this);
		String title = menu.getContext().getString(R.string.wechat_demo_title);
		String text = menu.getContext().getString(R.string.share_content);
		switch (v.getId()) {
			case R.id.btnUpdate: {
				weibo.share(AbstractWeibo.SHARE_TEXT, title, text);
			}
			break;
			case R.id.btnUpload: {
				weibo.share(AbstractWeibo.SHARE_IMAGE_TEXT, title, text, picPath);
			}
			break;
			case R.id.btnMusic: {
				String musicUrl = "http://119.188.72.27/2/file.data.vdisk.me/37414786/1f486ed742895fd9e6487568047a20b29a9d0df0?ip=1364785283,10.73.26.26&ssig=1iVh5iRmC%2B&Expires=1364784083&KID=sae,l30zoo1wmz&fn=%E9%93%83%E5%A3%B0%E6%8A%A5%E5%91%8A%E5%A4%A7%E7%8E%8B.mp3";
				String thumbUrl = picPath;
				weibo.share(AbstractWeibo.SHARE_MUSIC, title, text, musicUrl, thumbUrl);
			}
			break;
			case R.id.btnVideo: {
				String videoUrl = "http://t.cn/zT7cZAo";
				String thumbUrl = picPath;
				weibo.share(AbstractWeibo.SHARE_VIDEO, title, text, videoUrl, thumbUrl);
			}
			break;
			case R.id.btnWebpage: {
				String url = "http://sharesdk.cn";
				String thumbUrl = picPath;
				weibo.share(AbstractWeibo.SHARE_WEBPAGE, title, text, url, thumbUrl);
			}
			break;
			case R.id.btnApp: {
				String appPath = picPath; // app的本地地址
				String thumbUrl = picPath;
				weibo.share(AbstractWeibo.SHARE_APPS, title, text, appPath, thumbUrl);
			}
			break;
		}
	}
	
	private String getPath() {
		String path = null;
		try {
			Activity act = (Activity) menu.getContext();
			if (Environment.getExternalStorageDirectory().exists()) {
				path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/pic.jpg";
			}
			else {
				path = act.getApplication().getFilesDir().getAbsolutePath() + "/pic.jpg";
			}
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
				Bitmap pic = BitmapFactory.decodeResource(
						menu.getResources(), R.drawable.pic);
				FileOutputStream fos = new FileOutputStream(file);
				pic.compress(CompressFormat.JPEG, 100, fos);
				fos.flush();
				fos.close();
			}
		} catch(Throwable t) {
			t.printStackTrace();
			path = null;
		}
		return path;
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
		t.printStackTrace();
		Message msg = new Message();
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = weibo;
		handler.sendMessage(msg);
	}
	
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
