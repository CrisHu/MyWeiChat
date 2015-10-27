package com.example.myweichat.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.myweichat.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class HorizontalListViewAdapter extends BaseAdapter{
	
	public static int[] horizontalgallery = {R.drawable.girl22,R.drawable.girl33,R.drawable.girl223,R.drawable.girl233,R.drawable.girl222,R.drawable.girl333}; 
	private List<Map<String,Object>> data = getData();
	
	private List<Map<String, Object>> getData()  
    {  
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
        Map<String, Object> map;  
        for(int i=0;i<horizontalgallery.length;i++)  
        {  
            map = new HashMap<String, Object>();  
            map.put("img", horizontalgallery[i]);  
            list.add(map);  
        }  
        return list;  
    }
	
	public HorizontalListViewAdapter(Context con){
		mInflater=LayoutInflater.from(con);
	}
	@Override
	public int getCount() {
		return data.size();
	}
	private LayoutInflater mInflater;
	@Override
	public Object getItem(int position) {
		return position;
	}
	
	private ViewHolder vh = new ViewHolder();
	
	private static class ViewHolder {
		private ImageView im;
	}
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(R.layout.horizontallistview_item, null);
		vh.im=(ImageView)convertView.findViewById(R.id.girl22or33);
		vh.im.setBackgroundResource((Integer)data.get(position).get("img")); 
		return convertView;
	}
}