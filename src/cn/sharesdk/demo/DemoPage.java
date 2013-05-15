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
import cn.sharesdk.sohu.microblog.SohuMicroBlog;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.twitter.Twitter;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

/** 
 * Share SDK接口演示页面，包括演示使用快捷分享完成图文分享、
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
		pageView.findViewById(R.id.btnShareSh).setOnClickListener(this);
	}
	
	/** 获取页面View */
	public View getPage() {
		return pageView;
	}
	
	// 使用快捷分享完成图文分享
	private void showGrid(boolean silent) {
		Intent i = new Intent(menu.getContext(), ShareAllGird.class);
		// 分享时Notification的图标
		i.putExtra("notif_icon", R.drawable.ic_launcher);
		// 分享时Notification的标题
		i.putExtra("notif_title", menu.getContext().getString(R.string.app_name));
		
		// address是接收人地址，仅在信息和邮件使用，否则可以不提供
		i.putExtra("address", "12345678901");
		// title标题，在印象笔记、邮箱、信息、微信（包括好友和朋友圈）、人人网和QQ空间使用，否则可以不提供
		i.putExtra("title", menu.getContext().getString(R.string.share));
		// titleUrl是标题的网络链接，仅在QQ空间使用，否则可以不提供
		i.putExtra("titleUrl", "http://sharesdk.cn");
		// text是分享文本，所有平台都需要这个字段
		i.putExtra("text", menu.getContext().getString(R.string.share_content));
		// imagePath是本地的图片路径，所有平台都支持这个字段，不提供，则表示不分享图片
		i.putExtra("imagePath", MainActivity.TEST_IMAGE);
		// url仅在人人网和微信（包括好友和朋友圈）中使用，否则可以不提供
		i.putExtra("url", "http://sharesdk.cn");
		// thumbPath是缩略图的本地路径，仅在微信（包括好友和朋友圈）中使用，否则可以不提供
		i.putExtra("thumbPath", MainActivity.TEST_IMAGE);
		// appPath是待分享应用程序的本地路劲，仅在微信（包括好友和朋友圈）中使用，否则可以不提供
		i.putExtra("appPath", MainActivity.TEST_IMAGE);
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用，否则可以不提供
		i.putExtra("comment", menu.getContext().getString(R.string.share));
		// site是分享此内容的网站名称，仅在QQ空间使用，否则可以不提供
		i.putExtra("site", menu.getContext().getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用，否则可以不提供
		i.putExtra("siteUrl", "http://sharesdk.cn");
		
		// 是否直接分享
		i.putExtra("silent", silent);
		menu.getContext().startActivity(i);
	}
	
	// 使用快捷分享完成直接分享
	private void showShare(final String platform) {
		Intent i = new Intent(menu.getContext(), SharePage.class);
		// 分享时Notification的图标
		i.putExtra("notif_icon", R.drawable.ic_launcher);
		// 分享时Notification的标题
		i.putExtra("notif_title", menu.getContext().getString(R.string.app_name));
		
		// address是接收人地址，仅在信息和邮件使用，否则可以不提供
		i.putExtra("address", "12345678901");
		// title标题，在印象笔记、邮箱、信息、微信（包括好友和朋友圈）、人人网和QQ空间使用，否则可以不提供
		i.putExtra("title", menu.getContext().getString(R.string.share));
		// titleUrl是标题的网络链接，仅在QQ空间使用，否则可以不提供
		i.putExtra("titleUrl", "http://sharesdk.cn");
		// text是分享文本，所有平台都需要这个字段
		i.putExtra("text", menu.getContext().getString(R.string.share_content));
		// imagePath是本地的图片路径，在豆瓣、Facebook、网易微博、新浪微博、腾讯微博、Twitter、邮箱、
		// 信息和微信（包括好友和朋友圈）图片分享中使用，否则可以不提供
		i.putExtra("imagePath", MainActivity.TEST_IMAGE);
		// imageUrl是网络的图片路径，仅在人人网和QQ空间使用，否则可以不提供
		i.putExtra("imageUrl", "http://sharesdk.cn/Public/Frontend/images/logo.png");
		// url仅在人人网和微信（包括好友和朋友圈）中使用，否则可以不提供
		i.putExtra("url", "http://sharesdk.cn");
		// thumbPath是缩略图的本地路径，仅在微信（包括好友和朋友圈）中使用，否则可以不提供
		i.putExtra("thumbPath", MainActivity.TEST_IMAGE);
		// appPath是待分享应用程序的本地路劲，仅在微信（包括好友和朋友圈）中使用，否则可以不提供
		i.putExtra("appPath", MainActivity.TEST_IMAGE);
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用，否则可以不提供
		i.putExtra("comment", menu.getContext().getString(R.string.share));
		// site是分享此内容的网站名称，仅在QQ空间使用，否则可以不提供
		i.putExtra("site", menu.getContext().getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用，否则可以不提供
		i.putExtra("siteUrl", "http://sharesdk.cn");
		
		// 是平台名称
		i.putExtra("platform", platform);
		menu.getContext().startActivity(i);
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
				showGrid(false);
			}
			break;
			case R.id.btnShareAll: {
				// 直接分享
				showGrid(true);
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
				showShare(SinaWeibo.NAME);
			}
			break;
			case R.id.btnShareTc: {
				// 分享到腾讯微博
				showShare(TencentWeibo.NAME);
			}
			break;
			case R.id.btnShareFb: {
				// 分享到facebook
				showShare(Facebook.NAME);
			}
			break;
			case R.id.btnShareTw: {
				// 分享到twitter
				showShare(Twitter.NAME);
			}
			break;
			case R.id.btnShareRr: {
				// 分享到人人网
				showShare(Renren.NAME);
			}
			break;
			case R.id.btnShareQz: {
				// 分享到qq空间
				showShare(QZone.NAME);
			}
			break;
			case R.id.btnShareDb: {
				// 分享到豆瓣
				showShare(Douban.NAME);
			}
			break;
			case R.id.btnShareEn: {
				// 分享到印象笔记
				showShare(Evernote.NAME);
			}
			break;
			case R.id.btnShareNemb: {
				// 分享到网易微博
				showShare(NetEaseMicroBlog.NAME);
			}
			break;
			case R.id.btnShareSh: {
				// 分享到搜狐微博
				showShare(SohuMicroBlog.NAME);
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
	
	/** 处理操作结果 */
	public boolean handleMessage(Message msg) {
		AbstractWeibo weibo = (AbstractWeibo) msg.obj;
		String text = MainActivity.actionToString(msg.arg2);
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
