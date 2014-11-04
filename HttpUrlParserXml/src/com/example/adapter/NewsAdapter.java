package com.example.adapter;

import java.util.List;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.httpurlparserxml.R;
import com.example.util.AsyncImgLoder;
import com.example.util.AsyncImgLoder.ImageCallback;
import com.example.vo.News;

public class NewsAdapter extends BaseAdapter{
	private final String TAG = NewsAdapter.class.getSimpleName();
	private final String HOST = "http://192.168.1.245:8080/serversocket/";
	private AsyncImgLoder mAsyncImgLoder;
	private Context mContext;
	private List<News> mNewsList;
	private ViewHolder mViewHolder;
	public NewsAdapter(Context mContext, List<News> mNewsList) {
		super();
		this.mContext = mContext;
		this.mNewsList = mNewsList;
		mAsyncImgLoder = new AsyncImgLoder(mContext);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mNewsList.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public View getView(int position, View v, final ViewGroup parent) {
		if(v == null){
			mViewHolder = new ViewHolder();
			v = LayoutInflater.from(mContext).inflate(R.layout.item_list, null);
			mViewHolder.title = (TextView) v.findViewById(R.id.titleText);
			mViewHolder.content = (TextView) v.findViewById(R.id.contentText);
			mViewHolder.img = (ImageView) v.findViewById(R.id.imageItemView);
			v.setTag(mViewHolder);
		}else{
			mViewHolder = (ViewHolder) v.getTag();
		}
		mViewHolder.title.setText(mNewsList.get(position).getNewstitle());
		mViewHolder.content.setText(mNewsList.get(position).getNewscontent());
		String url = HOST + mNewsList.get(position).getNewsurl();
		Log.d(TAG, url);
		mViewHolder.img.setTag(url);
		Bitmap image = mAsyncImgLoder.imageImage(url, new ImageCallback() {
			
			@Override
			public void ImageLoded(String url, Bitmap image) {
				ImageView img = (ImageView) parent.findViewWithTag(url);
				if(img != null && image != null){
					img.setImageBitmap(image);
				}
			}
		});
		
		if(image != null){
			mViewHolder.img.setImageBitmap(image);
		}else{
			mViewHolder.img.setImageResource(R.drawable.ic_launcher);
		}
		return v;
	}
	
	class ViewHolder{
		TextView title,content;
		ImageView img;
	}
}
