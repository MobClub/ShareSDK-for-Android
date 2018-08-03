package cn.sharesdk.demo.entity;

import java.io.Serializable;

import cn.sharesdk.framework.Platform;

/**
 * Created by yjin on 2017/5/11.
 */

public class ShareListItemInEntity  implements Serializable{
	private String name;
	private int icon;
	private int type;
	private Platform platform;
	private String platName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
		if(platform != null){
			setPlatName(platform.getName());
		}
	}

	public String getPlatName() {
		return platName;
	}

	public void setPlatName(String platName) {
		this.platName = platName;
	}
}
