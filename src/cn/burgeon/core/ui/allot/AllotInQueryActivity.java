package cn.burgeon.core.ui.allot;

import android.os.Bundle;
import cn.burgeon.core.R;
import cn.burgeon.core.ui.BaseActivity;

public class AllotInQueryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_allot_in_query);
    }
}
