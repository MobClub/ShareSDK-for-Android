package cn.sharesdk.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.sharesdk.demo.R;
import cn.sharesdk.demo.manager.platform.PlatformUITypeManager;


public class SharePlatformAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private List<Integer> platForms;
	private Context context;
	private LayoutInflater inflater;
	private OnClickItemListener onClickItemListener;

	public void setOnClickItemListener(OnClickItemListener onClickItemListener){
		this.onClickItemListener = onClickItemListener;
	}

	public SharePlatformAdapter(List<Integer> lists,Context context){
		this.platForms = lists;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}
	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.share_platform_item,parent,false);
		return new PlatFormViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
		final int shareType = platForms.get(position);
		if (holder instanceof PlatFormViewHolder) {
			PlatFormViewHolder viewHolder = (PlatFormViewHolder) holder;
			viewHolder.platformIcon.setImageResource(PlatformUITypeManager.getPlatformIcon(shareType));
			viewHolder.platformName.setText(PlatformUITypeManager.getPlatformName(shareType));
			viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (onClickItemListener != null) {
						onClickItemListener.onItemClick(shareType);
					}
				}
			});
		}
	}

	@Override
	public int getItemCount() {
		return platForms.size();
	}
	private class PlatFormViewHolder extends RecyclerView.ViewHolder{
		ImageView platformIcon;
		TextView platformName;
		RelativeLayout platformBtn;

		public PlatFormViewHolder(View itemView) {
			super(itemView);
			platformIcon = itemView.findViewById(R.id.mSelectedPlatform);
			platformName = itemView.findViewById(R.id.mplatformName);
			platformBtn = itemView.findViewById(R.id.shareBtn);
		}
	}

	/**
	 * 点击事件的实现
	 */
	public interface OnClickItemListener{
		void onItemClick(int platform);
	}
}
