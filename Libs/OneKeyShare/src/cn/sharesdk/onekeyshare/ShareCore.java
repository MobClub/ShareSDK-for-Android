//#if def{lang} == cn
/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 * 
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */
//#endif

package cn.sharesdk.onekeyshare;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map.Entry;
import cn.sharesdk.framework.AbstractWeibo;
import cn.sharesdk.framework.AbstractWeibo.ShareParams;

//#if def{lang} == cn
/**
 * ShareCore是快捷分享的实际出口，此类使用了反射的方式，配合传递进来的HashMap，
 *构造{@link ShareParams}对象，并执行分享，使快捷分享不再需要考虑目标平台
 */
//#endif
public class ShareCore {
	
	//#if def{lang} == cn
	/**
	 * 向指定平台分享内容
	 * <p>
	 * <b>注意：</b><br>
	 * 参数data的键值需要严格按照{@link ShareParams}不同子类具体字段来命名，
	 *否则无法反射此字段，也无法设置其值。
	 */
	//#endif
	public boolean share(AbstractWeibo weibo, HashMap<String, Object> data) {
		if (weibo == null || data == null) {
			return false;
		}
		
		ShareParams sp = null;
		try {
			sp = getShareParams(weibo, data);
		} catch(Throwable t) {
			sp = null;
		}
		
		if (sp != null) {
			weibo.share(sp);
		}
		return false;
	}
	
	private ShareParams getShareParams(AbstractWeibo weibo, 
			HashMap<String, Object> data) throws Throwable {
		String className = weibo.getClass().getName() + "$ShareParams";
		Class<?> cls = Class.forName(className);
		if (cls == null) {
			return null;
		}
		
		Object sp = cls.newInstance();
		if (sp == null) {
			return null;
		}
		
		for (Entry<String, Object> ent : data.entrySet()) {
			try {
				Field fld = cls.getField(ent.getKey());
				if (fld != null) {
					fld.setAccessible(true);
					fld.set(sp, ent.getValue());
				}
			} catch(Throwable t) {}
		}
		
		return (ShareParams) sp;
	}
	
}
