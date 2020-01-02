package com.lottery.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpPostUrl {

	public static String doPostUrl(String urlStr, Map<String, String> params){
		
		
		
		return null;
	}

	/**
	 * post方式请求http服务
	 * 
	 * @param urlStr
	 * @param params
	 *            name=yxd&age=25
	 * @return
	 * @throws Exception
	 */
	public static String getURLByPost(String urlStr, String params) throws Exception {
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
		printWriter.write(params);
		printWriter.flush();
		BufferedReader in = null;
		StringBuilder sb = new StringBuilder();
		try {
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String str = null;
			while ((str = in.readLine()) != null) {
				sb.append(str);
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				conn.disconnect();
				if (in != null) {
					in.close();
				}
				if (printWriter != null) {
					printWriter.close();
				}
			} catch (IOException ex) {
				throw ex;
			}
		}
		return sb.toString();
	}
}
