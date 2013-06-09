//#if def{lang} == cn
/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 * 
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */
//#endif

package cn.sharesdk.demo;

import java.util.HashMap;
import m.framework.ui.SlidingMenu;
import cn.sharesdk.framework.AbstractWeibo;
import cn.sharesdk.framework.TitleLayout;
import cn.sharesdk.framework.WeiboActionListener;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import cn.sharesdk.wechat.utils.WechatClientNotExistException;
import cn.sharesdk.wechat.utils.WechatHelper.ShareParams;
import cn.sharesdk.wechat.utils.WechatTimelineNotSupportedException;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckedTextView;
import android.widget.Toast;

//#if def{lang} == cn
/** 微信api的演示页面，展示了“微信好友”和“微信朋友圈”的接口 */
//#endif
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
		pageView.findViewById(R.id.btnUploadBm).setOnClickListener(this);
		pageView.findViewById(R.id.btnMusic).setOnClickListener(this);
		pageView.findViewById(R.id.btnVideo).setOnClickListener(this);
		pageView.findViewById(R.id.btnWebpage).setOnClickListener(this);
		pageView.findViewById(R.id.btnWebpageBm).setOnClickListener(this);
		pageView.findViewById(R.id.btnApp).setOnClickListener(this);
		pageView.findViewById(R.id.btnFile).setOnClickListener(this);
	}
	
	//#if def{lang} == cn
	/** 获取页面的View实例 */
	//#endif
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
		ShareParams sp = ctvStWm.isChecked() ? 
				new WechatMoments.ShareParams() : new Wechat.ShareParams();
		sp.title = menu.getContext().getString(R.string.wechat_demo_title);
		sp.text = menu.getContext().getString(R.string.share_content);
		sp.shareType = AbstractWeibo.SHARE_TEXT;
		switch (v.getId()) {
			case R.id.btnUpload: {
				sp.shareType = AbstractWeibo.SHARE_IMAGE;
				sp.imagePath = MainActivity.TEST_IMAGE;
			}
			break;
			case R.id.btnUploadBm: {
				sp.shareType = AbstractWeibo.SHARE_IMAGE;
				sp.imageData = BitmapFactory.decodeResource(v.getResources(), R.drawable.ic_launcher);
			}
			break;
			case R.id.btnMusic: {
				sp.shareType = AbstractWeibo.SHARE_MUSIC;
				sp.url = "http://119.188.72.27/2/file.data.vdisk.me/37414786/" +
						"1f486ed742895fd9e6487568047a20b29a9d0df0?ip=1364785283,10.73.26.26&" +
						"ssig=1iVh5iRmC%2B&Expires=1364784083&KID=sae,l30zoo1wmz&fn=" +
						"%E9%93%83%E5%A3%B0%E6%8A%A5%E5%91%8A%E5%A4%A7%E7%8E%8B.mp3";
				sp.thumbPath = MainActivity.TEST_IMAGE;
			}
			break;
			case R.id.btnVideo: {
				sp.shareType = AbstractWeibo.SHARE_VIDEO;
				sp.url = "http://t.cn/zT7cZAo";
				sp.thumbPath = MainActivity.TEST_IMAGE;
			}
			break;
			case R.id.btnWebpage: {
				sp.shareType = AbstractWeibo.SHARE_WEBPAGE;
				sp.url = "http://t.cn/zT7cZAo";
				sp.thumbPath = MainActivity.TEST_IMAGE;
			}
			break;
			case R.id.btnWebpageBm: {
				sp.shareType = AbstractWeibo.SHARE_WEBPAGE;
				sp.url = "http://t.cn/zT7cZAo";
				sp.thumbData = BitmapFactory.decodeResource(v.getResources(), R.drawable.ic_launcher);
			}
			break;
			case R.id.btnApp: {
				sp.shareType = AbstractWeibo.SHARE_APPS;
				sp.appPath = MainActivity.TEST_IMAGE; // def{note.app_local_path.def{lang}}
				sp.thumbPath = MainActivity.TEST_IMAGE;
			}
			break;
			case R.id.btnFile: {
				sp.shareType = AbstractWeibo.SHARE_FILE;
				sp.appPath = MainActivity.TEST_IMAGE; // def{note.app_local_path.def{lang}}
				sp.thumbPath = MainActivity.TEST_IMAGE;
			}
		}
		weibo.share(sp);
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
		String text = MainActivity.actionToString(msg.arg2);
		switch (msg.arg1) {
			case 1: { // def{note.complete.def{lang}}
				AbstractWeibo weibo = (AbstractWeibo) msg.obj;
				text = weibo.getName() + " completed at " + text;
			}
			break;
			case 2: { // def{note.error.def{lang}}
				if (msg.obj instanceof WechatClientNotExistException) {
					text = menu.getContext().getString(R.string.wechat_client_inavailable);
				}
				else if (msg.obj instanceof WechatTimelineNotSupportedException) {
					text = menu.getContext().getString(R.string.wechat_client_inavailable);
				}
				else {
					text = menu.getContext().getString(R.string.share_failed);
				}
			}
			break;
			case 3: { // def{note.cancel.def{lang}}
				AbstractWeibo weibo = (AbstractWeibo) msg.obj;
				text = weibo.getName() + " canceled at " + text;
			}
			break;
		}
		
		Toast.makeText(menu.getContext(), text, Toast.LENGTH_LONG).show();
		return false;
	}
	
}
