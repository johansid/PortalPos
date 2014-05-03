package cn.burgeon.core.ui.system;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.burgeon.core.App;
import cn.burgeon.core.R;
import cn.burgeon.core.ui.BaseActivity;
import cn.burgeon.core.utils.PreferenceUtils;

public class SystemDataDownloadActivity extends BaseActivity{
		
	private final String TAG = "SystemDataDownloadActivity";
	
	//data URLs
	private final String userDataURL =       //"http://g.burgeon.cn:2080/portalpospda/DownloadFiles/sys_user.zip";
										     "http://music.baidu.com/data/music/file?link=http://zhangmenshiting.baidu.com/data2/music/64022204/73383361399042861128.mp3?xcode=879a034135f4e9e6c643f4dc0c2a2588ec0497093d29d6f3&song_id=7338336";																						  
	private final String productDataURL =    "http://music.baidu.com/data/music/file?link=http://zhangmenshiting.baidu.com/data2/music/64022204/73383361399042861128.mp3?xcode=879a034135f4e9e6c643f4dc0c2a2588ec0497093d29d6f3&song_id=7338336"; 	
	private final String vipTypeURL =        //"http://g.burgeon.cn:2080/portalpospda/DownloadFiles/tc_vip.zip";
										     "http://music.baidu.com/data/music/file?link=http://zhangmenshiting.baidu.com/data2/music/64022204/73383361399042861128.mp3?xcode=879a034135f4e9e6c643f4dc0c2a2588ec0497093d29d6f3&song_id=7338336";
	private final String itemStrategyURL =   //"http://g.burgeon.cn:2080/portalpospda/DownloadFiles/tc_stuff.zip";			
										     "http://music.baidu.com/data/music/file?link=http://zhangmenshiting.baidu.com/data2/music/64022204/73383361399042861128.mp3?xcode=879a034135f4e9e6c643f4dc0c2a2588ec0497093d29d6f3&song_id=7338336"; 	
	private final String systemParamURL =    //"http://g.burgeon.cn:2080/portalpospda/DownloadFiles/sys_parm.zip";
										     "http://music.baidu.com/data/music/file?link=http://zhangmenshiting.baidu.com/data2/music/64022204/73383361399042861128.mp3?xcode=879a034135f4e9e6c643f4dc0c2a2588ec0497093d29d6f3&song_id=7338336"; 	
	//文件下载路径
	//目前仅使用外置sdcard下载
	private String downloadPath;

	//保存的文件名字
	private final String userDataDownloadFileName = "guang1.mp3";
													//"userData.zip";
	private final String productDataDownloadFileName = "guang2.mp3";
													//"productData.zip";
	private final String vipTypeDownloadFileName = "guang3.mp3";
													//"vipType.zip";
	private final String itemStrategyDownloadFileName = "guang4.mp3";
													//"itemStrategy.zip";
	private final String systemParamDownloadFileName = "guang5.mp3"; 
													//"systemParam.zip";

	//开始下载消息
	private final int userDataDownloadStartMsg = 0x1;
	private final int productDataDownloadStartMsg = 0x2;
	private final int vipTypeDownloadStartMsg = 0x3;
	private final int itemStrategyDownloadStartMsg = 0x4;
	private final int systemParamDownloadStartMsg = 0x5;	

	//下载进度消息
	private final int userDataDownloadProgressMsg = 0x10;
	private final int productDataDownloadProgressMsg = 0x11;
	private final int vipTypeDownloadProgressMsg = 0x12;
	private final int itemStrategyDownloadProgressMsg = 0x13;
	private final int systemParamDownloadProgressMsg = 0x14;
	
	//下载结束消息
	private final int userDataDownloadFinishMsg = 0x20;
	private final int productDataDownloadFinishMsg = 0x21;
	private final int vipTypeDownloadFinishMsg = 0x22;
	private final int itemStrategyDownloadFinishMsg = 0x23;
	private final int systemParamDownloadFinishMsg = 0x24;
	
