package cn.sharesdk.demo.entity;

/**
 * Created by yjin on 2017/5/17.
 */

/**
 * 分享实体类，完成分享内容。
 */
public class SharePlatforOutEntity  {
	private boolean localVideoShare;//本地视频
	private boolean appShare;//应用分享
	private boolean faceShare;//表情分享
	private boolean miniWXShare;//小程序分享
	private boolean imgsShares;//多图分享
	private boolean shotSahre;//截图分享
	private boolean singlersImgShare;//单图分享
	private boolean txtShare;//文本分享
	private boolean imgTxtShare;//图文分享
	private boolean musicShare;//音乐分享
	private boolean videoUrlShare;//视频链接分享
	private boolean webPageShare;//链接分享

	public boolean isLocalVideoShare() {
		return localVideoShare;
	}

	public void setLocalVideoShare(boolean localVideoShare) {
		this.localVideoShare = localVideoShare;
	}

	public boolean isAppShare() {
		return appShare;
	}

	public void setAppShare(boolean appShare) {
		this.appShare = appShare;
	}

	public boolean isFaceShare() {
		return faceShare;
	}

	public void setFaceShare(boolean faceShare) {
		this.faceShare = faceShare;
	}

	public boolean isMiniWXShare() {
		return miniWXShare;
	}

	public void setMiniWXShare(boolean miniWXShare) {
		this.miniWXShare = miniWXShare;
	}

	public boolean isImgsShares() {
		return imgsShares;
	}

	public void setImgsShares(boolean imgsShares) {
		this.imgsShares = imgsShares;
	}

	public boolean isShotSahre() {
		return shotSahre;
	}

	public void setShotSahre(boolean shotSahre) {
		this.shotSahre = shotSahre;
	}

	public boolean isSinglersImgShare() {
		return singlersImgShare;
	}

	public void setSinglersImgShare(boolean singlersImgShare) {
		this.singlersImgShare = singlersImgShare;
	}

	public boolean isTxtShare() {
		return txtShare;
	}

	public void setTxtShare(boolean txtShare) {
		this.txtShare = txtShare;
	}

	public boolean isImgTxtShare() {
		return imgTxtShare;
	}

	public void setImgTxtShare(boolean imgTxtShare) {
		this.imgTxtShare = imgTxtShare;
	}

	public boolean isMusicShare() {
		return musicShare;
	}

	public void setMusicShare(boolean musicShare) {
		this.musicShare = musicShare;
	}

	public boolean isVideoUrlShare() {
		return videoUrlShare;
	}

	public void setVideoUrlShare(boolean videoUrlShare) {
		this.videoUrlShare = videoUrlShare;
	}

	public boolean isWebPageShare() {
		return webPageShare;
	}

	public void setWebPageShare(boolean webPageShare) {
		this.webPageShare = webPageShare;
	}
}
