package cn.sharesdk.demo.entity;

import cn.sharesdk.framework.Platform;

/**
 * Created by yjin on 2017/5/12.
 */

public class PlatformEntity extends OutBaseEntity {

	private int type;
	private String name;
	private int icon;
	private String status;
	private Platform platform;

	public int getmType() {
		return type;
	}

	public void setmType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getmIcon() {
		return icon;
	}

	public void setmIcon(int mIcon) {
		this.icon = mIcon;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Platform getmPlatform() {
		return platform;
	}

	public void setmPlatform(Platform mPlatform) {
		this.platform = mPlatform;
	}
}
