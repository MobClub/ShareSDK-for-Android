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
import cn.sharesdk.renren.Renren;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.sohu.microblog.SohuMicroBlog;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.twitter.Twitter;
import cn.sharesdk.youdao.YouDao;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 * Share SDK接口演示页面，包括演示使用快捷分享完成图文分享、
 *无页面直接分享、授权、关注和不同平台的分享等等功能。
 */
public class DemoPage extends SlidingMenuPage implements
		OnClickListener, WeiboActionListener {
	private TitleLayout llTitle;

	public DemoPage(SlidingMenu menu) {
		super(menu);
		View pageView = getPage();

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

	protected View initPage() {
		return LayoutInflater.from(menu.getContext())
				.inflate(R.layout.page_demo, null);
	}

	// 使用快捷分享完成分享
	private void showShare(boolean silent, String platform) {
		Intent i = getShareIntent(silent, platform);
		new ShareAllGird(menu.getContext()).show(i);
	}

	private Intent getShareIntent(boolean silent, String platform) {
		Intent i = new Intent();
		// 分享时Notification的图标
		i.putExtra("notif_icon", R.drawable.ic_launcher);
		// 分享时Notification的标题
		i.putExtra("notif_title", menu.getContext().getString(R.string.app_name));

		// address是接收人地址，仅在信息和邮件使用，否则可以不提供
		i.putExtra("address", "12345678901");
		// title标题，在印象笔记、邮箱、信息、微信（包括好友和朋友圈）、人人网和QQ空间使用，否则可以不提供
		i.putExtra("title", menu.getContext().getString(R.string.share));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用，否则可以不提供
		i.putExtra("titleUrl", "http://sharesdk.cn");
		// text是分享文本，所有平台都需要这个字段
		i.putExtra("text", menu.getContext().getString(R.string.share_content));
		// imagePath是本地的图片路径，除Linked-In外的所有平台都支持这个字段
		i.putExtra("imagePath", MainActivity.TEST_IMAGE);
		// imageUrl是图片的网络路径，新浪微博、人人网、QQ空间和Linked-In支持此字段
		i.putExtra("imageUrl", "http://img.appgo.cn/imgs/sharesdk/content/2013/06/13/1371120300254.jpg");
		// url仅在微信（包括好友和朋友圈）中使用，否则可以不提供
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

		// foursquare分享时的地方名
		i.putExtra("venueName", "Southeast in China");
		// foursquare分享时的地方描述
		i.putExtra("venueDescription", "This is a beautiful place!");
		// foursquare分享时的地方纬度
		i.putExtra("latitude", 23.122619f);
		// foursquare分享时的地方经度
		i.putExtra("longitude", 113.372338f);

		if (platform != null) {
			// platform是平台名称
			i.putExtra("platform", platform);
		}
		// 是否直接分享
		i.putExtra("silent", silent);
		// 设置自定义的外部回调
		i.putExtra("callback", OneKeyShareCallback.class.getName());

		return i;
	}

	/** 操作演示的代码集中于此方法 */
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
				showShare(false, null);
			}
			break;
			case R.id.btnShareAll: {
				// 直接分享
				showShare(true, null);
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
				showShare(false, SinaWeibo.NAME);
			}
			break;
			case R.id.btnShareTc: {
				// 分享到腾讯微博
				showShare(false, TencentWeibo.NAME);
			}
			break;
			case R.id.btnShareFb: {
				// 分享到facebook
				showShare(false, Facebook.NAME);
			}
			break;
			case R.id.btnShareTw: {
				// 分享到twitter
				showShare(false, Twitter.NAME);
			}
			break;
			case R.id.btnShareRr: {
				// 分享到人人网
				showShare(false, Renren.NAME);
			}
			break;
			case R.id.btnShareQz: {
				// 分享到qq空间
				showShare(false, QZone.NAME);
			}
			break;
			case R.id.btnShareDb: {
				// 分享到豆瓣
				showShare(false, Douban.NAME);
			}
			break;
			case R.id.btnShareEn: {
				// 分享到印象笔记
				showShare(false, Evernote.NAME);
			}
			break;
			case R.id.btnShareNemb: {
				// 分享到网易微博
				showShare(false, NetEaseMicroBlog.NAME);
			}
			break;
			case R.id.btnShareSh: {
				// 分享到搜狐微博
				showShare(false, SohuMicroBlog.NAME);
			}
			break;
			case R.id.btnShareKaiXin: {
				// 分享到开心网
				showShare(false, KaiXin.NAME);
			}
			break;
			case R.id.btnLinkedIn: {
				// 分享到有道云笔记
				showShare(false, LinkedIn.NAME);
			}
			break;
			case R.id.btnShareYouDao: {
				// 分享到有道云笔记
				showShare(false, YouDao.NAME);
			}
			break;
			case R.id.btnShareFourSquare: {
				// 分享到foursquare
				showShare(false, FourSquare.NAME);
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
