package cn.sharesdk.demo;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import cn.sharesdk.framework.CustomPlatform;

public class Laiwang extends CustomPlatform {
	public static final String NAME = Laiwang.class.getSimpleName();

	public Laiwang(Context context) {
		super(context);
	}

	public String getName() {
		return NAME;
	}

	protected boolean checkAuthorize(int action, Object extra) {
		return isValid();
	}

	public boolean isValid() {
		return isClientInstalled();
	}

	private boolean isClientInstalled() {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setPackage("com.alibaba.android.babylon");
		i.setType("image/*");
		PackageManager pm = getContext().getPackageManager();
		List<?> ris = pm.queryIntentActivities(
				i, PackageManager.GET_ACTIVITIES);
		return ris != null && ris.size() > 0;
	}

	protected void doShare(ShareParams params) {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setPackage("com.alibaba.android.babylon");
		if (!TextUtils.isEmpty(params.imagePath)
				&& (new File(params.imagePath).exists())) {
			Uri uri = Uri.fromFile(new File(params.imagePath));
			i.putExtra(Intent.EXTRA_STREAM, uri);
			i.setType("image/*");
			try {
				getContext().startActivity(i);
				if (listener != null) {
					HashMap<String, Object> res = new HashMap<String, Object>();
					res.put("ShareParams", params);
					listener.onComplete(this, ACTION_SHARE, res);
				}
			} catch (Throwable t) {
				listener.onError(this, ACTION_SHARE, t);
			}
		} else if (!TextUtils.isEmpty(params.text)) {
			i.putExtra(Intent.EXTRA_TEXT, params.text);
			i.setType("text/plain");
			try {
				getContext().startActivity(i);
				if (listener != null) {
					HashMap<String, Object> res = new HashMap<String, Object>();
					res.put("ShareParams", params);
					listener.onComplete(this, ACTION_SHARE, res);
				}
			} catch (Throwable t) {
				listener.onError(this, ACTION_SHARE, t);
			}
		} else if (listener != null) {
			listener.onError(this, ACTION_SHARE, new Throwable("Share content is empty!"));
		}
	}

}
