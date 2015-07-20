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
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.TitleLayout;

import com.mob.tools.utils.UIHandler;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

/** examples of custom interfaces */
public class CustomerPage extends SlidingMenuPage implements
		OnClickListener, PlatformActionListener {
	/** action code of Douban */
	private static final short ACTION_DOUBAN = 1;
	/** action code of Facebook */
	private static final short ACTION_FACEBOOK = 2;

	/** action code of Renren */
	private static final short ACTION_RENREN = 4;
	/** action code of SinaWeibo */
	private static final short ACTION_SINAWEIBO = 5;
	/** action code of Qzone */
	private static final short ACTION_QZONE = 6;
	/** action code of TencentWeibo */
	private static final short ACTION_TENCENTWEIBO = 7;
	/** action code of Twitter */
	private static final short ACTION_TWITTER = 8;
	/** action code of Kaixin */
	private static final short ACTION_KAIXIN = 9;

	/** action code of Youdao */
	private static final short ACTION_YOUDAONOTE = 11;
	/** action code of SohuSuishenkan */
	private static final short ACTION_SOHUSUISHENKAN = 12;
	/** action code of tumblr */
	private static final short ACTION_TUMBLR = 13;
	private TitleLayout llTitle;

	public CustomerPage(SlidingMenu menu) {
		super(menu);
		View pageView = getPage();
		llTitle = (TitleLayout) pageView.findViewById(R.id.llTitle);
		llTitle.getBtnBack().setOnClickListener(this);
		llTitle.getTvTitle().setText(R.string.sm_item_customer);
		LinearLayout llList = (LinearLayout) pageView.findViewById(R.id.llList);
		llList.setLayoutAnimation(InLayoutAnim.getAnimationController());
		for (int i = 0, size = llList.getChildCount(); i < size; i++) {
			llList.getChildAt(i).setOnClickListener(this);
		}
	}

	protected View initPage() {
		return LayoutInflater.from(getContext()).inflate(R.layout.page_customer, null);
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

		switch(v.getId()) {
			case R.id.btnDb: doubanEvent(); break;
			case R.id.btnFb: facebookEvent(); break;
			case R.id.btnRr: renren(); break;
			case R.id.btnSw: sinaWeibo(); break;
			case R.id.btnQz: qzone(); break;
			case R.id.btnTc: tencentWeibo(); break;
			case R.id.btnTw: twitter(); break;
			case R.id.btnKx: kaixin(); break;
			case R.id.btnYd: youdaoNote(); break;
			case R.id.btnShSSK: SohuSuishenkan(); break;
			case R.id.btnTumblr: tumblr();break;
		}
	}

	private void doubanEvent() {
		Platform douban = ShareSDK.getPlatform("Douban");
		douban.setPlatformActionListener(this);
		String url = "https://api.douban.com/shuo/v2/users/search";
		String method = "GET";
		short customerAction = ACTION_DOUBAN;
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("q", "ahbei");
		douban.customerProtocol(url, method, customerAction, values, null);
	}

	private void facebookEvent() {
		Platform facebook = ShareSDK.getPlatform("Facebook");
		facebook.setPlatformActionListener(this);
		String url = "https://graph.facebook.com/me/notifications";
		String method = "GET";
		short customerAction = ACTION_FACEBOOK;
		facebook.customerProtocol(url, method, customerAction, null, null);
	}

	private void renren() {
		// share local image
		Platform renren = ShareSDK.getPlatform("Renren");
		renren.setPlatformActionListener(this);
		String url = "https://api.renren.com/v2/photo/upload";
		String method = "POST";
		short customerAction = ACTION_RENREN;
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("description", "ShareSDK customer protocol test");
		HashMap<String, String> filePathes = new HashMap<String, String>();
		filePathes.put("file", MainActivity.TEST_IMAGE);
		renren.customerProtocol(url, method, customerAction, values, filePathes);

		// share online image
//		Platform renren = ShareSDK.getPlatform("Renren");
//		renren.setPlatformActionListener(this);
//		String url = "https://api.renren.com/v2/share/url/put";
//		String method = "POST";
//		short customerAction = ACTION_RENREN;
//		HashMap<String, Object> values = new HashMap<String, Object>();
//		values.put("comment", "ShareSDK customer protocol test");
//		values.put("url", MainActivity.TEXT_IMAGE_URL);
//		renren.customerProtocol(url, method, customerAction, values, null);
	}

	private void sinaWeibo() {
//		Platform weibo = ShareSDK.getPlatform("SinaWeibo");
//		weibo.SSOSetting(true);
//		weibo.setPlatformActionListener(this);
//		String url = "https://m.api.weibo.com/2/messages/send.json";
//		String method = "POST";
//		short customerAction = ACTION_SINAWEIBO;
//		HashMap<String, Object> values = new HashMap<String, Object>();
//		values.put("source", "androidv1101");
//		values.put("sender_id", "<weibo ID of the app woner>");
//		values.put("receiver_id", "<receiver ID>");
//		values.put("type", 1);
//		values.put("data", String.valueOf(System.currentTimeMillis()));
//		weibo.customerProtocol(url, method, customerAction, values, null);

		Platform weibo = ShareSDK.getPlatform("SinaWeibo");
		weibo.setPlatformActionListener(this);
		String url = "https://api.weibo.com/2/statuses/friends_timeline.json";
		String method = "GET";
		short customerAction = ACTION_SINAWEIBO;
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("count", 20);
		values.put("page", 1);
		weibo.customerProtocol(url, method, customerAction, values, null);
	}

	private void qzone() {
		Platform qzone = ShareSDK.getPlatform("QZone");
		qzone.setPlatformActionListener(this);
		String url = "https://graph.qq.com/blog/add_one_blog";
		String method = "POST";
		short customerAction = ACTION_QZONE;
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("title", getContext().getString(R.string.customer_qzone));
		values.put("content", getContext().getString(R.string.qzone_add_blog_sample));
		qzone.customerProtocol(url, method, customerAction, values, null);
	}

	private void tencentWeibo() {
		Platform weibo = ShareSDK.getPlatform("TencentWeibo");
		weibo.setPlatformActionListener(this);
		String url = "https://open.t.qq.com/api/friends/fanslist";
		String method = "GET";
		short customerAction = ACTION_TENCENTWEIBO;
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("reqnum", 30);
		values.put("startindex", 0);
		values.put("mode", 0);
		weibo.customerProtocol(url, method, customerAction, values, null);
	}

	private void twitter() {
		Platform twitter = ShareSDK.getPlatform("Twitter");
		twitter.setPlatformActionListener(this);
		String url = "https://api.twitter.com/1.1/statuses/home_timeline.json";
		String method = "GET";
		short customerAction = ACTION_TWITTER;
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("count", 20);
		twitter.customerProtocol(url, method, customerAction, values, null);
	}

	private void kaixin() {
		Platform kaixin = ShareSDK.getPlatform("KaiXin");
		kaixin.setPlatformActionListener(this);
		String url = "https://api.kaixin001.com/users/mfriends.json";
		String method = "GET";
		short customerAction = ACTION_KAIXIN;
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("num", 20);
		kaixin.customerProtocol(url, method, customerAction, values, null);
	}

	private void youdaoNote() {
		Platform youdao = ShareSDK.getPlatform("YouDao");
		youdao.setPlatformActionListener(this);
		String url = "http://note.youdao.com/yws/open/notebook/all.json";
		String method = "POST";
		short customerAction = ACTION_YOUDAONOTE;
		youdao.customerProtocol(url, method, customerAction, null, null);
	}

	private void SohuSuishenkan() {
		Platform  suiShenKan = ShareSDK.getPlatform("SohuSuishenkan");
		suiShenKan.setPlatformActionListener(this);
		String url = "https://api.sohu.com/rest/k/prv/1/bookmark/get-list";
		String method = "GET";
		short customerAction = ACTION_SOHUSUISHENKAN;
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("offset", 0);
		values.put("limit", 10);
		suiShenKan.customerProtocol(url, method, customerAction, values, null);
	}

	private void tumblr() {
		Platform tumblr = ShareSDK.getPlatform("Tumblr");
		tumblr.setPlatformActionListener(this);
		String baseHostName = tumblr.getDb().getUserId() + ".tumblr.com";
		String url = "http://api.tumblr.com/v2/blog/" + baseHostName + "/posts";
		String method = "GET";
		short customerAction = ACTION_TUMBLR;
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("offset", 0);
		values.put("limit", 20);
		tumblr.customerProtocol(url, method, customerAction, values, null);
	}

	public void onComplete(Platform plat, int action, HashMap<String, Object> res) {
		Message msg = new Message();
		msg.arg1 = 1;
		msg.arg2 = action;
		msg.obj = res;
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
		msg.obj = plat;
		UIHandler.sendMessage(msg, this);
	}

	/** handling custom actions */
	@SuppressWarnings("unchecked")
	public boolean handleMessage(Message msg) {
		switch (msg.arg1) {
			case 1: {
				// success
				JsonPage page = new JsonPage();
				String title = llTitle.getTvTitle().getText().toString();
				page.setData(title, (HashMap<String, Object>) msg.obj);
				page.show(getContext(), null);
			}
			break;
			case 2: {
				// failed
				Platform plat = (Platform) msg.obj;
				String text = actionToString(msg.arg2);
				text = plat.getName() + " caught error at " + text;
				Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
			}
			break;
			case 3: {
				// canceled
				Platform plat = (Platform) msg.obj;
				String text = actionToString(msg.arg2);
				text = plat.getName() + " canceled at " + text;
				Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
			}
			break;
		}

		return false;
	}

	/** converts ShareSDK actions into string */
	private String actionToString(int action) {
		switch (action) {
			case Platform.ACTION_AUTHORIZING: return "ACTION_AUTHORIZING";
			case Platform.ACTION_GETTING_FRIEND_LIST: return "ACTION_GETTING_FRIEND_LIST";
			case Platform.ACTION_FOLLOWING_USER: return "ACTION_FOLLOWING_USER";
			case Platform.ACTION_SENDING_DIRECT_MESSAGE: return "ACTION_SENDING_DIRECT_MESSAGE";
			case Platform.ACTION_TIMELINE: return "ACTION_TIMELINE";
			case Platform.ACTION_USER_INFOR: return "ACTION_USER_INFOR";
			case Platform.ACTION_SHARE: return "ACTION_SHARE";
			default: {
				short customerAction = (short) (action & Platform.CUSTOMER_ACTION_MASK);
				switch(customerAction) {
					case ACTION_DOUBAN: return "ACTION_DOUBAN";
					case ACTION_FACEBOOK: return "ACTION_FACEBOOK";
					case ACTION_RENREN: return "ACTION_RENREN";
					case ACTION_SINAWEIBO: return "ACTION_SINAWEIBO";
					case ACTION_QZONE: return "ACTION_QZONE";
					case ACTION_TENCENTWEIBO: return "ACTION_TENCENTWEIBO";
					case ACTION_TWITTER: return "ACTION_TWITTER";
					case ACTION_KAIXIN: return "ACTION_KAIXIN";
					case ACTION_YOUDAONOTE: return "ACTION_YOUDAONOTE";
					case ACTION_SOHUSUISHENKAN: return "ACTION_SOHUSUISHENKAN";
					case ACTION_TUMBLR: return "ACTION_TUMBLR";
				}
				return "UNKNOWN";
			}
		}
	}

}
