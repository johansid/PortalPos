package cn.burgeon.core.ui.system;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.android.volley.toolbox.DataDownloadUtils;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import cn.burgeon.core.App;
import cn.burgeon.core.R;
import cn.burgeon.core.ui.BaseActivity;
import cn.burgeon.core.utils.PreferenceUtils;

public class SystemDataDownloadActivity extends BaseActivity{
		
	private final String TAG = "SystemDataDownloadActivity";
	//data URLs
	private final String userDataURL = //"http://g.burgeon.cn:2080/portalpospda/DownloadFiles/sys_user.zip";
										"https://secure-appldnld.apple.com/iTunes11/031-3480.20140225.C1nB2/iTunes11.1.5.dmg";
										
																							  //iTunes for Mac
	private final String productDataURL = 
										"http://dldir1.qq.com/qqfile/QQforMac/QQ_V3.1.1.dmg"; //QQ for Mac
										
	
	private final String vipTypeURL = //"http://g.burgeon.cn:2080/portalpospda/DownloadFiles/tc_vip.zip";
										"http://dldir1.qq.com/qqfile/QQforMac/QQ_V3.1.1.dmg"; //QQ for Mac;
	
	
	private final String itemStrategyURL = //"http://g.burgeon.cn:2080/portalpospda/DownloadFiles/tc_stuff.zip";//这个地址不确定，草他妹
			
										"http://dldir1.qq.com/qqfile/QQforMac/QQ_V3.1.1.dmg"; //QQ for Mac
	
	private final String systemParamURL = //"http://g.burgeon.cn:2080/portalpospda/DownloadFiles/sys_parm.zip";
										"http://dldir1.qq.com/qqfile/QQforMac/QQ_V3.1.1.dmg"; //QQ for Mac
	
	//文件下载路径
	//目前仅使用外置sdcard下载
	private String downloadPath;
	//资料下载工具
	private DataDownloadUtils dataDownloadUtils;
	//下载的文件名字
	private final String userDataDownloadFileName = "userData.zip";
	private final String productDataDownloadFileName = "productData.zip";
	private final String vipTypeDownloadFileName = "vipType.zip";
	private final String itemStrategyDownloadFileName = "itemStrategy.zip";
	private final String systemParamDownloadFileName = "systemParam.zip";

	//下载结束消息
	private final int userDataDownloadFinishMsg = 0x1;
	private final int productDataDownloadFinishMsg = 0x2;
	private final int vipTypeDownloadFinishMsg = 0x3;
	private final int itemStrategyDownloadFinishMsg = 0x4;
	private final int systemParamDownloadFinishMsg = 0x5;
	
	//下载线程名字
	private final String USER_DATA_THREAD = "userDataThread";
	private final String PRODUCT_DATA_THREAD = "productDataThread";
	private final String VIP_TYPE_THREAD = "vipTypeThread";
	private final String ITEM_STRATEGY_THREAD = "itemStrategyThread";
	private final String SYSTEM_PARAM_THREAD = "systemParamThread";

	//线程下载标志
	private boolean userDataDownloading;
	private boolean productDataDownloading;
	private boolean vipTypeDownloading;
	private boolean itemStrategyDownloading;
	private boolean systemParamDownloading;
		
	private CheckBox mUserData;
	private CheckBox mProductData;
	private CheckBox mVipType;
	private CheckBox mItemStrategy;
	private CheckBox mSystemParam;
	private TextView statusStoreName;
	private TextView statusTime;
	
	private Button mDownloadButton;
	
	private boolean userDataChecked;
	private boolean productDataChecked;
	private boolean vipTypeChecked;
	private boolean itemStrategyChecked;
	private boolean systemParamChecked;
	
		
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setupFullscreen();
		setContentView(R.layout.activity_system_data_download);	
		
