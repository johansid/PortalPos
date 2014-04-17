package cn.burgeon.core.ui.allot;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.burgeon.core.R;
import cn.burgeon.core.ui.BaseActivity;

public class AllotInActivity extends BaseActivity {

    private Button detailBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_allot_in);

        init();
    }

    private void init() {
        detailBtn = (Button) findViewById(R.id.detailBtn);
        detailBtn.setOnClickListener(new ClickEvent());
    }

    class ClickEvent implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.detailBtn:
                    forwardActivity(AllotInDetailActivity.class);
                    break;
            }
        }
    }
}
