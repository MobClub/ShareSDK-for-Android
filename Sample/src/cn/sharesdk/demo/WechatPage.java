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
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.TitleLayout;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import cn.sharesdk.wechat.utils.WechatClientNotExistException;
import cn.sharesdk.wechat.utils.WechatTimelineNotSupportedException;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.Toast;

/** 微信api的演示页面，展示了“微信好友”和“微信朋友圈”的接口 */
public class WechatPage extends SlidingMenuPage implements
		OnClickListener, PlatformActionListener {
	private TitleLayout llTitle;
	private CheckedTextView ctvStWm;
	private View pageView;

	public WechatPage(SlidingMenu menu) {
		super(menu);
		pageView = getPage();

		llTitle = (TitleLayout) pageView.findViewById(R.id.llTitle);
		llTitle.getBtnBack().setOnClickListener(this);
		llTitle.getTvTitle().setText(R.string.sm_item_wechat);

		ctvStWm = (CheckedTextView) pageView.findViewById(R.id.ctvStWm);
		ViewGroup vp = (ViewGroup) ctvStWm.getParent();
		for (int i = 0, size = vp.getChildCount(); i < size; i++) {
			vp.getChildAt(i).setOnClickListener(this);
		}
	}

	protected View initPage() {
		return LayoutInflater.from(menu.getContext())
				.inflate(R.layout.page_wechate, null);
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
			pageView.findViewById(R.id.btnApp).setVisibility(
					ctvStWm.isChecked() ? View.GONE : View.VISIBLE);
			pageView.findViewById(R.id.btnAppExt).setVisibility(
					ctvStWm.isChecked() ? View.GONE : View.VISIBLE);
			pageView.findViewById(R.id.btnFile).setVisibility(
					ctvStWm.isChecked() ? View.GONE : View.VISIBLE);
			return;
		}

		String name = ctvStWm.isChecked() ? WechatMoments.NAME : Wechat.NAME;
		Platform plat = ShareSDK.getPlatform(menu.getContext(), name);
		plat.setPlatformActionListener(this);
		ShareParams sp = ctvStWm.isChecked() ?
				getWechatMomentsShareParams(v) : getWechatShareParams(v);
		plat.share(sp);
	}

	private ShareParams getWechatShareParams(View v) {
		Wechat.ShareParams sp = new Wechat.ShareParams();
		sp.title = menu.getContext().getString(R.string.wechat_demo_title);
		sp.text = menu.getContext().getString(R.string.share_content);
		sp.shareType = Platform.SHARE_TEXT;
		switch (v.getId()) {
			case R.id.btnUpload: {
				sp.shareType = Platform.SHARE_IMAGE;
				sp.imagePath = MainActivity.TEST_IMAGE;
			}
			break;
			case R.id.btnUploadBm: {
				sp.shareType = Platform.SHARE_IMAGE;
				sp.imageData = BitmapFactory.decodeResource(v.getResources(), R.drawable.ic_launcher);
			}
			break;
			case R.id.btnUploadUrl: {
				sp.shareType = Platform.SHARE_IMAGE;
				sp.imageUrl = "http://img.appgo.cn/imgs/sharesdk/content/2013/07/16/1373959974649.png";
			}
			break;
			case R.id.btnMusic: {
				sp.shareType = Platform.SHARE_MUSIC;
				sp.musicUrl = "http://staff2.ustc.edu.cn/~wdw/softdown/index.asp/0042515_05.ANDY.mp3";
				sp.url = "http://sharesdk.cn";
				sp.imagePath = MainActivity.TEST_IMAGE;
			}
			break;
			case R.id.btnVideo: {
				sp.shareType = Platform.SHARE_VIDEO;
				sp.url = "http://t.cn/zT7cZAo";
				sp.imagePath = MainActivity.TEST_IMAGE;
			}
			break;
			case R.id.btnWebpage: {
				sp.shareType = Platform.SHARE_WEBPAGE;
				sp.url = "http://t.cn/zT7cZAo";
				sp.imagePath = MainActivity.TEST_IMAGE;
			}
			break;
			case R.id.btnWebpageBm: {
				sp.shareType = Platform.SHARE_WEBPAGE;
				sp.url = "http://t.cn/zT7cZAo";
				sp.imageData = BitmapFactory.decodeResource(v.getResources(), R.drawable.ic_launcher);
			}
			break;
			case R.id.btnWebpageUrl: {
				sp.shareType = Platform.SHARE_WEBPAGE;
				sp.url = "http://t.cn/zT7cZAo";
				sp.imageUrl = "http://img.appgo.cn/imgs/sharesdk/content/2013/07/16/1373959974649.png";
			}
			break;
			case R.id.btnApp: {
				sp.shareType = Platform.SHARE_APPS;
				sp.appPath = MainActivity.TEST_IMAGE; // app的本地地址
				sp.extInfo = "Share SDK received an app message from wechat client";
				sp.imagePath = MainActivity.TEST_IMAGE;
			}
			break;
			case R.id.btnAppExt: {
				sp.shareType = Platform.SHARE_APPS;
				sp.extInfo = "Share SDK received an app message from wechat client";  // 供微信回调的第三方信息（或者自定义脚本）
				sp.imagePath = MainActivity.TEST_IMAGE;
			}
			break;
			case R.id.btnFile: {
				sp.shareType = Platform.SHARE_FILE;
				sp.appPath = MainActivity.TEST_IMAGE; // app的本地地址
				sp.imagePath = MainActivity.TEST_IMAGE;
			}
		}
		return sp;
	}

	private ShareParams getWechatMomentsShareParams(View v) {
		WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
		sp.title = menu.getContext().getString(R.string.wechat_demo_title);
		sp.text = menu.getContext().getString(R.string.share_content);
		sp.shareType = Platform.SHARE_TEXT;
		switch (v.getId()) {
			case R.id.btnUpload: {
				sp.shareType = Platform.SHARE_IMAGE;
				sp.imagePath = MainActivity.TEST_IMAGE;
			}
			break;
			case R.id.btnUploadBm: {
				sp.shareType = Platform.SHARE_IMAGE;
				sp.imageData = BitmapFactory.decodeResource(v.getResources(), R.drawable.ic_launcher);
			}
			break;
			case R.id.btnUploadUrl: {
				sp.shareType = Platform.SHARE_IMAGE;
				sp.imageUrl = "http://img.appgo.cn/imgs/sharesdk/content/2013/07/16/1373959974649.png";
			}
			break;
			case R.id.btnMusic: {
				sp.shareType = Platform.SHARE_MUSIC;
				sp.musicUrl = "http://staff2.ustc.edu.cn/~wdw/softdown/index.asp/0042515_05.ANDY.mp3";
				sp.url = "http://sharesdk.cn";
				sp.imagePath = MainActivity.TEST_IMAGE;
			}
			break;
			case R.id.btnVideo: {
				sp.shareType = Platform.SHARE_VIDEO;
				sp.url = "http://t.cn/zT7cZAo";
				sp.imagePath = MainActivity.TEST_IMAGE;
			}
			break;
			case R.id.btnWebpage: {
				sp.shareType = Platform.SHARE_WEBPAGE;
				sp.url = "http://t.cn/zT7cZAo";
				sp.imagePath = MainActivity.TEST_IMAGE;
			}
			break;
			case R.id.btnWebpageBm: {
				sp.shareType = Platform.SHARE_WEBPAGE;
				sp.url = "http://t.cn/zT7cZAo";
				sp.imageData = BitmapFactory.decodeResource(v.getResources(), R.drawable.ic_launcher);
			}
			break;
			case R.id.btnWebpageUrl: {
				sp.shareType = Platform.SHARE_WEBPAGE;
				sp.url = "http://t.cn/zT7cZAo";
				sp.imageUrl = "http://img.appgo.cn/imgs/sharesdk/content/2013/07/16/1373959974649.png";
			}
			break;
		}
		return sp;
	}

	public void onComplete(Platform plat, int action,
			HashMap<String, Object> res) {
		Message msg = new Message();
		msg.arg1 = 1;
		msg.arg2 = action;
		msg.obj = plat;
		handler.sendMessage(msg);
	}

	public void onCancel(Platform plat, int action) {
		Message msg = new Message();
		msg.arg1 = 3;
		msg.arg2 = action;
		msg.obj = plat;
		handler.sendMessage(msg);
	}

	public void onError(Platform plat, int action, Throwable t) {
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
			case 1: { // 成功
				Platform plat = (Platform) msg.obj;
				text = plat.getName() + " completed at " + text;
			}
			break;
			case 2: { // 失败
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
			case 3: { // 取消
				Platform plat = (Platform) msg.obj;
				text = plat.getName() + " canceled at " + text;
			}
			break;
		}

		Toast.makeText(menu.getContext(), text, Toast.LENGTH_LONG).show();
		return false;
	}

}
