package cn.burgeon.core.ui.system;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import cn.burgeon.core.App;
import cn.burgeon.core.R;
import cn.burgeon.core.ui.BaseActivity;
import cn.burgeon.core.utils.PreferenceUtils;

public class SystemDataCleanActivity extends BaseActivity{
	private CheckBox mUserData;
	private CheckBox mProductData;
	private CheckBox mVipType;
	private CheckBox mItemStrategy;
	private CheckBox mSystemParam;
	private TextView statusStoreName;
	private TextView statusTime;
	
	private Button mDownloadButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setupFullscreen();
		setContentView(R.layout.activity_system_data_clean);	
		
		init();
	}
	
	private void init(){
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
			public void onClick(View v) {
	
			}
		});		
		
	}

    // 初始化门店信息
	private void initStoreNameAndTime(){
		statusStoreName.setText(App.getPreferenceUtils().getPreferenceStr(PreferenceUtils.store_key));
		statusTime.setText(getCurrDate());				
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
	}
}
