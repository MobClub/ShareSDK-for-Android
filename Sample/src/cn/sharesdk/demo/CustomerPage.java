/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.demo;

import java.util.HashMap;
import cn.sharesdk.douban.Douban;
import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.framework.AbstractWeibo;
import cn.sharesdk.framework.TitleLayout;
import cn.sharesdk.framework.WeiboActionListener;
import cn.sharesdk.kaixin.KaiXin;
import cn.sharesdk.netease.microblog.NetEaseMicroBlog;
import cn.sharesdk.renren.Renren;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.sohu.microblog.SohuMicroBlog;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.twitter.Twitter;
import cn.sharesdk.youdao.YouDao;
import android.content.Intent;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;
import m.framework.ui.widget.slidingmenu.SlidingMenu;

/** 自定义接口的演示页面 */
public class CustomerPage extends SlidingMenuPage implements
		OnClickListener, WeiboActionListener {
	/** 豆瓣自定义事件代码 */
	private static final short ACTION_DOUBAN = 1;
	/** Facebook自定义事件代码 */
	private static final short ACTION_FACEBOOK = 2;
	/** 网易微博自定义事件代码 */
	private static final short ACTION_NETEASEMB = 3;
	/** 人人网自定义事件代码 */
	private static final short ACTION_RENREN = 4;
	/** 新浪微博自定义事件代码 */
	private static final short ACTION_SINAWEIBO = 5;
	/** QQ空间自定义事件代码 */
	private static final short ACTION_QZONE = 6;
	/** 腾讯微博自定义事件代码 */
	private static final short ACTION_TENCENTWEIBO = 7;
	/** twitter自定义事件代码 */
	private static final short ACTION_TWITTER = 8;
	/** 开心网自定义事件代码 */
	private static final short ACTION_KAIXIN = 9;
	/** 搜狐微博自定义事件代码 */
	private static final short ACTION_SOHUMB = 10;
	/** 有道云笔记自定义事件代码 */
	private static final short ACTION_YOUDAONOTE = 11;
	private TitleLayout llTitle;

	public CustomerPage(SlidingMenu menu) {
		super(menu);
		View pageView = getPage();
		llTitle = (TitleLayout) pageView.findViewById(R.id.llTitle);
		llTitle.getBtnBack().setOnClickListener(this);
		llTitle.getTvTitle().setText(R.string.sm_item_customer);
		LinearLayout llList = (LinearLayout) pageView.findViewById(R.id.llList);
		for (int i = 0, size = llList.getChildCount(); i < size; i++) {
			llList.getChildAt(i).setOnClickListener(this);
		}
	}

	protected View initPage() {
		return LayoutInflater.from(menu.getContext())
				.inflate(R.layout.page_customer, null);
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

		switch(v.getId()) {
			case R.id.btnDb: doubanEvent(); break;
			case R.id.btnFb: facebookEvent(); break;
			case R.id.btnNemb: neteaseMb(); break;
			case R.id.btnRr: renren(); break;
			case R.id.btnSw: sinaWeibo(); break;
			case R.id.btnQz: qzone(); break;
			case R.id.btnTc: tencentWeibo(); break;
			case R.id.btnTw: twitter(); break;
			case R.id.btnKx: kaixin(); break;
			case R.id.btnShmb: sohuMb(); break;
			case R.id.btnYd: youdaoNote(); break;
		}
	}

	private void doubanEvent() {
		Douban douban = new Douban(menu.getContext());
		douban.setWeiboActionListener(this);
		String url = "https://api.douban.com/shuo/v2/users/search";
		String method = "GET";
		short customerAction = ACTION_DOUBAN;
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("q", "ahbei");
		douban.customerProtocol(url, method, customerAction, values, null);
	}

	private void facebookEvent() {
		Facebook facebook = new Facebook(menu.getContext());
		facebook.setWeiboActionListener(this);
		String url = "https://graph.facebook.com/me/notifications";
		String method = "GET";
		short customerAction = ACTION_FACEBOOK;
		facebook.customerProtocol(url, method, customerAction, null, null);
	}

	private void neteaseMb() {
		NetEaseMicroBlog nemb = new NetEaseMicroBlog(menu.getContext());
		nemb.setWeiboActionListener(this);
		String url = "https://api.t.163.com/trends/recommended.json";
		String method = "GET";
		short customerAction = ACTION_NETEASEMB;
		nemb.customerProtocol(url, method, customerAction, null, null);
	}

	private void renren() {
		Renren renren = new Renren(menu.getContext());
		renren.setWeiboActionListener(this);
		String url = "https://api.renren.com/restserver.do";
		String method = "POST";
		short customerAction = ACTION_RENREN;
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("method", "photos.upload");
		values.put("caption", "Share SDK customer protocol test");
		HashMap<String, String> filePathes = new HashMap<String, String>();
		filePathes.put("upload", MainActivity.TEST_IMAGE);
		renren.customerProtocol(url, method, customerAction, values, filePathes);
	}

	private void sinaWeibo() {
		SinaWeibo weibo = new SinaWeibo(menu.getContext());
		weibo.setWeiboActionListener(this);
		String url = "https://api.weibo.com/2/statuses/friends_timeline.json";
		String method = "GET";
		short customerAction = ACTION_SINAWEIBO;
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("count", 20);
		values.put("page", 1);
		weibo.customerProtocol(url, method, customerAction, values, null);
	}

	private void qzone() {
		QZone qzone = new QZone(menu.getContext());
		qzone.setWeiboActionListener(this);
		String url = "https://graph.qq.com/blog/add_one_blog";
		String method = "POST";
		short customerAction = ACTION_QZONE;
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("title", menu.getContext().getString(R.string.customer_qzone));
		values.put("content", menu.getContext().getString(R.string.qzone_add_blog_sample));
		qzone.customerProtocol(url, method, customerAction, values, null);
	}

	private void tencentWeibo() {
		TencentWeibo weibo = new TencentWeibo(menu.getContext());
		weibo.setWeiboActionListener(this);
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
		Twitter twitter = new Twitter(menu.getContext());
		twitter.setWeiboActionListener(this);
		String url = "https://api.twitter.com/1.1/statuses/home_timeline.json";
		String method = "GET";
		short customerAction = ACTION_TWITTER;
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("count", 20);
		twitter.customerProtocol(url, method, customerAction, values, null);
	}

	private void kaixin() {
		KaiXin kaixin = new KaiXin(menu.getContext());
		kaixin.setWeiboActionListener(this);
		String url = "https://api.kaixin001.com/users/mfriends.json";
		String method = "GET";
		short customerAction = ACTION_KAIXIN;
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("num", 20);
		kaixin.customerProtocol(url, method, customerAction, values, null);
	}

	private void sohuMb() {
		SohuMicroBlog shmb = new SohuMicroBlog(menu.getContext());
		shmb.setWeiboActionListener(this);
		String url = "https://api.t.sohu.com/statuses/friends.json";
		String method = "GET";
		short customerAction = ACTION_SOHUMB;
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("page", 1);
		values.put("count", 20);
		shmb.customerProtocol(url, method, customerAction, values, null);
	}

	private void youdaoNote() {
		YouDao youdao = new YouDao(menu.getContext());
		youdao.setWeiboActionListener(this);
		String url = "http://note.youdao.com/yws/open/notebook/all.json";
		String method = "POST";
		short customerAction = ACTION_YOUDAONOTE;
		youdao.customerProtocol(url, method, customerAction, null, null);
	}

	public void onComplete(AbstractWeibo weibo, int action,
			HashMap<String, Object> res) {
		JsonUtils ju = new JsonUtils();
		String json = ju.format(ju.fromHashMap(res));
		System.out.println(json);

		Message msg = new Message();
		msg.arg1 = 1;
		msg.arg2 = action;
		msg.obj = json;
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
		switch (msg.arg1) {
			case 1: { // 成功
				Intent i = new Intent(menu.getContext(), JsonPage.class);
				i.putExtra("title", llTitle.getTvTitle().getText().toString());
				String data = String.valueOf(msg.obj);
				if (data.length() > 10000) {
					System.out.println("Respose data len: " + data.length());
					JsonPage.bigData = data;
				}
				else {
					i.putExtra("data", data);
				}
				menu.getContext().startActivity(i);
			}
			break;
			case 2: { // 失败
				AbstractWeibo weibo = (AbstractWeibo) msg.obj;
				String text = actionToString(msg.arg2);
				text = weibo.getName() + " caught error at " + text;
				Toast.makeText(menu.getContext(), text, Toast.LENGTH_SHORT).show();
			}
			break;
			case 3: { // 取消
				AbstractWeibo weibo = (AbstractWeibo) msg.obj;
				String text = actionToString(msg.arg2);
				text = weibo.getName() + " canceled at " + text;
				Toast.makeText(menu.getContext(), text, Toast.LENGTH_SHORT).show();
			}
			break;
		}

		return false;
	}

	/** 将action转换为String */
	private String actionToString(int action) {
		switch (action) {
			case AbstractWeibo.ACTION_AUTHORIZING: return "ACTION_AUTHORIZING";
			case AbstractWeibo.ACTION_GETTING_FRIEND_LIST: return "ACTION_GETTING_FRIEND_LIST";
			case AbstractWeibo.ACTION_FOLLOWING_USER: return "ACTION_FOLLOWING_USER";
			case AbstractWeibo.ACTION_SENDING_DIRECT_MESSAGE: return "ACTION_SENDING_DIRECT_MESSAGE";
			case AbstractWeibo.ACTION_TIMELINE: return "ACTION_TIMELINE";
			case AbstractWeibo.ACTION_USER_INFOR: return "ACTION_USER_INFOR";
			case AbstractWeibo.ACTION_SHARE: return "ACTION_SHARE";
			default: {
				short customerAction = (short) (action & AbstractWeibo.CUSTOMER_ACTION_MASK);
				switch(customerAction) {
					case ACTION_DOUBAN: return "ACTION_DOUBAN";
					case ACTION_FACEBOOK: return "ACTION_FACEBOOK";
					case ACTION_NETEASEMB: return "ACTION_NETEASEMB";
					case ACTION_RENREN: return "ACTION_RENREN";
					case ACTION_SINAWEIBO: return "ACTION_SINAWEIBO";
					case ACTION_QZONE: return "ACTION_QZONE";
					case ACTION_TENCENTWEIBO: return "ACTION_TENCENTWEIBO";
					case ACTION_TWITTER: return "ACTION_TWITTER";
					case ACTION_KAIXIN: return "ACTION_KAIXIN";
					case ACTION_SOHUMB: return "ACTION_SOHUMB";
					case ACTION_YOUDAONOTE: return "ACTION_YOUDAONOTE";
				}
				return "UNKNOWN";
			}
		}
	}

}
