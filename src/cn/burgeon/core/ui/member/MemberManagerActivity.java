package cn.burgeon.core.ui.member;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import cn.burgeon.core.Constant;
import cn.burgeon.core.R;
import cn.burgeon.core.adapter.MemberManagerAdapter;
import cn.burgeon.core.ui.BaseActivity;

public class MemberManagerActivity extends BaseActivity {
	private GridView memberGV;
	private MemberManagerAdapter mAdapter;
	private TextView storeTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupFullscreen();
		setContentView(R.layout.activity_member_manager);

		init();
		initStoreData(storeTV);
	}

	private void init() {
		storeTV = (TextView) findViewById(R.id.storeTV);
		memberGV = (GridView) findViewById(R.id.memberGV);
		mAdapter = new MemberManagerAdapter(this);
		memberGV.setAdapter(mAdapter);
		memberGV.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String itemValue = (String) parent.getItemAtPosition(position);
				if (itemValue != null
						&& Constant.memberManagerTextValues[0].equals(itemValue)) {
					forwardActivity(MemberRegistActivity.class);
				} else if (itemValue != null
						&& Constant.memberManagerTextValues[1].equals(itemValue)) {
					forwardActivity(MemberListActivity.class);
				} else if (itemValue != null
						&& Constant.memberManagerTextValues[2].equals(itemValue)) {
				}else if (itemValue != null
						&& Constant.memberManagerTextValues[2].equals(itemValue)) {
				}
			}
		});
	}
}
