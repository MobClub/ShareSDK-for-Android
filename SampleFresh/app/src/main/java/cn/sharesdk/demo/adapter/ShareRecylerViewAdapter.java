package cn.sharesdk.demo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.MobSDK;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.demo.R;
import cn.sharesdk.demo.entity.ShareListItemInEntity;
import cn.sharesdk.demo.entity.SharePlatformType;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.loopshare.LoopSharePasswordListener;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;


public class ShareRecylerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Handler.Callback{
	private List<ShareListItemInEntity> listEntity;
	private Context context;
	private LayoutInflater inflater;
	private ListOnItemListener onItemListener;
	private ShotOnClickListener onShotListener;
	private Handler handler;
	private Activity activity;

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

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
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
			shareViewHolder.mVideoshareSelected.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onShotListener.onSharkClick();
				}
			});
			//口令分享
			shareViewHolder.passwrodShare.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("key1", "value1");
					map.put("key2", "value2");
					map.put("key3", "value3");
					map.put("key4", "value4");

					HashMap<String, Object> paramsMap = new HashMap<String, Object>();
					paramsMap.put("params", map);

					String paramasStr = "复制本段内容，“想你所想，高端品牌适合高端的你”，去粘贴给好友，打开ShareSDK查看详情";
					ShareSDK.preparePassWord(paramsMap, paramasStr, new LoopSharePasswordListener() {
						@Override
						public void onResult(Object var1) {
							Log.d("SharSDK", "onResult " + var1);
							Intent intent = new Intent();
							intent.setAction(Intent.ACTION_SEND);
							intent.setType("text/plain");
							intent.putExtra(Intent.EXTRA_TEXT, String.valueOf(var1));
							intent = Intent.createChooser(intent, String.valueOf(var1));
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							MobSDK.getContext().startActivity(intent);
						}

						@Override
						public void onError(Throwable var1) {
							Log.d("SharSDK", "onError " + var1);
							Toast toast = Toast.makeText(context, String.valueOf(var1), Toast.LENGTH_LONG);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
						}
					});
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
		oks.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
		oks.setActivity(getActivity());
		String customText = manager.getText();
		if (customText != null) {
			oks.setText(customText);
		} else {
			manager.getText();
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
		oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
			@Override
			public void onShare(Platform platform, Platform.ShareParams shareParams) {
				if(platform.getName().equals("Douyin")){
					shareParams.setShareType(Platform.SHARE_VIDEO);
				}
			}
		});

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
		public RelativeLayout mVideoshareSelected;
		public RelativeLayout passwrodShare;
		public RelativeLayout customParameters;

		public ShareViewHolder(View itemView) {
			super(itemView);
			shareSelected = itemView.findViewById(R.id.mSelected);
			shotSelected = itemView.findViewById(R.id.mShot);
			mVideoshareSelected = itemView.findViewById(R.id.layout_mVideoShare);
			passwrodShare = itemView.findViewById(R.id.layout_passwordshare);
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
			icon = itemView.findViewById(R.id.mIcon);
			name = itemView.findViewById(R.id.mTitle);
			layout = itemView.findViewById(R.id.onMainLayout);
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
