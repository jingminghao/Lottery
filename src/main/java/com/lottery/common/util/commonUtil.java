package com.lottery.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class commonUtil {

	/**
	 * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
	 * 
	 * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
	 * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
	 * 
	 * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
	 * 192.168.1.100
	 * 
	 * 用户真实IP为： 192.168.1.110
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	
	/***
	 * 将值进行解码处理
	 * 
	 * @param val
	 *            值
	 * @param enco
	 *            编码格式
	 * @return
	 */
	public static String decode(String val, String enco) {
		try {
			if (val != null && !"".equals(val)) {
				return URLDecoder.decode(val, enco);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/***
	 * 将值进行解码处理
	 * 
	 * @param val
	 *            值
	 * @return
	 */
	public static String decode(String val) {
		try {
			if (val != null && !"".equals(val)) {
				return URLDecoder.decode(val, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/***
	 * 将值进行编码处理
	 * 
	 * @param val
	 *            值
	 * @return
	 */
	public static String encode(String val) {
		try {
			if (val != null && !"".equals(val)) {
				return URLEncoder.encode(val, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/***
	 * 将值进行编码处理
	 * 
	 * @param val
	 *            值
	 * @param enco
	 *            编码格式
	 * @return
	 */
	public static String encode(String val, String enco) {
		try {
			if (val != null && !"".equals(val)) {
				return URLEncoder.encode(val, enco);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/***
	 * 生成md5加密串
	 * 
	 * @param val 要加密的值
	 * @param charSetName 字符编码 utf-8/gbk
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static String setDigestMD5(String val, String charSetName)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(val.getBytes(charSetName));
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		System.out.println("MD5FOR UTF-8 VAL:" + result);
		return result;
	}

	/***
	 * 生成要的签名
	 * 
	 * @param param
	 *            参数列表
	 * @param pwdkey
	 *            密钥
	 * @param charSetName
	 *            字符编码 utf-8/gbk
	 * @return 签名
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String createSign(Map<String, Object> param, String pwdkey, String charSetName)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		String str = null;
		if (param != null && param.size() > 0) {
			String[] params = new String[param.size()];
			int index = 0;
			for (String p : param.keySet()) {
				params[index] = p;
				index++;
			}
			Arrays.sort(params);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < params.length; i++) {
				String val = param.get(params[i])==null?"":param.get(params[i]).toString();
				if (!("0".equals(val) || "".equals(val))) {
					val = commonUtil.decode(val);
					sb.append(params[i] + "=" + val + "#");
				}
			}
			sb.append(pwdkey);

			System.out.println("签名前的值:" + sb.toString());

			str = commonUtil.setDigestMD5(sb.toString(), charSetName).toLowerCase();
		} else {
			str = commonUtil.setDigestMD5(pwdkey, charSetName).toLowerCase();
		}

		System.out.println("签名加密后的值:" + str.toString());

		return str;
	}

}
