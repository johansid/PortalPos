package cn.burgeon.core.ui.allot;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import cn.burgeon.core.Constant;
import cn.burgeon.core.R;
import cn.burgeon.core.adapter.AllotManagerAdapter;
import cn.burgeon.core.ui.BaseActivity;

import static android.widget.AdapterView.*;

public class AllotManagerActivity extends BaseActivity {

    private GridView allotGV;
    private AllotManagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_allot_manager);

        init();
    }

    private void init() {
        allotGV = (GridView) findViewById(R.id.allotGV);
        mAdapter = new AllotManagerAdapter(this);
        allotGV.setAdapter(mAdapter);
        allotGV.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = (String) parent.getItemAtPosition(position);
                if (itemValue != null && Constant.allotManagerTextValues[0].equals(itemValue)) {
                    forwardActivity(AllotOutInActivity.class);
                } else if (itemValue != null && Constant.allotManagerTextValues[1].equals(itemValue)) {
                    forwardActivity(ReplenishmentActivity.class);
                } else if (itemValue != null && Constant.allotManagerTextValues[2].equals(itemValue)) {
                    forwardActivity(ReplenishmentOrderActivity.class);
                }
            }
        });
    }
}
