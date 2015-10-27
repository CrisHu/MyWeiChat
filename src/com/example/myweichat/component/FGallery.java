package com.example.myweichat.component;

import java.util.Timer;
import java.util.TimerTask;

import com.example.myweichat.R;
import com.example.myweichat.mainfragment.DiscoveryF;

import android.app.Notification.Action;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

@SuppressWarnings("deprecation")
public class FGallery extends Gallery {
	private int position;
	private static final int timerAnimation = 1;
	int[] a = {0,1,2};
	private final Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case timerAnimation:
				position = (getSelectedItemPosition()+1)%DiscoveryF.portImg.size();
				onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
				for(int i : a)
					if(i != position)
						DiscoveryF.portImg.get(i).setImageResource(R.drawable.ic_focus);
				DiscoveryF.portImg.get(position).setImageResource(R.drawable.ic_focus_select);
				break;
			default:
				break;
			}
		};
	};

	private Timer timer = new Timer();
	private TimerTask task = new TimerTask() {
		public void run() {
			mHandler.sendEmptyMessage(timerAnimation);
		}
	};

	public FGallery(Context paramContext) {
		super(paramContext);
	}

	public FGallery(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);

	}

	public FGallery(Context paramContext, AttributeSet paramAttributeSet,
			int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	private boolean isScrollingLeft(MotionEvent paramMotionEvent1,
			MotionEvent paramMotionEvent2) {
		float f2 = paramMotionEvent2.getX();
		float f1 = paramMotionEvent1.getX();
		if (f2 > f1)
			return true;
		return false;
	}

	public boolean onFling(MotionEvent paramMotionEvent1,
			MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2) {
		if(paramMotionEvent1.getAction() == MotionEvent.ACTION_DOWN){
			destroy();
			int keyCode;
			if (isScrollingLeft(paramMotionEvent1, paramMotionEvent2)) {
				keyCode = KeyEvent.KEYCODE_DPAD_LEFT;
			} else {
				keyCode = KeyEvent.KEYCODE_DPAD_RIGHT;
			}
			onKeyDown(keyCode, null);
			if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)
				position = (getSelectedItemPosition()+1)%DiscoveryF.portImg.size();
			else
				position = (getSelectedItemPosition()-1)%DiscoveryF.portImg.size();
			//Log.e("", "get:"+getSelectedItemPosition()+";position:"+position);
			if(position < 0);
			else {
				for(int i : a)
					if(i != position)
						DiscoveryF.portImg.get(i).setImageResource(R.drawable.ic_focus);
				DiscoveryF.portImg.get(position).setImageResource(R.drawable.ic_focus_select);
			}
			start();
		}
		return true;
	}

	public void start() {
		task.cancel();
		timer.cancel();
		timer = new Timer();
		task = new TimerTask() {
			public void run() {
				mHandler.sendEmptyMessage(timerAnimation);
			}
		};
		timer.schedule(task, 3000, 3000);
	}
	
	public void destroy() {
		timer.cancel();
	}
}
