/*
 * Offical Website:http://www.ShareSDK.cn
 * Support QQ: 4006852216
 * Offical Wechat Account:ShareSDK   (We will inform you our updated news at the first time by Wechat, if we release a new version. If you get any problem, you can also contact us with Wechat, we will reply you within 24 hours.)
 *
 * Copyright (c) 2013 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.socialization.sample;

import java.io.File;
import java.io.FileOutputStream;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.utils.UIHandler;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.socialization.CommentFilter;
import cn.sharesdk.socialization.QuickCommentBar;
import cn.sharesdk.socialization.Socialization;
import cn.sharesdk.socialization.CommentFilter.FilterItem;
import cn.sharesdk.socialization.component.TopicTitle;
import android.os.Bundle;
import android.os.Message;
import android.os.Handler.Callback;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements Callback, OnClickListener {
	private static final String FILE_NAME = "/pic_glance_back.jpg";
	private String testImage;
	// Simulat topic ID
	private String topicId;
	// Simulat topic title
	private String topicTitle;
	// Simulat topic publish time
	private String topicPublishTime;
	// Simulat topic author
	private String topicAuthor;
	private OnekeyShare oks;
	private QuickCommentBar qcBar;
	private CommentFilter filter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_comment_like);
		ShareSDK.initSDK(this);
		ShareSDK.registerService(Socialization.class);

		new Thread() {
			public void run() {
				initImagePath();
				UIHandler.sendEmptyMessageDelayed(1, 100, MainActivity.this);
			}
		}.start();
	}

	protected void onDestroy() {
		ShareSDK.stopSDK(this);
		super.onDestroy();
	}

	private void initImagePath() {
		try {
			String cachePath = cn.sharesdk.framework.utils.R.getCachePath(this, null);
			testImage = cachePath + FILE_NAME;
			File file = new File(testImage);
			if (!file.exists()) {
				file.createNewFile();
				Bitmap pic = BitmapFactory.decodeResource(getResources(), R.drawable.pic);
				FileOutputStream fos = new FileOutputStream(file);
				pic.compress(CompressFormat.JPEG, 100, fos);
				fos.flush();
				fos.close();
			}
		} catch(Throwable t) {
			t.printStackTrace();
			testImage = null;
		}
	}

	public boolean handleMessage(Message msg) {
		topicId = getString(R.string.comment_like_id);
		topicTitle = getString(R.string.comment_like_title);
		topicPublishTime = getString(R.string.comment_like_publich_time);
		topicAuthor = getString(R.string.comment_like_author);

		TopicTitle tt = (TopicTitle) findViewById(R.id.llTopicTitle);
		String topicTitle = getString(R.string.comment_like_title);
		tt.setTitle(topicTitle);
		tt.setPublishTime(getString(R.string.comment_like_publich_time));
		tt.setAuthor(getString(R.string.comment_like_author));

		Socialization service = ShareSDK.getService(Socialization.class);
		service.setCustomPlatform(new MyPlatform(this));
		initOnekeyShare();
		initQuickCommentBar();
		return false;
	}

	// Socialization service dependents on OnekeyShare， this method initializes a onekeyshare instance
	// the following codes are copied from DemoPage
	private void initOnekeyShare() {
		oks = new OnekeyShare();
		oks.setNotification(R.drawable.logo_sharesdk, getString(R.string.app_name));
		oks.setAddress("12345678901");
		oks.setTitle(getString(R.string.share));
		oks.setTitleUrl("http://sharesdk.cn");
		oks.setText(getString(R.string.share_content));
		oks.setImagePath(testImage);
		oks.setImageUrl("http://img.appgo.cn/imgs/sharesdk/content/2013/07/25/1374723172663.jpg");
		oks.setUrl("http://www.sharesdk.cn");
		oks.setFilePath(testImage);
		oks.setComment(getString(R.string.share));
		oks.setSite(getString(R.string.app_name));
		oks.setSiteUrl("http://sharesdk.cn");
		oks.setVenueName("ShareSDK");
		oks.setVenueDescription("This is a beautiful place!");
		oks.setLatitude(23.056081f);
		oks.setLongitude(113.385708f);
		oks.disableSSOWhenAuthorize();
		oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
			public void onShare(Platform platform, ShareParams paramsToShare) {
				// shorten the text field of twitter share content
				if ("Twitter".equals(platform.getName())) {
					paramsToShare.setText(platform.getContext().getString(R.string.share_content_short));
				}
			}
		});
	}

	private void initQuickCommentBar() {
		qcBar = (QuickCommentBar) findViewById(R.id.qcBar);
		qcBar.setTopic(topicId, topicTitle, topicPublishTime, topicAuthor);
		qcBar.getBackButton().setOnClickListener(this);
		CommentFilter.Builder builder = new CommentFilter.Builder();
		// non-empty filter
		builder.append(new FilterItem() {
			public boolean onFilter(String comment) {
				if (TextUtils.isEmpty(comment)) {
					return true;
				} else if (comment.trim().length() <= 0) {
					return true;
				} else if (comment.trim().toLowerCase().equals("null")) {
					return true;
				}
				return false;
			}
		});
		// limit filter
		builder.append(new FilterItem() {
			// returns true if the comment passed in is spam
			public boolean onFilter(String comment) {
				if (comment != null) {
					String pureComment = comment.trim();
					String wordText = cn.sharesdk.framework.utils.R.toWordText(pureComment, 140);
					if (wordText.length() != pureComment.length()) {
						return true;
					}
				}
				return false;
			}
		});
		filter = builder.build();
		qcBar.setCommentFilter(filter);
		qcBar.setOnekeyShare(oks);
	}

	public void onClick(View v) {
		if (v.equals(qcBar.getBackButton())) {
			finish();
		}
	}

}
