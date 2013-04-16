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
import cn.sharesdk.evernote.Evernote;
import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.framework.AbstractWeibo;
import cn.sharesdk.framework.TitleLayout;
import cn.sharesdk.framework.WeiboActionListener;
import cn.sharesdk.netease.microblog.NetEaseMicroBlog;
import cn.sharesdk.renren.Renren;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.twitter.Twitter;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

/** 演示授权并获取获取AccessToken */
public class GetTokenPage extends Activity implements Callback, 
		OnClickListener, WeiboActionListener {
	private TitleLayout llTitle;
	private Handler handler;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handler = new Handler(this);
		setContentView(R.layout.page_get_access_token);
		llTitle = (TitleLayout) findViewById(R.id.llTitle);
		llTitle.getBtnBack().setOnClickListener(this);
		llTitle.getTvTitle().setText(R.string.demo_get_access_token);
		
		findViewById(R.id.btnSw).setOnClickListener(this);
		findViewById(R.id.btnTc).setOnClickListener(this);
		findViewById(R.id.btnFb).setOnClickListener(this);
		findViewById(R.id.btnTw).setOnClickListener(this);
		findViewById(R.id.btnRr).setOnClickListener(this);
		findViewById(R.id.btnQz).setOnClickListener(this);
		findViewById(R.id.btnDb).setOnClickListener(this);
		findViewById(R.id.btnEn).setOnClickListener(this);
		findViewById(R.id.btnNemb).setOnClickListener(this);
	}
	
	/** 演示的逻辑代码 */
	public void onClick(View v) {
		if (v.equals(llTitle.getBtnBack())) {
			finish();
			return;
		}
		
		String name = null;
		switch(v.getId()) {
			case R.id.btnSw: name = SinaWeibo.NAME; break;
			case R.id.btnTc: name = TencentWeibo.NAME; break;
			case R.id.btnFb: name = Facebook.NAME; break;
			case R.id.btnTw: name = Twitter.NAME; break;
			case R.id.btnRr: name = Renren.NAME; break;
			case R.id.btnQz: name = QZone.NAME; break;
			case R.id.btnDb: name = Douban.NAME; break;
			case R.id.btnEn: name = Evernote.NAME; break;
			case R.id.btnNemb: name = NetEaseMicroBlog.NAME; break;
		}
		
		// 授权
		if (name != null) {
			AbstractWeibo weibo = AbstractWeibo.getWeibo(this, name);
			weibo.setWeiboActionListener(this);
			weibo.authorize();
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
		Message msg = new Message();
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = weibo;
		handler.sendMessage(msg);
	}
	
	/** 通过Toast显示操作结果 */
	public boolean handleMessage(Message msg) {
		AbstractWeibo weibo = (AbstractWeibo) msg.obj;
		String text = AbstractWeibo.actionToString(msg.arg2);
		switch (msg.arg1) {
			case 1: { // 成功
				text = weibo.getName() + " get token: " + weibo.getDb().getToken();
			}
			break;
			case 2: { // 失败
				text = weibo.getName() + " caught error";
			}
			break;
			case 3: { // 取消
				text = weibo.getName() + " authorization canceled";
			}
			break;
		}
		
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
		return false;
	}
	
}
