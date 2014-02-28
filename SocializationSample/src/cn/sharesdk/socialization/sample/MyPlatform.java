package cn.sharesdk.socialization.sample;

import android.content.Context;
import cn.sharesdk.socialization.SocializationCustomPlatform;

/** a demo of custom platform */
public class MyPlatform extends SocializationCustomPlatform {

	public MyPlatform(Context context) {
		super(context);
	}

	public String getName() {
		// returns the name display in account selecting page
		return "ShareSDK";
	}

	public int getLogo() {
		// returns the icon resource display in account selecting page
		return R.drawable.logo_sharesdk;
	}

	protected boolean checkAuthorize(int action) {
		// returns true, if you are sure the user to comment the topic is signed in
		// or, if returns false, means the user hasn't signed in and ShareSDK will
		// lead your app to the method of doAuthorize()
		return true;
	}

	protected UserBrief doAuthorize() {
		// lead your user to sign in and returns UserBrief to ShareSDK
		UserBrief user = new UserBrief();
		user.userId = "123456";
		user.userNickname = getName() + "_123456";
		user.userAvatar = "http://sharesdk.cn/Public/Frontend/images/android-log-bg.png";
		user.userGender = UserGender.Male;
		user.userVerifyType = UserVerifyType.Verified;
		return user;
	}

}
