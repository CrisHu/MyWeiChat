package com.example.myweichat.mainfragment;

import com.example.myweichat.R;

import android.os.Bundle;  
import android.support.v4.app.Fragment;
import android.view.LayoutInflater; 
import android.view.View;   
import android.view.ViewGroup;  
  
public class WeiChatF extends Fragment  
{  
	View view;
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState)  
    {  
        view = inflater.inflate(R.layout.fragment_wei_chat, container, false);
        return view;  
    }  
}  