package com.example.myweichat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

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
import android.view.GestureDetector;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener,TitleF.Switchdorn,TitleF.UserDetail,DrawerLayout.DrawerListener,OnPageChangeListener,OnGestureListener,OnTouchListener{
	private AddressBookF ABF;
	private DiscoveryF DF;
	private WeiChatF WCF;
	private MEF MEF;
	GestureDetector mGestureDetector; 
	private ViewPager mViewPager;
	private BottomButton bbme,bbd,bbab,bbwc;
	private PageAdapter mPageAdapter;
	private DrawerLayout userDetail = null;
	private UserDetail userdetail;
	private List<Fragment> LF = new ArrayList<Fragment>();
	private int state = 0,firstanim = 0;
	private int[] img = {R.drawable.weixin,R.drawable.addressbook,R.drawable.discovery,R.drawable.app};
	private int[] imgon = {R.drawable.onweixin,R.drawable.onaddressbook,R.drawable.ondiscovery,R.drawable.onapp};
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		//this.overridePendingTransition(R.anim.activity_open,0);  
		Utils.onActivityCreateSetTheme(this);  
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initTitleF();
		initComponent();
		initViewPager();
		mGestureDetector = new GestureDetector((OnGestureListener) this);
		Intent sthstate = getIntent();
		if(Utils.ButtonState != 0 && sthstate.getStringExtra("BottomButton") != null){
			if(Integer.parseInt(sthstate.getStringExtra("BottomButton")) != 0)
				firstanim = 1;
//			onPageSelected(Integer.parseInt(sthstate.getStringExtra("BottomButton")));
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
	}
	
	protected void initTitleF(){
		StyleController.setobjectsrc(TitleF.IB, getResources().getDrawable(R.drawable.switchtonight));
		setSwitchButton(TitleF.IB);
		userDetail = (DrawerLayout) findViewById(R.id.userdetail);
		userDetail.setDrawerListener(this);
		findViewById(R.id.userdetaildrawer).setBackgroundResource(R.color.black_alittle_trans);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int window_width = dm.widthPixels;
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
		if(rstate != 3)MEF.closeAll();
		mViewPager.setCurrentItem(rstate,false);
		StyleController.setobjectsrc(findViewById(id),getResources().getDrawable(imgon[rstate]));
		StyleController.setobjectsrc(findViewById(id),R.style.BottonButtonOn);
        Refresh(id);
        freshAnim(id);
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
	
//	protected void dialog() {
//		Toast.makeText(this, "如要返回请再次确认", Toast.LENGTH_LONG);
//		if (keyCode == KeyEvent.KEYCODE_BACK && new KeyEvent().getRepeatCount() == 0)
//		AlertDialog.Builder builder = new Builder(MainActivity.this);
//		builder.setMessage("EXIT?");
//		builder.setTitle("WARNING");
//		builder.setNegativeButton("EXIT",new DialogInterface.OnClickListener(){
//			@Override
//			public void onClick(DialogInterface dialog, int which){
//				dialog.dismiss();
//				Utils.ButtonState = 0;
//				finish();
//			}
//		 });
//		builder.setPositiveButton("WAIT!",new DialogInterface.OnClickListener(){
//			@Override
//			public void onClick(DialogInterface dialog, int which){
//				dialog.dismiss();
//			}
//	 });
//		builder.create().show();
//	}
//	 
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && userDetail.isDrawerOpen(userdetail))userDetail.closeDrawers();
		else finish();
		return false;
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
	public void switchdorn() {
		// TODO Auto-generated method stub
		if(Utils.sTheme == 2){
		   StyleController.setobjectsrc(TitleF.IB, getResources().getDrawable(R.drawable.switchtodaily));
		   Utils.sTheme = 1;
		}
		else{
			StyleController.setobjectsrc(TitleF.IB, getResources().getDrawable(R.drawable.switchtonight));
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
		Animation userdetaillistanim; 
		userdetaillistanim = AnimationUtils.loadAnimation(this,R.anim.user_detail_drawer_apperance); 
		userdetaillistanim.setDuration(500);
		findViewById(R.id.userdetaildrawer).startAnimation(userdetaillistanim);
		userDetail.openDrawer(Gravity.LEFT);
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
			StyleController.setobjectsrc(iv, getResources().getDrawable(R.drawable.switchtodaily));
		else
			StyleController.setobjectsrc(iv, getResources().getDrawable(R.drawable.switchtonight));
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
	}
	@Override
	public void onDrawerStateChanged(int arg0) {
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		if(arg0 == 2 && mViewPager.getCurrentItem() != state)
			ReadytoRefresh(mViewPager.getCurrentItem(),returnId(mViewPager.getCurrentItem()));
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
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
		return mGestureDetector.onTouchEvent(event);
	}
}
