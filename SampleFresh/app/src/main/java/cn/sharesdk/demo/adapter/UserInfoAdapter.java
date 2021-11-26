package cn.sharesdk.demo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.sharesdk.demo.R;
import cn.sharesdk.demo.entity.PlatformEntity;
import cn.sharesdk.demo.entity.SharePlatformType;
import cn.sharesdk.framework.Platform;


public class UserInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private List<PlatformEntity> lists;
	private Context context;
	private LayoutInflater inflater;
	private UserInfoOnItemClickListener authorizationOnItemClickListener;
	private int onClickCurrentPostion;

	public UserInfoAdapter(Context context, List<PlatformEntity> entitys) {
		this.context = context;
		this.lists = entitys;
		inflater = LayoutInflater.from(context);
	}

	public int getOnClickCurrentPostion() {
		return onClickCurrentPostion;
	}

	public void setAuthorizationOnItemClickListener(UserInfoOnItemClickListener authorizationOnItemClickListener) {
		this.authorizationOnItemClickListener = authorizationOnItemClickListener;
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view;
		if (viewType == SharePlatformType.TITLE_SHARE_PLAT) {
			view = inflater.inflate(R.layout.share_title_content, parent, false);
			return new ShareViewHolderTitleNormal(view);
		} else {
			view = inflater.inflate(R.layout.normal_authorization_layout, parent, false);
			return new ShareViewAuthorizationNormal(view);
		}
	}

	@Override
	public int getItemViewType(int position) {
		PlatformEntity entity = lists.get(position);
		if (entity.getmType() == SharePlatformType.TITLE_SHARE_PLAT) {
			return SharePlatformType.TITLE_SHARE_PLAT;
		} else {
			return SharePlatformType.FOREIGN_SHARE_PLAT;
		}
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
		PlatformEntity entity = lists.get(position);
		if (holder instanceof ShareViewHolderTitleNormal) {
			ShareViewHolderTitleNormal holderTitleNormal = (ShareViewHolderTitleNormal) holder;
			holderTitleNormal.textView.setText(entity.getName());
		}
		if (holder instanceof ShareViewAuthorizationNormal) {
			ShareViewAuthorizationNormal shareViewAuthNormal = (ShareViewAuthorizationNormal) holder;
			Platform platform = entity.getmPlatform();
			if (platform == null) {
				shareViewAuthNormal.authorization.setText(context.getString(R.string.userinfo_txt));
				shareViewAuthNormal.authorization.setBackgroundResource(R.drawable.show_sure_author_userinfo_bg);
				shareViewAuthNormal.authorization.setTextColor(Color.parseColor("#FFC794"));
			} else if (platform.getName().equals("Dingding")) {
				String tempCode = lists.get(position).getmPlatform().getDb().get("tmp_auth_code");
				if (tempCode != null && tempCode.length() > 0) {
					shareViewAuthNormal.authorization.setText(context.getString(R.string.userinfo_show));
					shareViewAuthNormal.authorization.setBackgroundResource(R.drawable.show_userinfo_bg_);
					shareViewAuthNormal.authorization.setTextColor(Color.WHITE);
				} else {
					shareViewAuthNormal.authorization.setText(context.getString(R.string.userinfo_txt));
					shareViewAuthNormal.authorization.setBackgroundResource(R.drawable.show_sure_author_userinfo_bg);
					shareViewAuthNormal.authorization.setTextColor(Color.parseColor("#FFC794"));
				}
			} else if (platform.getName().equals("Snapchat")) {
				int lengthInt = lists.get(position).getmPlatform().getDb().exportData().length();
				if (lengthInt > 2) {
					shareViewAuthNormal.authorization.setText(context.getString(R.string.userinfo_show));
					shareViewAuthNormal.authorization.setBackgroundResource(R.drawable.show_userinfo_bg_);
					shareViewAuthNormal.authorization.setTextColor(Color.WHITE);
				} else {
					shareViewAuthNormal.authorization.setText(context.getString(R.string.userinfo_txt));
					shareViewAuthNormal.authorization.setBackgroundResource(R.drawable.show_sure_author_userinfo_bg);
					shareViewAuthNormal.authorization.setTextColor(Color.parseColor("#FFC794"));
				}
			} else {
				if (platform.isAuthValid()) {
					shareViewAuthNormal.authorization.setText(context.getString(R.string.userinfo_show));
					shareViewAuthNormal.authorization.setBackgroundResource(R.drawable.show_userinfo_bg_);
					shareViewAuthNormal.authorization.setTextColor(Color.WHITE);
				} else {
					shareViewAuthNormal.authorization.setText(context.getString(R.string.userinfo_txt));
					shareViewAuthNormal.authorization.setBackgroundResource(R.drawable.show_sure_author_userinfo_bg);
					shareViewAuthNormal.authorization.setTextColor(Color.parseColor("#FFC794"));
				}
			}
			shareViewAuthNormal.authorization.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					authorizationOnItemClickListener.OnItemClickListener(v, position);
				}
			});
			String name = entity.getName();
			if (!TextUtils.isEmpty(name)) {
				shareViewAuthNormal.textView.setText(entity.getName());
			}
			int icon = entity.getmIcon();
			if (icon > 1) {
				shareViewAuthNormal.platIcon.setImageResource(icon);
			}
		}
	}

	@Override
	public int getItemCount() {
		return lists.size();
	}

	class ShareViewHolderTitleNormal extends RecyclerView.ViewHolder {
		public TextView textView;

		public ShareViewHolderTitleNormal(View itemView) {
			super(itemView);
			textView = itemView.findViewById(R.id.titleTxt);
		}
	}

	class ShareViewAuthorizationNormal extends RecyclerView.ViewHolder {
		TextView textView;
		ImageView platIcon;
		TextView authorization;

		public ShareViewAuthorizationNormal(View itemView) {
			super(itemView);
			textView = itemView.findViewById(R.id.platName);
			platIcon = itemView.findViewById(R.id.platIcon);
			authorization = itemView.findViewById(R.id.platStatus);
		}
	}

	public interface UserInfoOnItemClickListener {
		void OnItemClickListener(View view, int position);
	}
}