	//下载线程名字
	private final String USER_DATA_THREAD = "userDataThread";
	private final String PRODUCT_DATA_THREAD = "productDataThread";
	private final String VIP_TYPE_THREAD = "vipTypeThread";
	private final String ITEM_STRATEGY_THREAD = "itemStrategyThread";
	private final String SYSTEM_PARAM_THREAD = "systemParamThread";

	//线程正在下载标志
	private boolean userDataDownloading;
	private boolean productDataDownloading;
	private boolean vipTypeDownloading;
	private boolean itemStrategyDownloading;
	private boolean systemParamDownloading;

	//下载进度条
	private ProgressBar userDataProgressBar;
	private ProgressBar productDataProgressBar;
	private ProgressBar vipTypeProgressBar;
	private ProgressBar itemStrategyProgressBar;
	private ProgressBar systemParamProgressBar;
	
	//下载百分比
	private TextView userDataPercentTextView;
	private TextView productDataPercentTextView;
	private TextView vipTypePercentTextView;
	private TextView itemStrategyPercentTextView;
	private TextView systemParamPercentTextView;
	
	//用户选择框
	private CheckBox userDataCheckBox;
	private CheckBox productDataCheckBox;
	private CheckBox vipTypeCheckBox;
	private CheckBox itemStrategyCheckBox;
	private CheckBox systemParamCheckBox;
	private TextView statusStoreName;
	private TextView statusTime;

	//用户选择项记录标志
	private boolean userDataChecked;
	private boolean productDataChecked;
	private boolean vipTypeChecked;
	private boolean itemStrategyChecked;
	private boolean systemParamChecked;
	
	//下载按钮
	private Button mDownloadButton;
		
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setupFullscreen();
		setContentView(R.layout.activity_system_data_download);	
		
