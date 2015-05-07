package com.sveil.other.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class CRMHttpUtil {
	private static final String DEFAULT_CHARSET = "UTF-8";

	/**
	 * 发送Post请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public static String post(String url, String params) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		StringBuffer bufferRes = null;
		URL urlGet = new URL(url);
		HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
		// 连接超时
		http.setConnectTimeout(25000);
		// 读取超时 --服务器响应比较慢，增大时间
		http.setReadTimeout(25000);
		http.setRequestMethod("POST");
		http.setRequestProperty("Content-Type", "application/json");
		http.setDoOutput(true);
		http.setDoInput(true);
		http.connect();
		OutputStream out = http.getOutputStream();
		out.write(params.getBytes("UTF-8"));
		out.flush();
		out.close();
		InputStream in = http.getInputStream();
		BufferedReader read = new BufferedReader(new InputStreamReader(in, DEFAULT_CHARSET));
		String valueString = null;
		bufferRes = new StringBuffer();
		while ((valueString = read.readLine()) != null) {
			bufferRes.append(valueString);
		}
		in.close();
		if (http != null) {
			// 关闭连接
			http.disconnect();
		}
		return bufferRes.toString();
	}
}
