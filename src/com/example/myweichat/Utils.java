package com.example.myweichat;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class Utils {
	protected static int sTheme,ButtonState = 0;    
    public final static int THEME_DAILY = 1;  
    public final static int THEME_NIGHT = 2;  
    public final static int THEME_DAILYS = 3;  
    public final static int THEME_NIGHTS = 4;
    public static void changeToTheme(Activity activity, int theme,int state)  
    {  
    	ButtonState = 1;
        sTheme = theme;  
        activity.finish();
        Intent restart = new Intent(activity, activity.getClass());
        restart.putExtra("BottomButton", state+"");
        activity.startActivity(restart);  
    }  
    public static void onActivityCreateSetTheme(Activity activity)  
    {  
        switch (sTheme)  
        {   
	        case THEME_DAILY:  
	        	activity.setTheme(R.style.MyWeiChatD);  
	            break;  
	        case THEME_NIGHT:  
	            activity.setTheme(R.style.MyWeiChatN);  
	            break;  
	        default:
	        	switch(new WROperation(activity.getApplicationContext()).Getthemestate()+2){
		        	case THEME_DAILYS:  
		        		activity.setTheme(R.style.MyWeiChatD);  
		        		sTheme = 1;
		        		break;  
		        	case THEME_NIGHTS:  
		        		activity.setTheme(R.style.MyWeiChatN);
		        		sTheme = 2;
		        		break; 
	        	}
        }  
    }  
}
