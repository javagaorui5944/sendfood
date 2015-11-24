package com.fenghuo.util;

import java.io.File;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fenghuo.domain.Snacks;

public class CommonUtil {
	private static Logger log = LoggerFactory.getLogger(CommonUtil.class);

	/**
	 * @author jipeng
	 * @return
	 * 
	 *         构造返回json
	 * 
	 */
	public static JSONObject constructResponse(int code, String msg, Object data) {
		JSONObject jo = new JSONObject();
		jo.put("code", code);
		jo.put("msg", msg);
		jo.put("data", data);
		return jo;
	}

	/**
	 * 返回的数据进行格式转换，并且进行 html 转义
	 * 
	 * @param code
	 *            状态标示
	 * @param msg
	 *            描述信息
	 * @param data
	 *            需要返回的数据
	 * @return
	 */
	public static JSONObject constructHtmlResponse(int code, String msg,
			Object data) {
		// &lt; 对应符号< , &gt; 对应符号>
		JSONObject jo = new JSONObject();
		jo.put("code", code);
		jo.put("msg", msg);
		jo.put("data", data);
		// String json = jo.toJSONString().replaceAll("&",
		// "&amp;").replaceAll("\"", "&quot;").replaceAll("'",
		// "&acute;").replaceAll("<", "&lt;").replaceAll(">","&gt;");
		// return JSONObject.parseObject(json);
		return jo;
	}

