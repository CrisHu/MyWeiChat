package com.example.myweichat.controller;

import com.example.myweichat.component.BottomButton;
import com.example.myweichat.component.UserDetail;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class StyleController{
	public static Context contextinStyleController;
	public static void setobjectsrc(View view,Object obj){
		if(view instanceof ImageView && obj instanceof Drawable){
			((ImageView) view).setImageDrawable((Drawable) obj);
			return;
		}
		if(view instanceof ImageView && obj instanceof Integer){
			((ImageView) view).setBackgroundResource((int) obj);
			return;
		}
		if(view instanceof BottomButton && obj instanceof Drawable){
			((BottomButton) view).setBackground((Drawable) obj);
			return;
		}
		if(view instanceof BottomButton && obj instanceof Integer){
			((BottomButton) view).setTextStyle((int) obj);
			return;
		}
		if(view instanceof LinearLayout && obj instanceof Integer){
			((LinearLayout) view).setBackgroundResource((int) obj);
			return;
		}
		if(view instanceof TextView && obj instanceof Integer){
			((TextView) view).setTextAppearance(contextinStyleController,(Integer) obj);
			return;
		}
		if(view instanceof ListView && obj instanceof Integer){
			((ListView) view).setBackgroundResource((int) obj);
			return;
		}
		if(view instanceof UserDetail && obj instanceof Integer){
			((UserDetail) view).setBackgroundResource((int) obj);
			return;
		}
	}
	public static void setobjectsty(){
		
	}
}
