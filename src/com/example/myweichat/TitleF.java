package com.example.myweichat;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class TitleF extends Fragment implements OnClickListener{
	protected static ImageView IB;
	protected static UserButton UB;
	
	public interface Switchdorn  
    {  
        void switchdorn();  
    } 
	
	public interface UserDetail  
    {  
		void userDetail();
    }
	
	@Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)  
    {  
        View view = inflater.inflate(R.layout.fragment_title, container, false);
        if(Utils.sTheme == 2){
			StyleController.setobjectsrc((LinearLayout)view.findViewById(R.id.titlell), R.color.title_style_night);
			}
		else{
			StyleController.setobjectsrc((LinearLayout)view.findViewById(R.id.titlell), R.color.title_style_daily);
			}
        IB = (ImageView)view.findViewById(R.id.switchstate);
        IB.setOnClickListener(this);
        UB = (UserButton)view.findViewById(R.id.nowuser);
        UB.initUB("DEFAULTUSER",getResources().getDrawable(R.drawable.default_user));
        UB.setOnClickListener(this);
        return view;  
    }
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			// TODO Auto-generated method stub
			case R.id.switchstate:
				if (getActivity() instanceof Switchdorn)  
		            ((Switchdorn) getActivity()).switchdorn(); 
				break;
			case R.id.nowuser:
				if (getActivity() instanceof UserDetail)  
		            ((UserDetail) getActivity()).userDetail(); 
				break;
		}
	} 
}
