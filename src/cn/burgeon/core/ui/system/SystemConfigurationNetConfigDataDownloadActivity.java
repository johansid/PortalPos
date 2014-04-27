package cn.burgeon.core.ui.system;

import cn.burgeon.core.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class SystemConfigurationNetConfigDataDownloadActivity extends Activity {


	private CheckBox mUserData;
	private CheckBox mProductData;
	private CheckBox mVipType;
	private CheckBox mItemStrategy;
	private CheckBox mParam;
	
	private Button mDownload;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.system_management_download);
		
		initViews();
	}
	
	private void initViews() {
		mUserData = (CheckBox) findViewById(R.id.user_data);
		mProductData = (CheckBox) findViewById(R.id.product_data);
		mVipType = (CheckBox) findViewById(R.id.vip_type);
		mItemStrategy = (CheckBox) findViewById(R.id.item_strategy);
		mParam = (CheckBox) findViewById(R.id.param);
		
		mDownload = (Button) findViewById(R.id.download);
		
		mDownload.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
	
			}
		});

	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
	}
}
