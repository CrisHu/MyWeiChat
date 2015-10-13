package com.example.myweichat;

import java.io.File; 
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException; 

import org.apache.http.util.EncodingUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context; 
public class WROperation {
	
	private static String MYPATH; 
	private static Context context; 
	private static File file;
	
	public WROperation(Context context) { 
		this.context = context; 
		MYPATH = this.context.getExternalFilesDir(null).getPath(); 
		file = new File(MYPATH + "//" + "config.dat");
	}
	
	public static String createorreadSDFile() throws IOException{
		String config = "";
		if (!file.exists()) 
		{ 
			file.createNewFile();
		}
		else
		{
			config = readSDFile(file.getName());
		}
		return config;
	}
	
	public static String readSDFile(String fileName) throws IOException {
		String res="";  
        FileInputStream fis = new FileInputStream(file);   
        int length = fis.available();   
        byte [] buffer = new byte[length];   
        fis.read(buffer);       
        res = EncodingUtils.getString(buffer, "UTF-8");   
        fis.close();       
        return res;    
	}    
	  
	public static void writeSDFile(String write_str) throws IOException{ 
        FileOutputStream fos = new FileOutputStream(file);    
        byte [] bytes = write_str.getBytes(); 
        fos.write(bytes);   
        fos.close();   
	}   
	
	protected static int Getthemestate(){
		int themestate = 100;
		try {
			String config = createorreadSDFile();
			if(!config.equals("")){
				JSONObject JSONconfig = new JSONObject(config);
				themestate = (int)JSONconfig.get("theme");
			}
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return themestate;
	}
} 
