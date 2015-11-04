package cn.sharesdk.socialization.sample;

import cn.sharesdk.socialization.SocializationCustomPlatform;
import android.content.Context;

/**
 * 自定义平台例子
 * <p>
 * 对于自己已经有用户系统的应用，希望可以利用自己系统里的用户
 *来完成评论功能，可以模范本类实现
 */
public class MyPlatform extends SocializationCustomPlatform {

	public MyPlatform(Context context) {
		super(context);
	}

	public String getName() {
		// 返回显示在帐号选择列表中的平台名称
		return "MyPlatform";
	}

	public int getLogo() {
		// 返回显示在帐号选择列表中的平台图标
		return R.drawable.logo_myplatform;
	}

	protected boolean checkAuthorize(int action) {
		// 返回true，表示用户已经登录（或授权），ShareSDK可以执行操作
		// 返回false，表示用户没有登录（或授权），ShareSDK会中断操作，调用doAuthorize执行登录（或授权）
		// 因此，请添加登录（或授权）状态的判断，返回正确的结果，指导ShareSDK进行后续操作
		return true;
	}

	protected UserBrief doAuthorize() {
		// 引导用户完成登录（或授权）操作，然后返回UserBrief对象
		UserBrief user = new UserBrief();
		user.userId = "123456";
		user.userNickname = getName() + "_123456";
		user.userAvatar = "http://mob.com/Public/Frontend/images/android-log-bg.png";
		user.userGender = UserGender.Male;
		user.userVerifyType = UserVerifyType.Verified;
		return user;
	}

}
