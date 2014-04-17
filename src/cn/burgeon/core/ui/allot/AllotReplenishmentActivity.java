package cn.burgeon.core.ui.allot;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import cn.burgeon.core.Constant;
import cn.burgeon.core.R;
import cn.burgeon.core.adapter.AllotManagerAdapter;
import cn.burgeon.core.adapter.ReplenishmentAdapter;
import cn.burgeon.core.ui.BaseActivity;

public class AllotReplenishmentActivity extends BaseActivity {

    private GridView replenishmentGV;
    private ReplenishmentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_replenishment);

        init();
    }

    private void init() {
        replenishmentGV = (GridView) findViewById(R.id.replenishmentGV);
        mAdapter = new ReplenishmentAdapter(this);
        replenishmentGV.setAdapter(mAdapter);
        replenishmentGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = (String) parent.getItemAtPosition(position);
                if (itemValue != null && Constant.replenishmentTextValues[0].equals(itemValue)) {
                    forwardActivity(AllotReplenishmentApplyActivity.class);
                } else if (itemValue != null && Constant.replenishmentTextValues[2].equals(itemValue)) {
                    forwardActivity(AllotReplenishmentQueryActivity.class);
                }
            }
        });
    }
}
