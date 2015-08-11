/*
 * Offical Website:http://www.mob.com
 * Support QQ: 4006852216
 * Offical Wechat Account:ShareSDK   (We will inform you our updated news at the first time by Wechat, if we release a new version. If you get any problem, you can also contact us with Wechat, we will reply you within 24 hours.)
 *
 * Copyright (c) 2013 mob.com. All rights reserved.
 */

package cn.sharesdk.demo;

import java.util.HashMap;

import cn.sharesdk.demo.widget.SlidingMenu;
import cn.sharesdk.framework.Platform;

import com.mob.tools.utils.UIHandler;

import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.TitleLayout;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.Toast;

/** page to show Yixin apis. */
public class AlipayPage extends SlidingMenuPage implements
		OnClickListener, PlatformActionListener {
	private TitleLayout llTitle;
	private CheckedTextView[] ctvPlats;
	private View pageView;

	public AlipayPage(SlidingMenu menu) {
		super(menu);
		pageView = getPage();

		llTitle = (TitleLayout) pageView.findViewById(R.id.llTitle);
		llTitle.getBtnBack().setOnClickListener(this);
		llTitle.getTvTitle().setText(R.string.sm_item_alipay);

		ctvPlats = new CheckedTextView[] {
				(CheckedTextView) pageView.findViewById(R.id.ctvStWc),
				(CheckedTextView) pageView.findViewById(R.id.ctvStWm),
				(CheckedTextView) pageView.findViewById(R.id.ctvStWf)
		};
		for (View v : ctvPlats) {
			v.setVisibility(View.GONE);
		}

		ctvPlats = new CheckedTextView[] {
				(CheckedTextView) pageView.findViewById(R.id.ctvStWc),
				(CheckedTextView) pageView.findViewById(R.id.ctvStWm),
				(CheckedTextView) pageView.findViewById(R.id.ctvStWf)
		};
		for (int i = 0; i < ctvPlats.length; i++) {
			ctvPlats[i].setVisibility(View.GONE);
		}

		//设置点击事件
		ViewGroup vp = (ViewGroup) ctvPlats[0].getParent().getParent();
		vp.setLayoutAnimation(InLayoutAnim.getAnimationController());
		for (int i = 0, size = vp.getChildCount(); i < size; i++) {
			vp.getChildAt(i).setOnClickListener(this);
		}

		int[] invIds = new int[] {
				R.id.btnMusic,
				R.id.btnVideo,
				R.id.btnEmoji,
				R.id.btnEmojiUrl,
				R.id.btnEmojiBitmap,
				R.id.btnApp,
				R.id.btnAppExt,
				R.id.btnFile
		};
		for (int id : invIds) {
			pageView.findViewById(id).setVisibility(View.GONE);
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

		Platform plat = ShareSDK.getPlatform("Alipay");
		if(!plat.isClientValid()){
			Toast.makeText(getContext(), R.string.alipay_client_inavailable, Toast.LENGTH_LONG).show();
			return;
		}

		ShareParams sp = getShareParams(v);
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
				sp.setImageUrl(MainActivity.TEST_IMAGE_URL);
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
				// success
				Platform plat = (Platform) msg.obj;
				text = plat.getName() + " completed at " + text;
			}
			break;
			case 2: {
				// failed
				if ("AlipayClientNotExistException".equals(msg.obj.getClass().getSimpleName())) {
					text = getContext().getString(R.string.alipay_client_inavailable);
				} else if ("AlipayNotSupportedException".equals(msg.obj.getClass().getSimpleName())) {
					text = getContext().getString(R.string.alipay_client_inavailable);
				} else {
					text = getContext().getString(R.string.share_failed);
				}
			}
			break;
			case 3: {
				// canceled
				Platform plat = (Platform) msg.obj;
				text = plat.getName() + " canceled at " + text;
			}
			break;
		}

		Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
		return false;
	}

}
