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
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckedTextView;
import android.widget.Toast;
import cn.sharesdk.douban.Douban;
import cn.sharesdk.evernote.Evernote;
import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.foursquare.FourSquare;
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

//#if def{lang} == cn
/** 
 * 授权和取消授权演示页面
 * <p>
 * 由于UI显示的需要授权过的平台显示账户的名称，
 *因此此页面事实上展示的是“获取用户资料”和“取消
 *授权”两个功能。如果想看纯粹的“授权”操作，请参
 *考{@link GetTokenPage}页面的相关代码。
 */
//#endif
public class AuthPage implements Callback, 
		OnClickListener, WeiboActionListener {
	private SlidingMenu menu;
	private View pageView;
	private TitleLayout llTitle;
	private Handler handler;

	public AuthPage(SlidingMenu menu) {
		handler = new Handler(this);
		this.menu = menu;
		pageView = LayoutInflater.from(menu.getContext())
				.inflate(R.layout.page_auth, null);
		
		llTitle = (TitleLayout) pageView.findViewById(R.id.llTitle);
		llTitle.getBtnBack().setOnClickListener(this);
		llTitle.getTvTitle().setText(R.string.sm_item_auth);
		
		pageView.findViewById(R.id.ctvSw).setOnClickListener(this);
		pageView.findViewById(R.id.ctvTc).setOnClickListener(this);
		pageView.findViewById(R.id.ctvFb).setOnClickListener(this);
		pageView.findViewById(R.id.ctvTw).setOnClickListener(this);
		pageView.findViewById(R.id.ctvRr).setOnClickListener(this);
		pageView.findViewById(R.id.ctvQz).setOnClickListener(this);
		pageView.findViewById(R.id.ctvDb).setOnClickListener(this);
		pageView.findViewById(R.id.ctvNemb).setOnClickListener(this);
		pageView.findViewById(R.id.ctvEn).setOnClickListener(this);
		pageView.findViewById(R.id.ctvSoHu).setOnClickListener(this);
		pageView.findViewById(R.id.ctvKaiXin).setOnClickListener(this);
		pageView.findViewById(R.id.ctvYouDao).setOnClickListener(this);
		pageView.findViewById(R.id.ctvFourSquare).setOnClickListener(this);
		
		// def{note.req_weibo_list.def{lang}}
		AbstractWeibo[] weibos = AbstractWeibo.getWeiboList(menu.getContext());
		for (AbstractWeibo weibo : weibos) {
			if (!weibo.isValid()) {
				continue;
			}
			
			CheckedTextView ctv = getView(weibo);
			if (ctv != null) {
				ctv.setChecked(true);
				String userName = weibo.getDb().get("nickname"); // getAuthedUserName();
				if (userName == null || userName.length() <= 0
						|| "null".equals(userName)) {
					// def{note.authed_but_no_data_then_req.def{lang}}
					userName = getWeiboName(weibo);
					weibo.setWeiboActionListener(this);
					weibo.showUser(null);
				}
				ctv.setText(userName);
			}
		}
	}
	
	public View getPage() {
		return pageView;
	}
	
	//#if def{lang} == cn
	/** 授权和取消授权的逻辑代码 */
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
		
		AbstractWeibo weibo = getWeibo(v.getId());
		CheckedTextView ctv = (CheckedTextView) v;
		if (weibo == null) {
			ctv.setChecked(false);
			ctv.setText(R.string.not_yet_authorized);
			return;
		}
		
		if (weibo.isValid()) {
			weibo.removeAccount();
			ctv.setChecked(false);
			ctv.setText(R.string.not_yet_authorized);
			return;
		}
		
		weibo.setWeiboActionListener(this);
		weibo.showUser(null);
	}
	
	private AbstractWeibo getWeibo(int vid) {
		String name = null;
		switch(vid) {
			case R.id.ctvSw: name = SinaWeibo.NAME; break;
			case R.id.ctvTc: name = TencentWeibo.NAME; break;
			case R.id.ctvFb: name = Facebook.NAME; break;
			case R.id.ctvTw: name = Twitter.NAME; break;
			case R.id.ctvRr: name = Renren.NAME; break;
			case R.id.ctvQz: name = QZone.NAME; break;
			case R.id.ctvDb: name = Douban.NAME; break;
			case R.id.ctvEn: name = Evernote.NAME; break;
			case R.id.ctvNemb: name = NetEaseMicroBlog.NAME; break;
			case R.id.ctvSoHu: name = SohuMicroBlog.NAME; break;
			case R.id.ctvKaiXin: name = KaiXin.NAME; break;
			case R.id.ctvYouDao: name = YouDao.NAME; break;
			case R.id.ctvFourSquare: name = FourSquare.NAME; break;
		}
		
		if (name != null) {
			return AbstractWeibo.getWeibo(menu.getContext(), name);
		}
		return null;
	}
	
	private CheckedTextView getView(AbstractWeibo weibo) {
		if (weibo == null) {
			return null;
		}
		
		String name = weibo.getName();
		if (name == null) {
			return null;
		}
		
		View v = null;
		if (SinaWeibo.NAME.equals(name)) {
			v = pageView.findViewById(R.id.ctvSw);
		}
		else if (TencentWeibo.NAME.equals(name)) {
			v = pageView.findViewById(R.id.ctvTc);
		}
		else if (Facebook.NAME.equals(name)) {
			v = pageView.findViewById(R.id.ctvFb);
		}
		else if (Twitter.NAME.equals(name)) {
			v = pageView.findViewById(R.id.ctvTw);
		}
		else if (Renren.NAME.equals(name)) {
			v = pageView.findViewById(R.id.ctvRr);
		}
		else if (QZone.NAME.equals(name)) {
			v = pageView.findViewById(R.id.ctvQz);
		}
		else if (Douban.NAME.equals(name)) {
			v = pageView.findViewById(R.id.ctvDb);
		}
		else if (Evernote.NAME.equals(name)) {
			v = pageView.findViewById(R.id.ctvEn);
		}
		else if (NetEaseMicroBlog.NAME.equals(name)) {
			v = pageView.findViewById(R.id.ctvNemb);
		}
		else if (SohuMicroBlog.NAME.equals(name)) {
			v = pageView.findViewById(R.id.ctvSoHu);
		}
		else if (KaiXin.NAME.equals(name)) {
			v = pageView.findViewById(R.id.ctvKaiXin);
		}
		else if (YouDao.NAME.equals(name)) {
			v = pageView.findViewById(R.id.ctvYouDao);
		}
		else if (FourSquare.NAME.equals(name)) {
			v = pageView.findViewById(R.id.ctvFourSquare);
		}
		if (v == null) {
			return null;
		}
		
		if (! (v instanceof CheckedTextView)) {
			return null;
		}
		
		return (CheckedTextView) v;
	}

	private String getWeiboName(AbstractWeibo weibo) {
		if (weibo == null) {
			return null;
		}
		
		String name = weibo.getName();
		if (name == null) {
			return null;
		}
		
		int res = 0;
		if (SinaWeibo.NAME.equals(name)) {
			res = R.string.sinaweibo;
		}
		else if (TencentWeibo.NAME.equals(name)) {
			res = R.string.tencentweibo;
		}
		else if (Facebook.NAME.equals(name)) {
			res = R.string.facebook;
		}
		else if (Twitter.NAME.equals(name)) {
			res = R.string.twitter;
		}
		else if (Renren.NAME.equals(name)) {
			res = R.string.renren;
		}
		else if (QZone.NAME.equals(name)) {
			res = R.string.qzone;
		}
		else if (Douban.NAME.equals(name)) {
			res = R.string.douban;
		}
		else if (Evernote.NAME.equals(name)) {
			res = R.string.evernote;
		}
		else if (NetEaseMicroBlog.NAME.equals(name)) {
			res = R.string.neteasemicroblog;
		}
		else if (SohuMicroBlog.NAME.equals(name)) {
			res = R.string.sohumicroblog;
		}
		else if (KaiXin.NAME.equals(name)) {
			res = R.string.kaixin;
		}
		else if (YouDao.NAME.equals(name)) {
			res = R.string.youdao;
		}
		else if (FourSquare.NAME.equals(name)) {
			res = R.string.foursquare;
		}
		if (res == 0) {
			return name;
		}
		
		return menu.getResources().getString(res);
	}
	
	public void onComplete(AbstractWeibo weibo, int action,
			HashMap<String, Object> res) {
		Message msg = new Message();
		msg.arg1 = 1;
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

	public void onCancel(AbstractWeibo weibo, int action) {
		Message msg = new Message();
		msg.arg1 = 3;
		msg.arg2 = action;
		msg.obj = weibo;
		handler.sendMessage(msg);
	}

	//#if def{lang} == cn
	/** 
	 * 处理操作结果
	 * <p>
	 * 如果获取到用户的名称，则显示名称；否则如果已经授权，则显示
	 *平台名称
	 */
	//#endif
	public boolean handleMessage(Message msg) {
		AbstractWeibo weibo = (AbstractWeibo) msg.obj;
		String text = MainActivity.actionToString(msg.arg2);
		switch (msg.arg1) {
			case 1: { // def{note.complete.def{lang}}
				text = weibo.getName() + " completed at " + text;
				Toast.makeText(menu.getContext(), text, Toast.LENGTH_SHORT).show();
			}
			break;
			case 2: { // def{note.error.def{lang}}
				text = weibo.getName() + " caught error at " + text;
				Toast.makeText(menu.getContext(), text, Toast.LENGTH_SHORT).show();
				return false;
			}
			case 3: { // def{note.cancel.def{lang}}
				text = weibo.getName() + " canceled at " + text;
				Toast.makeText(menu.getContext(), text, Toast.LENGTH_SHORT).show();
				return false;
			}
		}
		
		CheckedTextView ctv = getView(weibo);
		if (ctv != null) {
			ctv.setChecked(true);
			String userName = weibo.getDb().get("nickname"); // getAuthedUserName();
			if (userName == null || userName.length() <= 0
					|| "null".equals(userName)) {
				userName = getWeiboName(weibo);
			}
			ctv.setText(userName);
		}
		return false;
	}

}
