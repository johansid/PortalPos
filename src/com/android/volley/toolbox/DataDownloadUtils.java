package com.android.volley.toolbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.util.Log;

public class DataDownloadUtils {
	
	private final String TAG = "DownloadFileUtils";
    private String SDPath;
    
    public DataDownloadUtils(){
        //得到当前外部存储设备的目录
        SDPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
        Log.d(TAG,"___NORMAL Sdcard dir :" + SDPath);
        
        SDPath = "/storage/sdcard1/";
        
        getSDPath2();
        disPlayBlockStatus();
    }
 
    public String getSDPath1(){    	
    	//return Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
    	return SDPath;
    }    
    
    public String getSDPath2(){    	
    	File sdDir = null; 
    	//判断sd卡是否存在
    	boolean sdCardExist = Environment.getExternalStorageState() 
    		.equals(android.os.Environment.MEDIA_MOUNTED); 
    	
    	if (sdCardExist){ 
    		//获取跟目录
    		sdDir = Environment.getExternalStorageDirectory(); 
    	} 
    	Log.d(TAG,"____TRUELY DIR :" + sdDir.toString());
    	return sdDir.toString();    	
    } 
    
    /**
     * 在SD卡上创建文件
     * @param fileName
     * @return
     */
    public File createSDFile(String fileName){
    	
        File file = new File(fileName);
        
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return file;
    }
    
    /**
     * 在SD卡上创建目录
     * @param dirName
     * @return
     */
    public File createSDDir(String dirName){
        File file = new File(SDPath+dirName);
        file.mkdir();
        return file;
    }
    
    /**
     * 判断SD卡上文件是否存在
     * @param fileName
     * @return
     */
    public boolean isFileExist(String fileName){
        File file = new File(SDPath+fileName);
        return file.exists();
    }
    /**
     * 将一个inputStream里面的数据写到SD卡中
     * @param path
     * @param fileName
     * @param inputStream
     * @return
*/
    public File writeToSDfromInput(String path,String fileName,InputStream inputStream){
        
        File file = createSDFile(path+fileName);
        OutputStream outStream = null;
        
        try {
            outStream = new FileOutputStream(file);
            
            byte[] buffer = new byte[4*1024];
            
            int hasRead = 0;
            while((hasRead = inputStream.read(buffer) ) > 0){
                outStream.write(buffer,0,hasRead);
            }
            
            outStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
            	if(outStream != null){
            		outStream.close();
            	}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
    
    private long disPlayBlockStatus(){
    	StatFs statfs=new StatFs(getSDPath1()); 
	    //获取block的SIZE 	    
	    long blockSize = statfs.getBlockSize(); 
	    Log.d(TAG,"BlockSize:" + blockSize);
	    //获取BLOCK数量 
	    long totalBlocks = statfs.getBlockCount(); 
	    Log.d(TAG,"totalBocks:" + totalBlocks);
	    //可用的Block的数量 
	    long availableBlock = statfs.getAvailableBlocks(); 
	    Log.d(TAG,"avilable bolck:" + availableBlock);
	    Log.d(TAG,"Size:" + blockSize * availableBlock / 1024 /1024);
	    return (long) blockSize * availableBlock;
    }
    
}