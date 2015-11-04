/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package cn.sharesdk.demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.Toast;

import java.util.HashMap;

import cn.sharesdk.demo.widget.SlidingMenu;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.TitleLayout;

import com.mob.tools.utils.UIHandler;

/** 微信api的演示页面，展示了“微信好友”、“微信朋友圈”和“微信收藏夹”的接口 */
public class WechatPage extends SlidingMenuPage implements
		OnClickListener, PlatformActionListener {
	private TitleLayout llTitle;
	private CheckedTextView[] ctvPlats;
	private View pageView;

	public WechatPage(SlidingMenu menu) {
		super(menu);
		pageView = getPage();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("BypassApproval", false);
		ShareSDK.setPlatformDevInfo("Wechat", map);
		ShareSDK.setPlatformDevInfo("WechatMoments", map);

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
		vp.setLayoutAnimation(InLayoutAnim.getAnimationController());
		for (int i = 0, size = vp.getChildCount(); i < size; i++) {
			vp.getChildAt(i).setOnClickListener(this);
		}
	}

	protected View initPage() {
		return LayoutInflater.from(getContext()).inflate(R.layout.page_wechate, null);
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
				findViewById(id).setVisibility(View.VISIBLE);
			}
			for (int id : invIds) {
				findViewById(id).setVisibility(View.GONE);
			}
			return;
		}

		Platform plat = null;
		ShareParams sp = getShareParams(v);
		if (ctvPlats[0].isChecked()) {
			plat = ShareSDK.getPlatform("Wechat");
		} else if (ctvPlats[1].isChecked()) {
			plat = ShareSDK.getPlatform("WechatMoments");
		} else {
			plat = ShareSDK.getPlatform("WechatFavorite");
		}
		plat.setPlatformActionListener(this);
		plat.share(sp);
	}

	private ShareParams getShareParams(View v) {
		ShareParams sp = new ShareParams();
		sp.setTitle(getContext().getString(R.string.wechat_demo_title));
		sp.setText(getContext().getString(R.string.share_content));
		sp.setShareType(Platform.SHARE_TEXT);
		switch (v.getId()) {
			case R.id.btnUpload: {
				sp.setShareType(Platform.SHARE_IMAGE);
				sp.setImagePath(MainActivity.TEST_IMAGE);
			}
			break;
			case R.id.btnUploadBm: {
				sp.setShareType(Platform.SHARE_IMAGE);
				Bitmap imageData = BitmapFactory.decodeResource(v.getResources(), R.drawable.ic_launcher);
				sp.setImageData(imageData);
			}
			break;
			case R.id.btnUploadUrl: {
				sp.setShareType(Platform.SHARE_IMAGE);
				sp.setImageUrl("http://www.wyl.cc/wp-content/uploads/2014/02/10060381306b675f5c5.jpg");
			}
			break;
			case R.id.btnEmoji: {
				sp.setShareType(Platform.SHARE_EMOJI);
				sp.setImagePath(MainActivity.TEST_IMAGE);
			}
			break;
			case R.id.btnEmojiUrl: {
				sp.setShareType(Platform.SHARE_EMOJI);
				String imageUrl = "http://f1.sharesdk.cn/imgs/2013/10/17/okvCkwz_144x114.gif";
				sp.setImageUrl(imageUrl);
			}
			break;
			case R.id.btnEmojiBitmap: {
				sp.setShareType(Platform.SHARE_EMOJI);
				Bitmap imageData = BitmapFactory.decodeResource(v.getResources(), R.drawable.ic_launcher);
				sp.setImageData(imageData);
			}
			break;
			case R.id.btnMusic: {
				sp.setShareType(Platform.SHARE_MUSIC);
				String musicUrl = "http://media.ringring.vn/ringtone/realtone/0/0/161/165346.mp3";
				sp.setMusicUrl(musicUrl);
				sp.setUrl("http://www.mob.com");
				sp.setImagePath(MainActivity.TEST_IMAGE);
			}
			break;
			case R.id.btnVideo: {
				sp.setShareType(Platform.SHARE_VIDEO);
				sp.setUrl("http://www.mob.com");
				sp.setImagePath(MainActivity.TEST_IMAGE);
			}
			break;
			case R.id.btnWebpage: {
				sp.setShareType(Platform.SHARE_WEBPAGE);
				sp.setUrl("http://www.mob.com");
				sp.setImagePath(MainActivity.TEST_IMAGE);
			}
			break;
			case R.id.btnWebpageBm: {
				sp.setShareType(Platform.SHARE_WEBPAGE);
				sp.setUrl("http://www.mob.com");
				Bitmap imageData = BitmapFactory.decodeResource(v.getResources(), R.drawable.ic_launcher);
				sp.setImageData(imageData);
			}
			break;
			case R.id.btnWebpageUrl: {
				sp.setShareType(Platform.SHARE_WEBPAGE);
				sp.setUrl("http://www.mob.com");
				sp.setImageUrl(MainActivity.TEST_IMAGE_URL);
			}
			break;
			case R.id.btnApp: {
				sp.setShareType(Platform.SHARE_APPS);
				// 待分享app的本地地址
				sp.setFilePath(MainActivity.TEST_IMAGE);
				String extInfo = "ShareSDK received an app message from wechat client";
				sp.setExtInfo(extInfo);
				sp.setImagePath(MainActivity.TEST_IMAGE);
			}
			break;
			case R.id.btnAppExt: {
				sp.setShareType(Platform.SHARE_APPS);
				// 供微信回调的第三方信息（或者自定义脚本）
				String extInfo = "ShareSDK received an app message from wechat client";
				sp.setExtInfo(extInfo);
				sp.setImagePath(MainActivity.TEST_IMAGE);
			}
			break;
			case R.id.btnFile: {
				sp.setShareType(Platform.SHARE_FILE);
				// 待分享文件的本地地址
				sp.setFilePath(MainActivity.TEST_IMAGE);
				sp.setImagePath(MainActivity.TEST_IMAGE);
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
		UIHandler.sendMessage(msg, this);
	}

	public void onCancel(Platform plat, int action) {
		Message msg = new Message();
		msg.arg1 = 3;
		msg.arg2 = action;
		msg.obj = plat;
		UIHandler.sendMessage(msg, this);
	}

	public void onError(Platform plat, int action, Throwable t) {
		t.printStackTrace();

		Message msg = new Message();
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = t;
		UIHandler.sendMessage(msg, this);
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
					text = getContext().getString(R.string.ssdk_wechat_client_inavailable);
				}
				else if ("WechatTimelineNotSupportedException".equals(msg.obj.getClass().getSimpleName())) {
					text = getContext().getString(R.string.ssdk_wechat_client_inavailable);
				}
				else {
					text = getContext().getString(R.string.ssdk_oks_share_failed);
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

		Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
		return false;
	}

}
