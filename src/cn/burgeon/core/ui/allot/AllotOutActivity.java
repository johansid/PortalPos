package cn.burgeon.core.ui.allot;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.burgeon.core.R;
import cn.burgeon.core.ui.BaseActivity;

public class AllotOutActivity extends BaseActivity {
    private Button addBtn, queryBtn, delBtn, backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_allot_out);

        init();
    }

    private void init() {
        addBtn = (Button) findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new ClickEvent());
        queryBtn = (Button) findViewById(R.id.queryBtn);
        delBtn = (Button) findViewById(R.id.delBtn);
        backBtn = (Button) findViewById(R.id.backBtn);
    }

    class ClickEvent implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.addBtn:
                    forwardActivity(AllotOutApplyActivity.class);
                    break;
            }
        }
    }
}
