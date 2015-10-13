package com.example.myweichat;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;  
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;  
import android.view.View;   
import android.view.View.OnClickListener;
import android.view.ViewGroup;  
import android.widget.ImageView;
  
public class DiscoveryF extends Fragment implements OnClickListener{  
	ImageView DANCE,SHAKE;
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState)  
    {  
        View view = inflater.inflate(R.layout.fragment_discovery, container, false);
        DANCE = (ImageView)view.findViewById(R.id.dance);
        DANCE.setOnClickListener(this);
        DANCE.setBackgroundResource(R.drawable.girl22);
        SHAKE = (ImageView)view.findViewById(R.id.shake);
        SHAKE.setOnClickListener(this);
        SHAKE.setBackgroundResource(R.drawable.girl33);
        return view;  
    }
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.dance:
			DANCE.setBackgroundResource(R.anim.dance);
		    AnimationDrawable animationDrawabled = (AnimationDrawable)DANCE.getBackground();
		    if(animationDrawabled.isRunning()){
		    	animationDrawabled.stop();
		        DANCE.setBackgroundResource(R.drawable.girl22);
		    }
		    else animationDrawabled.start();
		    break;
		case R.id.shake:
		    SHAKE.setBackgroundResource(R.anim.shake);
		    AnimationDrawable animationDrawables = (AnimationDrawable)SHAKE.getBackground();
		    if(animationDrawables.isRunning()){
		    	animationDrawables.stop();
		        SHAKE.setBackgroundResource(R.drawable.girl33);
		    }
		    else animationDrawables.start();
		    break;
		}
	}  
}  