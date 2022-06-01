package cn.sharesdk.demo.wxapi;

import android.util.Log;
import android.widget.Toast;

import com.mob.tools.utils.Hashon;

import cn.sharesdk.wechat.utils.WXMediaMessage;
import cn.sharesdk.wechat.utils.WechatHandlerActivity;

public class WXEntryActivity extends WechatHandlerActivity {


	@Override
	public void onGetMessageFromWXReq(WXMediaMessage msg) {
		//小程序返回回调
		Log.i("qqq111",new Hashon().fromObject(msg));
		Toast.makeText(this,"openid:"+msg.openId+" msg:"+msg.wxminiprogram_ext_msg,
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onShowMessageFromWXReq(WXMediaMessage msg) {
		Log.i("qqq111",new Hashon().fromObject(msg));
	}
}
