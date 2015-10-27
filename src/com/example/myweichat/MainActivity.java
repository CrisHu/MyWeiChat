package com.example.myweichat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.myweichat.adapter.PageAdapter;
import com.example.myweichat.component.BottomButton;
import com.example.myweichat.component.UserDetail;
import com.example.myweichat.controller.StyleController;
import com.example.myweichat.controller.Utils;
import com.example.myweichat.controller.WROperation;
import com.example.myweichat.mainfragment.AddressBookF;
import com.example.myweichat.mainfragment.DiscoveryF;
import com.example.myweichat.mainfragment.TitleF;
import com.example.myweichat.mainfragment.WeiChatF;
import com.example.myweichat.mainfragment.MEF;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.view.GestureDetector;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends FragmentActivity implements OnClickListener,TitleF.Switchdorn,TitleF.UserDetail,DrawerLayout.DrawerListener,OnPageChangeListener,OnGestureListener,OnTouchListener{
	private AddressBookF ABF;
	private DiscoveryF DF;
	private WeiChatF WCF;
	private MEF MEF;
	private GestureDetector mGestureDetector; 
	private static RelativeLayout refresh;
	protected static ViewPager mViewPager;
	private BottomButton bbme,bbd,bbab,bbwc;
	private PageAdapter mPageAdapter;
	private DrawerLayout userDetail = null;
	private UserDetail userdetail;
	private DisplayMetrics dm;
	public static int window_width;
	public static int window_height;
	private int defaultheight = 200,maxheight = 1500,refresh_height;//触发刷新所需要的最小滑动距离/最大可接受滑动距离
	private List<Fragment> LF = new ArrayList<Fragment>();
	public static int state = 0,firstanim = 0,canrefresh = 1,HELP_STATE = 4;
	public static int dorefresh,freshisanimating;
	private int[] img = {R.drawable.weixin,R.drawable.addressbook,R.drawable.discovery,R.drawable.app};
	private int[] imgon = {R.drawable.onweixin,R.drawable.onaddressbook,R.drawable.ondiscovery,R.drawable.onapp};
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		Utils.onActivityCreateSetTheme(this);  
		super.onCreate(savedInstanceState);
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		window_width = dm.widthPixels;
		window_height = dm.heightPixels;
		dorefresh = 1;
		freshisanimating = 0;
		setContentView(R.layout.activity_main);
		initComponent();
		initTitleF();
		initViewPager();
		mGestureDetector = new GestureDetector((OnGestureListener) this);
		Intent sthstate = getIntent();
		if(Utils.ButtonState != 0 && sthstate.getStringExtra("BottomButton") != null){
			if(Integer.parseInt(sthstate.getStringExtra("BottomButton")) != HELP_STATE)
				firstanim = 1;
			state = HELP_STATE;    //置为不存在，帮助刷新
			onClick(findViewById(returnId(Integer.parseInt(sthstate.getStringExtra("BottomButton")))));
		}
	}
	
	protected void initComponent(){
		bbwc = (BottomButton)findViewById(R.id.bbwc);
		bbab = (BottomButton)findViewById(R.id.bbab);
		bbd = (BottomButton)findViewById(R.id.bbd);
		bbme = (BottomButton)findViewById(R.id.bbme);
		bbwc.initBB("WECHAT",R.style.BottonButtonOn,getResources().getDrawable(imgon[0]));
		bbab.initBB("HELLO",R.style.BottomButtonOff,getResources().getDrawable(img[1]));
		bbd.initBB("FIND",R.style.BottomButtonOff,getResources().getDrawable(img[2]));
		bbme.initBB("MYSELF",R.style.BottomButtonOff,getResources().getDrawable(img[3]));
		bbwc.setOnClickListener(this);
		bbab.setOnClickListener(this);
		bbd.setOnClickListener(this);
		bbme.setOnClickListener(this);
		WCF = new WeiChatF();
        ABF = new AddressBookF();
        DF = new DiscoveryF();
        MEF = new MEF();
        LF.add(WCF);
        LF.add(ABF);
        LF.add(DF);
        LF.add(MEF);
	    refresh = (RelativeLayout)findViewById(R.id.refresh);
	    refresh.setVisibility(View.INVISIBLE);
	    refresh.getLayoutParams().height=(int) (1.2*window_height/13.4);
	    //AbsoluteLayout 布局修改
	    refresh_height = refresh.getLayoutParams().height;
	    findViewById(R.id.fragmenttitle).getLayoutParams().height = refresh_height;
	    findViewById(R.id.fragmenttitle).layout(0,0, refresh.getRight(),refresh_height);
	    findViewById(R.id.fragment).getLayoutParams().height = (int) (refresh_height * 11 / 1.2);
	    findViewById(R.id.fragment).setX(0);
	    findViewById(R.id.fragment).setY(findViewById(R.id.fragmenttitle).getHeight());
	    refresh.bringToFront();
	    findViewById(R.id.fragmenttitle).bringToFront();
	}
	
	protected void initTitleF(){
		StyleController.setobjectsrc(TitleF.IB, R.drawable.switchtonight);
		setSwitchButton(TitleF.IB);
		userDetail = (DrawerLayout) findViewById(R.id.userdetail);
		userDetail.setDrawerListener(this);
		findViewById(R.id.userdetaildrawer).setBackgroundResource(R.color.black_alittle_trans);
		userdetail = (UserDetail)findViewById(R.id.userdetaildrawer);
		userdetail.getLayoutParams().width=5*window_width/7;
		userdetail.setOnClickListener(this);
		setUserDetailDrawer((UserDetail)findViewById(R.id.userdetaildrawer));
	}
	
	protected void initViewPager(){
		mViewPager = (ViewPager) findViewById(R.id.fragment);
        mPageAdapter = new PageAdapter(getSupportFragmentManager(), LF);
		mViewPager.setAdapter(mPageAdapter);
		mViewPager.setOffscreenPageLimit(3);//保存相邻3页
		mViewPager.setOnPageChangeListener(this);
		mViewPager.setLongClickable(true); 
		mViewPager.setOnTouchListener(this);
	}
	
	protected void ReadytoRefresh(int rstate,final int id){
		if(refresh.getAnimation() != null){
		refresh.getAnimation().cancel();
		freshisanimating = 0;
		canrefresh = 0;
		refresh.setVisibility(View.INVISIBLE);
	}
		mViewPager.setCurrentItem(rstate,false);
		StyleController.setobjectsrc(findViewById(id),getResources().getDrawable(imgon[rstate]));
		StyleController.setobjectsrc(findViewById(id),R.style.BottonButtonOn);
        Refresh(id);
        freshAnim(id);
		if(rstate != 3)MEF.closeAll();
		if(rstate != 2)DF.galleryStop();
		else DF.galleryStart();
	}
	
	public void freshAnim(final int id){
		if(firstanim != 0){
			firstanim = 0;
			return;
		}
		Animation anim = AnimationUtils.loadAnimation(this,R.anim.bb_shake); 
        ((BottomButton)findViewById(id)).getimgview().startAnimation(anim);
        anim.setAnimationListener(new AnimationListener() {  
            @Override  
            public void onAnimationEnd(Animation animation) {  
                // TODO Auto-generated method stub 
            	Animation anim = AnimationUtils.loadAnimation(MainActivity.this,R.anim.bb_shaken); 
            	((BottomButton)findViewById(id)).getimgview().startAnimation(anim);
            }
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
			}  
        });
	}
	
	protected void Refresh(int i){
		int[] a = {R.id.bbwc,R.id.bbab,R.id.bbd,R.id.bbme};
		for(int view:a){
			if(view != i){
				View thisview = findViewById(view);
				StyleController.setobjectsrc(thisview,R.style.BottomButtonOff);
					switch(view){
						case R.id.bbwc:
							StyleController.setobjectsrc(thisview,getResources().getDrawable(img[0]));
							break;
						case R.id.bbab:
							StyleController.setobjectsrc(thisview,getResources().getDrawable(img[1]));
							break;
						case R.id.bbd:
							StyleController.setobjectsrc(thisview,getResources().getDrawable(img[2]));
							break;
						case R.id.bbme:
							StyleController.setobjectsrc(thisview,getResources().getDrawable(img[3]));
							break;
				}
			}
		}
	}
	
	protected int returnId(int state){
		int id = 0;
		switch(state){
			case 0:
				id = R.id.bbwc;
				break;
			case 1:
				id = R.id.bbab;
				break;
			case 2:
				id = R.id.bbd;
				break;
			case 3:
				id = R.id.bbme;
				break;
		}
		return id;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && userDetail.isDrawerOpen(userdetail))userDetail.closeDrawers();
		else {
			state = 0;
			finish();
		}
		return false;
	}
	
	public void setUserDetailDrawer(UserDetail ud){
		if(Utils.sTheme == 2){
			StyleController.setobjectsrc(ud, R.color.title_style_night);
			}
		else{
			StyleController.setobjectsrc(ud, R.color.title_style_daily);
			}
	}
	public void setSwitchButton(ImageView iv){
		if(Utils.sTheme == 2)
			StyleController.setobjectsrc(iv, R.drawable.switchtodaily);
		else
			StyleController.setobjectsrc(iv, R.drawable.switchtonight);
	}

	public void doActionUp(){
		if(refresh.getTop() > 200){
			canrefresh = 0;
			freshisanimating = 1;
			TranslateAnimation backto200 = new TranslateAnimation(0,0,0,200 - refresh.getTop()); //相对当前的位置什么的
			backto200.setDuration(200);
			final Animation rotate = AnimationUtils.loadAnimation(this,R.anim.refresh); 
			refresh.startAnimation(backto200);
			backto200.setAnimationListener(new AnimationListener(){
				@Override
				public void onAnimationEnd(Animation animation)
				{
					refresh.layout(refresh.getLeft(),200,refresh.getRight(),200 + refresh_height);
					refresh.clearAnimation();
					refresh.startAnimation(rotate);
				}
				@Override
				public void onAnimationRepeat(Animation animation)
				{
				}
				@Override
				public void onAnimationStart(Animation animation)
				{
				}
			});
			rotate.setAnimationListener(new AnimationListener(){
				@Override
				public void onAnimationEnd(Animation animation)
				{
					freshisanimating = 0;
					refresh.clearAnimation();
					refresh.layout(refresh.getLeft(),0,refresh.getRight(),refresh_height);
					refresh.setVisibility(View.INVISIBLE);
					if(dorefresh == 1){
						if(state == 2)DF.galleryStop();
						mViewPager.getAdapter().notifyDataSetChanged();
						PageAdapter.i = 0;
					}
				}
				@Override
				public void onAnimationRepeat(Animation animation)
				{
				}
				@Override
				public void onAnimationStart(Animation animation)
				{
				}
			});
			return;
		}
		if(refresh.getTop() != 0 && canrefresh == 1){
			canrefresh = 0;
			freshisanimating = 1;
			int sparerefreshheight = refresh.getTop();
			TranslateAnimation showAnim = new TranslateAnimation(0,0,sparerefreshheight,0);
			refresh.layout(refresh.getLeft(),0,refresh.getRight(),refresh_height);
			showAnim.setDuration(300*Math.abs(sparerefreshheight)/refresh_height);
			refresh.startAnimation(showAnim);
			showAnim.setAnimationListener(new AnimationListener(){
				@Override
				public void onAnimationEnd(Animation animation)
				{
					refresh.clearAnimation();
					refresh.setVisibility(View.INVISIBLE);
					freshisanimating = 0;
				}
				@Override
				public void onAnimationRepeat(Animation animation)
				{
				}
				@Override
				public void onAnimationStart(Animation animation)
				{
				}
			});
			return;
		}
		refresh.setVisibility(View.INVISIBLE);
	}
	public void doActionDown(){
		if(freshisanimating == 0)
			canrefresh = 1;
	}
	public void dorefresh(int dis){
		  if(canrefresh == 1 && state != 3){
			  if(dis > 10)
				  refresh.setVisibility(View.VISIBLE);
			  dis = (int) Math.sqrt((maxheight * maxheight - Math.pow((dis - defaultheight - maxheight),2)))/5;
			  refresh.layout(refresh.getLeft(),dis, refresh.getRight(),dis + refresh_height);
		  }
	}
	
	public static void interruptrefresh(){
		if(refresh.getAnimation() != null){
			dorefresh = 0;
			refresh.getAnimation().cancel();
			canrefresh = 0;
			freshisanimating = 0;
			refresh.setVisibility(View.INVISIBLE);
		}
	}
	
	@Override
	public void switchdorn() {
		// TODO Auto-generated method stub
		if(Utils.sTheme == 2){
		   StyleController.setobjectsrc(TitleF.IB, R.drawable.switchtodaily);
		   Utils.sTheme = 1;
		}
		else{
			StyleController.setobjectsrc(TitleF.IB, R.drawable.switchtonight);
			Utils.sTheme = 2; 
		}
		
		try {
			JSONObject JSONconfig = new JSONObject();
			JSONconfig.put("theme",Utils.sTheme);
			new WROperation(this).writeSDFile(JSONconfig.toString());
			Utils.changeToTheme(this,Utils.sTheme,state);
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void userDetail() {
		// TODO Auto-generated method stub
		if(refresh.getAnimation() != null){
			refresh.getAnimation().cancel();
		}
		refresh.setVisibility(View.INVISIBLE);
		freshisanimating = 0;
		Animation userdetaillistanim = AnimationUtils.loadAnimation(this,R.anim.user_detail_drawer_apperance); 
		userdetaillistanim.setDuration(500);
		findViewById(R.id.userdetaildrawer).startAnimation(userdetaillistanim);
		userDetail.openDrawer(Gravity.LEFT);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.bbwc:
				if(state == 0)break;
	            state = 0;
	            ReadytoRefresh(state,R.id.bbwc);
	            break;
			case R.id.bbab:
				if(state == 1)break;
	            state = 1;
	            ReadytoRefresh(state,R.id.bbab);
	            onPageSelected(state);
	            break;  
			case R.id.bbd:
				if(state == 2)break; 
	            state = 2;
	            ReadytoRefresh(state,R.id.bbd);
	            onPageSelected(state);
	            break;  
			case R.id.bbme:
				if(state == 3)break;
	            state = 3;
	            ReadytoRefresh(state,R.id.bbme);
	            onPageSelected(state);
	            break;
		}
	}
	
	@Override
	public void onDrawerClosed(View arg0) {
		for(LinearLayout ll:UserDetail.lvll)
			ll.setVisibility(View.GONE);
	}
	@Override
	public void onDrawerOpened(View arg0) {
		for(LinearLayout ll:UserDetail.lvll)
			ll.setVisibility(View.VISIBLE);
		UserDetail.startanim();
	}
	@Override
	public void onDrawerSlide(View arg0, float arg1) {
		StyleController.setobjectsrc(UserDetail.imageviewbili, R.drawable.bili_up);
		interruptrefresh();
	}
	@Override
	public void onDrawerStateChanged(int arg0) {
		canrefresh = 1;
		dorefresh = 1;
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		if(arg0 == 2 && mViewPager.getCurrentItem() != state)
			ReadytoRefresh(mViewPager.getCurrentItem(),returnId(mViewPager.getCurrentItem()));
		canrefresh = 1;
		dorefresh = 1;
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		interruptrefresh();
	}
	@Override
	public void onPageSelected(int arg0) {
		state = arg0;
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
			float distanceY){
		if(e2 != null && e1 != null)
			dorefresh((int)(e2.getY() - e1.getY()));
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
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN)
			doActionDown();
		if(event.getAction() == MotionEvent.ACTION_UP){
			doActionUp();
        }
		return mGestureDetector.onTouchEvent(event);
	}
}
