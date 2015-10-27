package com.example.myweichat.component;

import com.example.myweichat.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BottomButton extends LinearLayout {

	private ImageView imageView;
    private TextView  textView;
    private Context context;
	
	public BottomButton(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	public BottomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.linearlayout_bottombutton, this);
        imageView=(ImageView) findViewById(R.id.bbiv);
        textView=(TextView)findViewById(R.id.bbtv);
    }
     
    public void setBackground(Drawable drawable) { 
        imageView.setImageDrawable(drawable);
    }
    
    public ImageView getimgview(){
    	return this.imageView;
    }
   
    public void setText(String text) { 
        textView.setText(text); 
    } 
    
    public void setTextStyle(int colorstyleid){
    	textView.setTextAppearance(this.getContext(), colorstyleid);;
    }
    
    public void initBB(String text,int colorstyleid,Drawable drawable){
    	setTextStyle(colorstyleid);
    	setText(text); 
    	setBackground(drawable); 
	}
}
