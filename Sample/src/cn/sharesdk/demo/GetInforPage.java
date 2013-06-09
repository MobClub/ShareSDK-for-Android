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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;
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
import cn.sharesdk.renren.Renren;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.sohu.microblog.SohuMicroBlog;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.twitter.Twitter;
import cn.sharesdk.youdao.YouDao;

//#if def{lang} == cn
/** 
 * 演示获取用户资料
 * <p>
 * 启动页面时传递一个int类型的字段type，用于标记获取自己的资料（type = 0）
 *还是别人的资料（type = 1）。如果尝试获取别人的资料，示例代码会获取不同
 *平台Share SDK的官方帐号的资料。
 * <p>
 * 如果资料获取成功，会通过{@link ShowInforPage}展示
 */
//#endif
public class GetInforPage extends Activity implements Callback, 
		OnClickListener, WeiboActionListener {
	private int type; // def{note.id_type.def{lang}}
	private TitleLayout llTitle;
	private Handler handler;
	int page = 0;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		type = getIntent().getIntExtra("type", 0);
		handler = new Handler(this);
		setContentView(R.layout.page_get_user_info);
		llTitle = (TitleLayout) findViewById(R.id.llTitle);
		llTitle.getBtnBack().setOnClickListener(this);
		llTitle.getTvTitle().setText(type == 0 ? R.string.demo_get_my_info 
				: R.string.demo_get_other_info);
		
		LinearLayout llList = (LinearLayout) findViewById(R.id.llList);
		for (int i = 0, size = llList.getChildCount(); i < size; i++) {
			llList.getChildAt(i).setOnClickListener(this);
		}
		
		if (type != 0) {
			for (int i = 2, size = llList.getChildCount(); i < size; i++) {
				llList.getChildAt(i).setVisibility(View.GONE);
			}
			findViewById(R.id.btnFsquare).setVisibility(View.GONE);
		}
	}
	
	//#if def{lang} == cn
	/** 具体获取资料的演示代码 */
	//#endif
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
			case R.id.btnNemb: name = NetEaseMicroBlog.NAME; break;
			case R.id.btnEn: name = Evernote.NAME; break;
			case R.id.btnSh: name = SohuMicroBlog.NAME; break;
			case R.id.btnKxin: name = KaiXin.NAME; break;
			case R.id.btnYdao: name = YouDao.NAME; break;
			case R.id.btnFsquare: name = FourSquare.NAME; break;
			case R.id.btnLinkedIn: name = LinkedIn.NAME; break;
		}
		
		if (name != null) {
			AbstractWeibo weibo = AbstractWeibo.getWeibo(this, name);
			weibo.setWeiboActionListener(this);
			String account = null;
			if ("SinaWeibo".equals(name)) {
				account = MainAdapter.SDK_SINAWEIBO_UID;
			}
			else if ("TencentWeibo".equals(name)) {
				account = MainAdapter.SDK_TENCENTWEIBO_UID;
			}
			weibo.showUser(type == 0 ? null : account);
//			Ln.e("list friend page %s", page);
//			weibo.listFriend(10, page, account);
//			page ++;
		}
	}

	public void onComplete(AbstractWeibo weibo, int action,
			HashMap<String, Object> res) {
		Message msg = new Message();
		msg.arg1 = 1;
		msg.arg2 = action;
		msg.obj = weibo;
		handler.sendMessage(msg);
		
		Message msg2 = new Message();
		msg2.what = 1;
		JsonUtils ju = new JsonUtils();
		String json = ju.fromHashMap(res);
		msg2.obj = ju.format(json);
		handler.sendMessage(msg2);
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
	/** 处理操作结果 */
	//#endif
	public boolean handleMessage(Message msg) {
		switch(msg.what) {
			case 1: {
				Intent i = new Intent(this, ShowInforPage.class);
				i.putExtra("data", String.valueOf(msg.obj));
				startActivity(i);
			}
			break;
			default: {
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
				
				Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
			}
			break;
		}
		return false;
	}

}
