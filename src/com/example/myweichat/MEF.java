package com.example.myweichat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Context;
import android.os.Bundle;  
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;  
import android.view.View;   
import android.view.ViewGroup;  
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
  
public class MEF extends Fragment implements OnItemClickListener{
	
	public static int open[] = {0,0,0,0};//{是否有菜单打开，第一个菜单是否打开}
	public static ListView lv;  
	private View view;
    public ArrayList<Map<Integer,View>> invisible;
    private List<Map<String,Object>> data;
    public static ArrayList<ArrayList<String>> all = new ArrayList<ArrayList<String>>(); 
    private static String[] main = {"Function","Entertain","Option"};
    private static String[] name = {"Function","Location/Navi","Speech Service","Entertain","Movie Theater","Music Time","Option"};
    private int[] src = {R.drawable.function,R.drawable.location,R.drawable.speech,R.drawable.entertain,R.drawable.movie,R.drawable.music,R.drawable.option};
    
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState)  
    {   
//    	if (null == view){
    	for(int i = 0,j=0;i < 3;i++){
			ArrayList<String> a = new ArrayList<String>();
    		for(;;j++){
    			a.add(name[j]);
    			if(i == 2)break;
    			if(name[j+1] == main[i+1])
    				{
    					j++;
	    				break;
    				}
    		}
    		all.add(a);
    	}
        invisible = new ArrayList<Map<Integer,View>>();
    	view = inflater.inflate(R.layout.fragment_me, container, false);
        lv = (ListView)view.findViewById(R.id.melv);
        data = getData();  
        FuncAdapter adapter = new FuncAdapter(view.getContext());  
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
		lv.setDividerHeight(1);
//    	}
        return view;  
    }  
    
    private List<Map<String, Object>> getData()  
    {  
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
        Map<String, Object> map;  
        for(int i=0;i<src.length;i++)  
        {  
            map = new HashMap<String, Object>();  
            map.put("img", src[i]);  
            map.put("txt", name[i]);  
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
    
    public class FuncAdapter extends BaseAdapter{  
    	private Context context;
        private LayoutInflater mInflater = null; 
        
        private FuncAdapter(Context context)  
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
                convertView = mInflater.inflate(R.layout.item_function, null);  
                holder.img = (ImageView)convertView.findViewById(R.id.funciv);  
                holder.txt = (TextView)convertView.findViewById(R.id.functv); 
                holder.llayout = (LinearLayout)convertView.findViewById(R.id.layout);
                convertView.setTag(holder);  
            }
            else holder = (ViewHolder)convertView.getTag();
            StyleController.contextinStyleController = this.context;
            setTxtTheme(holder.txt);
            holder.img.setImageResource((Integer)data.get(position).get("img"));  
            holder.txt.setText((String)data.get(position).get("txt"));
            String txt = holder.txt.getText()+"";
            for(String a : main)
            	if(a.equals(txt)){
                    setmMainTheme(holder.llayout);
                    return convertView;
            	}
        	setOptTheme(holder.llayout);
        	int i = 0;
        	for(;i < all.size();i++){
        		if(((ArrayList<String>)(all.get(i))).contains(txt))break;
        	}
        	Map<Integer,View> map = new HashMap<Integer,View>(); 
        	map.put(i,holder.llayout);
        	invisible.add(map);
        	if(open[i+1] == 0){
	        	holder.llayout.setVisibility(View.GONE);
        	}
        	else {
        		holder.llayout.setVisibility(View.VISIBLE);
        	}
        	return convertView;
        }
    } 

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		switch(((TextView)(arg1.findViewById(R.id.functv))).getText()+""){
			case "Function":
				doandrefresh(1);
				break;
			case "Entertain":
				doandrefresh(2);
				break;
			case "Option":
				doandrefresh(3);
				break;
		}

	}
	
	public void setopen(int position,int isOn){
		int sum = 0;
		open[position] = isOn;
		for(int a : open)sum = sum+a;
		open[0] = sum == 0?0:1;
		//if(sum == 1 && open[0] == 1)open[0]=0;
	}
	
	public void closeAll(){
		if(open[0] == 0)return;
		for(int i = 0; i < lv.getChildCount(); i++){  
	         View view = lv.getChildAt(i);  
	         TextView txt = (TextView)view.findViewById(R.id.functv); 
	         int com = 0;
	         for(String a : main)
		         if(!txt.getText().equals(a))com++;
	         if(com == main.length){
		         LinearLayout ll = (LinearLayout)view.findViewById(R.id.layout);
	        	 ll.setVisibility(View.GONE);
	         }
	     }
		setopen(1,0);
		setopen(2,0);
		setopen(3,0);
	}
	
	public void setOptTheme(LinearLayout ll){
        if(Utils.sTheme == 2){
        	StyleController.setobjectsrc(ll, R.color.black_afew_trans);
    	}
		else{
        	StyleController.setobjectsrc(ll, R.color.white_alittle_trans);
    	}
	}
	
	public void setmMainTheme(LinearLayout ll){
		if(Utils.sTheme == 2){
        	StyleController.setobjectsrc(ll, R.color.black_alittle_trans);
    	}
        else{
        	StyleController.setobjectsrc(ll, R.color.white_little_trans);
    	}
	}
	
	public void setTxtTheme(TextView tv){
		if(Utils.sTheme == 2){
        	StyleController.setobjectsrc(tv, R.style.NightText);
    	}
        else{
        	StyleController.setobjectsrc(tv, R.style.DailyText);
    	}
	}
	
	private void doandrefresh(int no){
		int time = 500;
		ArrayList<View> viewList = new ArrayList<>();
		if(open[no] == 0){
			for(int i = 0;i < invisible.size();i++){
				Map<Integer,View> thismap = invisible.get(i);
				if(thismap.get(no-1) == null)continue;
				View thisview = thismap.get(no-1);
				viewList.add(thisview);
			}
			setopen(no,1);
			animation(viewList,time,0,1);
//			for(int j = 1;j < open.length;j++){
//				if(j != no && open[j] == 1)doandrefresh(j);
//			}
		}
		else{
			for(int i = 0;i < invisible.size();i++){
				Map<Integer,View> thismap = invisible.get(i);
				if(thismap.get(no-1) == null)continue;
				View thisview = thismap.get(no-1);
				viewList.add(thisview);
			}
			setopen(no,0);
			animation(viewList,time,viewList.size()-1,0);
		}
	}
	
	public void animation(final ArrayList<View> viewList,final int time,final int index,final int opstate){
		if(!(index < viewList.size()) || index <0)return;
		Animation funcitemanim; 
		if(opstate == 1){
			funcitemanim = AnimationUtils.loadAnimation(this.view.getContext(),R.anim.list_item_appearance); 
			funcitemanim.setDuration(time);
			viewList.get(index).startAnimation(funcitemanim);
			viewList.get(index).setVisibility(View.VISIBLE);
			funcitemanim.setAnimationListener(new AnimationListener() {  
	            @Override  
	            public void onAnimationStart(Animation animation) {
	            	animation(viewList,time+100,index+1,opstate);
	            } 
	            @Override  
	            public void onAnimationRepeat(Animation animation) {  
	            }  
	            @Override  
	            public void onAnimationEnd(Animation animation) {
	            }  
	        });
		}
		else
		{
        	viewList.get(index).setVisibility(View.GONE);
        	animation(viewList,time+100,index-1,opstate);
//			funcitemanim = AnimationUtils.loadAnimation(this.view.getContext(),R.anim.list_item_disappearance); 
//			funcitemanim.setDuration(time);
//			viewList.get(index).startAnimation(funcitemanim);
//			funcitemanim.setAnimationListener(new AnimationListener() {  
//	            @Override  
//	            public void onAnimationStart(Animation animation) {
//	            	animation(viewList,time+100,index-1,opstate);
//	            } 
//	            @Override  
//	            public void onAnimationRepeat(Animation animation) {  
//	            }  
//	            @Override  
//	            public void onAnimationEnd(Animation animation) {
//	            	viewList.get(index).setVisibility(View.GONE);
//	            }  
//	        });
		}
	}
}  
