package yaolong.base.common.util;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 *
 * @author
 *
 */
public class HttpUtils {

	/**
	 * CONTENT TYPE : application/json
	 */
	public static final String CONTENT_TYPE_JSON = "application/json";

	private static SSLConnectionSocketFactory socketFactory;

	// private static CloseableHttpClient httpClient;
	//
	// private static CloseableHttpClient httpsGetClient;
	//
	// private static CloseableHttpClient httpsPostClient;

	/**
	 * 
	 * @param url
	 * @param headers
	 * @param refer
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static CloseableHttpResponse doGet(String url, Map<String, String> headers, String refer)
			throws ClientProtocolException, IOException {
		HttpGet get = new HttpGet(url);
		setRequestHeader(get, headers, refer);
		CloseableHttpResponse response = buildHttpClient().execute(get);
		return response;
	}

	/**
	 * 
	 * @param url
	 * @param headers
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static CloseableHttpResponse doGet(String url, Map<String, String> headers)
			throws ClientProtocolException, IOException {
		HttpGet get = new HttpGet(url);
		setRequestHeader(get, headers);
		CloseableHttpResponse response = buildHttpClient().execute(get);
		return response;
	}

	/**
	 * 
	 * @param url
	 * @param values
	 * @param headers
	 * @param refer
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static CloseableHttpResponse doPost(String url, List<NameValuePair> values, Map<String, String> headers,
			String refer) throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(url);
		setRequestHeader(post, headers, refer);
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(values, Consts.UTF_8);
		post.setEntity(entity);
		CloseableHttpResponse response = buildHttpClient().execute(post);
		return response;
	}

	public static CloseableHttpResponse doPost(String url, List<NameValuePair> values, Map<String, String> headers)
			throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(url);
		setRequestHeader(post, headers);
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(values, Consts.UTF_8);
		post.setEntity(entity);
		CloseableHttpResponse response = buildHttpClient().execute(post);
		return response;
	}

	/**
	 * 
	 * @param url
	 * @param values
	 * @param headers
	 * @param refer
	 * @param contentType
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static CloseableHttpResponse doPost(String url, List<NameValuePair> values, Map<String, String> headers,
			String refer, String contentType) throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(url);
		setRequestHeader(post, headers, refer);
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(values, Consts.UTF_8);
		if (contentType != null && !"".equals(contentType.trim())) {
			entity.setContentType(contentType);
		}
		post.setEntity(entity);
		CloseableHttpResponse response = buildHttpClient().execute(post);
		return response;
	}

	public static CloseableHttpResponse doPost(String url, String contentType, List<NameValuePair> values,
			Map<String, String> headers) throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(url);
		setRequestHeader(post, headers);
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(values, Consts.UTF_8);
		if (contentType != null && !"".equals(contentType.trim())) {
			entity.setContentType(contentType);
		}
		post.setEntity(entity);
		CloseableHttpResponse response = buildHttpClient().execute(post);
		return response;
	}

	/**
	 * 
	 * @param url
	 * @param json
	 * @param headers
	 * @param refer
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static CloseableHttpResponse doPost(String url, String json, Map<String, String> headers, String refer)
			throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(url);
		setRequestHeader(post, headers, refer);
		StringEntity entity = new StringEntity(json, "utf-8");// 解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType(CONTENT_TYPE_JSON);
		post.setEntity(entity);
		CloseableHttpResponse response = buildHttpClient().execute(post);
		return response;
	}

	/**
	 * 
	 * @param url
	 * @param headers
	 * @param refer
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static CloseableHttpResponse doHttpsGet(String url, Map<String, String> headers, String refer)
			throws ClientProtocolException, IOException {
		HttpGet get = new HttpGet(url);
		setRequestHeader(get, headers, refer);
		CloseableHttpResponse response = buildHttpsGetClient().execute(get);
		return response;
	}

	/**
	 * 
	 * @param url
	 * @param values
	 * @param headers
	 * @param refer
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static CloseableHttpResponse doHttpsPost(String url, List<NameValuePair> values, Map<String, String> headers,
			String refer) throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(url);
		setRequestHeader(post, headers, refer);
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(values, Consts.UTF_8);
		post.setEntity(entity);
		CloseableHttpResponse response = buildHttpsPostClient().execute(post);
		return response;
	}

	/**
	 * 
	 * @param url
	 * @param values
	 * @param headers
	 * @param refer
	 * @param contentType
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static CloseableHttpResponse doHttpsPost(String url, List<NameValuePair> values, Map<String, String> headers,
			String refer, String contentType) throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(url);
		setRequestHeader(post, headers, refer);
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(values, Consts.UTF_8);
		if (contentType != null && !"".equals(contentType.trim())) {
			entity.setContentType(contentType);
		}
		post.setEntity(entity);
		CloseableHttpResponse response = buildHttpsPostClient().execute(post);
		return response;
	}

	/**
	 * 
	 * @param url
	 * @param json
	 * @param headers
	 * @param refer
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static CloseableHttpResponse doHttpsPost(String url, String json, Map<String, String> headers, String refer)
			throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(url);
		setRequestHeader(post, headers, refer);
		StringEntity entity = new StringEntity(json, "utf-8");// 解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType(CONTENT_TYPE_JSON);
		post.setEntity(entity);
		CloseableHttpResponse response = buildHttpsPostClient().execute(post);
		return response;
	}

	public static CloseableHttpResponse doHttpsPost(String url, String json, Map<String, String> headers)
			throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(url);
		setRequestHeader(post, headers);
		StringEntity entity = new StringEntity(json, "utf-8");// 解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType(CONTENT_TYPE_JSON);
		post.setEntity(entity);
		CloseableHttpClient client = buildHttpsPostClient();
		CloseableHttpResponse response = client.execute(post);
		return response;
	}

	public static CloseableHttpClient buildHttpClient() {
		// 首先设置全局的标准cookie策略
		RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
		// 设置可关闭的httpclient
		return HttpClients.custom().setDefaultRequestConfig(config).build();
	}

	public static CloseableHttpClient buildHttpsGetClient() {
		enableSSL();
		RequestConfig defaultRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
				.setExpectContinueEnabled(true)
				.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
				.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
		// 设置可关闭的httpclient
		// 创建可用Scheme
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();
		// 创建ConnectionManager，添加Connection配置信息
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry);
		return HttpClients.custom().setConnectionManager(connectionManager)
				.setDefaultRequestConfig(defaultRequestConfig).build();
	}

	public static CloseableHttpClient buildHttpsPostClient() {
		enableSSL();
		RequestConfig defaultRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
				.setExpectContinueEnabled(true)
				.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
				.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
		// 创建可用Scheme
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();
		// 创建ConnectionManager，添加Connection配置信息
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry);
		return HttpClients.custom().setConnectionManager(connectionManager)
				.setDefaultRequestConfig(defaultRequestConfig).build();

	}

	private static TrustManager manager = new X509TrustManager() {

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	};

	private static void enableSSL() {
		try {
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new TrustManager[] { manager }, null);
			socketFactory = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
	}

	private static void setRequestHeader(HttpRequestBase request, Map<String, String> headers, String refer) {
		if (headers != null && headers.size() > 0) {
			for (Entry<String, String> entry : headers.entrySet()) {
				request.setHeader(entry.getKey(), entry.getValue());
			}
		}
		if (refer != null) {
			request.setHeader("Referer", refer);
		}
	}

	private static void setRequestHeader(HttpRequestBase request, Map<String, String> headers) {
		if (headers != null && headers.size() > 0) {
			for (Entry<String, String> entry : headers.entrySet()) {
				request.setHeader(entry.getKey(), entry.getValue());
			}
		}
	}

}
