package com.example.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetUtil {
	private static final  String TAG = NetUtil.class.getSimpleName();
	public static boolean isAwailable(Context context){
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = connMgr.getActiveNetworkInfo();
		if(netInfo == null || !netInfo.isAvailable()){
			return false;
		}
		return true;
	}
	
	public static InputStream getInputStreamFromURL(String url){
		InputStream in = null;
		HttpGet get = new HttpGet(url);
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 5*1000);
		HttpConnectionParams.setSoTimeout(params, 5*1000);
		HttpClient client = new DefaultHttpClient(params);
		HttpResponse resp;
		try {
			resp = client.execute(get);
			if(resp.getStatusLine().getStatusCode() == 200){
				HttpEntity ent = resp.getEntity();
				in = ent.getContent();
			}else{
//				Log.d(TAG, "鏈嶅姟鍣ㄥ紓甯�);"
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return in;
	}
	
	public static Bitmap getIngImageFromURL(String url){
		Bitmap in = null;
		HttpGet get = new HttpGet(url);
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 5*1000);
		HttpConnectionParams.setSoTimeout(params, 5*1000);
		HttpClient client = new DefaultHttpClient(params);
		HttpResponse resp;
		try {
			resp = client.execute(get);
			if(resp.getStatusLine().getStatusCode() == 200){
				HttpEntity ent = resp.getEntity();
				in = BitmapFactory.decodeStream(ent.getContent());
			}else{
//				Log.d(TAG, "鏈嶅姟鍣ㄥ紓甯�);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return in;
	}
}
