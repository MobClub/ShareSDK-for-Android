package cn.sharesdk.demo.entity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.UIHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.sharesdk.demo.R;
import cn.sharesdk.demo.utils.AuthorizationUserInfoUtils;
import cn.sharesdk.demo.utils.ResourcesUtils;
import cn.sharesdk.framework.CustomPlatform;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by yjin on 2017/5/15.
 */

public class PlatformMananger {
	private List<ShareListItemInEntity> lists = new ArrayList<>();
	private List<ShareListItemInEntity> chinaList = new ArrayList<>();
	private List<ShareListItemInEntity> systemList = new ArrayList<>();
	private List<PlatformEntity> systemListNormal = new ArrayList<>();

	private List<PlatformEntity> chinaListNormal = new ArrayList<>();
	private List<PlatformEntity> normalList = new ArrayList<>();

	private List<PlatformEntity> chinaListNormalUserInfo = new ArrayList<>();
	private List<PlatformEntity> normalListUserInfo = new ArrayList<>();

	private static PlatformMananger instance;
	private Context context = null;
	public static final String SDK_SINAWEIBO_UID = "3189087725";
	public static final String SDK_TENCENTWEIBO_UID = "shareSDK";
	public static String[] china = {"SinaWeibo", "TencentWeibo", "QZone", "Wechat", "WechatMoments", "WechatFavorite"
			, "QQ", "Renren", "KaiXin", "Douban", "YouDao", "Yixin", "YixinMoments", "Mingdao", "Alipay", "AlipayMoments", "Dingding", "Meipai","Cmcc"};
	public static String[] system = {"Email", "ShortMessage", "Bluetooth"};

	private PlatformMananger(Context context) {
		this.context = context;
		Platform[] list = ShareSDK.getPlatformList();
		if (list != null) {
			Message msg = new Message();
			msg.obj = list;
			UIHandler.sendMessage(msg, new Handler.Callback() {
				public boolean handleMessage(Message msg) {
					afterPlatformsGot((Platform[]) msg.obj);
					return false;
				}
			});
		}
	}

	private void afterPlatformsGot(Platform[] platforms) {
		ShareListItemInEntity entity = null;
		PlatformEntity normalEntity = null;
		for (Platform platform : platforms) {
			String name = platform.getName();
			//客户端分享的情况
//			if (DemoUtils.isUseClientToShare(name)) {
//				continue;
//			}
			if (platform instanceof CustomPlatform) {
				continue;
			}
			//#if def{lang} == cn
			// 处理左边按钮和右边按钮
			//#elif def{lang} == en
			// initiate buttons
			//#endif
			entity = new ShareListItemInEntity();
			entity.setPlatform(platform);
			normalEntity = new PlatformEntity();
			normalEntity.setmPlatform(platform);
			entity.setType(SharePlatformType.FOREIGN_SHARE_PLAT);
			int platNameRes = ResHelper.getStringRes(context, "ssdk_" + name.toLowerCase());
			String resName = "ssdk_oks_classic_" + name;
			int resId = ResourcesUtils.getBitmapRes(context, resName.toLowerCase());
			if (resId > 0) {
				entity.setIcon(resId);
				normalEntity.setmIcon(resId);
			}
			if (platNameRes > 0) {
				String platName = context.getString(platNameRes);
				entity.setName(platName);
				normalEntity.setName(platName);
				String text = context.getString(R.string.share_to_format, platName);
			}
			if (Arrays.asList(china).contains(name) ) {
				if(!name.equals("Cmcc")){
					chinaList.add(entity);
				}
				if (AuthorizationUserInfoUtils.canAuthorize(name)) {
					chinaListNormal.add(normalEntity);
				}
				if (AuthorizationUserInfoUtils.canGetUserInfo(name)) {
					chinaListNormalUserInfo.add(normalEntity);
				}
			} else {
				if (Arrays.asList(system).contains(name)) {
					systemList.add(entity);
					if (AuthorizationUserInfoUtils.canAuthorize(name)) {
						systemListNormal.add(normalEntity);
					}
					if (AuthorizationUserInfoUtils.canGetUserInfo(name)) {
						systemListNormal.add(normalEntity);
					}
				} else {
					lists.add(entity);
					if (AuthorizationUserInfoUtils.canAuthorize(name)) {
						normalList.add(normalEntity);
					}
					if (AuthorizationUserInfoUtils.canGetUserInfo(name)) {
						normalListUserInfo.add(normalEntity);
					}
				}

			}

		}
	}

	public static PlatformMananger getInstance(Context context) {
		synchronized (PlatformMananger.class) {
			if (instance == null) {
				synchronized (PlatformMananger.class) {
					if (instance == null) {
						instance = new PlatformMananger(context);
					}
				}
			}
		}
		return instance;
	}

	public List<ShareListItemInEntity> getList() {
		return lists;
	}

	public void setList(List<ShareListItemInEntity> list) {
		this.lists = list;
	}

	public List<PlatformEntity> getNormalList() {
		return normalList;
	}

	public void setNormalList(List<PlatformEntity> normalList) {
		this.normalList = normalList;
	}

	public List<ShareListItemInEntity> getChinaList() {
		return chinaList;
	}

	public void setChinaList(List<ShareListItemInEntity> chinaList) {
		this.chinaList = chinaList;
	}

	public List<ShareListItemInEntity> getSystemList() {
		return systemList;
	}

	public void setSystemList(List<ShareListItemInEntity> systemList) {
		this.systemList = systemList;
	}

	public List<PlatformEntity> getChinaListNormal() {
		return chinaListNormal;
	}

	public void setChinaListNormal(List<PlatformEntity> chinaListNormal) {
		this.chinaListNormal = chinaListNormal;
	}

	public List<PlatformEntity> getSystemListNormal() {
		return systemListNormal;
	}

	public void setSystemListNormal(List<PlatformEntity> systemListNormal) {
		this.systemListNormal = systemListNormal;
	}

	public List<PlatformEntity> getChinaListNormalUserInfo() {
		return chinaListNormalUserInfo;
	}

	public void setChinaListNormalUserInfo(List<PlatformEntity> chinaListNormalUserInfo) {
		this.chinaListNormalUserInfo = chinaListNormalUserInfo;
	}

	public List<PlatformEntity> getNormalListUserInfo() {
		return normalListUserInfo;
	}

	public void setNormalListUserInfo(List<PlatformEntity> normalListUserInfo) {
		this.normalListUserInfo = normalListUserInfo;
	}
}
