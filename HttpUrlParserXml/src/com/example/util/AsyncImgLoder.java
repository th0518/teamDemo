package com.example.util;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class AsyncImgLoder {
 
	private HashMap<String, SoftReference<Bitmap>> mImageCache;
	private Context mContext;
	public AsyncImgLoder(Context context) {
		mContext = context;
		mImageCache = new HashMap<String,SoftReference<Bitmap>>();
	}
	
	public interface ImageCallback{
		void ImageLoded(String url,Bitmap image);
	}
	
	public Bitmap imageImage(final String url,final ImageCallback imageCallback){
		Bitmap result = null;
		if(mImageCache.containsKey(url)){
			result = mImageCache.get(url).get();
			if(result != null){
				return result;
			}
		}
		final Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					imageCallback.ImageLoded(url, (Bitmap)msg.obj);
					break;

				case 2:
					Toast.makeText(mContext, "获取失败", 1).show();
					break;
				case 3:
					Toast.makeText(mContext, "网络异常", 1).show();
					break;
				}
				super.handleMessage(msg);
			}
		};
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				if(NetUtil.isAwailable(mContext)){
					Bitmap image = NetUtil.getIngImageFromURL(url);
					if(image != null){
						mImageCache.put(url, new SoftReference<Bitmap>(image));
						Message msg = handler.obtainMessage(1,image);
						handler.sendMessage(msg);
					}else{
						handler.sendEmptyMessage(2);
					}
				}else{
					handler.sendEmptyMessage(3);
				}
			}
		}).start();
		return result;
	}
}
