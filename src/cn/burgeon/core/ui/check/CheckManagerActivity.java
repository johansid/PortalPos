package cn.burgeon.core.ui.check;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import cn.burgeon.core.App;
import cn.burgeon.core.Constant;
import cn.burgeon.core.R;
import cn.burgeon.core.adapter.CheckManagerAdapter;
import cn.burgeon.core.ui.BaseActivity;
import cn.burgeon.core.utils.PreferenceUtils;

public class CheckManagerActivity extends BaseActivity {

	private GridView checkGV;
	private CheckManagerAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupFullscreen();
		setContentView(R.layout.activity_check_manager);

		init();
	}

	private void init() {
        // 初始化门店信息
        TextView storeTV = (TextView) findViewById(R.id.storeTV);
        storeTV.setText(App.getPreferenceUtils().getPreferenceStr(PreferenceUtils.store_key));

		checkGV = (GridView) findViewById(R.id.checkGV);
		mAdapter = new CheckManagerAdapter(this);
		checkGV.setAdapter(mAdapter);
		checkGV.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String itemValue = (String) parent.getItemAtPosition(position);
				if (itemValue != null && Constant.checkManagerTextValues[0].equals(itemValue)) {
					forwardActivity(CheckScanActivity.class);
				} else if (itemValue != null && Constant.checkManagerTextValues[1].equals(itemValue)) {
					forwardActivity(CheckDocManagerActivity.class);
				} else if (itemValue != null && Constant.checkManagerTextValues[2].equals(itemValue)) {
					forwardActivity(CheckQueryActivity.class);
				}
			}
		});
	}
}
