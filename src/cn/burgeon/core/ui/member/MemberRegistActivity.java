package cn.burgeon.core.ui.member;

import cn.burgeon.core.R;
import cn.burgeon.core.ui.BaseActivity;
import android.os.Bundle;

public class MemberRegistActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupFullscreen();
		setContentView(R.layout.activity_member_regist);
	}
}
