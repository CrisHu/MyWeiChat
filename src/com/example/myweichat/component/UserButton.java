package com.example.myweichat.component;

import com.example.myweichat.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserButton extends LinearLayout {

	private ImageView imageView;
    private TextView  textView;
    private Context context;
	
	public UserButton(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	public UserButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.linearlayout_userbutton, this);
        imageView=(ImageView) findViewById(R.id.ubiv);
        textView=(TextView)findViewById(R.id.ubtv);
    }
     
    public void setBackground(Drawable drawable) { 
        imageView.setImageDrawable(drawable);
    } 
   
    public void setText(String text) { 
        textView.setText(text); 
    } 
    
    public void initUB(String text,Drawable drawable){
    	setText(text); 
    	setBackground(drawable); 
	}
}