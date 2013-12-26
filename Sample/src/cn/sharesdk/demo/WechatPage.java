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
import cn.sharesdk.wechat.favorite.WechatFavorite;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.Toast;

/** 微信api的演示页面，展示了“微信好友”、“微信朋友圈”和“微信收藏夹”的接口 */
public class WechatPage extends SlidingMenuPage implements
		OnClickListener, PlatformActionListener {
	private TitleLayout llTitle;
	private CheckedTextView[] ctvPlats;
	private View pageView;

	public WechatPage(SlidingMenu menu) {
		super(menu);
		pageView = getPage();

		llTitle = (TitleLayout) pageView.findViewById(R.id.llTitle);
		llTitle.getBtnBack().setOnClickListener(this);
		llTitle.getTvTitle().setText(R.string.sm_item_wechat);

		ctvPlats = new CheckedTextView[] {
				(CheckedTextView) pageView.findViewById(R.id.ctvStWc),
				(CheckedTextView) pageView.findViewById(R.id.ctvStWm),
				(CheckedTextView) pageView.findViewById(R.id.ctvStWf)
		};
		ctvPlats[0].setChecked(true);
		for (View v : ctvPlats) {
			v.setOnClickListener(this);
		}
		ViewGroup vp = (ViewGroup) ctvPlats[0].getParent().getParent();
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

		if (v instanceof CheckedTextView) {
			for (CheckedTextView ctv : ctvPlats) {
				ctv.setChecked(ctv.equals(v));
			}

			int[] visIds = null;
			int[] invIds = null;
			if (v.equals(ctvPlats[0])) {
				visIds = new int[] {
						R.id.btnUpdate,
						R.id.btnUpload,
						R.id.btnUploadBm,
						R.id.btnUploadUrl,
						R.id.btnEmoji,
						R.id.btnEmojiUrl,
						R.id.btnEmojiBitmap,
						R.id.btnMusic,
						R.id.btnVideo,
						R.id.btnWebpage,
						R.id.btnWebpageBm,
						R.id.btnWebpageUrl,
						R.id.btnApp,
						R.id.btnAppExt,
						R.id.btnFile
				};
				invIds = new int[] {};
			} else if (v.equals(ctvPlats[1])) {
				visIds = new int[] {
						R.id.btnUpdate,
						R.id.btnUpload,
						R.id.btnUploadBm,
						R.id.btnUploadUrl,
						R.id.btnMusic,
						R.id.btnVideo,
						R.id.btnWebpage,
						R.id.btnWebpageBm,
						R.id.btnWebpageUrl
				};
				invIds = new int[] {
						R.id.btnEmoji,
						R.id.btnEmojiUrl,
						R.id.btnEmojiBitmap,
						R.id.btnApp,
						R.id.btnAppExt,
						R.id.btnFile
				};
			} else {
				visIds = new int[] {
						R.id.btnUpdate,
						R.id.btnUpload,
						R.id.btnUploadBm,
						R.id.btnUploadUrl,
						R.id.btnMusic,
						R.id.btnVideo,
						R.id.btnWebpage,
						R.id.btnWebpageBm,
						R.id.btnWebpageUrl,
						R.id.btnFile
				};
				invIds = new int[] {
						R.id.btnEmoji,
						R.id.btnEmojiUrl,
						R.id.btnEmojiBitmap,
						R.id.btnApp,
						R.id.btnAppExt
				};
			}

			for (int id : visIds) {
				menu.findViewById(id).setVisibility(View.VISIBLE);
			}
			for (int id : invIds) {
				menu.findViewById(id).setVisibility(View.GONE);
			}
			return;
		}

		Platform plat = null;
		ShareParams sp = null;
		if (ctvPlats[0].isChecked()) {
			plat = ShareSDK.getPlatform(menu.getContext(), "Wechat");
			sp = getWechatShareParams(v);
		} else if (ctvPlats[1].isChecked()) {
			plat = ShareSDK.getPlatform(menu.getContext(), "WechatMoments");
			sp = getWechatMomentsShareParams(v);
		} else {
			plat = ShareSDK.getPlatform(menu.getContext(), "WechatFavorite");
			sp = getWechatFavoriteShareParams(v);
		}
		plat.setPlatformActionListener(this);
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
			case R.id.btnEmoji: {
				sp.shareType = Platform.SHARE_EMOJI;
				sp.imagePath = MainActivity.TEST_IMAGE;
			}
			break;
			case R.id.btnEmojiUrl: {
				sp.shareType = Platform.SHARE_EMOJI;
				sp.imageUrl = "http://f1.sharesdk.cn/imgs/2013/10/17/okvCkwz_144x114.gif";
			}
			break;
			case R.id.btnEmojiBitmap: {
				sp.shareType = Platform.SHARE_EMOJI;
				sp.imageData = BitmapFactory.decodeResource(v.getResources(), R.drawable.ic_launcher);
			}
			break;
			case R.id.btnMusic: {
				sp.shareType = Platform.SHARE_MUSIC;
				sp.musicUrl = "http://ubuntuone.com/45XSEOwdODtXSH0WYGAcR7";
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
				// 待分享app的本地地址
				sp.filePath = MainActivity.TEST_IMAGE;
				sp.extInfo = "ShareSDK received an app message from wechat client";
				sp.imagePath = MainActivity.TEST_IMAGE;
			}
			break;
			case R.id.btnAppExt: {
				sp.shareType = Platform.SHARE_APPS;
				// 供微信回调的第三方信息（或者自定义脚本）
				sp.extInfo = "ShareSDK received an app message from wechat client";
				sp.imagePath = MainActivity.TEST_IMAGE;
			}
			break;
			case R.id.btnFile: {
				sp.shareType = Platform.SHARE_FILE;
				// 待分享文件的本地地址
				sp.filePath = MainActivity.TEST_IMAGE;
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
			case R.id.btnEmoji: {
				sp.shareType = Platform.SHARE_EMOJI;
				sp.imagePath = MainActivity.TEST_IMAGE;
			}
			break;
			case R.id.btnEmojiUrl: {
				sp.shareType = Platform.SHARE_EMOJI;
				sp.imageUrl = "http://f1.sharesdk.cn/imgs/2013/10/17/okvCkwz_144x114.gif";
			}
			break;
			case R.id.btnEmojiBitmap: {
				sp.shareType = Platform.SHARE_EMOJI;
				sp.imageData = BitmapFactory.decodeResource(v.getResources(), R.drawable.ic_launcher);
			}
			break;
			case R.id.btnMusic: {
				sp.shareType = Platform.SHARE_MUSIC;
				sp.musicUrl = "http://ubuntuone.com/45XSEOwdODtXSH0WYGAcR7";
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

	private ShareParams getWechatFavoriteShareParams(View v) {
		WechatFavorite.ShareParams sp = new WechatFavorite.ShareParams();
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
				sp.musicUrl = "http://ubuntuone.com/45XSEOwdODtXSH0WYGAcR7";
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
			case R.id.btnFile: {
				sp.shareType = Platform.SHARE_FILE;
				// 待分享文件的本地地址
				sp.filePath = MainActivity.TEST_IMAGE;
				sp.imagePath = MainActivity.TEST_IMAGE;
			}
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
			case 1: {
				// 成功
				Platform plat = (Platform) msg.obj;
				text = plat.getName() + " completed at " + text;
			}
			break;
			case 2: {
				// 失败
				if ("WechatClientNotExistException".equals(msg.obj.getClass().getSimpleName())) {
					text = menu.getContext().getString(R.string.wechat_client_inavailable);
				}
				else if ("WechatTimelineNotSupportedException".equals(msg.obj.getClass().getSimpleName())) {
					text = menu.getContext().getString(R.string.wechat_client_inavailable);
				}
				else {
					text = menu.getContext().getString(R.string.share_failed);
				}
			}
			break;
			case 3: {
				// 取消
				Platform plat = (Platform) msg.obj;
				text = plat.getName() + " canceled at " + text;
			}
			break;
		}

		Toast.makeText(menu.getContext(), text, Toast.LENGTH_LONG).show();
		return false;
	}

}
