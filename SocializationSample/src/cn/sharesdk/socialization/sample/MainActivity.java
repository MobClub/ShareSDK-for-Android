/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package cn.sharesdk.socialization.sample;

import static com.mob.tools.utils.R.getStringRes;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.socialization.Comment;
import cn.sharesdk.socialization.CommentFilter;
import cn.sharesdk.socialization.CommentFilter.FilterItem;
import cn.sharesdk.socialization.CommentListener;
import cn.sharesdk.socialization.LikeListener;
import cn.sharesdk.socialization.QuickCommentBar;
import cn.sharesdk.socialization.Socialization;
import cn.sharesdk.socialization.component.ReplyTooFrequentlyException;
import cn.sharesdk.socialization.component.TopicTitle;

import com.mob.tools.utils.UIHandler;

/** 评论和赞功能的演示页面 */
public class MainActivity extends Activity implements Callback, OnClickListener {
	// 模拟的主题id
	private String topicId;
	// 模拟的主题标题
	private String topicTitle;
	// 模拟的主题发布时间
	private String topicPublishTime;
	// 模拟的主题作者
	private String topicAuthor;
	private OnekeyShare oks;
	private QuickCommentBar qcBar;
	private CommentFilter filter;
	private Context context;

	private static final int INIT_SDK = 1;
	private static final int AFTER_LIKE = 2;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_comment_like);
		context = MainActivity.this;
		ShareSDK.initSDK(this);
		ShareSDK.registerService(Socialization.class);
		Socialization service = ShareSDK.getService(Socialization.class);
		service.setCustomPlatform(new MyPlatform(this));

		new Thread() {
			public void run() {
				UIHandler.sendEmptyMessageDelayed(INIT_SDK, 100, MainActivity.this);
			}
		}.start();

		//设置评论监听
		Socialization.setCommentListener(new CommentListener() {

			@Override
			public void onSuccess(Comment comment) {
				int resId = getStringRes(context, "ssdk_socialization_reply_succeeded");
				if (resId > 0) {
					Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onFail(Comment comment) {
				Toast.makeText(context, comment.getFileCodeString(context), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onError(Throwable throwable) {
				if (throwable instanceof ReplyTooFrequentlyException) {
					int resId = getStringRes(context, "ssdk_socialization_replay_too_frequently");
					if (resId > 0) {
						Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
					}
				} else {
					throwable.printStackTrace();
				}
			}
		});

		Socialization.setLikeListener(new LikeListener() {

			@Override
			public void onSuccess(String topicId, String topicTitle, String commentId) {
				Message msg = new Message();
				msg.what = AFTER_LIKE;
				msg.arg1 = 1;
				UIHandler.sendMessage(msg, MainActivity.this);
			}

			@Override
			public void onFail(String topicId, String topicTitle, String commentId, String error) {
				Message msg = new Message();
				msg.what = AFTER_LIKE;
				msg.arg1 = 2;
				UIHandler.sendMessage(msg, MainActivity.this);
			}

		});
	}

	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case INIT_SDK:
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
			break;
		case AFTER_LIKE:
			if(msg.arg1 == 1){
				//success
				int resId = getStringRes(context, "like_success");
				if (resId > 0) {
					Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
				}
			}else {
				//fail
				int resId = getStringRes(context, "like_fail");
				if (resId > 0) {
					Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case 3:
			break;
		default:
			break;

		}

		return false;
	}

	// Socialization服务依赖OnekeyShare组件，此方法初始化一个OnekeyShare对象
	// 此方法的代码从DemoPage中复制而来
	private void initOnekeyShare() {
		oks = new OnekeyShare();
		oks.setAddress("12345678901");
		oks.setTitle(getString(R.string.ssdk_oks_share));
		oks.setTitleUrl("http://mob.com");
		oks.setText(getString(R.string.share_content));
		oks.setUrl("http://www.mob.com");
		oks.setComment(getString(R.string.ssdk_oks_share));
		oks.setSite(getString(R.string.app_name));
		oks.setSiteUrl("http://mob.com");
		oks.setVenueName("ShareSDK");
		oks.setVenueDescription("This is a beautiful place!");
		oks.disableSSOWhenAuthorize();
		oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
			public void onShare(Platform platform, ShareParams paramsToShare) {
				// 改写twitter分享内容中的text字段，否则会超长，
				// 因为twitter会将图片地址当作文本的一部分去计算长度
				if ("Twitter".equals(platform.getName())) {
					paramsToShare.setText(platform.getContext().getString(R.string.share_content_short));
				}
			}
		});
	}

	private void initQuickCommentBar() {
		qcBar = (QuickCommentBar) findViewById(R.id.qcBar);
		qcBar.setTopic(topicId, topicTitle, topicPublishTime, topicAuthor);
		qcBar.setTextToShare(getString(R.string.share_content));
		qcBar.getBackButton().setOnClickListener(this);
		qcBar.setAuthedAccountChangeable(false);

		CommentFilter.Builder builder = new CommentFilter.Builder();
		// 非空过滤器
		builder.append(new FilterItem() {
			// 返回true表示是垃圾评论
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

			@Override
			public int getFilterCode() {
				return 0;
			}
		});
		// 字数上限过滤器
		builder.append(new FilterItem() {
			// 返回true表示是垃圾评论
			public boolean onFilter(String comment) {
				if (comment != null) {
					String pureComment = comment.trim();
					String wordText = com.mob.tools.utils.R.toWordText(pureComment, 140);
					if (wordText.length() != pureComment.length()) {
						return true;
					}
				}
				return false;
			}

			@Override
			public int getFilterCode() {
				return 0;
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
