package cn.burgeon.core.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import cn.burgeon.core.Constant;
import cn.burgeon.core.R;
import cn.burgeon.core.adapter.AllotManagerAdapter;
import cn.burgeon.core.adapter.SystemAdapter;
import cn.burgeon.core.ui.allot.AllotManagerActivity;

public class SystemActivity extends BaseActivity {

    private GridView sysGV;
    private SystemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_system);

        init();
    }

    private void init() {
        sysGV = (GridView) findViewById(R.id.sysGV);
        mAdapter = new SystemAdapter(this);
        sysGV.setAdapter(mAdapter);
        sysGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = (String) parent.getItemAtPosition(position);
                if (itemValue != null && Constant.sysTextValues[2].equals(itemValue)) {
                    forwardActivity(AllotManagerActivity.class);
                }
            }
        });
    }
}
