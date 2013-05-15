/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 * 
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.demo;

import java.util.HashMap;
import m.framework.ui.SlidingMenu;
import cn.sharesdk.framework.AbstractWeibo;
import cn.sharesdk.framework.AbstractWeibo.ShareParams;
import cn.sharesdk.framework.TitleLayout;
import cn.sharesdk.framework.WeiboActionListener;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import cn.sharesdk.wechat.utils.WechatClientNotExistException;
import cn.sharesdk.wechat.utils.WechatTimelineNotSupportedException;
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
		int type = AbstractWeibo.SHARE_TEXT;
		switch (v.getId()) {
			case R.id.btnUpdate: type = AbstractWeibo.SHARE_TEXT; break;
			case R.id.btnUpload: type = AbstractWeibo.SHARE_IMAGE; break;
			case R.id.btnMusic: type = AbstractWeibo.SHARE_MUSIC; break;
			case R.id.btnVideo: type = AbstractWeibo.SHARE_VIDEO; break;
			case R.id.btnWebpage: type = AbstractWeibo.SHARE_WEBPAGE; break;
			case R.id.btnApp: type = AbstractWeibo.SHARE_APPS; break;
		}
		ShareParams sp = ctvStWm.isChecked() ? getMomentSP(type) : getWechatSP(type);
		weibo.share(sp);
	}
	
	private ShareParams getWechatSP(int shareType) {
		Wechat.ShareParams sp = new Wechat.ShareParams();
		sp.shareType = shareType;
		sp.title = menu.getContext().getString(R.string.wechat_demo_title);;
		sp.text = menu.getContext().getString(R.string.share_content);
		sp.imagePath = MainActivity.TEST_IMAGE;
		sp.thumbPath = MainActivity.TEST_IMAGE;
		sp.appPath = MainActivity.TEST_IMAGE; // app的本地地址
		
		String musicUrl = "http://119.188.72.27/2/file.data.vdisk.me/37414786/1f486ed742895fd9e6487568047a20b29a9d0df0?ip=1364785283,10.73.26.26&ssig=1iVh5iRmC%2B&Expires=1364784083&KID=sae,l30zoo1wmz&fn=%E9%93%83%E5%A3%B0%E6%8A%A5%E5%91%8A%E5%A4%A7%E7%8E%8B.mp3";
		String videoUrl = "http://t.cn/zT7cZAo";
		String webpageUrl = "http://t.cn/zT7cZAo";
		switch (shareType) {
			case AbstractWeibo.SHARE_MUSIC: sp.url = musicUrl; break;
			case AbstractWeibo.SHARE_VIDEO: sp.url = videoUrl; break;
			case AbstractWeibo.SHARE_WEBPAGE: sp.url = webpageUrl; break;
		}
		return sp;
	}
	
	private ShareParams getMomentSP(int shareType) {
		WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
		sp.shareType = shareType;
		sp.title = menu.getContext().getString(R.string.wechat_demo_title);;
		sp.text = menu.getContext().getString(R.string.share_content);
		sp.imagePath = MainActivity.TEST_IMAGE;
		sp.thumbPath = MainActivity.TEST_IMAGE;
		sp.appPath = MainActivity.TEST_IMAGE; // app的本地地址
		
		String musicUrl = "http://119.188.72.27/2/file.data.vdisk.me/37414786/1f486ed742895fd9e6487568047a20b29a9d0df0?ip=1364785283,10.73.26.26&ssig=1iVh5iRmC%2B&Expires=1364784083&KID=sae,l30zoo1wmz&fn=%E9%93%83%E5%A3%B0%E6%8A%A5%E5%91%8A%E5%A4%A7%E7%8E%8B.mp3";
		String videoUrl = "http://t.cn/zT7cZAo";
		String webpageUrl = "http://t.cn/zT7cZAo";
		switch (shareType) {
			case AbstractWeibo.SHARE_MUSIC: sp.url = musicUrl; break;
			case AbstractWeibo.SHARE_VIDEO: sp.url = videoUrl; break;
			case AbstractWeibo.SHARE_WEBPAGE: sp.url = webpageUrl; break;
		}
		return sp;
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
		msg.obj = t;
		handler.sendMessage(msg);
	}
	
	public boolean handleMessage(Message msg) {
		AbstractWeibo weibo = (AbstractWeibo) msg.obj;
		String text = MainActivity.actionToString(msg.arg2);
		switch (msg.arg1) {
			case 1: { // 成功
				text = weibo.getName() + " completed at " + text;
			}
			break;
			case 2: { // 失败
				if (msg.obj instanceof WechatClientNotExistException) {
					text = cn.sharesdk.onekeyshare.res.R.getString(
							menu.getContext(), "wechat_client_not_install");
				}
				else if (msg.obj instanceof WechatTimelineNotSupportedException) {
					text = cn.sharesdk.onekeyshare.res.R.getString(
							menu.getContext(), "wechat_timeline_not_support");
				}
				else {
					text = cn.sharesdk.onekeyshare.res.R.getString(
							menu.getContext(), "share_failed");
				}
			}
			break;
			case 3: { // 取消
				text = weibo.getName() + " canceled at " + text;
			}
			break;
		}
		
		Toast.makeText(menu.getContext(), text, Toast.LENGTH_LONG).show();
		return false;
	}
	
}