	/**
	 * 返回的数据进行格式转换，并且进行 html 转义
	 * 
	 * @return
	 */
	public static JSONObject escapeHtmlOjbect(JSONObject jo) {
		Iterator<String> keys = jo.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			Object value = jo.get(key);
			if (value instanceof JSONObject) {
				return escapeHtmlOjbect((JSONObject) value);
			} else if (value instanceof JSONArray) {
				value = escapeHtmlArray((JSONArray) value);
			} else if (value instanceof String) {
				String valueStr = value.toString();
				if (valueStr != null && !"".equals(valueStr)) {
					valueStr = valueStr.toString().replaceAll("&", "&amp;")
							.replaceAll("\"", "&quot;")
							.replaceAll("'", "&acute;").replaceAll("<", "&lt;")
							.replaceAll(">", "&gt;");
					jo.put(key, valueStr);
				}
			}
		}
		return jo;
	}

	public static JSONArray escapeHtmlArray(JSONArray ja) {
		Iterator<Object> keys = ja.iterator();
		while (keys.hasNext()) {
			Object object = keys.next();
			if (object instanceof JSONObject) {
				object = escapeHtmlOjbect((JSONObject) object);
			} else if (object instanceof JSONArray) {
				object = escapeHtmlArray((JSONArray) object);
			} else if (object instanceof String) {
				String valueStr = object.toString();
				if (valueStr != null && !"".equals(valueStr)) {
					valueStr = valueStr.toString().replaceAll("&", "&amp;")
							.replaceAll("\"", "&quot;")
							.replaceAll("'", "&acute;").replaceAll("<", "&lt;")
							.replaceAll(">", "&gt;");
					object = valueStr;
				}
			}
		}
		return ja;
	}

	public static JSONObject defaultOrder2JSON(List<Snacks> snacks) {
		JSONObject jo = new JSONObject();
		JSONArray array = new JSONArray();
		for (Snacks s : snacks) {
			JSONObject j = new JSONObject();
			if (s.getSnacks_id() != null) {
				j.put("snacks_id", s.getSnacks_id());
			}
			if (s.getSnacks_name() != null) {
				j.put("snacks_name", s.getSnacks_name());
			}
			if (s.getSnacks_stock_number() != null) {
				j.put("snacks_stock_number", s.getSnacks_stock_number());
			}
			if (s.getSnacks_bar_code() != null) {
				j.put("snacks_bar_code", s.getSnacks_bar_code());
			}
			if (s.getSnacks_pic() != null) {
				j.put("snacks_pic", s.getSnacks_pic());
			}
			if (s.getSnacks_cost_price() != null) {
				j.put("snacks_cost_price", s.getSnacks_cost_price());
			}
			if (s.getSnacks_sell_price() != null) {
				j.put("snacks_sell_price", s.getSnacks_sell_price());
			}
			if (s.getSnacks_status() != null) {
				j.put("snacks_status", s.getSnacks_status());
			}
			array.add(j);
		}
		jo.put("snacks", array);
		return jo;
	}

	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional
												// '-' and decimal.
	}

	public static boolean isInRange(String range, int hour, int minute) {
		try {
			if (!StringUtils.hasText(range)) {
				return false;
			}

			/** 去掉所有空格 */
			range = StringUtils.trimAllWhitespace(range);
			String[] ranges = range.split(",");
			for (String section : ranges) {
				if (!StringUtils.hasText(section)) {
					continue;
				}

				String[] time = section.split("-");
				if (time.length < 2) {
					continue;
				}

				String stime = time[0];
				String etime = time[1];

				if (!StringUtils.hasText(stime) || !StringUtils.hasText(etime)) {
					continue;
				}

				String[] stimes = stime.split(":");
				String[] etimes = etime.split(":");

				int sh, eh, sm = 0, em = 0;

				if (!isNumeric(stimes[0])) {
					continue;
				}
				sh = Integer.parseInt(stimes[0]);
				if (stimes.length > 1) {
					if (!isNumeric(stimes[1])) {
						continue;
					}
					sm = Integer.parseInt(stimes[1]);
				}

				if (!isNumeric(etimes[0])) {
					continue;
				}
				eh = Integer.parseInt(etimes[0]);
				if (etimes.length > 1) {
					if (!isNumeric(etimes[1])) {
						continue;
					}
					em = Integer.parseInt(etimes[1]);
				}

				if (hour > sh && hour < eh) {
					return true;
				}
				if (hour == sh && minute >= sm) {
					return true;
				}
				if (hour == eh && minute < em) {
					return true;
				}

			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return false;
	}

	public static boolean isInList(List<Long> ids, long id) {
		for (Long i : ids) {
			if (i.equals(id)) {
				return true;
			}
		}

		return false;
	}

	/*
	 * author: jipeng date: 2013-10-16 purpose: 将时间范围统一为标准表示形式，如9-12 -->
	 * 9:00-12:00
	 */
	public static String normalizeTimeRange(String range) {
		if (!StringUtils.hasText(range)) {
			return "";
		}

		/** 去掉所有空格 */
		range = StringUtils.trimAllWhitespace(range);

		String result = "";
		String[] ranges = range.split(",");
		for (String section : ranges) {
			String oneSec = "";

			if (!StringUtils.hasText(section)) {
				continue;
			}

			String[] time = section.split("-");
			if (time.length < 2) {
				continue;
			}

			String stime = time[0];
			String etime = time[1];

			if (!StringUtils.hasText(stime) || !StringUtils.hasText(etime)) {
				continue;
			}

			String[] stimes = stime.split(":");
			String[] etimes = etime.split(":");

			if (!isNumeric(stimes[0])) {
				continue;
			}
			oneSec = oneSec + stimes[0];

			if (stimes.length > 1) {
				if (!isNumeric(stimes[1])) {
					continue;
				}
				if (stimes[1].length() < 2) {
					oneSec = oneSec + ":0" + stimes[1];
				} else {
					oneSec = oneSec + ":" + stimes[1];
				}
			} else {
				oneSec = oneSec + ":00";
			}

			if (!isNumeric(etimes[0])) {
				continue;
			}
			oneSec = oneSec + "-" + etimes[0];

			if (etimes.length > 1) {
				if (!isNumeric(etimes[1])) {
					continue;
				}
				if (etimes[1].length() < 2) {
					oneSec = oneSec + ":0" + etimes[1];
				} else {
					oneSec = oneSec + ":" + etimes[1];
				}
			} else {
				oneSec = oneSec + ":00";
			}

			if (StringUtils.hasText(result)) {
				result = result + "," + oneSec;
			} else {
				result = oneSec;
			}

		}

		return result;

	}

	/**
	 * @author jipeng
	 * @param code
	 * @param msg
	 * @param data
	 * @return 构造返回JSON
	 */
	public static JSONObject constructResponseJSON(int code, String msg,
			String data) {
		JSONObject jo = new JSONObject();
		jo.put("code", code);
		jo.put("msg", msg);
		jo.put("data", data);
		return jo;
	}

	/*
	 * author: jipeng date:2013-11-06 purpose: 构造异常返回json
	 */
	public static JSONObject constructExceptionJSON(int code, String msg,
			String data) {
		JSONObject jo = new JSONObject();
		jo.put("code", code);
		jo.put("msg", msg);
		jo.put("data", data);
		return jo;
	}

	/**
	 * 待插入通配符的字符串
	 * 
	 * @param src
	 * @return 如果src为空或者不含有非空白字符，返回"" 否则返回将src所有空格去后，首尾及两字符间均插入".*"通配符的字符串，
	 */
	public static String insertWildcard(String src) {
		/** src为null,或者不含有非空白字符 */
		if (!StringUtils.hasText(src)) {
			return "";
		}

		src = StringUtils.trimAllWhitespace(src);
		StringBuilder sb = new StringBuilder(".*");
		for (int i = 0; i < src.length(); i++) {
			sb.append(src.charAt(i) + ".*");
		}

		return sb.toString();
	}

	/**
	 * 首尾插入通配符
	 * 
	 * @param src
	 * @return
	 */
	public static String insertWildcardAtBothEnds(String src) {
		/** src为null,或者不含有非空白字符 */
		if (!StringUtils.hasText(src)) {
			return "";
		}

		src = StringUtils.trimAllWhitespace(src);
		StringBuilder sb = new StringBuilder(".*");
		sb.append(src + ".*");

		return sb.toString();
	}

	/**
	 * 由原始图片地址得到指定大小的图片url地址
	 * 
	 * @param original
	 * @param width
	 * @return
	 */
	public static String assembleMTCpPicUrl(String original, Integer width) {
		if (!StringUtils.hasText(original)) {
			return original;
		}
		try {
			String result = "";
			URI uri = new URI(original);

			if (uri.getScheme() == null) {
				return original;
			}
			result += uri.getScheme() + "://";

			if (uri.getHost() == null) {
				return original;
			}
			result += uri.getHost();

			if (uri.getPort() != -1) {
				result += ":" + uri.getPort();
			}

			result += "/" + width + ".0" + uri.getRawPath();

			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			return original;
		}
	}

	/**
	 * UUID
	 * 
	 * @return生成的GUID为一串32位字符组成的128位数据
	 */
	public String GUID() {
		String a = null;

		// 产生 5 个 GUID
		for (int i = 0; i < 5; i++) {
			// 创建 GUID 对象
			UUID uuid = UUID.randomUUID();
			// 得到对象产生的ID
			a = uuid.toString();
			// 转换为大写
			a = a.toUpperCase();
			// 替换 -
			a = a.replaceAll("-", "_");
			// System.out.println(a);
		}
		System.out.println(a + "a");
		return a;
	}

	/**
	 * 重新上传食物图片,删除原来的图片
	 * 
	 * @param 图片路径
	 * @return boolean flag
	 */
	/*
	 * public boolean deleteFile(String sPath) { boolean flag = false; File file
	 * = new File(sPath);
	 * 
	 * // 路径为文件且不为空则进行删除 if (file.isFile() && file.exists()) { file.delete();
	 * 
	 * flag = true; } return flag; }
	 */
	public boolean deleteFile(String sPath) {
		File file = new File(sPath);
		boolean result = false;
		int tryCount = 0;
		while (!result && tryCount++ < 10) {
			System.gc();
			result = file.delete();
		}
		return result;
	}
}
