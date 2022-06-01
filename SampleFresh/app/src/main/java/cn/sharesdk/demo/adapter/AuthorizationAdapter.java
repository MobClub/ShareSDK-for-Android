package cn.sharesdk.demo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
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


public class AuthorizationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private List<PlatformEntity> lists;
	private Context context;
	private LayoutInflater inflater;
	private AuthorizationOnItemClickListener authorizationOnItemClickListener;
	private int currentPosition;

	public AuthorizationAdapter(Context context, List<PlatformEntity> entitys) {
		this.context = context;
		this.lists = entitys;
		inflater = LayoutInflater.from(context);
	}

	public void setAuthorizationOnItemClickListener(AuthorizationOnItemClickListener authorizationOnItemClickListener) {
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
			if (lists.get(position).getmPlatform() == null) {
				shareViewAuthNormal.authorization.setText(context.getString(R.string.authorization_txt));
			} else {
				if (lists.get(position).getmPlatform().isAuthValid()) {
					shareViewAuthNormal.authorization.setText(context.getString(R.string.authorization_txt_delete));

				} else if (lists.get(position).getmPlatform().getName().equals("Dingding")) {
					//钉钉平台没有token，特殊处理下
					try {
						String tempCode = lists.get(position).getmPlatform().getDb().get("tmp_auth_code");
						if (tempCode != null && tempCode.length() > 0) {
							shareViewAuthNormal.authorization.setText(context.getString(R.string.authorization_txt_delete));
						} else {
							shareViewAuthNormal.authorization.setText(context.getString(R.string.authorization_txt));
						}
					} catch (Throwable t) {
						Log.e("QQQ", " AuthorizationAdapter t: " + t);
					}

				} else if (lists.get(position).getmPlatform().getName().equals("Snapchat")) {
					try {
						int lengthInt = lists.get(position).getmPlatform().getDb().exportData().length();
						if (lengthInt > 2) {
							shareViewAuthNormal.authorization.setText(context.getString(R.string.authorization_txt_delete));
						} else {
							shareViewAuthNormal.authorization.setText(context.getString(R.string.authorization_txt));
						}
					} catch (Throwable t) {
						Log.e("QQQ", " AuthorizationAdapter t: " + t);
					}
				} else {
					shareViewAuthNormal.authorization.setText(context.getString(R.string.authorization_txt));
				}
			}
			shareViewAuthNormal.authorization.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					authorizationOnItemClickListener.OnItemClickListener(v, position);
					currentPosition = position;
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

	public int getOnClickCurrentPosition() {
		return currentPosition;
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

	public interface AuthorizationOnItemClickListener {
		void OnItemClickListener(View view, int position);
	}
}
