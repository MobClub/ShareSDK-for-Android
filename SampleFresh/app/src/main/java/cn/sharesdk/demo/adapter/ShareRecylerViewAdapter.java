package cn.sharesdk.demo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import cn.sharesdk.demo.R;
import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.demo.entity.ShareListItemInEntity;
import cn.sharesdk.demo.entity.SharePlatformType;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by yjin on 2017/5/11.
 */

public class ShareRecylerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Handler.Callback{
	private List<ShareListItemInEntity> listEntity;
	private Context context;
	private LayoutInflater inflater;
	private ListOnItemListener onItemListener;
	private ShotOnClickListener onShotListener;
	private Handler handler;

	public void setOnItemListener(ListOnItemListener onItemListener){
		this.onItemListener = onItemListener;
	}

	public void setOnShotListener(ShotOnClickListener onShotListener){
		this.onShotListener = onShotListener;
	}

	public ShareRecylerViewAdapter(Context context, List<ShareListItemInEntity> entitys) {
		this.context = context;
		this.listEntity = entitys;
		inflater =LayoutInflater.from(context);
		handler = new Handler(Looper.getMainLooper(), this);
	}

	@Override
	public int getItemViewType(int position) {
		if (listEntity.get(position).getType() == SharePlatformType.DIRECT_SHARE_PLAT) {
			return SharePlatformType.DIRECT_SHARE_PLAT;
		} else if (listEntity.get(position).getType() == SharePlatformType.TITLE_SHARE_PLAT) {
			return SharePlatformType.TITLE_SHARE_PLAT;
		} else  {
			return SharePlatformType.FOREIGN_SHARE_PLAT;
		}
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		final View view;
		if (viewType == SharePlatformType.DIRECT_SHARE_PLAT) {
			view = inflater.inflate(R.layout.layout_share_yanshi_item,parent,false);
			return new ShareViewHolder(view);
		} else if (viewType == SharePlatformType.TITLE_SHARE_PLAT) {
			view = inflater.inflate(R.layout.share_title_content,parent,false);
			return new ShareViewHolderTitle(view);
		} else {
			view = inflater.inflate(R.layout.share_content_text,parent,false);
			return new ShareViewHolderContext(view);
		}
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if(holder instanceof ShareViewHolderTitle){
			ShareViewHolderTitle viewHolderTitle = (ShareViewHolderTitle)holder;
			viewHolderTitle.setData(position);
		}
		if(holder instanceof ShareViewHolder){
			ShareViewHolder shareViewHolder = (ShareViewHolder)holder;
			shareViewHolder.shareSelected.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onOneKeyShare();
				}
			});
			shareViewHolder.shotSelected.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onShotListener.onClick();
				}
			});
			shareViewHolder.sharkSelected.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onShotListener.onSharkClick();
				}
			});
		}
		if(holder instanceof ShareViewHolderContext){
			final ShareViewHolderContext viewHolderContext = (ShareViewHolderContext)holder;
			viewHolderContext.setData(position);
			viewHolderContext.layout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onItemListener.onClick(viewHolderContext.itemView,viewHolderContext.getLayoutPosition());
				}
			});
		}
	}
	private void showToast(String text){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 一键分享
	 */
	private void onOneKeyShare(){
		cn.sharesdk.onekeyshare.OnekeyShare oks = new cn.sharesdk.onekeyshare.OnekeyShare();
		oks.setAddress("12345678901");
		ResourcesManager manager = ResourcesManager.getInstace(context);
		if(!TextUtils.isEmpty(manager.getFilePath())){
			oks.setFilePath(manager.getFilePath());
		} else{
			oks.setFilePath(manager.getFilePath());
		}
		oks.setTitle(manager.getTitle());
		oks.setTitleUrl(manager.getTitleUrl());
		oks.setUrl(manager.getUrl());
		oks.setMusicUrl(manager.getMusicUrl());
		String customText = manager.getText();
		if (customText != null) {
			oks.setText(customText);
		} else if (manager.getText() != null && manager.getText().contains("0")) {
			oks.setText(manager.getText());
		} else {
			oks.setText(context.getString(R.string.share_content));
		}
		oks.setComment(manager.getComment());
		oks.setSite(manager.getSite());
		oks.setSiteUrl(manager.getSiteUrl());
		oks.setVenueName(manager.getVenueName());
		oks.setVenueDescription(manager.getVenueDescription());
		oks.setSilent(true);
		oks.setLatitude(23.169f);
		oks.setLongitude(112.908f);
		oks.setCallback(new PlatformActionListener() {
			@Override
			public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
				String msg = ResourcesManager.actionToString(i);
				String text = platform.getName() + " completed at " + msg;
				Message message = new Message();
				message.obj = text;
				handler.sendMessage(message);
			}

			@Override
			public void onError(Platform platform, int i, Throwable throwable) {
				String msg = ResourcesManager.actionToString(i);
				String text = platform.getName() + "caught error at " + msg;
				Message message = new Message();
				message.obj = text;
				handler.sendMessage(message);
			}

			@Override
			public void onCancel(Platform platform, int i) {
				String msg = ResourcesManager.actionToString(i);
				String text = platform.getName() + " canceled at " + msg;
				Message message = new Message();
				message.obj = text;
				handler.sendMessage(message);
			}
		});
		Bitmap logo = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
		String label = context.getString(R.string.app_name);
		View.OnClickListener listener = new View.OnClickListener() {
			public void onClick(View v) {
				String text = "Customer Logo -- ShareSDK ";
				Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
			}
		};
		oks.setCustomerLogo(logo, label, listener);
		oks.show(context);
	}

	@Override
	public int getItemCount() {
		return listEntity.size();
	}

	@Override
	public boolean handleMessage(Message msg) {
		String toastMsg = (String) msg.obj;
		showToast(toastMsg);
		return false;
	}

	class ShareViewHolder extends RecyclerView.ViewHolder {
		public ImageView shareSelected;
		public ImageView shotSelected;
		public ImageView sharkSelected;
		public ShareViewHolder(View itemView) {
			super(itemView);
			shareSelected =(ImageView) itemView.findViewById(R.id.mSelected);
			shotSelected = (ImageView) itemView.findViewById(R.id.mShot);
			sharkSelected = (ImageView) itemView.findViewById(R.id.mShark);
		}
	}
	class ShareViewHolderTitle extends RecyclerView.ViewHolder {
		public TextView textView;
		public ShareViewHolderTitle(View itemView) {
			super(itemView);
			textView =(TextView) itemView.findViewById(R.id.titleTxt);
		}

		public void setData(int position){
			textView.setText(listEntity.get(position).getName());
		}
	}

	class ShareViewHolderContext extends RecyclerView.ViewHolder {
		public ImageView icon;
		public TextView name;
		public RelativeLayout layout;
		public ShareViewHolderContext(View itemView) {
			super(itemView);
			icon = (ImageView)itemView.findViewById(R.id.mIcon);
			name = (TextView)itemView.findViewById(R.id.mTitle);
			layout = (RelativeLayout) itemView.findViewById(R.id.onMainLayout);
		}

		public void setData(int postion){
			name.setText(listEntity.get(postion).getName());
			icon.setImageResource(listEntity.get(postion).getIcon());
		}
	}

	public interface ListOnItemListener{
		public void onClick(View view,int position);
	}

	public interface ShotOnClickListener{
		public void onClick();
		public void onSharkClick();
	}
}
