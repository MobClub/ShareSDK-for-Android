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

import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.Toast;
import cn.sharesdk.demo.widget.SlidingMenu;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.TitleLayout;

import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.UIHandler;

//#if def{lang} == cn
/** 易信api的演示页面，展示了“易信好友”和“易信朋友圈”的接口 */
//#elif def{lang} == en
/** page to show Yixin apis. */
//#endif
public class YixinPage extends SlidingMenuPage implements 
		OnClickListener, PlatformActionListener {
	private TitleLayout llTitle;
	private CheckedTextView[] ctvPlats;
	private View pageView;
	
	public YixinPage(SlidingMenu menu) {
		super(menu);
		pageView = getPage();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("BypassApproval", false);
		ShareSDK.setPlatformDevInfo("Yixin", map);
		ShareSDK.setPlatformDevInfo("YixinMoments", map);
		
		llTitle = (TitleLayout) pageView.findViewById(R.id.llTitle);
		llTitle.getBtnBack().setOnClickListener(this);
		llTitle.getTvTitle().setText(R.string.sm_item_yixin);
		
		ctvPlats = new CheckedTextView[] {
				(CheckedTextView) pageView.findViewById(R.id.ctvStWc),
				(CheckedTextView) pageView.findViewById(R.id.ctvStWm),
				(CheckedTextView) pageView.findViewById(R.id.ctvStWf)
		};
		int[] labels = new int[] {
				R.string.share_to_yixin,
				R.string.share_to_yixin_moment,
				R.string.share_to_yixin_favorite
		};
		for (int i = 0; i < ctvPlats.length; i++) {
			ctvPlats[i].setText(labels[i]);
			ctvPlats[i].setOnClickListener(this);
		}
		ViewGroup vp = (ViewGroup) ctvPlats[0].getParent().getParent();
		vp.setLayoutAnimation(InLayoutAnim.getAnimationController());
		for (int i = 0, size = vp.getChildCount(); i < size; i++) {
			vp.getChildAt(i).setOnClickListener(this);
		}
		
		ctvPlats[0].setChecked(true);
		int[] invIds = new int[] {
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
		ctvPlats[2].setVisibility(View.GONE);
	}
	
	protected View initPage() {
		return LayoutInflater.from(getContext()).inflate(R.layout.page_wechate, null);
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
			plat = ShareSDK.getPlatform("Yixin");
		} else if (ctvPlats[1].isChecked()) {
			plat = ShareSDK.getPlatform("YixinMoments");
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
				sp.setImagePath(MainActivity.testImage);
			} break;
			case R.id.btnUploadBm: {
				sp.setShareType(Platform.SHARE_IMAGE);
				Bitmap imageData = BitmapFactory.decodeResource(v.getResources(), R.drawable.ic_launcher);
				sp.setImageData(imageData);
			} break;
			case R.id.btnUploadUrl: {
				sp.setShareType(Platform.SHARE_IMAGE);
				sp.setImageUrl(MainActivity.testImageUrl);
			} break;
			case R.id.btnMusic: {
				sp.setShareType(Platform.SHARE_MUSIC);
				String musicUrl = "http://ubuntuone.com/45XSEOwdODtXSH0WYGAcR7";
				sp.setMusicUrl(musicUrl);
				sp.setUrl("http://www.mob.com");
				sp.setImagePath(MainActivity.testImage);
			} break;
			case R.id.btnVideo: {
				sp.setShareType(Platform.SHARE_VIDEO);
				sp.setUrl("http://www.mob.com");
				sp.setImagePath(MainActivity.testImage);
			} break;
			case R.id.btnWebpage: {
				sp.setShareType(Platform.SHARE_WEBPAGE);
				sp.setUrl("http://www.mob.com");
				sp.setImagePath(MainActivity.testImage);
			} break;
			case R.id.btnWebpageBm: {
				sp.setShareType(Platform.SHARE_WEBPAGE);
				sp.setUrl("http://www.mob.com");
				Bitmap imageData = BitmapFactory.decodeResource(v.getResources(), R.drawable.ic_launcher);
				sp.setImageData(imageData);
			} break;
			case R.id.btnWebpageUrl: {
				sp.setShareType(Platform.SHARE_WEBPAGE);
				sp.setUrl("http://www.mob.com");
				sp.setImageUrl(MainActivity.testImageUrl);
			} break;
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
				//#if def{lang} == cn
				// 成功
				//#elif def{lang} == en
				// success
				//#endif
				int resId = ResHelper.getStringRes(getContext(), "ssdk_oks_share_completed");
				if (resId > 0) {
					text = getContext().getString(resId);
				}
			} break;
			case 2: {
				//#if def{lang} == cn
				// 失败
				//#elif def{lang} == en
				// failed
				//#endif
				if ("YixinClientNotExistException".equals(msg.obj.getClass().getSimpleName())) {
					text = getContext().getString(R.string.ssdk_yixin_client_inavailable);
				} else if ("YixinClientNotExistException".equals(msg.obj.getClass().getSimpleName())) {
					text = getContext().getString(R.string.ssdk_yixin_client_inavailable);
				} else {
					text = getContext().getString(R.string.ssdk_oks_share_failed);
				}
			} break;
			case 3: {
				//#if def{lang} == cn
				// 取消
				//#elif def{lang} == en
				// canceled
				//#endif
				int resId = ResHelper.getStringRes(getContext(), "ssdk_oks_share_canceled");
				if (resId > 0) {
					text = getContext().getString(resId);
				}
			} break;
		}
		
		Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
		return false;
	}
	
}
