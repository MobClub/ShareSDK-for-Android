package cn.sharesdk.demo.entity;

import android.content.Context;

import cn.sharesdk.demo.R;

/**
 * Created by yjin on 2017/5/16.
 */

public class ShareInEntityManager {

	public static ShareListItemInEntity createYanShi(Context mContext){
		ShareListItemInEntity entity = new ShareListItemInEntity();
		entity.setName(mContext.getString(R.string.item_title_txt_catagory_yanshi));
		entity.setType(SharePlatformType.TITLE_SHARE_PLAT);
		return entity;
	}

	public static ShareListItemInEntity createDirect(Context mContext){
		ShareListItemInEntity entity = new ShareListItemInEntity();
		entity.setType(SharePlatformType.DIRECT_SHARE_PLAT);
		return entity;
	}

	public static ShareListItemInEntity createInLand(Context mContext){
		ShareListItemInEntity entity = new ShareListItemInEntity();
		entity.setName(mContext.getString(R.string.item_title_txt_catagory_inland));
		entity.setType(SharePlatformType.TITLE_SHARE_PLAT);
		return entity;
	}

	public static ShareListItemInEntity createInternational(Context mContext){
		ShareListItemInEntity entity = new ShareListItemInEntity();
		entity.setName(mContext.getString(R.string.item_title_txt_catagory_internal));
		entity.setType(SharePlatformType.TITLE_SHARE_PLAT);
		return entity;
	}
	public static PlatformEntity createNormalInLand(Context mContext){
		PlatformEntity entity = new PlatformEntity();
		entity.setName(mContext.getString(R.string.item_title_txt_catagory_inland));
		entity.setmType(SharePlatformType.TITLE_SHARE_PLAT);
		return entity;
	}

	public static PlatformEntity createNormalInternational(Context mContext){
		PlatformEntity entity = new PlatformEntity();
		entity.setName(mContext.getString(R.string.item_title_txt_catagory_internal));
		entity.setmType(SharePlatformType.TITLE_SHARE_PLAT);
		return entity;
	}

	public static ShareListItemInEntity createSystem(Context mContext){
		ShareListItemInEntity entity = new ShareListItemInEntity();
		entity.setName(mContext.getString(R.string.item_title_txt_catagory_system));
		entity.setType(SharePlatformType.TITLE_SHARE_PLAT);
		return entity;
	}

	public static PlatformEntity createNormalSystem(Context mContext){
		PlatformEntity entity = new PlatformEntity();
		entity.setName(mContext.getString(R.string.item_title_txt_catagory_system));
		entity.setmType(SharePlatformType.TITLE_SHARE_PLAT);
		return entity;
	}

}
