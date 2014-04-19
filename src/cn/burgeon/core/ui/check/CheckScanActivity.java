package cn.burgeon.core.ui.check;

import android.os.Bundle;
import cn.burgeon.core.R;
import cn.burgeon.core.ui.BaseActivity;

public class CheckScanActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupFullscreen();
		setContentView(R.layout.activity_check_scan);
	}

}
