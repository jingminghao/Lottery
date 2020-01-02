package com.lottery.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpUtil {

	private static final Logger logger = Logger.getLogger(HttpUtil.class);

	private static PoolingHttpClientConnectionManager connMgr;
	private static RequestConfig requestConfig;
	private static final int MAX_TIMEOUT = 60000;

	static {
		// 设置连接池
		connMgr = new PoolingHttpClientConnectionManager();
		// 设置连接池大小
		connMgr.setMaxTotal(100);

		connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

		RequestConfig.Builder configBuilder = RequestConfig.custom();
		// 设置连接超时
		configBuilder.setConnectTimeout(MAX_TIMEOUT);
		// 设置读取超时
		configBuilder.setSocketTimeout(MAX_TIMEOUT);
		// 设置从连接池获取连接实例的超时
		configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
		// 在提交请求之前 测试连接是否可用
		// configBuilder.setStaleConnectionCheckEnabled(true);
		requestConfig = configBuilder.build();
	}

	public static String doPost(String url, Map<String, Object> headers, Map<String, Object> params)
			throws IOException {
		String response = null;
		response = doPostMuti(url, headers, params);

		return response;
	}

	public static String doPost(String url, Map<String, Object> params) throws IOException {
		String response = null;
		response = doPostMuti(url, null, params);
		return response;
	}

	private static String doPostMuti(String url, Map<String, Object> headers, Map<String, Object> params)
			throws IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String result = null;
		CloseableHttpResponse response = null;
		HttpEntity httpEntity = null;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		if (params != null) {
			Set<Entry<String, Object>> entrys = params.entrySet();
			Iterator<Entry<String, Object>> its = entrys.iterator();
			Entry<String, Object> entry = null;

			while (its.hasNext()) {
				entry = its.next();
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()!=null?String.valueOf(entry.getValue()):""));
			}
		}

		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);

		if (headers != null) {
			Set<Entry<String, Object>> entrys = headers.entrySet();
			Iterator<Entry<String, Object>> its = entrys.iterator();
			Entry<String, Object> entry = null;

			while (its.hasNext()) {
				entry = its.next();
				httpPost.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
			}
		}

		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

		try {
			response = doPostExecute(httpclient, httpPost);
			if (response != null) {
				logger.info(response.getStatusLine());
				httpEntity = response.getEntity();
				result = EntityUtils.toString(httpEntity, "utf-8");
				EntityUtils.consume(httpEntity);
				logger.info("返回数据：" + result);
			} else {
				logger.error("response==null");
			}

		} finally {
			if (httpEntity != null) {
				EntityUtils.consume(httpEntity);
			}
			if (response != null) {
				response.close();
			}
			httpclient.close();
		}
		return result;
	}

	private static CloseableHttpResponse doPostExecute(CloseableHttpClient httpclient, HttpPost httpPost)
			throws IOException {
		CloseableHttpResponse response = null;
		response = httpclient.execute(httpPost);
		String reasonPhrase = response.getStatusLine().getReasonPhrase();
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			throw new IOException(reasonPhrase);
		}
		return response;
	}

	/**
	 * post请求
	 * 
	 * @param url
	 * @param headers
	 * @param json
	 * @return
	 * @throws PcimException
	 */
	public static String doPost(String url, Map<String, String> headers, String json) throws IOException {
		String response = null;
		response = doPostJsonMuti(url, headers, json);
		return response;
	}

	private static String doPostJsonMuti(String url, Map<String, String> headers, String json) throws IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String result = null;
		CloseableHttpResponse response = null;
		HttpEntity httpEntity = null;
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);
		if (headers != null) {
			Set<Entry<String, String>> entrys = headers.entrySet();
			Iterator<Entry<String, String>> its = entrys.iterator();
			Entry<String, String> entry = null;

			while (its.hasNext()) {
				entry = its.next();
				httpPost.addHeader(entry.getKey(), entry.getValue());
			}
		}

		StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// 解决中文乱码问题
		stringEntity.setContentEncoding("UTF-8");
		stringEntity.setContentType("application/json");
		httpPost.setEntity(stringEntity);
		try {
			response = doPostExecute(httpclient, httpPost);
			if (response != null) {
				logger.info(response.getStatusLine());
				httpEntity = response.getEntity();
				result = EntityUtils.toString(httpEntity, "utf-8");
				EntityUtils.consume(httpEntity);
				logger.info("返回数据：" + result);
			} else {
				logger.error("response==null");
			}

		} finally {
			if (httpEntity != null) {
				EntityUtils.consume(httpEntity);
			}
			if (response != null) {
				response.close();
			}
			httpclient.close();
		}
		return result;
	}

}
