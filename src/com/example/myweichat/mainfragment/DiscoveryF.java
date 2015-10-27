package com.example.myweichat.mainfragment;

import java.util.ArrayList;

import com.example.myweichat.MainActivity;
import com.example.myweichat.R;
import com.example.myweichat.adapter.HorizontalListViewAdapter;
import com.example.myweichat.adapter.ImgAdapter;
import com.example.myweichat.component.FGallery;
import com.example.myweichat.component.HorizontalListView;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;  
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;  
import android.view.MotionEvent;
import android.view.View;   
import android.view.GestureDetector.OnGestureListener;
import android.view.ViewGroup;  
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
  
public class DiscoveryF extends Fragment implements OnGestureListener,OnItemClickListener{  
	private HorizontalListViewAdapter hlva;
	private HorizontalListView hlv;
	private FGallery gallery = null;
	private ArrayList<Integer> imgList;
	public static ArrayList<ImageView> portImg;
	private View view;
	private LinearLayout ll_focus_indicator_container = null;
	private class IndexImageView{
		ImageView imageview;
		int index;
	}
	private IndexImageView isplaying = null;
	
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState)  
    {  
        view = inflater.inflate(R.layout.fragment_discovery, container, false);
        hlv=(HorizontalListView)view.findViewById(R.id.horizontallistview1);
		hlva=new HorizontalListViewAdapter(view.getContext());
		hlva.notifyDataSetChanged();
		hlv.setAdapter(hlva);
		hlv.setOnItemClickListener(this);
		
		ll_focus_indicator_container = (LinearLayout)view.findViewById(R.id.ll_focus_indicator_container);
		imgList = new ArrayList<Integer>();
		imgList.add(R.drawable.img1);
		imgList.add(R.drawable.img2);
		imgList.add(R.drawable.img3);
		InitFocusIndicatorContainer();
		gallery = (FGallery)view.findViewById(R.id.gallery);
		gallery.setAdapter(new ImgAdapter(view.getContext(), imgList));
		gallery.setFocusable(true);
		//		if(arg1 == view.findViewById(R.id.gallery)){
//					selIndex = selIndex % imgList.size();
//					portImg.get(preSelImgIndex).setImageResource(R.drawable.ic_focus);
//					portImg.get(selIndex).setImageResource(R.drawable.ic_focus_select);
//					preSelImgIndex = selIndex;
	//			}
		if(MainActivity.state == 2)
			gallery.start();
        return view;  
    }
    
    public void galleryStop(){
    	if(gallery != null)
    		gallery.destroy();
    }
    
    public void galleryStart(){
    	if(gallery != null)
    		gallery.start();
    }
    
    private void InitFocusIndicatorContainer() {
		portImg = new ArrayList<ImageView>();
		for (int i = 0; i < imgList.size(); i++) {
			ImageView localImageView = new ImageView(view.getContext());
			localImageView.setId(i);
			ImageView.ScaleType localScaleType = ImageView.ScaleType.FIT_XY;
			localImageView.setScaleType(localScaleType);
			LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(
					24, 24);
			localImageView.setLayoutParams(localLayoutParams);
			localImageView.setPadding(5, 5, 5, 5);
			localImageView.setImageResource(R.drawable.ic_focus);
			if(i == 0)
				localImageView.setImageResource(R.drawable.ic_focus_select);
			portImg.add(localImageView);
			this.ll_focus_indicator_container.addView(localImageView);
		}
	}
    
    public void horizontalanimop(ImageView im,int index){
		switch(index){
			case 0:
		    	im.setBackgroundResource(R.anim.dance);
		    	break;
			case 1:
		    	im.setBackgroundResource(R.anim.moe);
		    	break;
			case 2:
		    	im.setBackgroundResource(R.anim.bibi);
		    	break;
			case 3:
		    	im.setBackgroundResource(R.anim.shake);
		    	break;
		}
		AnimationDrawable ad = (AnimationDrawable)im.getBackground();
		if(ad.isRunning()){
			ad.stop();
			isplaying = null;
			switch(index){
				case 0:
			    	im.setBackgroundResource(R.drawable.girl22);
			    	break;
				case 1:
			    	im.setBackgroundResource(R.drawable.girl223);
			    	break;
				case 2:
			    	im.setBackgroundResource(R.drawable.girl33);
			    	break;
				case 3:
			    	im.setBackgroundResource(R.drawable.girl233);
			    	break;
			}
	    }
	    else {
	    	if(isplaying != null){
	    		isplaying.imageview.setBackgroundResource(HorizontalListViewAdapter.horizontalgallery[isplaying.index]);
	    	}
	    	ad.start();
	    	isplaying = new IndexImageView();
	    	isplaying.index = index;
	    	isplaying.imageview = im;
	    }
	}
    
	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}
	@Override
	public void onShowPress(MotionEvent e) {
	}
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}
	@Override
	public void onLongPress(MotionEvent e) {
	}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ImageView now = (ImageView)view.findViewById(R.id.girl22or33);
		horizontalanimop(now,position);
	}  
}  