		initViews();
		initDataDownloadUtils();
	}
	
	private void initViews(){
		// 初始化门店信息
		statusStoreName = (TextView) findViewById(R.id.statusStoreName);
		statusTime = (TextView) findViewById(R.id.statusTime);
		initStoreNameAndTime();
		
		mUserData = (CheckBox) findViewById(R.id.userDataCheckBox);
		mProductData = (CheckBox) findViewById(R.id.productDataCheckBox);
		mVipType = (CheckBox) findViewById(R.id.vipTypeCheckBox);
		mItemStrategy = (CheckBox) findViewById(R.id.itemStrategyCheckBox);
		mSystemParam = (CheckBox) findViewById(R.id.systemParamCheckBox);
		
		mDownloadButton = (Button) findViewById(R.id.downloadButton);
		
		mDownloadButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v){ 
				checkToStartDownload();
			}
		});			
	}

	//初始化下载
	private void initDataDownloadUtils(){
		dataDownloadUtils = new DataDownloadUtils();
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
		
		if(mUserData.isChecked()){ 
			userDataChecked = true;
			Log.d(TAG,"userDataChecked" + userDataChecked);
		}
		
		if(mProductData.isChecked()){
			productDataChecked = true;
			Log.d(TAG,"productDataChecked" + productDataChecked);
		}
		
		if(mVipType.isChecked()){ 
			vipTypeChecked = true;
			Log.d(TAG,"vipTypeChecked" + vipTypeChecked);
		}
		
		if(mItemStrategy.isChecked()){
			itemStrategyChecked = true;
			Log.d(TAG,"itemStrategyChecked" + itemStrategyChecked);
		}
		
		if(mSystemParam.isChecked()){ 
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
	private boolean fileExist(final String fileName){
		return dataDownloadUtils.isFileExist(fileName);
	}
	
	//用户所有选择同时下载
	private void startDownload(){	
		
		//开始下载系统参数
		if(systemParamChecked && !fileExist(systemParamDownloadFileName) && !systemParamDownloading){ 
			//设置标志
			systemParamDownloading = true;
			Toast.makeText(this,R.string.tipsStartDownloadSystemParam, Toast.LENGTH_SHORT).show();
			setupProgress();
			//干活线程
			new Thread(new downloadFileRunnable(systemParamURL,dataDownloadUtils.getSDPath1(),systemParamDownloadFileName),
					SYSTEM_PARAM_THREAD)
					.start();
		}else if(systemParamChecked && fileExist(systemParamDownloadFileName) && !systemParamDownloading){
			showTips(R.string.tipsSystemParamDownloadFileExist);
		}else if(systemParamChecked && fileExist(systemParamDownloadFileName) && systemParamDownloading){
			showTips(R.string.tipsSystemParamDownloading);
		}
		
		//开始下载单品策略
		if(itemStrategyChecked && !fileExist(itemStrategyDownloadFileName) && !itemStrategyDownloading){ 
			//设置标志
			itemStrategyDownloading = true;
			Toast.makeText(this,R.string.tipsStartDownloadItemStrategy, Toast.LENGTH_SHORT).show();
			setupProgress();		
			//干活线程
			new Thread(new downloadFileRunnable(itemStrategyURL,dataDownloadUtils.getSDPath1(),itemStrategyDownloadFileName),
					ITEM_STRATEGY_THREAD)
					.start();			
		}else if(itemStrategyChecked && fileExist(itemStrategyDownloadFileName) && !itemStrategyDownloading){
			showTips(R.string.tipsItemStrategyDownloadFileExist);
		}else if(itemStrategyChecked && fileExist(itemStrategyDownloadFileName) && itemStrategyDownloading){
			showTips(R.string.tipsItemStrategyDownloading);
		}
		
		//开始下载会员类型
		if(vipTypeChecked && !fileExist(vipTypeDownloadFileName) && !vipTypeDownloading){ 
			//设置标志
			vipTypeDownloading = true;
			Toast.makeText(this,R.string.tipsStartDownloadVipType, Toast.LENGTH_SHORT).show();
			setupProgress();
			//干活线程
			new Thread(new downloadFileRunnable(vipTypeURL,dataDownloadUtils.getSDPath1(),vipTypeDownloadFileName),
					VIP_TYPE_THREAD)
					.start();
		}else if(vipTypeChecked && fileExist(vipTypeDownloadFileName) && !vipTypeDownloading){
			showTips(R.string.tipsVipTypeDownloadFileExist);
		}else if(vipTypeChecked && fileExist(vipTypeDownloadFileName) && vipTypeDownloading){
			showTips(R.string.tipsVipTypeDownloading);
		}
		
		//开始下载商品资料
		if(productDataChecked && !fileExist(productDataDownloadFileName) && !productDataDownloading){ 
			//设置标志
			productDataDownloading = true;
			Toast.makeText(this,R.string.tipsStartDownloadProductData, Toast.LENGTH_SHORT).show();
			setupProgress();
			//干活线程
			new Thread(new downloadFileRunnable(productDataURL,dataDownloadUtils.getSDPath1(),productDataDownloadFileName),
					PRODUCT_DATA_THREAD)
					.start();
		}else if(productDataChecked && fileExist(productDataDownloadFileName) && !productDataDownloading){
			showTips(R.string.tipsProductDataDownloadFileExist);
		}else if(productDataChecked && fileExist(productDataDownloadFileName) && productDataDownloading){
			showTips(R.string.tipsProductDataDownloading);
		}
		
		//开始下载用户资料
		if(userDataChecked && !fileExist(userDataDownloadFileName) && !userDataDownloading){ 
			//设置标志
			userDataDownloading = true;
			Toast.makeText(this, R.string.tipsStartDownloadUserData, Toast.LENGTH_SHORT).show();
			setupProgress();
			//干活线程
			new Thread(new downloadFileRunnable(userDataURL,dataDownloadUtils.getSDPath1(),userDataDownloadFileName),
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
		private Message msg = new Message();
		
		downloadFileRunnable(String url,String savePath,String saveName){
			downloadFileRunnable.this.url = url;
			downloadFileRunnable.this.savePath = savePath;
			downloadFileRunnable.this.saveName = saveName;
		}
		
		@Override
		public void run() {
			//真正干活的
			downLoadFile(url,savePath,saveName);
			if(Thread.currentThread().getName().equals(USER_DATA_THREAD)){ 
				Log.d(TAG,USER_DATA_THREAD + "OVER");
				//发送下载完毕消息
				msg.what = userDataDownloadFinishMsg;
				showToastHandler.sendMessage(msg);
				//设置下载完毕标志
				userDataDownloading = false;
			}
			if(Thread.currentThread().getName().equals(PRODUCT_DATA_THREAD)){ 
				Log.d(TAG,PRODUCT_DATA_THREAD + "OVER");
				//发送下载完毕消息
				msg.what = productDataDownloadFinishMsg;
				showToastHandler.sendMessage(msg);
				//设置下载完毕标志
				productDataDownloading = false;
			}
			if(Thread.currentThread().getName().equals(VIP_TYPE_THREAD)){
				Log.d(TAG,VIP_TYPE_THREAD + "OVER");
				//发送下载完毕消息
				msg.what = vipTypeDownloadFinishMsg;
				showToastHandler.sendMessage(msg);
				//设置下载完毕标志				
				vipTypeDownloading = false;
			}
			if(Thread.currentThread().getName().equals(ITEM_STRATEGY_THREAD)) {
				Log.d(TAG,ITEM_STRATEGY_THREAD + "OVER");
				//发送下载完毕消息
				msg.what = itemStrategyDownloadFinishMsg;
				showToastHandler.sendMessage(msg);
				//设置下载完毕标志
				itemStrategyDownloading = false;
			}
			if(Thread.currentThread().getName().equals(SYSTEM_PARAM_THREAD)) {
				Log.d(TAG,SYSTEM_PARAM_THREAD + "OVER");
				//发送下载完毕消息
				msg.what = systemParamDownloadFinishMsg;
				showToastHandler.sendMessage(msg);
				//设置下载完毕标志
				systemParamDownloading = false;			
			}
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

	private void setupProgress(){
		//即将添加，敬请期待！
	}
	
	public void downLoadFile(String url,String savePath,String saveName){
	     InputStream inputStream = null;
	     DataDownloadUtils fileUtils = new DataDownloadUtils();
	     
	     if(fileUtils.isFileExist(saveName)){
	    	 processExistFiles();
	     }else{
	         inputStream = getInputStreamFormUrl(url);
	         File resultFile = fileUtils.writeToSDfromInput(savePath, saveName, inputStream);
	         if(resultFile == null){
	             return ;
	         }
	     }
	     return ;
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

	//显示下载完毕提示
	Handler showToastHandler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			
			if(msg.what == userDataDownloadFinishMsg){
				Toast.makeText(SystemDataDownloadActivity.this, R.string.tipsUserDataDownloadFinish, Toast.LENGTH_SHORT).show();
			}
			if(msg.what == productDataDownloadFinishMsg){
				Toast.makeText(SystemDataDownloadActivity.this, R.string.tipsProductDataDownloadFinish, Toast.LENGTH_SHORT).show();
			}
			if(msg.what == vipTypeDownloadFinishMsg){
				Toast.makeText(SystemDataDownloadActivity.this, R.string.tipsVipTypeDownloadFinish, Toast.LENGTH_SHORT).show();
			}
			if(msg.what == itemStrategyDownloadFinishMsg){
				Toast.makeText(SystemDataDownloadActivity.this, R.string.tipsItemStrategyDownloadFinish, Toast.LENGTH_SHORT).show();
			}
			if(msg.what == systemParamDownloadFinishMsg){
				Toast.makeText(SystemDataDownloadActivity.this, R.string.tipsSystemParamDownloadFinish, Toast.LENGTH_SHORT).show();
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
		this.finish();
	}
}

	