package cn.burgeon.core.ui.system;

import android.os.Bundle;
import cn.burgeon.core.R;
import cn.burgeon.core.ui.BaseActivity;

public class SystemDataCleanActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setupFullscreen();
		setContentView(R.layout.activity_system_data_clean);		
	}
}
