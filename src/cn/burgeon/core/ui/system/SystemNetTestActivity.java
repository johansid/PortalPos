
														package cn.burgeon.core.ui.system;
														import java.io.IOException; import
														java.io.InputStream ; import java.
														net.HttpURLConnection;import java.
														net.MalformedURLException;  import
				java.net.URL;import java.util.Random; import org.apache.http.HttpResponse; import org.apache.http.client.HttpClient; 
				   import org.apache.http.client.methods.HttpGet;import org.apache.http.impl.client.DefaultHttpClient;import org   
				   	 .apache.http.params.BasicHttpParams; import org.apache.http.params.HttpConnectionParams;import android.      
				         app.AlertDialog;import android.net.ConnectivityManager ;import android.net.NetworkInfo ; import   
				            android.os.Bundle;import android.os.Handler;import android.os.Message;import android.text 
				               .TextUtils ; import android.util.Log ; import android.view.LayoutInflater ; import 
				                  android.view.View ; import  cn.burgeon.core.App ; import  android.view.View.
				                     OnClickListener;import android.view.ViewGroup;import android.widget.
				                        Button ;  import  android.widget.ScrollView ; import android.   
				                           widget.TextView; import cn.burgeon.core.R ; import cn.
				                              burgeon.core.ui.BaseActivity;import cn.burgeon
				                                         .core.utils.PreferenceUtils;



	
                               ;;;;;;;;;;;
	        ;;;;;;;;;;;;;;;;  ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	     ;;;;;;;;;;;;;;;;   ;public class SystemNetTestActivity extends BaseActivity{;;;
	  ;;;;;;;;;;;;;;;;   ;;;private final static String TAG = "SystemNetTestActivity";
	;;;;;;;;;;;;;;;;  ;;;;;;;;;;;              ;;;;;;;;;;;;              ;;;;;;;;;;;    
	   ;;;;;;;;;;;                              ;;;;;;;;;;;;             ;;;;;;;;;
	   ;;;;;;;;;;;                               ;;;;;;;;;;;;            
	   ;;;;;;;;;;;                                ;;;;;;;;;;;;
	   ;;;;;;;;;;;              ;;;;;;;;           ;;;;;;;;;;;;      ;;;;;;;;;
	   ;;;;;;;;;;;             ;;;;;;;;            ;;;;;;;;;;;;       ;;;;;;;;;
	   ;;;;;;;;;;;            ;;;;;;;;             ;;;;;;;;;;;;        ;;;;;;;;;
	   ;;;;;;;;;;;           ;;;;;;;;              ;;;;;;;;;;;;         ;;;;;;;;;
	   ;;;;;;;;;;;          ;;;;;;;;              ;;;;;;;;;;;;          ;;;;;;;;;
	   ;;;;;;;;;;;                               ;;;;;;;;;;;;           ;;;;;;;;;
	   ;;;;;;;;;;;                             ;;;;;;;;;;;;           
	   ;;;;;;;;;;;                 ;;;;;;;;;;;;;;;;;;;;;;;            
	   ;;;;;;;;;;;                   ;;;;;;;;;;;;;;;;;;;;
	                                   ;;;;;;;;;;;;;;;;
	
	                               		
	                                ;;;;;;;;;;;;;;
                                    ;;;;;;;;;;;;;;
	private final int URLAvailableMsg       = 1;private final int URLUnAvailableMsg     = 2;
	private final int networkUnAvailableMsg = 3;private final int URLAddressNotSetMsg   = 4;
                                    private String 
                                    URLAddress;;;;
	                                ;;;;;;;private 
	                                ScrollView niuBSv;
	          private TextView currentURLAddressTextView;;;;;;;;;;;;;;;;;
	          private TextView URLAddressTextView;;;;;;;;;;;;;;;;;;;;;;;;
	          private TextView niuBEffectText;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	          private Button                                 startButton;
	          private boolean                                testing;;;;;
	
	          
	          
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setupFullscreen();
		setContentView(R.layout.activity_system_net_test);
		getSettedURLAddress();
		initViews();
	}

	//取得已经设置好的URLAddress
	private void getSettedURLAddress(){
		URLAddress = App.getPreferenceUtils().getPreferenceStr(PreferenceUtils.URLAddressKey);
	}
		
	private String getURLAddress(){
		return URLAddress;
	}
	
	private void initViews(){
		currentURLAddressTextView = (TextView) findViewById(R.id.currentURLAddressTextView);
		URLAddressTextView = (TextView) findViewById(R.id.URLAddressTextView);
		niuBEffectText = (TextView) findViewById(R.id.niuBEffectText);
		niuBSv = (ScrollView) findViewById(R.id.niuBSv);
		URLAddressTextView.setText( URLAddress);
		
		startButton = (Button) findViewById(R.id.startButton);
		startButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!testing){
					new Thread(netRunnable).start();
				}else{
					showTips(R.string.tipsServerTesting);
				}
			}
		});
		
		//服务器地址未设置隐藏UI
		if( TextUtils.isEmpty(URLAddress)){
			showTips(R.string.tipsServerURLNotSet);
			currentURLAddressTextView.setVisibility(View.INVISIBLE);
			URLAddressTextView.setVisibility(View.INVISIBLE);
			startButton.setVisibility(View.INVISIBLE);
			return;
		}
	}
	
	//检测网络状态 Runnable
	private Runnable netRunnable = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			testing = true;
			checkServer();
			testing = false;
		}
	
	};
	
	//检测服务器地址
	private void checkServer(){
				
		if( !networkAvailable() ){
			netHandler.sendEmptyMessage(networkUnAvailableMsg);
			return;
		}
		
		showNiuBEffect();
	}
	
	//更新UI Hanlder
	private Handler netHandler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			if(msg.what == URLUnAvailableMsg){
				showTips(R.string.tipsURLUnAvailable);
			}
			if(msg.what == URLAvailableMsg){
				showTips(R.string.tipsURLAvailable);
			}
			if(msg.what == networkUnAvailableMsg){
				showTips(R.string.tipsNetworkUnReachable$_$);
			}
		}
	};
	
	//处理输入URL
	private String processURL(String url){
		String processedUrl = "";
		if(!url.startsWith("http://")){
			processedUrl = "http://" + url;
		}else{
			processedUrl = url;
		}
		return processedUrl;
	}
	
	//检测URL有效:方法1
	private boolean checkURL3(String url){ 
        try {  
            URL urll = new URL(processURL(url));  
            InputStream in = urll.openStream();  
            return true;
        }catch (Exception e1) {    
            return false;
        } 	
	}	
	
	//检测URL有效:方法2
	private boolean checkURL2(String url){
		boolean value=false;
		try {
			HttpURLConnection conn=(HttpURLConnection)new URL( processURL(url) ).openConnection();
			int code=conn.getResponseCode();
			if(code!=200){
				value=false;
			}else{
				value=true;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	//检测URL有效:方法3
	private boolean checkURL(String url){
		HttpGet getMethod = new HttpGet( processURL(url) ); 
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 2*1000);
		HttpConnectionParams.setSoTimeout(httpParams, 2*1000);
		HttpClient httpClient = new DefaultHttpClient(httpParams);  
		boolean result = false;
		try {
		    HttpResponse response = httpClient.execute(getMethod);   
		    int code = response.getStatusLine().getStatusCode();
		    Log.d(TAG,"code:" + code);
		    if(code == 200){
		    	result = true;
		    }
		} catch (Exception e) {
		    e.printStackTrace();  
		} 
		return result;
	}
	
	//检测网络状态
	private boolean networkAvailable(){
		ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if(networkInfo != null && networkInfo.isConnected())  return true;
		return false;	
	}
	
    private void showTips(int whichTips){
    	LayoutInflater inflater = getLayoutInflater();

    	View tipsLayout = inflater.inflate(R.layout.inventory_refresh_tips, 
    			(ViewGroup)this.findViewById(R.id.inventoryRefreshTipsLayout));
    	TextView tipsText = (TextView) tipsLayout.findViewById(R.id.inventoryRefreshingTipsText);
    	tipsText.setText(whichTips);
    	
    	new AlertDialog.Builder(this)
    		.setTitle(getString(R.string.tipsDataDownload))
    		.setView(tipsLayout)
    		.setPositiveButton(getString(R.string.confirm),null)
    		.show();
    	
    }
     
	private void showNiuBEffect(){

		if( checkURL2( getURLAddress() ) ){
			niuBColorHandler.sendEmptyMessage('G');
		}else{
			niuBColorHandler.sendEmptyMessage('R');
		}
		
		svBgColorHandler.sendEmptyMessage('B');
		for(int i = 0;i < 200;i ++){
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			niuBHandler.sendEmptyMessage('X');
		}
		svBgColorHandler.sendEmptyMessage('W');

		if( !networkAvailable() ){
			netHandler.sendEmptyMessage(networkUnAvailableMsg);
			return;
		}
		
		if( checkURL2( getURLAddress() ) ){
			netHandler.sendEmptyMessage(URLAvailableMsg);
		}else{
			netHandler.sendEmptyMessage(URLUnAvailableMsg);
		}
	}
	
	public String getRandomString(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			if ("char".equalsIgnoreCase(charOrNum)) {
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}

	private Handler niuBColorHandler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			if(msg.what == 'G'){
				niuBEffectText.setTextColor(android.graphics.Color.GREEN);				
			}else if(msg.what == 'R'){
				niuBEffectText.setTextColor(android.graphics.Color.RED);
			}
		}
	};
	
	private Handler svBgColorHandler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			if(msg.what == 'B'){
				niuBSv.setBackgroundColor(android.graphics.Color.BLACK);				
			}else if(msg.what == 'W'){
				niuBEffectText.setText("");;
				niuBSv.setBackgroundColor(android.graphics.Color.WHITE);
			}
		}
	};

	private void scroll(){
        int offset = niuBEffectText.getMeasuredHeight() - niuBSv.getMeasuredHeight();
        if (offset < 0) {
                offset = 0;
        }
        niuBSv.scrollTo(0, offset);
	}
		
	
																		  ;;;;private Handler niuBHandler = new Handler()
									  {;;;;     						  ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	           ;;;;    				  ;;;;;								  ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	          ;;;;   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;		      ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	         ;;;;   public void handleMessage(Message msg){;		      ;;;;;;;;;;;;;;;;;          ;;;;;;;;;;;;;;;;;;;;;;;;;
	        ;;;;                      ;;;;;                               ;;;;;;;;;;;;;;;;           ;;;;;;;;;;;;;;;;;;;;;;;;;;
	                ;;;;;;super.handleMessage(msg);;;;;;;;;;;;;           ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	   niuBEffectText.setText(niuBEffectText.getText() + "\n" +           getRandomString(new Random().nextInt(100)));;;;;;;;
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;           ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	                                  ;;;;;								  ;;;;;;;;;;;;;;;;;;;;;
	                       	          ;;;;;							      ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	                       	          ;;;;;							      ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	                       	          ;;;;;							      ;;;;;;;;;;;;;;;;           ;;;;;;;;;;;;;;;;;;;;;;;;
	                       	          ;;;;;				                  ;;;;;;;;;;;;;;;;;          ;;;;;;;;;;;;;;;;;;;;;;;;;
	                       	          ;;;;;							      ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	                       	          ;;;;;							      ;;;;;;;;;;;;;;;;;;;scroll();;;;;;;;;;;;;;;;;;;;}};
			
		
		                        	
}
