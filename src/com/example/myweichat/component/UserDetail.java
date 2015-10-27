package com.example.myweichat.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.myweichat.MainActivity;
import com.example.myweichat.R;
import com.example.myweichat.controller.StyleController;
import com.example.myweichat.controller.Utils;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UserDetail extends LinearLayout implements OnClickListener,OnTouchListener{
	protected static ListView lv;
	private int time = 100;
    private Context context;
    protected static AnimationDrawable animationDrawable;
    private List<Map<String,Object>> data;
    public static ImageView imageviewbili;
    protected static ArrayList<Animation> lvanim = new ArrayList<Animation>();
    public static ArrayList<LinearLayout> lvll = new ArrayList<LinearLayout>();
    private int[] img = {R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4,R.drawable.image5,R.drawable.image6,R.drawable.image7,R.drawable.image8,R.drawable.image9};
	public UserDetail(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	public UserDetail(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.linearlayout_userdetail, this);
        imageviewbili = (ImageView)findViewById(R.id.littletv);
        imageviewbili.setOnTouchListener(this);
        imageviewbili.setBackgroundResource(R.drawable.bili_up);
        ImageView usericon = (ImageView)findViewById(R.id.usericon);
		TextView textview = (TextView)findViewById(R.id.username);
		
        RelativeLayout.LayoutParams layoutParamsb = (RelativeLayout.LayoutParams)imageviewbili.getLayoutParams();
        layoutParamsb.height = (int) (MainActivity.window_height*3/11/1.5);
        layoutParamsb.width = 270*layoutParamsb.height/208;
        imageviewbili.setLayoutParams(layoutParamsb);
        
		StyleController.contextinStyleController = context;
		setusernametextTheme(textview);
		data = getData();  
		UserDetailAdapter adapter = new UserDetailAdapter(context);  
        lv = (ListView)findViewById(R.id.userlv);
        lv.setAdapter(adapter);
		lv.setDividerHeight(1);
    }
	
	private List<Map<String, Object>> getData()  
    {  
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
        Map<String, Object> map;  
        for(int i=0;i<9;i++)  
        {  
            map = new HashMap<String, Object>();  
            map.put("txt", i+""); 
            map.put("img", img[i]);
            list.add(map);  
        }  
        return list;  
    }
    
    private class ViewHolder  
    {  
    	public LinearLayout llayout;
        public ImageView img;  
        public TextView txt;
    } 
    
    public class UserDetailAdapter extends BaseAdapter{  
    	private Context context;
        private LayoutInflater mInflater = null; 
        
        private UserDetailAdapter(Context context)  
        {
        	super();
            this.mInflater = LayoutInflater.from(context); 
            this.context = context;
        }  
        @Override  
        public int getCount() {  
            return data.size();  
        }  
        @Override  
        public Object getItem(int position) {  
            return position;  
        }  
        @Override  
        public long getItemId(int position) {  
            return position;  
        }  
        @Override  
        public View getView(int position,View convertView,ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null){  
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item_userop, null);  
                holder.img = (ImageView)convertView.findViewById(R.id.useropiv);  
                holder.txt = (TextView)convertView.findViewById(R.id.useroptv); 
                holder.llayout = (LinearLayout)convertView.findViewById(R.id.layout);
                convertView.setTag(holder);  
            }
            else holder = (ViewHolder)convertView.getTag();
            holder.txt.setText((String)data.get(position).get("txt"));
            holder.img.setBackgroundResource(((int)data.get(position).get("img")));
            StyleController.contextinStyleController = this.context;
            setItemTextTheme(holder.txt);
            Animation userdetailitemanim; 
            userdetailitemanim = AnimationUtils.loadAnimation(context,R.anim.list_item_appearance); 
            userdetailitemanim.setDuration(time+=100);
            lvanim.add(userdetailitemanim);
			holder.llayout.setVisibility(View.GONE);
			lvll.add(holder.llayout);
        	return convertView;
        }
    } 
    
    public void setItemTextTheme(TextView tv){
    	if(Utils.sTheme == 2){
        	StyleController.setobjectsrc(tv,R.style.NightUserText);
    	}
        else{
        	StyleController.setobjectsrc(tv,R.style.DailyUserText);
    	}
	}
    
    public void setusernametextTheme(TextView tv){
		StyleController.setobjectsrc(tv,R.style.NightText);
    }
    
    public static void startanim(){
    	for(int i = 0;i < lvll.size();i++)
    		lvll.get(i).startAnimation(lvanim.get(i));
    }

	@Override
	public void onClick(View v) {
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction() == MotionEvent.ACTION_DOWN){
	        imageviewbili.setBackgroundResource(R.drawable.bili_down);
        } 
		if(event.getAction() == MotionEvent.ACTION_UP){
	        imageviewbili.setBackgroundResource(R.drawable.bili_up);
        }
		return true;
	}
}
