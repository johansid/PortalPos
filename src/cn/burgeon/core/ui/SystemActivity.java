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
import cn.burgeon.core.ui.inventory.InventoryManagerActivity;
import cn.burgeon.core.ui.member.MemberManagerActivity;
import cn.burgeon.core.ui.sales.SalesManagerActivity;

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
                }else if (itemValue != null && Constant.sysTextValues[0].equals(itemValue)) {
                    forwardActivity(SalesManagerActivity.class);
                }else if (itemValue != null && Constant.sysTextValues[4].equals(itemValue)) {
                    forwardActivity(InventoryManagerActivity.class);
                }else if (itemValue != null && Constant.sysTextValues[1].equals(itemValue)) {
                    forwardActivity(MemberManagerActivity.class);
                }
            }
        });
    }
}
