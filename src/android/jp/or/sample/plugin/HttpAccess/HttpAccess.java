package jp.or.sample.plugin.HttpAccess;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpAccess {
    static CookieManager cookieManager;
    private static final String CHARSET = StandardCharsets.UTF_8.name();
	
	static public class BinaryResponse{
		public byte[] binary;
		public String mimeType;

		public BinaryResponse(byte[] binary, String mimeType){
			this.binary = binary;
			this.mimeType = mimeType;
		}
	}

    public static String doPostText(String requestUrl, String inputJson, Map<String, String> headers, int connTimeout) throws Exception{
        if( cookieManager == null ) {
            cookieManager = new CookieManager();
            CookieManager.setDefault(cookieManager);
        }
        BufferedReader reader = null;
        OutputStream os = null;
        HttpURLConnection urlCon = null;

        try {
            URL url = new URL(requestUrl);

            urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setConnectTimeout(connTimeout);
//            urlCon.setReadTimeout(10000);
            urlCon.setRequestMethod("POST");

            urlCon.setDoInput(true);
            urlCon.setDoOutput(inputJson != null);
            urlCon.setRequestProperty("Content-Type", "application/json");
            if (headers != null){
                for (Map.Entry<String, String> item : headers.entrySet()) {
                    urlCon.setRequestProperty(item.getKey(), encodeURIComponent(item.getValue()));
                }
            }
            urlCon.setUseCaches(false);

            urlCon.connect();

            if( inputJson != null ) {
                PrintStream ps = new PrintStream(urlCon.getOutputStream());
                ps.print(inputJson);
                ps.close();
            }

            int status = urlCon.getResponseCode();
            if( status != HttpURLConnection.HTTP_OK)
                throw new Exception("HTTP Status Error");

            InputStream is = urlCon.getInputStream();

            reader = new BufferedReader(new InputStreamReader(is));
            StringBuffer buffer = new StringBuffer();
            String str;
            while (null != (str = reader.readLine())) {
                buffer.append(str);
            }
            is.close();

            return buffer.toString();
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (reader != null)
                    reader.close();
                if (os != null)
                    os.close();
                if (urlCon != null)
                    urlCon.disconnect();
            } catch (IOException e) {
            }
        }
    }
	
	public static String doGetText(String requestUrl, Map<String, String> headers, int connTimeout) throws Exception{
        if( cookieManager == null ) {
            cookieManager = new CookieManager();
            CookieManager.setDefault(cookieManager);
        }
        BufferedReader reader = null;
        HttpURLConnection urlCon = null;

        try {
            URL url = new URL(requestUrl);

            urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setConnectTimeout(connTimeout);
//            urlCon.setReadTimeout(10000);

            if (headers != null){
                for (Map.Entry<String, String> item : headers.entrySet()) {
                    urlCon.setRequestProperty(item.getKey(), encodeURIComponent(item.getValue()));
                }
            }

            int status = urlCon.getResponseCode();
            if( status != HttpURLConnection.HTTP_OK)
                throw new Exception("HTTP Status Error");

            InputStream is = urlCon.getInputStream();

            reader = new BufferedReader(new InputStreamReader(is));

            StringBuffer buffer = new StringBuffer();
            String str;
            while (null != (str = reader.readLine())) {
                buffer.append(str);
            }
            is.close();

            return buffer.toString();
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (reader != null)
                    reader.close();
                if (urlCon != null)
                    urlCon.disconnect();
            } catch (IOException e) {
            }
        }
    }

    public static BinaryResponse doPostBinary(String requestUrl, String inputJson, Map<String, String> headers, int connTimeout) throws Exception{
        if( cookieManager == null ) {
            cookieManager = new CookieManager();
            CookieManager.setDefault(cookieManager);
        }
        BufferedReader reader = null;
        OutputStream os = null;
        HttpURLConnection urlCon = null;

        try {
            URL url = new URL(requestUrl);

            urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setConnectTimeout(connTimeout);
//            urlCon.setReadTimeout(10000);
            urlCon.setRequestMethod("POST");

            urlCon.setDoInput(true);
            urlCon.setDoOutput(inputJson != null);
            urlCon.setRequestProperty("Content-Type", "application/json");
            if (headers != null){
                for (Map.Entry<String, String> item : headers.entrySet()) {
                    urlCon.setRequestProperty(item.getKey(), encodeURIComponent(item.getValue()));
                }
            }
            urlCon.setUseCaches(false);

            urlCon.connect();

            if( inputJson != null ) {
                PrintStream ps = new PrintStream(urlCon.getOutputStream());
                ps.print(inputJson);
                ps.close();
            }

            int status = urlCon.getResponseCode();
            if( status != HttpURLConnection.HTTP_OK)
                throw new Exception("HTTP Status Error");

            InputStream is = urlCon.getInputStream();
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();

        	String mimeType = urlCon.getContentType();
        	if( mimeType == null )
        		mimeType = "application/octet-stream";
        	return new BinaryResponse(buffer, mimeType);
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (reader != null)
                    reader.close();
                if (os != null)
                    os.close();
                if (urlCon != null)
                    urlCon.disconnect();
            } catch (IOException e) {
            }
        }
    }
	
	public static BinaryResponse doGetBinary(String requestUrl, Map<String, String> headers, int connTimeout) throws Exception{
        if( cookieManager == null ) {
            cookieManager = new CookieManager();
            CookieManager.setDefault(cookieManager);
        }
        BufferedReader reader = null;
        HttpURLConnection urlCon = null;

        try {
            URL url = new URL(requestUrl);

            urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setConnectTimeout(connTimeout);
//            urlCon.setReadTimeout(10000);

            if (headers != null){
                for (Map.Entry<String, String> item : headers.entrySet()) {
                    urlCon.setRequestProperty(item.getKey(), encodeURIComponent(item.getValue()));
                }
            }

            int status = urlCon.getResponseCode();
            if( status != HttpURLConnection.HTTP_OK)
                throw new Exception("HTTP Status Error");

            InputStream is = urlCon.getInputStream();
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();

        	String mimeType = urlCon.getContentType();
        	if( mimeType == null )
        		mimeType = "application/octet-stream";
        	return new BinaryResponse(buffer, mimeType);
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (reader != null)
                    reader.close();
                if (urlCon != null)
                    urlCon.disconnect();
            } catch (IOException e) {
            }
        }
    }
	
	public static String encodeURIComponent(String text) {
        if (text.isEmpty()) {
            return "";
        }
        String code;
        try {
            code = URLEncoder.encode(text, CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        int textLength = text.length();
        int codeLength = code.length();
        StringBuilder builder = new StringBuilder((textLength + codeLength + 1) / 2);
        for (int i = 0; i < codeLength; ++i) {
            char entry = code.charAt(i);
            switch (entry) {
                case '+':
                    builder.append("%20");
                    break;
                case '%':
                    if (i > codeLength - 2) {
                        break;
                    }
                    char a = code.charAt(i += 1);
                    char b = code.charAt(i += 1);
                    switch (a) {
                        case '2':
                            switch (b) {
                                case '1':
                                    builder.append("!");
                                    break;
                                case '7':
                                    builder.append("'");
                                    break;
                                case '8':
                                    builder.append("(");
                                    break;
                                case '9':
                                    builder.append(")");
                                    break;
                                default:
                                    builder.append("%");
                                    builder.append(a);
                                    builder.append(b);
                                    break;
                            }
                            break;
                        case '7':
                            if (b == 'e' || b == 'E') {
                                builder.append("~");
                            } else {
                                builder.append("%");
                                builder.append(a);
                                builder.append(b);
                            }
                            break;
                        default:
                            builder.append("%");
                            builder.append(a);
                            builder.append(b);
                            break;
                    }
                    break;
                default:
                    builder.append(entry);
                    break;
            }
        }
        return builder.toString();
    }
}