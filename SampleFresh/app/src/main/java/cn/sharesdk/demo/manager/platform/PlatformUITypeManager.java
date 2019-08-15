package cn.sharesdk.demo.manager.platform;

import cn.sharesdk.demo.R;
import cn.sharesdk.framework.Platform;

/**
 * Created by yjin on 2017/5/17.
 */

/**
 * 类型资源管理类，资源图片跟分享类型的映射。
 */
public class PlatformUITypeManager {

	public static int getPlatformIcon(int share_type) {
		switch (share_type) {
			case Platform.SHARE_VIDEO: {
				return R.mipmap.base_video;
			}
			case Platform.SHARE_TEXT: {
				return R.mipmap.share_text;
			}
			case Platform.SHARE_IMAGE: {
				return R.mipmap.share_multimages;
			}
			case Platform.SHARE_APPS: {
				return R.mipmap.share_app;
			}
			case Platform.SHARE_FILE: {
				return R.mipmap.base_file;
			}
			case Platform.SHARE_EMOJI: {
				return R.mipmap.share_icon;
			}
			case Platform.SHARE_WXMINIPROGRAM: {
				return R.mipmap.share_mini_program;
			}
			case Platform.SHARE_MUSIC: {
				return R.mipmap.share_url_music;
			}
			case Platform.SHARE_LINKCARD: {
				return R.mipmap.share_url_music;
			}
			default: {
				return R.mipmap.share_webpage;
			}
		}
	}

	public static int getPlatformName(int share_type) {
		switch (share_type) {
			case Platform.SHARE_VIDEO: {
				return R.string.platform_share_video;
			}
			case Platform.SHARE_TEXT: {
				return R.string.platform_share_text;
			}
			case Platform.SHARE_IMAGE: {
				return R.string.platform_share_image;
			}
			case Platform.SHARE_APPS: {
				return R.string.platform_share_app;
			}
			case Platform.SHARE_FILE: {
				return R.string.platform_share_file;
			}
			case Platform.SHARE_EMOJI: {
				return R.string.platform_share_emoji;
			}
			case Platform.SHARE_WXMINIPROGRAM: {
				return R.string.platform_share_mini_app;
			}
			case Platform.SHARE_WEBPAGE: {
				return R.string.platform_share_webpage;
			}
			case Platform.SHARE_MUSIC: {
				return R.string.platform_share_music;
			}
			case Platform.SHARE_LINKCARD: {
				return R.string.platform_share_linkcard;
			}
			case Platform.QQ_MINI_PROGRAM: {
				return R.string.platform_share_qqmini;
			}
			default: {
				return R.string.platform_share_webpage;
			}
		}
	}
}
