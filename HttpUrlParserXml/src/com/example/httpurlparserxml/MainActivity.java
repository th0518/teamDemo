package com.example.httpurlparserxml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapter.NewsAdapter;
import com.example.util.NetUtil;
import com.example.vo.News;

public class MainActivity extends Activity {
	private final String TAG = MainActivity.class.getSimpleName();
	private final String URL = "http://192.168.1.245:8080/serversocket/sub/pro01_data.xml";
	private ListView mListView;
	private Context mContext;
	private AsyncXml mAsyncXml;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
		mListView = (ListView) findViewById(R.id.listView);
		if(NetUtil.isAwailable(mContext)){
			mAsyncXml = new AsyncXml();
			mAsyncXml.execute(URL);
		}else{
			Toast.makeText(mContext, "网络异常", 1).show();
		}
	}
	
	@Override
	protected void onDestroy() {
		if(mAsyncXml != null){
			mAsyncXml.cancel(false);
			mAsyncXml = null;
		}
		super.onDestroy();
	}

	public List<News> getInputSreamFromNet(InputStream in){
		List<News> list = new ArrayList<News>();
		XmlPullParser parser = Xml.newPullParser();
		String tagName = "";
		News news = null;
		try {
			int evenType = parser.getEventType();
			parser.setInput(in, "utf-8");
			while(evenType != XmlPullParser.END_DOCUMENT){
				switch (evenType) {
				case XmlPullParser.START_DOCUMENT:
					Log.d(TAG, "START_DOCUMENT");
					break;
				case XmlPullParser.START_TAG:
					tagName = parser.getName();
					if("news".equals(tagName)){
						news = new News();
						news.setId(parser.getAttributeValue(0));
					}
					break;
				case XmlPullParser.TEXT:
					if(news != null){
						String text = parser.getText();
						Log.d(TAG, text);
						if("newsurl".equals(tagName)){
							news.setNewsurl(text);
						}else if("newstitle".equals(tagName)){
							news.setNewstitle(text);
						}else if("newscontent".equals(tagName)){
							news.setNewscontent(text);
						}
					}
					break;
				case XmlPullParser.END_TAG:
					tagName = parser.getName();
					if("news".equals(tagName)){
						list.add(news);
						news = null;
					}
					tagName = "";
					break;
				}
				evenType = parser.next();
			}
		} catch (XmlPullParserException e) {
			Log.d(TAG, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Log.d(TAG, e.getMessage());
			e.printStackTrace();
		}finally{
			try {
				if(in != null){
					in.close();
					in = null;
				}
			} catch (IOException e) {
				Log.d(TAG, e.getMessage());
				e.printStackTrace();
			}
		}
		return list;
	}
	
	class AsyncXml extends AsyncTask<String, Integer, List<News>>{

		@Override
		protected List<News> doInBackground(String... params) {
			InputStream in =NetUtil.getInputStreamFromURL(params[0]);
			if(in == null){
				Log.d(TAG, "in为空");
			}else{
				return getInputSreamFromNet(in);
			}
			return null;
		}
		@Override
		protected void onPostExecute(List<News> result) {
			if(result == null){
				Log.d(TAG, "result为空");
			}else{
				NewsAdapter adapter = new NewsAdapter(mContext, result);
				mListView.setAdapter(adapter);
			}
			super.onPostExecute(result);
		}
	}
}
