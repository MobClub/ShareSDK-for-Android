/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 * 
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 这是一个简易的Json-HashMap转换工具，可以将普通的json数据（字符串）
 *转换为一个HashMap<Srting, Object>表格，也可以反过来操作。此外还支
 *持将json数据格式化。
 */
public class JsonUtils {
	
	/** 将指定的json数据转成 {@link HashMap}<String, Object>对象 */
	public HashMap<String, Object> fromJson(String jsonStr) {
		try {
			if (jsonStr.startsWith("[")
					&& jsonStr.endsWith("]")) {
				jsonStr = "{\"fakelist\":" + jsonStr + "}";
			}
			
			JSONObject json = new JSONObject(jsonStr);
			return fromJson(json);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return new HashMap<String, Object>();
	}
	
	private HashMap<String, Object> fromJson(JSONObject json) 
			throws JSONException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		Iterator<String> iKey = json.keys();
		while(iKey.hasNext()) {
			String key = iKey.next();
			Object value = json.opt(key);
			if (JSONObject.NULL.equals(value)) {
				value = null;
			}
			if (value != null) {
				if (value instanceof JSONObject) {
					value = fromJson((JSONObject)value);
				}
				else if (value instanceof JSONArray) {
					value = fromJson((JSONArray)value);
				}
				map.put(key, value);
			}
		}
		return map;
	}

	private ArrayList<Object> fromJson(JSONArray array) 
			throws JSONException {
		ArrayList<Object> list = new ArrayList<Object>();
		for (int i = 0, size = array.length(); i < size; i++) {
			Object value = array.opt(i);
			if (value instanceof JSONObject) {
				value = fromJson((JSONObject)value);
			}
			else if (value instanceof JSONArray) {
				value = fromJson((JSONArray)value);
			}
			list.add(value);
		}
		return list;
	}

	/** 将指定的 {@link HashMap}<String, Object>对象转成json数据 */
	public String fromHashMap(HashMap<String, Object> map) {
		try {
			return getJSONObject(map).toString();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return "";
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject getJSONObject(HashMap<String, Object> map) 
			throws JSONException {
		JSONObject json = new JSONObject();
		for (Entry<String, Object> entry : map.entrySet()) {
			Object value = entry.getValue();
			if (value instanceof HashMap<?, ?>) {
				value = getJSONObject((HashMap<String, Object>)value);
			}
			else if (value instanceof ArrayList<?>) {
				value = getJSONArray((ArrayList<Object>)value);
			}
			json.put(entry.getKey(), value);
		}
		return json;
	}
	
	@SuppressWarnings("unchecked")
	private JSONArray getJSONArray(ArrayList<Object> list) 
			throws JSONException {
		JSONArray array = new JSONArray();
		for (Object value : list) {
			if (value instanceof HashMap<?, ?>) {
				value = getJSONObject((HashMap<String, Object>)value);
			}
			else if (value instanceof ArrayList<?>) {
				value = getJSONArray((ArrayList<Object>)value);
			}
			array.put(value);
		}
		return array;
	}
	
	/** 格式化一个json串 */
	public String format(String jsonStr) {
		try {
			return format("", fromJson(jsonStr));
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return "";
	}
	
	@SuppressWarnings("unchecked")
	private String format(String sepStr, HashMap<String, Object> map) {
		StringBuffer sb = new StringBuffer();
		sb.append("{\n");
		String mySepStr = sepStr + "\t";
		int i = 0;
		for (Entry<String, Object> entry : map.entrySet()) {
			if (i > 0) {
				sb.append(",\n");
			}
			sb.append(mySepStr).append('\"').append(entry.getKey()).append("\":");
			Object value = entry.getValue();
			if (value instanceof HashMap<?, ?>) {
				sb.append(format(mySepStr, (HashMap<String, Object>)value));
			}
			else if (value instanceof ArrayList<?>) {
				sb.append(format(mySepStr, (ArrayList<Object>)value));
			}
			else if (value instanceof String) {
				sb.append('\"').append(value).append('\"');
			}
			else {
				sb.append(value);
			}
			i++;
		}
		sb.append('\n').append(sepStr).append('}');
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	private String format(String sepStr, ArrayList<Object> list) {
		StringBuffer sb = new StringBuffer();
		sb.append("[\n");
		String mySepStr = sepStr + "\t";
		int i = 0;
		for (Object value : list) {
			if (i > 0) {
				sb.append(",\n");
			}
			sb.append(mySepStr);
			if (value instanceof HashMap<?, ?>) {
				sb.append(format(mySepStr, (HashMap<String, Object>)value));
			}
			else if (value instanceof ArrayList<?>) {
				sb.append(format(mySepStr, (ArrayList<Object>)value));
			}
			else if (value instanceof String) {
				sb.append('\"').append(value).append('\"');
			}
			else {
				sb.append(value);
			}
			i++;
		}
		sb.append('\n').append(sepStr).append(']');
		return sb.toString();
	}
	
}
