package cn.burgeon.core.ui.allot;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import cn.burgeon.core.R;
import cn.burgeon.core.adapter.ReplenishmentAdapter;
import cn.burgeon.core.adapter.ReplenishmentOrderAdapter;
import cn.burgeon.core.ui.BaseActivity;

public class ReplenishmentOrderActivity extends BaseActivity {

    private GridView replenishmentOrderGV;
    private ReplenishmentOrderAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_replenishment_order);

        init();
    }

    private void init() {
        replenishmentOrderGV = (GridView) findViewById(R.id.replenishmentOrderGV);
        mAdapter = new ReplenishmentOrderAdapter(this);
        replenishmentOrderGV.setAdapter(mAdapter);
        replenishmentOrderGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = (String) parent.getItemAtPosition(position);
            }
        });
    }
}
