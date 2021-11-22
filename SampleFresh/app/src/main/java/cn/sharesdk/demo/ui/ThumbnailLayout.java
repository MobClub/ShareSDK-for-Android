package cn.sharesdk.demo.ui;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.sharesdk.demo.R;



public class ThumbnailLayout extends LinearLayout {
	private TextView shareTxt;
	private long showDiss = 5000;
	private boolean isAutoHide;
	
	public ThumbnailLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public ThumbnailLayout(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.thumbnal_layout, null);
		shareTxt = (TextView) view.findViewById(R.id.shareFriends);
		addView(view);
	}

	@Override
	public void setVisibility(int visibility) {
		super.setVisibility(visibility);
		if (visibility == View.VISIBLE) {
			if (isAutoHide()) {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						setVisibility(View.GONE);
					}
				}, showDiss);
			}
		}
	}

	public boolean isAutoHide() {
		return isAutoHide;
	}

	public void setAutoHide(boolean autoHide) {
		isAutoHide = autoHide;
	}
}
