package com.example.myweichat.adapter;

import java.util.List;

import com.example.myweichat.MainActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class PageAdapter extends FragmentPagerAdapter{
	private List<Fragment> listFragments;
	private FragmentManager fm;
	public static int i = 0;
	
	public PageAdapter(FragmentManager fm,List<Fragment> listFragments) {
		super(fm);
		this.fm = fm;
		this.listFragments = listFragments;
	}

	@Override
	public Fragment getItem(int position) {
		return listFragments.get(position);
	}

	@Override
	public int getCount() {
		return listFragments.size();
	}
	
//	@Override  
//	public int getItemPosition(Object object) {  
//	    return POSITION_NONE;  
//	}  

	@Override  
    public int getItemPosition(Object object) {  
        if(i++ == MainActivity.state)  
            return POSITION_NONE; 
        else 
            return POSITION_UNCHANGED;    
    }  
}
