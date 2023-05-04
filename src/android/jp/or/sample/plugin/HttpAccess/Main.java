package jp.or.sample.plugin.HttpAccess;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Main extends CordovaPlugin {
	public static String TAG = "HttpAccess.Main";
	private Activity activity;

	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView)
	{
		Log.d(TAG, "[Plugin] initialize called");
		super.initialize(cordova, webView);

		activity = cordova.getActivity();
	}

	@Override
	public void onResume(boolean multitasking)
	{
		Log.d(TAG, "[Plugin] onResume called");
		super.onResume(multitasking);
	}

	@Override
	public void onPause(boolean multitasking)
	{
		Log.d(TAG, "[Plugin] onPause called");
		super.onPause(multitasking);
	}

	@Override
	public void onNewIntent(Intent intent)
	{
		Log.d(TAG, "[Plugin] onNewIntent called");
		super.onNewIntent(intent);
	}

	private Map<String, String> json2map(JSONObject json) throws Exception{
		HashMap<String, String> map = new HashMap<String, String>();
		Iterator<String> iterator = json.keys();
		while(iterator.hasNext()){
			String key = iterator.next();
			map.put(key, json.getString(key));
		}

		return map;
	}

	public static JSONArray toIntJSONArray( byte[] barray, int offset, int len )
	{
		JSONArray jarray = new JSONArray();
		for( int i = 0 ; i < len ; i++ )
			jarray.put(barray[offset + i] & 0x00ff);

		return jarray;
	}

	@Override
	public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException
	{
		Log.d(TAG, "[Plugin] execute called");
		if( action.equals("doPostText") ) {
			cordova.getThreadPool().execute(new Runnable() {
				public void run() {
					try {
						String requestUrl = args.getString(0);
						String inputJson = args.getString(1);
						Map<String, String> headers = json2map(args.getJSONObject(2));
						int connTimeout = args.getInt(3);
						String response = HttpAccess.doPostText(requestUrl, inputJson, headers, connTimeout);

						JSONObject result = new JSONObject();
						result.put("result", response);

						callbackContext.success(result);
					} catch (Exception ex) {
						callbackContext.error(ex.getMessage());
					}
				}
			});
		}else
		if( action.equals("doGetText") ){
			cordova.getThreadPool().execute(new Runnable() {
				public void run() {
					try {
						String requestUrl = args.getString(0);
						Map<String, String> headers = json2map(args.getJSONObject(1));
						int connTimeout = args.getInt(2);
						String response = HttpAccess.doGetText(requestUrl, headers, connTimeout);

						JSONObject result = new JSONObject();
						result.put("result", response);

						callbackContext.success(result);
					} catch (Exception ex) {
						callbackContext.error(ex.getMessage());
					}
				}
			});
		}else
		if( action.equals("doPostBinary") ) {
			cordova.getThreadPool().execute(new Runnable() {
				public void run() {
					try {
						String requestUrl = args.getString(0);
						String inputJson = args.getString(1);
						Map<String, String> headers = json2map(args.getJSONObject(2));
						int connTimeout = args.getInt(3);
						HttpAccess.BinaryResponse response = HttpAccess.doPostBinary(requestUrl, inputJson, headers, connTimeout);

						JSONObject result = new JSONObject();
						result.put("result", toIntJSONArray(response.binary, 0, response.binary.length));
						result.put("mimeType", response.mimeType);

						callbackContext.success(result);
					} catch (Exception ex) {
						callbackContext.error(ex.getMessage());
					}
				}
			});
		}else
		if( action.equals("doGetBinary") ){
			cordova.getThreadPool().execute(new Runnable() {
				public void run() {
					try {
						String requestUrl = args.getString(0);
						Map<String, String> headers = json2map(args.getJSONObject(1));
						int connTimeout = args.getInt(2);
						HttpAccess.BinaryResponse response = HttpAccess.doGetBinary(requestUrl, headers, connTimeout);

						JSONObject result = new JSONObject();
						result.put("result", toIntJSONArray(response.binary, 0, response.binary.length));
						result.put("mimeType", response.mimeType);

						callbackContext.success(result);
					} catch (Exception ex) {
						callbackContext.error(ex.getMessage());
					}
				}
			});		}else {
			String message = "Unknown action : (" + action + ") " + args.getString(0);
			Log.d(TAG, message);
			callbackContext.error(message);
			return false;
		}

		return true;
	}
}