		initViews();
		initDataDownload();
	}
	
	private void initViews(){
		// 初始化门店信息
		statusStoreName = (TextView) findViewById(R.id.statusStoreName);
		statusTime = (TextView) findViewById(R.id.statusTime);
		initStoreNameAndTime();
		
		userDataCheckBox = (CheckBox) findViewById(R.id.userDataCheckBox);
		productDataCheckBox = (CheckBox) findViewById(R.id.productDataCheckBox);
		vipTypeCheckBox = (CheckBox) findViewById(R.id.vipTypeCheckBox);
		itemStrategyCheckBox = (CheckBox) findViewById(R.id.itemStrategyCheckBox);
		systemParamCheckBox = (CheckBox) findViewById(R.id.systemParamCheckBox);

		userDataProgressBar = (ProgressBar) findViewById(R.id.userDataProgressBar);
		productDataProgressBar = (ProgressBar) findViewById(R.id.productDataProgressBar);
		vipTypeProgressBar = (ProgressBar) findViewById(R.id.vipTypeProgressBar);
		itemStrategyProgressBar = (ProgressBar) findViewById(R.id.itemStrategyProgressBar);
		systemParamProgressBar = (ProgressBar) findViewById(R.id.systemParamProgressBar);
		
		userDataPercentTextView = (TextView) findViewById(R.id.userDataPercentTextView);
		productDataPercentTextView = (TextView) findViewById(R.id.productDataPercentTextView);
		vipTypePercentTextView = (TextView) findViewById(R.id.vipTypePercentTextView);
		itemStrategyPercentTextView = (TextView) findViewById(R.id.itemStrategyPercentTextView);
		systemParamPercentTextView = (TextView) findViewById(R.id.systemParamPercentTextView);
		
		mDownloadButton = (Button) findViewById(R.id.downloadButton);
		
		mDownloadButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v){ 
				checkToStartDownload();
			}
		});			
	}

	//初始化下载
	private void initDataDownload(){
		downloadPath = "/storage/sdcard1/";
	}
	
    // 初始化门店信息
	private void initStoreNameAndTime(){
		statusStoreName.setText(App.getPreferenceUtils().getPreferenceStr(PreferenceUtils.store_key));
		statusTime.setText(getCurrDate());				
	}
	
	private void checkToStartDownload(){
		
		//检测用户是否有选择至少一个下载项
		if( !userMadeAChoice() ){
			showTips(R.string.tipsCanYouChooseOne$_$);
			return;
		}
			
		//检查网络连接状态
		if( !networkReachable() ){
			showTips(R.string.tipsNetworkUnReachable$_$);
			return;
		}
		
		//检测电池电量
		if( !batteryEnough() ){
			showTips(R.string.tipsBatteryNotEnough$_$);
			return;
		}
		
		//访问服务器并等待服务器响应
		if( !serverReachable() ){
			showTips(R.string.tipsServerUnReachable$_$);
			return;
		}

		//检测存储器剩余存储空间
		if( !memoryEnough() ){
			showTips(R.string.tipsMemoryNotEnough$_$);
			return;
		}
		
		//创建下载线程
		startDownload();
		
		//检测下载文件是否完整
		if( !checkDownloadFiles()){
			showTips(R.string.tipsPleaseDownloadAgain$_$);
			return;
		}
		
		//解压下载文件
		unZipDownloadFiles();
		
		//解析下载内容
		parseDownloadFiles();
		
		//将下载文件存入数据库
		saveDownloadFilesToSqlite();
			

	}
	
	//检测用户是否有选择至少一个下载项
	private boolean userMadeAChoice(){
		//init flag status every Download
		userDataChecked = false;
		productDataChecked = false;
		vipTypeChecked = false;
		itemStrategyChecked = false;
		systemParamChecked = false;		
		
		if(userDataCheckBox.isChecked()){ 
			userDataChecked = true;
			Log.d(TAG,"userDataChecked" + userDataChecked);
		}
		
		if(productDataCheckBox.isChecked()){
			productDataChecked = true;
			Log.d(TAG,"productDataChecked" + productDataChecked);
		}
		
		if(vipTypeCheckBox.isChecked()){ 
			vipTypeChecked = true;
			Log.d(TAG,"vipTypeChecked" + vipTypeChecked);
		}
		
		if(itemStrategyCheckBox.isChecked()){
			itemStrategyChecked = true;
			Log.d(TAG,"itemStrategyChecked" + itemStrategyChecked);
		}
		
		if(systemParamCheckBox.isChecked()){ 
			systemParamChecked = true;
			Log.d(TAG,"systemParamChecked" + systemParamChecked);
		}
		
		return (userDataChecked || productDataChecked || vipTypeChecked
					||itemStrategyChecked || systemParamChecked);
	}
	
	//检测网络状态
	private boolean networkReachable(){
		//即将添加，敬请期待！
		return true;		
	}
	
	//检测电池电量
	private boolean batteryEnough(){
		//即将添加，敬请期待！
		return true;		
	}
	
	//检测服务器状态
	private boolean serverReachable(){
		//即将添加，敬请期待！
		return true;
	}
	
	//获取即将下载的文件大小
	private void checkTheSizeOfDownloadData(){
		//即将添加，敬请期待！
				
	}
	
	//检测设备存储空间大小
	private boolean memoryEnough(){
		//计算应下载数据包的大小
		checkTheSizeOfDownloadData();
		return true;
	}
	
	//检测文件是否存在
    public boolean fileExist(String fileName){
        File file = new File(downloadPath+fileName);
        return file.exists();
    }
	
	//用户所有选择同时下载
	private void startDownload(){	
		
		//开始下载系统参数
		if(systemParamChecked && !fileExist(systemParamDownloadFileName) && !systemParamDownloading){ 
			//干活线程
			new Thread(new downloadFileRunnable(systemParamURL,getSDPath1(),systemParamDownloadFileName),
					SYSTEM_PARAM_THREAD)
					.start();
		}else if(systemParamChecked && fileExist(systemParamDownloadFileName) && !systemParamDownloading){
			showTips(R.string.tipsSystemParamDownloadFileExist);
		}else if(systemParamChecked && fileExist(systemParamDownloadFileName) && systemParamDownloading){
			showTips(R.string.tipsSystemParamDownloading);
		}
		
		//开始下载单品策略
		if(itemStrategyChecked && !fileExist(itemStrategyDownloadFileName) && !itemStrategyDownloading){ 
			//干活线程
			new Thread(new downloadFileRunnable(itemStrategyURL,getSDPath1(),itemStrategyDownloadFileName),
					ITEM_STRATEGY_THREAD)
					.start();			
		}else if(itemStrategyChecked && fileExist(itemStrategyDownloadFileName) && !itemStrategyDownloading){
			showTips(R.string.tipsItemStrategyDownloadFileExist);
		}else if(itemStrategyChecked && fileExist(itemStrategyDownloadFileName) && itemStrategyDownloading){
			showTips(R.string.tipsItemStrategyDownloading);
		}
		
		//开始下载会员类型
		if(vipTypeChecked && !fileExist(vipTypeDownloadFileName) && !vipTypeDownloading){ 
			//干活线程
			new Thread(new downloadFileRunnable(vipTypeURL,getSDPath1(),vipTypeDownloadFileName),
					VIP_TYPE_THREAD)
					.start();
		}else if(vipTypeChecked && fileExist(vipTypeDownloadFileName) && !vipTypeDownloading){
			showTips(R.string.tipsVipTypeDownloadFileExist);
		}else if(vipTypeChecked && fileExist(vipTypeDownloadFileName) && vipTypeDownloading){
			showTips(R.string.tipsVipTypeDownloading);
		}
		
		//开始下载商品资料
		if(productDataChecked && !fileExist(productDataDownloadFileName) && !productDataDownloading){ 
			//干活线程
			new Thread(new downloadFileRunnable(productDataURL,getSDPath1(),productDataDownloadFileName),
					PRODUCT_DATA_THREAD)
					.start();
		}else if(productDataChecked && fileExist(productDataDownloadFileName) && !productDataDownloading){
			showTips(R.string.tipsProductDataDownloadFileExist);
		}else if(productDataChecked && fileExist(productDataDownloadFileName) && productDataDownloading){
			showTips(R.string.tipsProductDataDownloading);
		}
		
		//开始下载用户资料
		if(userDataChecked && !fileExist(userDataDownloadFileName) && !userDataDownloading){ 
			//干活线程
			new Thread(new downloadFileRunnable(userDataURL,getSDPath1(),userDataDownloadFileName),
					USER_DATA_THREAD)
					.start();
		}else if(userDataChecked && fileExist(userDataDownloadFileName) && !userDataDownloading){
			showTips(R.string.tipsUserDataDownloadFileExist);
		}else if(userDataChecked && fileExist(userDataDownloadFileName) && userDataDownloading){
			showTips(R.string.tipsUserDataDownloading);
		}
	}
	
	//DownLoad File Runnable
	class downloadFileRunnable implements Runnable{
		private String url;
		private String savePath;
		private String saveName;
				
		downloadFileRunnable(String url,String savePath,String saveName){
			downloadFileRunnable.this.url = url;
			downloadFileRunnable.this.savePath = savePath;
			downloadFileRunnable.this.saveName = saveName;
		}
		
		@Override
		public void run() {
			//真正干活的
			downLoadFile(url,savePath,saveName);
		}
	}
	
	private boolean checkDownloadFiles(){
		return true;		
	}
	
	private void unZipDownloadFiles(){
		//即将添加，敬请期待！
	}

	private void parseDownloadFiles(){
		//即将添加，敬请期待！
	}
	
	private void saveDownloadFilesToSqlite(){
		//即将添加，敬请期待！
	}
	
	public void downLoadFile(String urlstr,String savePath,String saveName){
	     InputStream inputStream = null;
	     int fileSize = 0;
	     
	     if(fileExist(saveName)){
	    	 processExistFiles();
	     }else{
	    	 
	 		 try {
				URL url=new URL(urlstr);
				HttpURLConnection urlConn=(HttpURLConnection) url.openConnection();
				inputStream=urlConn.getInputStream();
				fileSize = urlConn.getContentLength();
				Log.d(TAG,"__FILESIZE__" + fileSize);
			 } catch (MalformedURLException e) {
				e.printStackTrace();
				showTips(R.string.tipsPleaseDownloadAgain$_$);
			 } catch (IOException e) {
				e.printStackTrace();
				showTips(R.string.tipsPleaseDownloadAgain$_$);
			 }
	    	 
	         File resultFile = writeToSDfromInput(savePath, saveName, inputStream,fileSize);
	         if(resultFile == null){
	             return ;
	         }
	     }
	}

	//发送下载开始消息
	private void sendDownloadStartMsg(){
		//创建消息
        Message msg = new Message();
        
		if(Thread.currentThread().getName().equals(USER_DATA_THREAD)){ 
			Log.d(TAG,USER_DATA_THREAD + "Start");
			msg.what = userDataDownloadStartMsg;
			//设置下载开始标志
			userDataDownloading = true;
		}
		if(Thread.currentThread().getName().equals(PRODUCT_DATA_THREAD)){ 
			Log.d(TAG,PRODUCT_DATA_THREAD + "Start");
			msg.what = productDataDownloadStartMsg;
			//设置下载开始标志
			productDataDownloading = true;
		}
		if(Thread.currentThread().getName().equals(VIP_TYPE_THREAD)){
			Log.d(TAG,VIP_TYPE_THREAD + "Start");
			msg.what = vipTypeDownloadStartMsg;
			//设置下载开始标志				
			vipTypeDownloading = true;
		}
		if(Thread.currentThread().getName().equals(ITEM_STRATEGY_THREAD)) {
			Log.d(TAG,ITEM_STRATEGY_THREAD + "Start");
			msg.what = itemStrategyDownloadStartMsg;
			//设置下载开始标志
			itemStrategyDownloading = true;
		}
		if(Thread.currentThread().getName().equals(SYSTEM_PARAM_THREAD)) {
			Log.d(TAG,SYSTEM_PARAM_THREAD + "Start");
			msg.what = systemParamDownloadStartMsg;
			//设置下载开始标志
			systemParamDownloading = true;			
		}
		//发送！
		updateTipsHandler.sendMessage(msg);
	}

	//发送下载进度消息
	private void sendDownloadProgressMsg(final Bundle data){
		//创建消息
		Message msg = new Message();
		//填充消息数据
		msg.setData(data);
		
		if(Thread.currentThread().getName().equals(USER_DATA_THREAD)){ 
			Log.d(TAG,USER_DATA_THREAD + "Progress");
			msg.what = userDataDownloadProgressMsg;
		}
		if(Thread.currentThread().getName().equals(PRODUCT_DATA_THREAD)){ 
			Log.d(TAG,PRODUCT_DATA_THREAD + "Progress");
			msg.what = productDataDownloadProgressMsg;
		}
		if(Thread.currentThread().getName().equals(VIP_TYPE_THREAD)){
			Log.d(TAG,VIP_TYPE_THREAD + "Progress");
			msg.what = vipTypeDownloadProgressMsg;
		}
		if(Thread.currentThread().getName().equals(ITEM_STRATEGY_THREAD)) {
			Log.d(TAG,ITEM_STRATEGY_THREAD + "Progress");
			msg.what = itemStrategyDownloadProgressMsg;
		}
		if(Thread.currentThread().getName().equals(SYSTEM_PARAM_THREAD)) {
			Log.d(TAG,SYSTEM_PARAM_THREAD + "Progress");
			msg.what = systemParamDownloadProgressMsg;		
		}
		//OK baby，we send Message now
		updateTipsHandler.sendMessage(msg);
	}
	
	//发送下载结束消息
	private void sendDownloadFinishMsg(){
		//创建消息
		Message msg = new Message();
		
		if(Thread.currentThread().getName().equals(USER_DATA_THREAD)){ 
			Log.d(TAG,USER_DATA_THREAD + "OVER");
			msg.what = userDataDownloadFinishMsg;
			//设置下载完毕标志
			userDataDownloading = false;
		}
		if(Thread.currentThread().getName().equals(PRODUCT_DATA_THREAD)){ 
			Log.d(TAG,PRODUCT_DATA_THREAD + "OVER");
			msg.what = productDataDownloadFinishMsg;
			//设置下载完毕标志
			productDataDownloading = false;
		}
		if(Thread.currentThread().getName().equals(VIP_TYPE_THREAD)){
			Log.d(TAG,VIP_TYPE_THREAD + "OVER");
			msg.what = vipTypeDownloadFinishMsg;
			//设置下载完毕标志				
			vipTypeDownloading = false;
		}
		if(Thread.currentThread().getName().equals(ITEM_STRATEGY_THREAD)) {
			Log.d(TAG,ITEM_STRATEGY_THREAD + "OVER");
			msg.what = itemStrategyDownloadFinishMsg;
			//设置下载完毕标志
			itemStrategyDownloading = false;
		}
		if(Thread.currentThread().getName().equals(SYSTEM_PARAM_THREAD)) {
			Log.d(TAG,SYSTEM_PARAM_THREAD + "OVER");
			msg.what = systemParamDownloadFinishMsg;
			//设置下载完毕标志
			systemParamDownloading = false;			
		}
		//OK，BABY，We send Message Now
		updateTipsHandler.sendMessage(msg);
	}
	
	//根据网址得到输入流
	public InputStream getInputStreamFormUrl(String urlstr){
		
		InputStream inputStream = null;
		
		try {
			URL url=new URL(urlstr);
			HttpURLConnection urlConn=(HttpURLConnection) url.openConnection();
			inputStream=urlConn.getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			showTips(R.string.tipsPleaseDownloadAgain$_$);
		} catch (IOException e) {
			e.printStackTrace();
			showTips(R.string.tipsPleaseDownloadAgain$_$);
		}
		
		return inputStream;
    }
	
	private void processExistFiles(){
		//即将添加，敬请期待！
	}

    public String getSDPath1(){    	
    	//return Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
    	return downloadPath;
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
    
    //在SD卡上创建文件
    public File createSDFile(String fileName){
    	
        File file = new File(fileName);
        
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return file;
    }
    
    //在SD卡上创建目录
    public File createSDDir(String dirName){
        File file = new File(downloadPath+dirName);
        file.mkdir();
        return file;
    }
    

    //将一个inputStream里面的数据写到SD卡中
    public File writeToSDfromInput(String path,String fileName,InputStream inputStream,long fileSize){
        
        File file = createSDFile(path+fileName);
        OutputStream outStream = null;
        //携带数据
        Bundle data = new Bundle();
        
        try {
        	//获取输出文件流
            outStream = new FileOutputStream(file);          
            //缓冲大小
            byte[] buffer = new byte[4*1024];
            
            //每次读取大小
            int hasRead = 0;
            //累计读取大小
            long totalRead = 0;
            //将下载内容写入到文件中
            
            //*****发送下载开始消息*****//
            sendDownloadStartMsg();
            
            //循环读取网络内容
            while((hasRead = inputStream.read(buffer) ) > 0){
            	totalRead += hasRead;
            	
            	Log.d(TAG,"____Downloaded____:" + (int)(((float)totalRead / (float)fileSize) * 100) + " %");
            	//填充发送数据
            	data.putInt("progress", (int)(((float)totalRead / (float)fileSize) * 100));
            	
            	//******发送进度条更新消息*****//
            	sendDownloadProgressMsg(data);
            	
            	//写文件
                outStream.write(buffer,0,hasRead);
            }
  
            //******发送下载结束信息******//
            sendDownloadFinishMsg();
            
            outStream.flush();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
        	//关闭输出流
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
 
	
	//显示各种提示，更新进度条
	Handler updateTipsHandler = new Handler(){				
		@Override
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			switch(msg.what){
			
				//显示下载开始消息
				case userDataDownloadStartMsg:
					userDataProgressBar.setVisibility(View.VISIBLE);
					Toast.makeText(SystemDataDownloadActivity.this, R.string.tipsStartDownloadUserData, Toast.LENGTH_SHORT).show();
					break;
				case productDataDownloadStartMsg:
					productDataProgressBar.setVisibility(View.VISIBLE);
					Toast.makeText(SystemDataDownloadActivity.this, R.string.tipsStartDownloadProductData, Toast.LENGTH_SHORT).show();
					break;
				case vipTypeDownloadStartMsg:
					vipTypeProgressBar.setVisibility(View.VISIBLE);
					Toast.makeText(SystemDataDownloadActivity.this, R.string.tipsStartDownloadVipType, Toast.LENGTH_SHORT).show();
					break;
				case itemStrategyDownloadStartMsg:
					itemStrategyProgressBar.setVisibility(View.VISIBLE);
					Toast.makeText(SystemDataDownloadActivity.this, R.string.tipsStartDownloadItemStrategy, Toast.LENGTH_SHORT).show();
					break;
				case systemParamDownloadStartMsg:
					systemParamProgressBar.setVisibility(View.VISIBLE);
					Toast.makeText(SystemDataDownloadActivity.this, R.string.tipsStartDownloadSystemParam, Toast.LENGTH_SHORT).show();
					break;
					
				//显示下载完成消息
				case userDataDownloadFinishMsg:
					Toast.makeText(SystemDataDownloadActivity.this, R.string.tipsUserDataDownloadFinish, Toast.LENGTH_SHORT).show();
					break;
				case productDataDownloadFinishMsg:
					Toast.makeText(SystemDataDownloadActivity.this, R.string.tipsProductDataDownloadFinish, Toast.LENGTH_SHORT).show();
					break;
				case vipTypeDownloadFinishMsg:
					Toast.makeText(SystemDataDownloadActivity.this, R.string.tipsVipTypeDownloadFinish, Toast.LENGTH_SHORT).show();
					break;
				case itemStrategyDownloadFinishMsg:
					Toast.makeText(SystemDataDownloadActivity.this, R.string.tipsItemStrategyDownloadFinish, Toast.LENGTH_SHORT).show();
					break;
				case systemParamDownloadFinishMsg:
					Toast.makeText(SystemDataDownloadActivity.this, R.string.tipsSystemParamDownloadFinish, Toast.LENGTH_SHORT).show();
					break;
					
				//设置下载进度	
				//我们有五个线程同时访问，所以Bundle数据必须独立			
				case userDataDownloadProgressMsg:{
					Bundle data = msg.getData();
					int progress = 0;
					if(data != null){
						progress = data.getInt("progress");
					}
					userDataProgressBar.setProgress(progress);
					userDataPercentTextView.setText(progress + "%");
					break;
				}
				case productDataDownloadProgressMsg:{
					Bundle data = msg.getData();
					int progress = 0;
					if(data != null){
						progress = data.getInt("progress");
					}
					productDataProgressBar.setProgress(progress);
					productDataPercentTextView.setText(progress + "%");
					break;
				}
				case vipTypeDownloadProgressMsg:{
					Bundle data = msg.getData();
					int progress = 0;
					if(data != null){
						progress = data.getInt("progress");
					}
					vipTypeProgressBar.setProgress(progress);
					vipTypePercentTextView.setText(progress + "%");
					break;
				}
				case itemStrategyDownloadProgressMsg:{
					Bundle data = msg.getData();
					int progress = 0;
					if(data != null){
						progress = data.getInt("progress");
					}
					itemStrategyProgressBar.setProgress(progress);
					itemStrategyPercentTextView.setText(progress + "%");
					break;
				}
				case systemParamDownloadProgressMsg:{
					Bundle data = msg.getData();
					int progress = 0;
					if(data != null){
						progress = data.getInt("progress");
					}
					systemParamProgressBar.setProgress(progress);
					systemParamPercentTextView.setText(progress + "%");
					break;	
				}

			}
			
		}
		
	};
	
    //显示对话框
    private void showTips(int whichTips){
    	LayoutInflater inflater = getLayoutInflater();
    	//布局文件待添加！！！！！！！！！！
    	View tipsLayout = inflater.inflate(R.layout.inventory_refresh_tips, 
    			(ViewGroup)findViewById(R.id.inventoryRefreshTipsLayout));
    	TextView tipsText = (TextView) tipsLayout.findViewById(R.id.inventoryRefreshingTipsText);
    	tipsText.setText(whichTips);
    	
    	new AlertDialog.Builder(this)
    		.setTitle(getString(R.string.tipsDataDownload))
    		.setView(tipsLayout)
    		.setPositiveButton(getString(R.string.confirm),null)
    		.show();
    	
    }
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}

	