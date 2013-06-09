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
import cn.sharesdk.douban.Douban;
import cn.sharesdk.evernote.Evernote;
import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.foursquare.FourSquare;
import cn.sharesdk.framework.AbstractWeibo;
import cn.sharesdk.framework.TitleLayout;
import cn.sharesdk.framework.WeiboActionListener;
import cn.sharesdk.kaixin.KaiXin;
import cn.sharesdk.linkedin.LinkedIn;
import cn.sharesdk.netease.microblog.NetEaseMicroBlog;
import cn.sharesdk.onekeyshare.ShareAllGird;
import cn.sharesdk.onekeyshare.SharePage;
import cn.sharesdk.renren.Renren;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.sohu.microblog.SohuMicroBlog;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.twitter.Twitter;
import cn.sharesdk.youdao.YouDao;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

//#if def{lang} == cn
/** 
 * Share SDK接口演示页面，包括演示使用快捷分享完成图文分享、
 *无页面直接分享、授权、关注和不同平台的分享等等功能。
 */
//#endif
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
		pageView.findViewById(R.id.btnShareSh).setOnClickListener(this);
		pageView.findViewById(R.id.btnShareKaiXin).setOnClickListener(this);
		pageView.findViewById(R.id.btnShareYouDao).setOnClickListener(this);
		pageView.findViewById(R.id.btnLinkedIn).setOnClickListener(this);
		pageView.findViewById(R.id.btnShareFourSquare).setOnClickListener(this);
	}
	
	//#if def{lang} == cn
	/** 获取页面View */
	//#endif
	public View getPage() {
		return pageView;
	}
	
	//#if def{lang} == cn
	// 使用快捷分享完成分享
	//#endif
	private void showGrid(boolean silent) {
		Intent i = new Intent(menu.getContext(), ShareAllGird.class);
		// def{note.share.extra.notif_icon.def{lang}}
		i.putExtra("notif_icon", R.drawable.ic_launcher);
		// def{note.share.extra.notif_title.def{lang}}
		i.putExtra("notif_title", menu.getContext().getString(R.string.app_name));
		
		// def{note.share.extra.address.def{lang}}
		i.putExtra("address", "12345678901");
		// def{note.share.extra.title.def{lang}}
		i.putExtra("title", menu.getContext().getString(R.string.share));
		// def{note.share.extra.titleUrl.def{lang}}
		i.putExtra("titleUrl", "http://sharesdk.cn");
		// def{note.share.extra.text.def{lang}}
		i.putExtra("text", menu.getContext().getString(R.string.share_content));
		// def{note.share.extra.imagePath.def{lang}}
		i.putExtra("imagePath", MainActivity.TEST_IMAGE);
		// def{note.share.extra.imageUrl.def{lang}}
		i.putExtra("imageUrl", "http://sharesdk.cn/Public/Frontend/images/v2/logo.png?version=20130502");
		// def{note.share.extra.url.def{lang}}
		i.putExtra("url", "http://sharesdk.cn");
		// def{note.share.extra.thumbPath.def{lang}}
		i.putExtra("thumbPath", MainActivity.TEST_IMAGE);
		// def{note.share.extra.appPath.def{lang}}
		i.putExtra("appPath", MainActivity.TEST_IMAGE);
		// def{note.share.extra.comment.def{lang}}
		i.putExtra("comment", menu.getContext().getString(R.string.share));
		// def{note.share.extra.site.def{lang}}
		i.putExtra("site", menu.getContext().getString(R.string.app_name));
		// def{note.share.extra.siteUrl.def{lang}}
		i.putExtra("siteUrl", "http://sharesdk.cn");
		
		// def{note.share.extra.venueName.def{lang}}
		i.putExtra("venueName", "Southeast in China");
		// def{note.share.extra.venueDescription.def{lang}}
		i.putExtra("venueDescription", "This is a beautiful place!");
		// def{note.share.extra.latitude.def{lang}}
		i.putExtra("latitude", 23.122619f);
		// def{note.share.extra.lontitude.def{lang}}
		i.putExtra("longitude", 113.372338f);
		
		// def{note.share.extra.silent.def{lang}}
		i.putExtra("silent", silent);
		// def{note.share.extra.callback.def{lang}}
		i.putExtra("callback", OneKeyShareCallback.class.getName());
		menu.getContext().startActivity(i);
	}
	
	//#if def{lang} == cn
	// 直接进入分享编辑页面
	//#endif
	private void showShare(final String platform) {
		Intent i = new Intent(menu.getContext(), SharePage.class);
		// def{note.share.extra.notif_icon.def{lang}}
		i.putExtra("notif_icon", R.drawable.ic_launcher);
		// def{note.share.extra.notif_title.def{lang}}
		i.putExtra("notif_title", menu.getContext().getString(R.string.app_name));
		
		// def{note.share.extra.address.def{lang}}
		i.putExtra("address", "12345678901");
		// def{note.share.extra.title.def{lang}}
		i.putExtra("title", menu.getContext().getString(R.string.share));
		// def{note.share.extra.titleUrl.def{lang}}
		i.putExtra("titleUrl", "http://sharesdk.cn");
		// def{note.share.extra.text.def{lang}}
		i.putExtra("text", menu.getContext().getString(R.string.share_content));
		// def{note.share.extra.imagePath.def{lang}}
		i.putExtra("imagePath", MainActivity.TEST_IMAGE);
		// def{note.share.extra.imageUrl.def{lang}}
		i.putExtra("imageUrl", "http://sharesdk.cn/Public/Frontend/images/v2/logo.png?version=20130502");
		// def{note.share.extra.url.def{lang}}
		i.putExtra("url", "http://sharesdk.cn");
		// def{note.share.extra.thumbPath.def{lang}}
		i.putExtra("thumbPath", MainActivity.TEST_IMAGE);
		// def{note.share.extra.appPath.def{lang}}
		i.putExtra("appPath", MainActivity.TEST_IMAGE);
		// def{note.share.extra.comment.def{lang}}
		i.putExtra("comment", menu.getContext().getString(R.string.share));
		// def{note.share.extra.site.def{lang}}
		i.putExtra("site", menu.getContext().getString(R.string.app_name));
		// def{note.share.extra.siteUrl.def{lang}}
		i.putExtra("siteUrl", "http://sharesdk.cn");
		
		// def{note.share.extra.venueName.def{lang}}
		i.putExtra("venueName", "Southeast in China");
		// def{note.share.extra.venueDescription.def{lang}}
		i.putExtra("venueDescription", "This is a beautiful place!");
		// def{note.share.extra.latitude.def{lang}}
		i.putExtra("latitude", 23.122619f);
		// def{note.share.extra.lontitude.def{lang}}
		i.putExtra("longitude", 113.372338f);
		
		// def{note.share.extra.platform.def{lang}}
		i.putExtra("platform", platform);
		// def{note.share.extra.callback.def{lang}}
		i.putExtra("callback", OneKeyShareCallback.class.getName());
		menu.getContext().startActivity(i);
	}
	
	//#if def{lang} == cn
	/** 操作演示的代码集中于此方法 */
	//#endif
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
				// def{note.demo.onclick.btnShareAllGui.def{lang}}
				showGrid(false);
			}
			break;
			case R.id.btnShareAll: {
				// def{note.demo.onclick.btnShareAll.def{lang}}
				showGrid(true);
			}
			break;
			case R.id.btnFlSw: {
				// def{note.demo.onclick.btnFlSw.def{lang}}
				AbstractWeibo weibo = AbstractWeibo.getWeibo(
						menu.getContext(), SinaWeibo.NAME);
				weibo.setWeiboActionListener(this);
				weibo.followFriend(MainAdapter.SDK_SINAWEIBO_UID);
			}
			break;
			case R.id.btnFlTc: {
				// def{note.demo.onclick.btnFlTc.def{lang}}
				AbstractWeibo weibo = AbstractWeibo.getWeibo(
						menu.getContext(), TencentWeibo.NAME);
				weibo.setWeiboActionListener(this);
				weibo.followFriend(MainAdapter.SDK_TENCENTWEIBO_UID);
			}
			break;
			case R.id.btnGetToken: {
				// def{note.demo.onclick.btnGetToken.def{lang}}
				Intent i = new Intent(menu.getContext(), GetTokenPage.class);
				menu.getContext().startActivity(i);
			}
			break;
			case R.id.btnVisitWc: {
				// def{note.demo.onclick.btnVisitWc.def{lang}}
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(MainAdapter.WECHAT_ADDR));
				menu.getContext().startActivity(i);
			}
			break;
			case R.id.btnGetInfor: {
				// def{note.demo.onclick.btnGetInfor.def{lang}}
				Intent i = new Intent(menu.getContext(), GetInforPage.class);
				i.putExtra("type", 0);
				menu.getContext().startActivity(i);
			}
			break;
			case R.id.btnGetUserInfor: {
				// def{note.demo.onclick.btnGetUserInfor.def{lang}}
				Intent i = new Intent(menu.getContext(), GetInforPage.class);
				i.putExtra("type", 1);
				menu.getContext().startActivity(i);
			}
			break;
			case R.id.btnShareSw: {
				// def{note.demo.onclick.btnShareSw.def{lang}}
				showShare(SinaWeibo.NAME);
			}
			break;
			case R.id.btnShareTc: {
				// def{note.demo.onclick.btnShareTc.def{lang}}
				showShare(TencentWeibo.NAME);
			}
			break;
			case R.id.btnShareFb: {
				// def{note.demo.onclick.btnShareFb.def{lang}}
				showShare(Facebook.NAME);
			}
			break;
			case R.id.btnShareTw: {
				// def{note.demo.onclick.btnShareTw.def{lang}}
				showShare(Twitter.NAME);
			}
			break;
			case R.id.btnShareRr: {
				// def{note.demo.onclick.btnShareRr.def{lang}}
				showShare(Renren.NAME);
			}
			break;
			case R.id.btnShareQz: {
				// def{note.demo.onclick.btnShareQz.def{lang}}
				showShare(QZone.NAME);
			}
			break;
			case R.id.btnShareDb: {
				// def{note.demo.onclick.btnShareDb.def{lang}}
				showShare(Douban.NAME);
			}
			break;
			case R.id.btnShareEn: {
				// def{note.demo.onclick.btnShareEn.def{lang}}
				showShare(Evernote.NAME);
			}
			break;
			case R.id.btnShareNemb: {
				// def{note.demo.onclick.btnShareNemb.def{lang}}
				showShare(NetEaseMicroBlog.NAME);
			}
			break;
			case R.id.btnShareSh: {
				// def{note.demo.onclick.btnShareSh.def{lang}}
				showShare(SohuMicroBlog.NAME);
			}
			break;
			case R.id.btnShareKaiXin: {
				// def{note.demo.onclick.btnShareKaiXin.def{lang}}
				showShare(KaiXin.NAME);
			}
			break;
			case R.id.btnLinkedIn: {
				// def{note.demo.onclick.btnShareYouDao.def{lang}}
				showShare(LinkedIn.NAME);
			}
			break;
			case R.id.btnShareYouDao: {
				// def{note.demo.onclick.btnShareYouDao.def{lang}}
				showShare(YouDao.NAME);
			}
			break;
			case R.id.btnShareFourSquare: {
				// def{note.demo.onclick.btnShareFourSquare.def{lang}}
				showShare(FourSquare.NAME);
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
		t.printStackTrace();
		
		Message msg = new Message();
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = weibo;
		handler.sendMessage(msg);
	}
	
	//#if def{lang} == cn
	/** 处理操作结果 */
	//#endif
	public boolean handleMessage(Message msg) {
		AbstractWeibo weibo = (AbstractWeibo) msg.obj;
		String text = MainActivity.actionToString(msg.arg2);
		switch (msg.arg1) {
			case 1: { // def{note.complete.def{lang}}
				text = weibo.getName() + " completed at " + text;
			}
			break;
			case 2: { // def{note.error.def{lang}}
				text = weibo.getName() + " caught error at " + text;
			}
			break;
			case 3: { // def{note.cancel.def{lang}}
				text = weibo.getName() + " canceled at " + text;
			}
			break;
		}
		
		Toast.makeText(menu.getContext(), text, Toast.LENGTH_SHORT).show();
		return false;
	}
	
}
